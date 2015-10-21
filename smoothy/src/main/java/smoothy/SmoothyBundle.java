package smoothy;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;

/**
 * Created by mehdi on 20/10/2015.
 */
public class SmoothyBundle {

    private SmoothyBundle() {

    }

    public static <T extends Activity> void bind(T activity) {
        if (activity == null) {
            return;
        }

        Intent intent = activity.getIntent();
        Bundle bundle = null;
        if (intent != null) {
            bundle = intent.getExtras();
        }
        bindTarget(activity, bundle);
    }

    public static void bind(Fragment fragment) {
        if (fragment == null) {
            return;
        }

        bindTarget(fragment, fragment.getArguments());
    }

    public static void bind(android.support.v4.app.Fragment fragment) {
        if (fragment == null) {
            return;
        }

        bindTarget(fragment, fragment.getArguments());
    }

    private static  <T, U> void bindTarget(T target, Bundle args) {
        SmoothyBundleWrapper<T, U> wrapper = getWrapper(args);
        if (wrapper != null) {
            wrapper.parse(target);
        }
    }

    private static <T, U> SmoothyBundleWrapper<T, U> getWrapper(Bundle bundle) {
        if (bundle != null && bundle.containsKey(SmoothyBundleWrapper.BUNDLE_KEY)) {
            return bundle.getParcelable(SmoothyBundleWrapper.BUNDLE_KEY);
        }

        return null;
    }

}
