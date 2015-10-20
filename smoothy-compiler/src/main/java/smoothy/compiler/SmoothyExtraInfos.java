package smoothy.compiler;

import android.app.Activity;
import android.app.Fragment;

import com.squareup.javapoet.ClassName;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.util.Elements;

/**
 * Created by mehdi on 19/10/2015.
 */
public class SmoothyExtraInfos {


    private TypeElement mTypeElement;
    private List<SmoothyEtraVariable> mPropertiesElements;

    public SmoothyExtraInfos(TypeElement typeElement) {
        mTypeElement = typeElement;
        mPropertiesElements = new ArrayList<>();
    }

    public TypeElement getTypeElement() {
        return mTypeElement;
    }

    public List<SmoothyEtraVariable> getPropertiesElements() {
        return mPropertiesElements;
    }

    public void addVariable(VariableElement variableElement) {
        mPropertiesElements.add(new SmoothyEtraVariable(variableElement));
    }

    private static boolean isType(ProcessingEnvironment processingEnvironment, Elements elements, Class cls, Element element) {
        TypeElement stringTypeElement = elements.getTypeElement(cls.getCanonicalName());
        if (stringTypeElement == null ) {
            return false;
        }
        return processingEnvironment.getTypeUtils().isAssignable(element.asType(), stringTypeElement.asType());
    }

    private static boolean isType(ProcessingEnvironment processingEnvironment, Elements elements, ClassName cls, Element element) {
        TypeElement stringTypeElement = elements.getTypeElement(cls.toString());
        if (stringTypeElement == null ) {
            return false;
        }
        return processingEnvironment.getTypeUtils().isAssignable(element.asType(), stringTypeElement.asType());
    }

    public boolean isFragment(ProcessingEnvironment processingEnvironment, Elements elements) {
        return isType(processingEnvironment, elements, Fragment.class, mTypeElement);
    }

    public boolean isSupportFragment(ProcessingEnvironment processingEnvironment, Elements elements) {
        ClassName className = ClassName.get("android.support.v4.app", "Fragment");
        return isType(processingEnvironment, elements, className, mTypeElement);
    }

    public boolean isActivity(ProcessingEnvironment processingEnvironment, Elements elements) {
        return isType(processingEnvironment, elements, Activity.class, mTypeElement);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Parent : ")
                .append(mTypeElement.getSimpleName());
        for (SmoothyEtraVariable propertiesElement : mPropertiesElements) {
            builder.append("\n")
                    .append("Property : ")
                    .append(propertiesElement.getVariableName())
                    .append("\n")
                    .append("Setter : ")
                    .append(propertiesElement.getSetterName());
        }
        return builder.toString();
    }

    public static class SmoothyEtraVariable {

        private VariableElement mVariableElement;

        public SmoothyEtraVariable(VariableElement variableElement) {
            mVariableElement = variableElement;
        }

        public VariableElement getVariableElement() {
            return mVariableElement;
        }

        public String getVariableName() {
            return mVariableElement.getSimpleName().toString();
        }

        public String getSetterName() {
            String name = getVariableName();
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < name.length(); i++) {
                char character = name.charAt(i);
                if (i == 0 && character == 'm') {
                    continue;
                }

                if (i == 1) {
                    builder.append(Character.toLowerCase(character));
                } else {
                    builder.append(character);
                }
            }
            return builder.toString();
        }

        public boolean isType(ProcessingEnvironment processingEnvironment, Elements elements, Class cls) {
            return SmoothyExtraInfos.isType(processingEnvironment, elements, cls, mVariableElement);
        }
    }
}
