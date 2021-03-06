// Generated by data binding compiler. Do not edit!
package com.app.go4lunch.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.app.go4lunch.R;
import java.lang.Deprecated;
import java.lang.Object;

public abstract class ItemRestaurantsBinding extends ViewDataBinding {
  @NonNull
  public final ImageView imgRestaurant;

  @NonNull
  public final ImageView imgRestaurantRating;

  @NonNull
  public final CardView parentCard;

  @NonNull
  public final RatingBar ratingBar;

  @NonNull
  public final TextView tvNumberMates;

  @NonNull
  public final TextView tvRestaurantAddress;

  @NonNull
  public final TextView tvRestaurantDistance;

  @NonNull
  public final TextView tvRestaurantHours;

  @NonNull
  public final TextView tvRestaurantName;

  protected ItemRestaurantsBinding(Object _bindingComponent, View _root, int _localFieldCount,
      ImageView imgRestaurant, ImageView imgRestaurantRating, CardView parentCard,
      RatingBar ratingBar, TextView tvNumberMates, TextView tvRestaurantAddress,
      TextView tvRestaurantDistance, TextView tvRestaurantHours, TextView tvRestaurantName) {
    super(_bindingComponent, _root, _localFieldCount);
    this.imgRestaurant = imgRestaurant;
    this.imgRestaurantRating = imgRestaurantRating;
    this.parentCard = parentCard;
    this.ratingBar = ratingBar;
    this.tvNumberMates = tvNumberMates;
    this.tvRestaurantAddress = tvRestaurantAddress;
    this.tvRestaurantDistance = tvRestaurantDistance;
    this.tvRestaurantHours = tvRestaurantHours;
    this.tvRestaurantName = tvRestaurantName;
  }

  @NonNull
  public static ItemRestaurantsBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot) {
    return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.item_restaurants, root, attachToRoot, component)
   */
  @NonNull
  @Deprecated
  public static ItemRestaurantsBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot, @Nullable Object component) {
    return ViewDataBinding.<ItemRestaurantsBinding>inflateInternal(inflater, R.layout.item_restaurants, root, attachToRoot, component);
  }

  @NonNull
  public static ItemRestaurantsBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.item_restaurants, null, false, component)
   */
  @NonNull
  @Deprecated
  public static ItemRestaurantsBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable Object component) {
    return ViewDataBinding.<ItemRestaurantsBinding>inflateInternal(inflater, R.layout.item_restaurants, null, false, component);
  }

  public static ItemRestaurantsBinding bind(@NonNull View view) {
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
  public static ItemRestaurantsBinding bind(@NonNull View view, @Nullable Object component) {
    return (ItemRestaurantsBinding)bind(component, view, R.layout.item_restaurants);
  }
}
