// Generated by data binding compiler. Do not edit!
package com.app.go4lunch.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.drawerlayout.widget.DrawerLayout;
import com.app.go4lunch.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import java.lang.Deprecated;
import java.lang.Object;

public abstract class ActivityHomeBinding extends ViewDataBinding {
  @NonNull
  public final BottomNavigationView bottomNavigation;

  @NonNull
  public final DrawerLayout navigationDrawer;

  @NonNull
  public final FrameLayout navigationDrawerFrameLayout;

  @NonNull
  public final NavigationView navigationDrawerNavView;

  @NonNull
  public final EditText searchBar;

  @NonNull
  public final Toolbar toolBar;

  protected ActivityHomeBinding(Object _bindingComponent, View _root, int _localFieldCount,
      BottomNavigationView bottomNavigation, DrawerLayout navigationDrawer,
      FrameLayout navigationDrawerFrameLayout, NavigationView navigationDrawerNavView,
      EditText searchBar, Toolbar toolBar) {
    super(_bindingComponent, _root, _localFieldCount);
    this.bottomNavigation = bottomNavigation;
    this.navigationDrawer = navigationDrawer;
    this.navigationDrawerFrameLayout = navigationDrawerFrameLayout;
    this.navigationDrawerNavView = navigationDrawerNavView;
    this.searchBar = searchBar;
    this.toolBar = toolBar;
  }

  @NonNull
  public static ActivityHomeBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot) {
    return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.activity_home, root, attachToRoot, component)
   */
  @NonNull
  @Deprecated
  public static ActivityHomeBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot, @Nullable Object component) {
    return ViewDataBinding.<ActivityHomeBinding>inflateInternal(inflater, R.layout.activity_home, root, attachToRoot, component);
  }

  @NonNull
  public static ActivityHomeBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.activity_home, null, false, component)
   */
  @NonNull
  @Deprecated
  public static ActivityHomeBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable Object component) {
    return ViewDataBinding.<ActivityHomeBinding>inflateInternal(inflater, R.layout.activity_home, null, false, component);
  }

  public static ActivityHomeBinding bind(@NonNull View view) {
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
  public static ActivityHomeBinding bind(@NonNull View view, @Nullable Object component) {
    return (ActivityHomeBinding)bind(component, view, R.layout.activity_home);
  }
}
