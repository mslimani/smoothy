package smoothy;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by mehdi on 20/10/2015.
 */
public class SmoothyParcelUtils {


    //region write
    public static void write(Parcel parcel, int value) {
        parcel.writeInt(value);
    }

    public static void write(Parcel parcel, Integer value) {
        parcel.writeInt(value);
    }

    public static void write(Parcel parcel, float value) {
        parcel.writeFloat(value);
    }

    public static void write(Parcel parcel, Float value) {
        parcel.writeFloat(value);
    }


    public static void write(Parcel parcel, long value) {
        parcel.writeLong(value);
    }

    public static void write(Parcel parcel, Long value) {
        parcel.writeLong(value);
    }

    public static void write(Parcel parcel, double value) {
        parcel.writeDouble(value);
    }

    public static void write(Parcel parcel, Double value) {
        parcel.writeDouble(value);
    }

    public static void write(Parcel parcel, String value) {
        parcel.writeString(value);
    }

    public static void write(Parcel parcel, Parcelable value) {
        parcel.writeParcelable(value, 0);
    }

    public static <T extends Parcelable> void write(Parcel parcel, List<T> value) {
        parcel.writeTypedList(value);
    }

    //endregion write

}
