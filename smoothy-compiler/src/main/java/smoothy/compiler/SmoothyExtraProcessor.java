package smoothy.compiler;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeSpec;

import java.io.Serializable;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;

import smoothy.BindExtra;

/**
 * Created by mehdi on 19/10/2015.
 */
public class SmoothyExtraProcessor {

    private static final String SUFFIX_CLS_MODEL = "Builder";

    private Elements mElementsUtils;

    public SmoothyExtraProcessor(Elements elements) {
        mElementsUtils = elements;
    }


    private void generateModel(ProcessingEnvironment processingEnvironment, SmoothyExtraInfos
            extraInfos) throws Exception {
        TypeElement typeElement = extraInfos.getTypeElement();
        TypeHelper typeHelper = new TypeHelper(processingEnvironment);

        boolean isFragment = typeHelper.isFragment(typeElement);
        boolean isSupportFragment = typeHelper.isSupportFragment(typeElement);

        boolean isActivity = typeHelper.isActivity(typeElement);
        boolean isService = typeHelper.isService(typeElement);

        String packageName = getPackageName(typeElement);
        String className = getClassName(typeElement);
        String modelName = className + SUFFIX_CLS_MODEL;

        ClassName buildType;
        if (isActivity || isService) {
            buildType = ClassNameUtils.getIntent();
        } else if (isFragment || isSupportFragment) {
            buildType = ClassName.get(packageName, className);
        } else {
            buildType = ClassName.get(Void.class);
        }

        TypeSpec.Builder modelBuilder = TypeSpec.classBuilder(modelName)
                .addModifiers(Modifier.PUBLIC)
                .addSuperinterface(
                        ParameterizedTypeName.get(
                                ClassName.get("smoothy", "SmoothyBundleWrapper"),
                                ClassName.get(packageName, className),
                                buildType
                        )
                )
                .addMethod(
                        MethodSpec.constructorBuilder()
                                .addModifiers(Modifier.PUBLIC)
                                .build()
                );

        MethodSpec.Builder constructorParcelBuilder = MethodSpec.constructorBuilder()
                .addParameter(ClassNameUtils.getParcel(), "parcel");

        MethodSpec.Builder parseMethodBuilder = MethodSpec.methodBuilder("parse")
                .addModifiers(Modifier.PUBLIC)
                .addParameter(ClassName.get(packageName, className), "target");

        MethodSpec.Builder buildMethodBuilder = MethodSpec.methodBuilder("build")
                .returns(buildType)
                .addModifiers(Modifier.PUBLIC)
                .addParameter(ClassNameUtils.getContext(), "context");

        if (isFragment || isSupportFragment) {
            ClassName bundleClassName = ClassName.get("android.os", "Bundle");
            buildMethodBuilder.addStatement("$T bundle = new $T()", bundleClassName, bundleClassName);
            buildMethodBuilder.addStatement("bundle.putParcelable($L, this)",
                    "SmoothyBundleWrapper.BUNDLE_KEY");
            buildMethodBuilder.addStatement("$L fragment = new $L()", className, className);
            buildMethodBuilder.addStatement("fragment.setArguments(bundle)");
            buildMethodBuilder.addStatement("return fragment");
        } else if (isActivity || isService) {
            buildMethodBuilder.addStatement("Intent intent = new Intent(context, $L.class)",
                    className);
            buildMethodBuilder.addStatement("intent.putExtra($L, this)",
                    "SmoothyBundleWrapper.BUNDLE_KEY");
            buildMethodBuilder.addStatement("return intent");
        } else {
            buildMethodBuilder.addStatement("// pas supporté");
            buildMethodBuilder.addStatement("return null");
        }


        MethodSpec.Builder writeToParcelMethodBuilder = MethodSpec.methodBuilder("writeToParcel")
                .addModifiers(Modifier.PUBLIC)
                .addParameter(ClassNameUtils.getParcel(), "parcel")
                .addParameter(int.class, "i");

        for (SmoothyExtraInfos.SmoothyEtraVariable smoothyEtraVariable : extraInfos.getPropertiesElements()) {
            VariableElement variableElement = smoothyEtraVariable.getVariableElement();
            String setterName = smoothyEtraVariable.getSetterName();
            String variableName = smoothyEtraVariable.getVariableName();
            modelBuilder.addField(
                    ClassName.get(variableElement.asType()),
                    smoothyEtraVariable.getVariableName(),
                    Modifier.PRIVATE
            );


            modelBuilder.addMethod(MethodSpec.methodBuilder(setterName)
                    .addModifiers(Modifier.PUBLIC)
                    .returns(ClassName.bestGuess(modelName))
                    .addParameter(ClassName.get(variableElement.asType()), setterName)
                    .addStatement("this.$L = $L", variableName, setterName)
                    .addStatement("return this")
                    .build());

            parseMethodBuilder.addStatement("target.$L = this.$L", variableName, variableName);

            if (typeHelper.isClass(variableElement, String.class)) {
                writeToParcelMethodBuilder.addStatement("parcel.writeString($L)", variableName);
                constructorParcelBuilder.addStatement("this.$L = parcel.readString()", variableElement);
            } else if (typeHelper.isClass(variableElement, Integer.class)) {
                writeToParcelMethodBuilder.addStatement("parcel.writeInt($L)", variableName);
                constructorParcelBuilder.addStatement("this.$L = parcel.readInt()", variableElement);
            } else if (typeHelper.isClass(variableElement, Long.class)) {
                writeToParcelMethodBuilder.addStatement("parcel.writeLong($L)", variableName);
                constructorParcelBuilder.addStatement("this.$L = parcel.readLong()", variableElement);
            } else if (typeHelper.isClass(variableElement, Float.class)) {
                writeToParcelMethodBuilder.addStatement("parcel.writeFloat($L)", variableName);
                constructorParcelBuilder.addStatement("this.$L = parcel.readFloat()", variableElement);
            } else if (typeHelper.isClass(variableElement, Double.class)) {
                writeToParcelMethodBuilder.addStatement("parcel.writeDouble($L)", variableName);
                constructorParcelBuilder.addStatement("this.$L = parcel.readDouble()", variableElement);
            } else if (typeHelper.isParcelable(variableElement)) {
                writeToParcelMethodBuilder.addStatement("parcel.writeParcelable($L, 0)", variableName);
                constructorParcelBuilder.addStatement("this.$L = parcel.readParcelable($L.class.getClassLoader())",
                        variableName, getClassName(processingEnvironment.getTypeUtils().asElement(variableElement.asType())));
            } else if (typeHelper.isClass(variableElement, Serializable.class)) {
                writeToParcelMethodBuilder.addStatement("parcel.writeSerializable($L)", variableName);
                constructorParcelBuilder.addStatement("this.$L = ($L) parcel.readSerializable()",
                        variableElement, ClassName.get(variableElement.asType()));
            } else if (typeHelper.isListParcelable(variableElement)) {
                writeToParcelMethodBuilder.addStatement("parcel.writeList($L)", variableName);
                constructorParcelBuilder.addStatement("this.$L = new $L()", variableElement,
                        ClassName.get(ArrayList.class));

                if (variableElement.asType() instanceof DeclaredType) {
                    DeclaredType declaredType = (DeclaredType) variableElement.asType();

                    List<? extends TypeMirror> typeArguments = declaredType.getTypeArguments();
                    if (!typeArguments.isEmpty()) {
                        constructorParcelBuilder.addStatement("parcel.readList($L, $L.class.getClassLoader())",
                                variableElement, ClassName.get(typeArguments.get(0)));
                    }

                }
            }

        }

        modelBuilder.addMethod(constructorParcelBuilder.build());
        modelBuilder.addMethod(writeToParcelMethodBuilder.build());
        modelBuilder.addMethod(parseMethodBuilder.build());
        modelBuilder.addMethod(buildMethodBuilder.build());

        modelBuilder.addMethod(MethodSpec.methodBuilder("describeContents")
                .addModifiers(Modifier.PUBLIC)
                .addStatement("return 0")
                .returns(int.class)
                .build());

        modelBuilder.addField(
                FieldSpec.builder(
                        ParameterizedTypeName.get(ClassNameUtils.getParcelableCreator()
                                , ClassName.get(packageName, modelName)
                        ), "CREATOR")
                        .addModifiers(Modifier.PUBLIC, Modifier.STATIC, Modifier.FINAL)
                        .initializer(CodeBlock.builder()
                                        .add("new Parcelable.Creator<$L>() { $L", modelName, System.getProperty("line.separator"))
                                        .add("  public $L createFromParcel(Parcel source) { $L", modelName, System.getProperty("line.separator"))
                                        .addStatement("     return new $L(source)", modelName)
                                        .add("  } $L$L", System.getProperty("line.separator"), System.getProperty("line.separator"))
                                        .add("  public $L[] newArray(int size) { $L", modelName, System.getProperty("line.separator"))
                                        .addStatement("     return new $L[size]", modelName)
                                        .add("  } $L", System.getProperty("line.separator"))
                                        .add("}")
                                        .build()
                        ).build());


        JavaFile modelJavaFile = JavaFile.builder(packageName, modelBuilder.build()).build();
        JavaFileObject modelJavaFileObject = processingEnvironment.getFiler().createSourceFile(packageName + "." + modelName);
        Writer modelSourceWriter = modelJavaFileObject.openWriter();
        modelSourceWriter.append(modelJavaFile.toString());
        modelSourceWriter.close();
    }

    public void process(RoundEnvironment roundEnvironment, ProcessingEnvironment processingEnvironment) {
        try {
            Set<? extends Element> elementsAnnotatedWith = roundEnvironment.getElementsAnnotatedWith(BindExtra.class);
            Map<String, SmoothyExtraInfos> infosMap = new HashMap<>();
            for (Element element : elementsAnnotatedWith) {
                String typeClassName = getFullName(element);
                SmoothyExtraInfos extraInfos;
                if (infosMap.containsKey(typeClassName)) {
                    extraInfos = infosMap.get(typeClassName);
                } else {
                    TypeElement typeElement = (TypeElement) element.getEnclosingElement();
                    extraInfos = new SmoothyExtraInfos(typeElement);
                    infosMap.put(typeClassName, extraInfos);
                }
                extraInfos.addVariable((VariableElement) element);
            }

            for (String key : infosMap.keySet()) {
                log(processingEnvironment, "Clé : " + key);
                SmoothyExtraInfos infos = infosMap.get(key);
                generateModel(processingEnvironment, infos);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String getPackageName(TypeElement element) throws Exception {
        PackageElement packageElement = mElementsUtils.getPackageOf(element);
        if (packageElement.isUnnamed()) {
            throw new Exception("Aucun package est indiqué pour " + element.getSimpleName());
        }
        return packageElement.getQualifiedName().toString();
    }

    private String getPackageName(Element element) throws Exception {
        PackageElement packageElement = mElementsUtils.getPackageOf(element);
        if (packageElement.isUnnamed()) {
            throw new Exception("Aucun package est indiqué pour " + element.getSimpleName());
        }
        return packageElement.getQualifiedName().toString();
    }


    private String getClassName(Element element) {
        return element.getSimpleName().toString();
    }

    private String getFullName(Element element) throws Exception {
        String packageName = getPackageName(element);
        String className = getClassName(element.getEnclosingElement());
        return packageName + "." + className;
    }

    private void log(ProcessingEnvironment environment, String msg) {
        environment.getMessager().printMessage(Diagnostic.Kind.NOTE, msg);
    }
}
