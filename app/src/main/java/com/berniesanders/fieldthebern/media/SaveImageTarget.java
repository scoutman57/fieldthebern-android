/*
 * Copyright (c) 2016 - Bernie 2016, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.berniesanders.fieldthebern.media;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.util.Base64;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import timber.log.Timber;

public class SaveImageTarget implements Target {

  public interface OnLoad {
    void onLoad(Bitmap bitmap, String encodedString);
  }

  private final OnLoad callback;
  private final Context context;

  public SaveImageTarget(OnLoad callback, Context context) {

    this.callback = callback;
    this.context = context;
  }

  /**
   * Callback when an image has been successfully loaded.
   * <p/>
   * <strong>Note:</strong> You must not recycle the bitmap.
   */
  @Override
  public void onBitmapLoaded(final Bitmap bitmap, Picasso.LoadedFrom from) {
    Timber.v("onBitmapLoaded %s", from.toString());

    String encodedString = base64EncodeBitmap(bitmap, context);

    callback.onLoad(bitmap, encodedString);
  }

  /**
   * TODO this doesnt seem to work correctly with the API.
   * It does seem to correctly convert a bitmap to a base64 string, but for some reason
   * the API does not understand the data
   * TODO this should maybe be move to it's own class
   */

  public static String base64EncodeBitmap(final Bitmap bitmap, Context context) {

    File originalFile = SavePhoto.saveBitmap(bitmap, context);
    InputStream inputStream = null;//You can get an inputStream using any IO API
    try {
      inputStream = new FileInputStream(originalFile.getAbsolutePath());
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
    byte[] bytes;
    byte[] buffer = new byte[8192];
    int bytesRead;
    ByteArrayOutputStream output = new ByteArrayOutputStream();
    try {
      while ((bytesRead = inputStream.read(buffer)) != -1) {
        output.write(buffer, 0, bytesRead);
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    bytes = output.toByteArray();
    String encodedString = Base64.encodeToString(bytes, Base64.NO_WRAP);
    Timber.v("encodedString " + encodedString);
    return encodedString;
  }

  /**
   * Callback indicating the image could not be successfully loaded.
   * <p/>
   * <strong>Note:</strong> The passed {@link Drawable} may be {@code null} if none has been
   * specified via {@link com.squareup.picasso.RequestCreator#error(Drawable)}
   * or {@link com.squareup.picasso.RequestCreator#error(int)}.
   */
  @Override
  public void onBitmapFailed(Drawable errorDrawable) {
    Timber.w("onBitmapFailed");
  }

  /**
   * Callback invoked right before your request is submitted.
   * <p/>
   * <strong>Note:</strong> The passed {@link Drawable} may be {@code null} if none has been
   * specified via {@link com.squareup.picasso.RequestCreator#placeholder(Drawable)}
   * or {@link com.squareup.picasso.RequestCreator#placeholder(int)}.
   */
  @Override
  public void onPrepareLoad(Drawable placeHolderDrawable) {

  }
}
