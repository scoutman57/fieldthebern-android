package com.berniesanders.canvass.views.holders;

import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.TextView;

import com.berniesanders.canvass.R;
import com.berniesanders.canvass.adapters.BaseViewHolder;
import com.berniesanders.canvass.models.Linkable;

import butterknife.Bind;
import butterknife.ButterKnife;

import static com.berniesanders.canvass.parsing.Linky.hasLinks;
import static com.berniesanders.canvass.parsing.Linky.linkify;

/**
 *
 */
public class LinkableTextHolder<M extends Linkable> extends BaseViewHolder<M> {

    @Bind(R.id.text)
    TextView textView;

    LinkableTextHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    @Override
    public void setModel(final M model) {
        super.setModel(model);
        setText(model);
    }

    protected void setText(final M model) {
        if (hasLinks(model.getLinks()) && hasText(model)) {
            textView.setMovementMethod(LinkMovementMethod.getInstance());
            textView.setText(Html.fromHtml(
                    linkify(model.getLinks(),
                            model.getText())));
            ;
        } else if (hasText(model)) {
            textView.setText(model.getText());
        }
    }

    protected boolean hasText(final M model) {
        return model.getText() !=null &&
                !model.getText().isEmpty();
    }
}