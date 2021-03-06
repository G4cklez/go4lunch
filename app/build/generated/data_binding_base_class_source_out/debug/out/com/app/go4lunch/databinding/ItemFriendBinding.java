// Generated by data binding compiler. Do not edit!
package com.app.go4lunch.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.app.go4lunch.R;
import java.lang.Deprecated;
import java.lang.Object;

public abstract class ItemFriendBinding extends ViewDataBinding {
  @NonNull
  public final ImageView imgUser;

  @NonNull
  public final CardView parentCard;

  @NonNull
  public final TextView test;

  @NonNull
  public final TextView tvUserName;

  protected ItemFriendBinding(Object _bindingComponent, View _root, int _localFieldCount,
      ImageView imgUser, CardView parentCard, TextView test, TextView tvUserName) {
    super(_bindingComponent, _root, _localFieldCount);
    this.imgUser = imgUser;
    this.parentCard = parentCard;
    this.test = test;
    this.tvUserName = tvUserName;
  }

  @NonNull
  public static ItemFriendBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot) {
    return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.item_friend, root, attachToRoot, component)
   */
  @NonNull
  @Deprecated
  public static ItemFriendBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot, @Nullable Object component) {
    return ViewDataBinding.<ItemFriendBinding>inflateInternal(inflater, R.layout.item_friend, root, attachToRoot, component);
  }

  @NonNull
  public static ItemFriendBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.item_friend, null, false, component)
   */
  @NonNull
  @Deprecated
  public static ItemFriendBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable Object component) {
    return ViewDataBinding.<ItemFriendBinding>inflateInternal(inflater, R.layout.item_friend, null, false, component);
  }

  public static ItemFriendBinding bind(@NonNull View view) {
    return bind(view, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.bind(view, component)
   */
  @Deprecated
  public static ItemFriendBinding bind(@NonNull View view, @Nullable Object component) {
    return (ItemFriendBinding)bind(component, view, R.layout.item_friend);
  }
}
