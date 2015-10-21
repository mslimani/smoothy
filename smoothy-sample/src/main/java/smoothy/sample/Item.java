package smoothy.sample;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by mehdi on 21/10/2015.
 */
public class Item implements Parcelable {

    public String a;
    public String b;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.a);
        dest.writeString(this.b);
    }

    public Item() {
    }

    protected Item(Parcel in) {
        this.a = in.readString();
        this.b = in.readString();
    }

    public static final Parcelable.Creator<Item> CREATOR = new Parcelable.Creator<Item>() {
        public Item createFromParcel(Parcel source) {
            return new Item(source);
        }

        public Item[] newArray(int size) {
            return new Item[size];
        }
    };
}
