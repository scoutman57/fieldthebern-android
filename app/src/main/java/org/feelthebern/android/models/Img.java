package org.feelthebern.android.models;

import android.os.Parcel;
import android.os.Parcelable;

import org.feelthebern.android.R;
import org.feelthebern.android.annotations.Layout;

/**
 *
    text: "http://feelthebern.org/wp-content/uploads/2015/07/US-rates-over-time.jpg",
    type: "img",
    width: 445,
    height: 512,
    caption: "Source",
    source: "http://www.washingtonpost.com/news/wonkblog/wp/2014/04/30/the-meteoric-costly-and-unprecedented-rise-of-incarceration-in-america/"
 */
@Layout(R.layout.row_img)
public class Img extends Content implements Parcelable {

    //text here is the url of the image
    //private String text;

    private int width;
    private int height;
    private String caption;
    private String source;  //attribution source

    /*
    text: "http://feelthebern.org/wp-content/uploads/2015/07/US-rates-over-time.jpg",
    type: "img",
    width: 445,
    height: 512,
    caption: "Source",
    source: "http://www.washingtonpost.com/news/wonkblog/wp/2014/04/30/the-meteoric-costly-and-unprecedented-rise-of-incarceration-in-america/"
    */


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest,flags);
        dest.writeInt(this.width);
        dest.writeInt(this.height);
        dest.writeString(this.caption);
        dest.writeString(this.source);
    }

    public Img() {
    }

    protected Img(Parcel in) {
        super(in);
        this.width = in.readInt();
        this.height = in.readInt();
        this.caption = in.readString();
        this.source = in.readString();
    }

    public static final Parcelable.Creator<Img> CREATOR = new Parcelable.Creator<Img>() {
        public Img createFromParcel(Parcel source) {
            return new Img(source);
        }

        public Img[] newArray(int size) {
            return new Img[size];
        }
    };
}