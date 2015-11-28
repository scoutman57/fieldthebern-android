package com.berniesanders.fieldthebern.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.berniesanders.fieldthebern.R;
import com.berniesanders.fieldthebern.annotations.Layout;

import java.util.ArrayList;
import java.util.List;

/**
 * TODO: delay implementation until implmenting all other content items
 */
@Layout(R.layout.row_nav)
public class Nav extends Content implements Parcelable, Linkable {
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
        dest.writeTypedList(links);
    }

    public Nav() {
    }

    protected Nav(Parcel in) {
        super(in);
        this.anchors = new ArrayList<Anchor>();
        in.readList(this.anchors, List.class.getClassLoader());
        this.links = in.createTypedArrayList(Link.CREATOR);
    }

    public static final Parcelable.Creator<Nav> CREATOR = new Parcelable.Creator<Nav>() {
        public Nav createFromParcel(Parcel source) {
            return new Nav(source);
        }

        public Nav[] newArray(int size) {
            return new Nav[size];
        }
    };
}