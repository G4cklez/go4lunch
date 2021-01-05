// Generated code from Butter Knife. Do not modify!
package com.app.go4lunch.view.activities;

import android.view.View;
import android.widget.Button;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import androidx.constraintlayout.widget.ConstraintLayout;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.app.go4lunch.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class AuthActivity_ViewBinding implements Unbinder {
  private LoginActivity target;

  private View view7f08004c;

  private View view7f08004d;

  @UiThread
  public AuthActivity_ViewBinding(LoginActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public AuthActivity_ViewBinding(final LoginActivity target, View source) {
    this.target = target;

    View view;
    target.progressBarLayout = Utils.findRequiredViewAsType(source, R.id.progress_bar_layout, "field 'progressBarLayout'", ConstraintLayout.class);
    view = Utils.findRequiredView(source, R.id.auth_activity_facebook_button, "field 'facebookButton' and method 'onClickFacebookButton'");
    target.facebookButton = Utils.castView(view, R.id.auth_activity_facebook_button, "field 'facebookButton'", Button.class);
    view7f08004c = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClickFacebookButton();
      }
    });
    view = Utils.findRequiredView(source, R.id.auth_activity_google_button, "field 'googleButton' and method 'onClickGoogleButton'");
    target.googleButton = Utils.castView(view, R.id.auth_activity_google_button, "field 'googleButton'", Button.class);
    view7f08004d = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClickGoogleButton();
      }
    });
  }

  @Override
  @CallSuper
  public void unbind() {
    LoginActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.progressBarLayout = null;
    target.facebookButton = null;
    target.googleButton = null;

    view7f08004c.setOnClickListener(null);
    view7f08004c = null;
    view7f08004d.setOnClickListener(null);
    view7f08004d = null;
  }
}
