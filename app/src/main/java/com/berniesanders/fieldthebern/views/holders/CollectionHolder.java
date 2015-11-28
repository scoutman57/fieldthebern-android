package com.berniesanders.fieldthebern.views.holders;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import com.berniesanders.fieldthebern.R;
import com.berniesanders.fieldthebern.adapters.BaseViewHolder;
import com.berniesanders.fieldthebern.models.Collection;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 *
 */
public class CollectionHolder extends BaseViewHolder<Collection> {

    @Bind(R.id.img)
    ImageView imageView;

    @Bind(R.id.txt)
    TextView textView;

    CollectionHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        itemView.setOnClickListener(this);
    }

    @Override
    public void setModel(final Collection model) {
        super.setModel(model);

        Picasso.with(imageView.getContext())
                .load(model.getImageUrlThumb())
                .into(imageView);

        textView.setText(model.getTitle());
    }
}