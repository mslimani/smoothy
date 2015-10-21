package smoothy.compiler;

import com.squareup.javapoet.ClassName;

/**
 * Created by mehdi on 21/10/2015.
 */
public class ClassNameUtils {

    private ClassNameUtils() {

    }

    public static ClassName getFragment() {
        return ClassName.get("android.app", "Fragment");
    }

    public static ClassName getSupportFragment() {
        return ClassName.get("android.support.v4.app", "Fragment");
    }

    public static ClassName getActivity() {
        return ClassName.get("android.app", "Activity");
    }

    public static ClassName getIntent() {
        return ClassName.get("android.content", "Intent");
    }

    public static ClassName getParcel() {
        return ClassName.get("android.os", "Parcel");
    }

    public static ClassName getContext() {
        return ClassName.get("android.content", "Context");
    }

    public static ClassName getBundle() {
        return ClassName.get("android.os", "Bundle");
    }

    public static ClassName getParcelable() {
        return ClassName.get("android.os", "Parcelable");
    }

    public static ClassName getParcelableCreator() {
        return ClassName.get("android.os", "Parcelable", "Creator");
    }

    public static ClassName getService() {
        return ClassName.get("android.app", "Service");
    }


}
