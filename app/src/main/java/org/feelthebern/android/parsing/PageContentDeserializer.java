package org.feelthebern.android.parsing;

import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import org.feelthebern.android.models.Content;
import org.feelthebern.android.models.H1;
import org.feelthebern.android.models.H2;
import org.feelthebern.android.models.H3;
import org.feelthebern.android.models.Iframe;
import org.feelthebern.android.models.Img;
import org.feelthebern.android.models.List;
import org.feelthebern.android.models.Nav;
import org.feelthebern.android.models.P;
import org.feelthebern.android.models.Quote;
import org.feelthebern.android.models.Video;

import java.lang.reflect.Type;

/**
 * Custom Gson deserializer for page 'content' to deserialize items into 'h1' 'p' etc
 *
 * TODO: this could use some unit tests
 * TODO: We'll need to do something similar with page.content json arrays of 'h1' 'h2' 'p' etc
 */
public class PageContentDeserializer implements JsonDeserializer<Content> {


    @Override
    public Content deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {

        Gson gson = new Gson(); //use a temp gson so we don't trigger a stack overflow.
        Content untypedContent = gson.fromJson(json, Content.class);
        Content typedContent = null;
        switch (untypedContent.getType()) {
            case "h1":
                typedContent = context.deserialize(json, H1.class);
                break;
            case "h2":
                typedContent = context.deserialize(json, H2.class);
                break;
            case "h3":
                typedContent = context.deserialize(json, H3.class);
                break;
            case "p":
                typedContent = context.deserialize(json, P.class);
                break;
            case "img":
                typedContent = context.deserialize(json, Img.class);
                break;
            case "nav":
                typedContent = context.deserialize(json, Nav.class);
                break;
            case "video":
                typedContent = context.deserialize(json, Video.class);
                break;
            case "quote":
                typedContent = context.deserialize(json, Quote.class);
                break;
            case "list":
                typedContent = context.deserialize(json, List.class);
                break;
            case "iframe":
                typedContent = context.deserialize(json, Iframe.class);
                break;
            default:
                throw new JsonParseException("unknown type:"+untypedContent.getType());
        }

        return typedContent;
    }
}
