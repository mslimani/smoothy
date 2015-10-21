package smoothy.compiler;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;

/**
 * Created by mehdi on 21/10/2015.
 */
public class TypeHelper {

    private ProcessingEnvironment mProcessingEnvironment;
    private Types mTypes;
    private Elements mElements;

    public TypeHelper(ProcessingEnvironment processingEnvironment) {
        mProcessingEnvironment = processingEnvironment;
        mTypes = mProcessingEnvironment.getTypeUtils();
        mElements = mProcessingEnvironment.getElementUtils();
    }

    public boolean isClass(Element element, Class<?> cls) {
        return isType(element, cls.getCanonicalName());
    }
    public boolean isFragment(Element element) {
        return isType(element, "android.app.Fragment");
    }

    public boolean isSupportFragment(Element element) {
        return isType(element, "android.support.v4.app.Fragment");
    }

    public boolean isActivity(Element element) {
        return isType(element, "android.app.Activity");
    }

    public boolean isService(Element element) {
        return isType(element, "android.app.Service");
    }

    public boolean isParcelable(Element element) {
        return isType(element, "android.os.Parcelable");
    }

    public boolean isListParcelable(Element element) {
        TypeMirror mirror = getWildcardType("java.util.List", "android.os.Parcelable");
        return mTypes.isAssignable(element.asType(), mirror);
    }

    private boolean isType(Element element, String cls) {
        return mTypes.isAssignable(element.asType(), mElements.getTypeElement(cls)
                .asType());
    }


    private TypeMirror getWildcardType(String type, String elementType) {
        Types types = mProcessingEnvironment.getTypeUtils();
        TypeElement element = mProcessingEnvironment.getElementUtils().getTypeElement(type);
        TypeMirror elType = mProcessingEnvironment.getElementUtils().getTypeElement(elementType).asType();
        return types.getDeclaredType(element, types.getWildcardType(elType, null));
    }


}
