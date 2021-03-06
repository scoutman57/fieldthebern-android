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

package com.berniesanders.fieldthebern.controllers;

/**
 *
 */

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.StringRes;
import android.support.v7.app.AppCompatActivity;
import com.berniesanders.fieldthebern.R;
import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;
import mortar.Presenter;
import mortar.bundler.BundleService;
import rx.functions.Action0;
import timber.log.Timber;

import static mortar.bundler.BundleService.getBundleService;

/**
 * Allows access to displaying dialogs from presenters and views
 * <p>
 * Example usage:
 * <p><pre>
 * {@code
 * if (!DialogService.get(getView()).isShowing()) {
 *     DialogService.
 *             get(getView())
 *             .setDialogConfig(
 *                     new DialogController.DialogConfig(
 *                             R.string.app_name,
 *                             R.string.back_to_map)
 *                             .withActions(new DialogController.DialogAction()
 *                                     .label(R.string.last_name)
 *                                     .action(new Action0() {
 *                                         @Override
 *                                         public void call() {
 *                                             Timber.v("click...?");
 *                                         }
 *                                     })));
 * }
 * }</pre>
 */
public class DialogController extends Presenter<DialogController.Activity> {

  private DialogFragment dialogFragment;

  public interface Activity {
    AppCompatActivity getActivity();
  }

  public static class DialogConfig {
    private String title;
    private String message;
    private DialogAction[] actions;

    public DialogConfig() {
    }

    public String title() {
      return this.title;
    }

    public String message() {
      return this.message;
    }

    public DialogConfig title(final String title) {
      this.title = title;
      return this;
    }

    public DialogConfig message(final String message) {
      this.message = message;
      return this;
    }

    public DialogConfig withActions(DialogAction... actions) {
      this.actions = actions;
      return this;
    }

    public boolean isSingleButton() {
      return actions.length == 1;
    }
  }

  public static class DialogAction {
    private int label;
    private Action0 action;

    public DialogAction label(@StringRes int label) {
      this.label = label;
      return this;
    }

    public DialogAction action(Action0 action) {
      this.action = action;
      return this;
    }

    public DialogAction() {
    }

    public int label() {
      return label;
    }

    public Action0 action() {
      return action;
    }
  }

  static private DialogConfig dialogConfig;
  private static final String DIALOG_TAG = "dialogTag";

  DialogController() {
  }

  @Override
  public void onLoad(Bundle savedInstanceState) {
    update();
  }

  @Override
  public void dropView(Activity view) {
    super.dropView(view);
  }

  public void setDialogConfig(DialogConfig dialogConfig) {
    DialogController.dialogConfig = dialogConfig;
    update();
  }

  @Override
  protected BundleService extractBundleService(Activity activity) {
    return getBundleService(activity.getActivity());
  }

  private void update() {
    if (!hasView()) {
      return;
    }

    if (dialogConfig != null) {
      android.app.FragmentManager fm = getView().getActivity().getFragmentManager();
      dialogFragment = (DialogFragment) fm.findFragmentByTag(DIALOG_TAG);
      dialogFragment = (dialogFragment == null) ? new QuickDialogFragment() : dialogFragment;
      if (!dialogFragment.isAdded()) {
        dialogFragment.show(fm, DIALOG_TAG);
      }
    } else if (dialogFragment != null) {
      dialogFragment.dismissAllowingStateLoss();
    }
  }

  public boolean isShowing() {
    return (dialogFragment != null && dialogFragment.isVisible());
  }

  public static class QuickDialogFragment extends DialogFragment {

    public final DialogConfig config;

    public QuickDialogFragment() {
      super();
      config = DialogController.dialogConfig;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

      AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
      // Issue #51 in Fabric, crash due to a null DialogConfig object. If that is the case just show a basic fragment.
      // I am not sure this is what we want, but will suffice to prevent the crash, this isnt a common Error, since its happened only to 4 devices/users
      if(config != null) {
        builder.setTitle(config.title())
            .setMessage(config.message())
            .setPositiveButton(config.actions[0].label(), createClickListener(0));

        if (!config.isSingleButton()) {
          builder.setNegativeButton(config.actions[1].label(), createClickListener(1));
        }
      }
      else {
        builder.setTitle(R.string.app_name).
            setPositiveButton(R.string.continue_btn, createClickListener(0));
      }

      Dialog dialog = builder.create();
      dialog.setCanceledOnTouchOutside(false);
      return dialog;
    }

    @Override
    public void onCancel(DialogInterface dialog) {
      super.onCancel(dialog);
      DialogController.dialogConfig = null;
      Timber.v("onCancel");
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
      super.onDismiss(dialog);
      //dialogConfig = null;
      Timber.v("onDismiss");
    }

    DialogInterface.OnClickListener createClickListener(final int actionIndex) {
      return new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
          if (dialogConfig != null) {
            dialogConfig.actions[actionIndex].action().call();
            dialogConfig = null;
          }
        }
      };
    }
  }

  @Module
  public static class DialogModule {

    @Provides
    @Singleton
    DialogController provideDialogController() {
      return new DialogController();
    }
  }
}
