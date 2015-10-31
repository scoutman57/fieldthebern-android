package org.feelthebern.android.models;

import android.os.Parcel;
import android.os.Parcelable;

import org.feelthebern.android.R;
import org.feelthebern.android.annotations.Layout;
import org.feelthebern.android.views.holders.Linkable;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
@Layout(R.layout.row_h3)
public class H3 extends Content implements Parcelable, Linkable {
    private List<Anchor> anchors;
    private List<Link> links;

    public List<Anchor> getAnchors() {
        return anchors;
    }

    public List<Link> getLinks() {
        return links;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest,flags);
        dest.writeList(this.anchors);
        dest.writeList(this.links);
    }

    public H3() {
    }

    protected H3(Parcel in) {
        super(in);
        this.anchors = new ArrayList<Anchor>();
        in.readList(this.anchors, List.class.getClassLoader());
        this.links = new ArrayList<Link>();
        in.readList(this.links, List.class.getClassLoader());
    }

    public static final Parcelable.Creator<H3> CREATOR = new Parcelable.Creator<H3>() {
        public H3 createFromParcel(Parcel source) {
            return new H3(source);
        }

        public H3[] newArray(int size) {
            return new H3[size];
        }
    };
}
