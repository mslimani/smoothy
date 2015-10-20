package smoothy;

import android.content.Context;
import android.os.Parcelable;

/**
 * Created by mehdi on 19/10/2015.
 */
public interface SmoothyBundleWrapper<T, U> extends Parcelable {

    String BUNDLE_KEY = "twix_bundle_extra";
    void parse(T target);
    U build(Context context);

}
