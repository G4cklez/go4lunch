// Generated code from Butter Knife. Do not modify!
package com.app.go4lunch.view.adapters;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.app.go4lunch.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class ListRestaurantsAdapter$ListRestaurantsViewHolder_ViewBinding implements Unbinder {
  private ListRestaurantsAdapter.ListRestaurantsViewHolder target;

  private View view7f0800f2;

  @UiThread
  public ListRestaurantsAdapter$ListRestaurantsViewHolder_ViewBinding(
      final ListRestaurantsAdapter.ListRestaurantsViewHolder target, View source) {
    this.target = target;

    View view;
    target.name = Utils.findRequiredViewAsType(source, R.id.item_list_restaurant_name_txt, "field 'name'", TextView.class);
    target.address = Utils.findRequiredViewAsType(source, R.id.item_list_restaurant_address_txt, "field 'address'", TextView.class);
    target.hours = Utils.findRequiredViewAsType(source, R.id.item_list_restaurant_hours_txt, "field 'hours'", TextView.class);
    target.distance = Utils.findRequiredViewAsType(source, R.id.item_list_restaurant_distance_txt, "field 'distance'", TextView.class);
    target.numberWorkmatesTxt = Utils.findRequiredViewAsType(source, R.id.item_list_restaurant_number_rating_txt, "field 'numberWorkmatesTxt'", TextView.class);
    target.peopleWorkmatesImage = Utils.findRequiredViewAsType(source, R.id.item_list_restaurant_people_rating_image, "field 'peopleWorkmatesImage'", ImageView.class);
    target.star1 = Utils.findRequiredViewAsType(source, R.id.item_list_restaurant_star_1_image, "field 'star1'", ImageView.class);
    target.star2 = Utils.findRequiredViewAsType(source, R.id.item_list_restaurant_star_2_image, "field 'star2'", ImageView.class);
    target.star3 = Utils.findRequiredViewAsType(source, R.id.item_list_restaurant_star_3_image, "field 'star3'", ImageView.class);
    target.illustration = Utils.findRequiredViewAsType(source, R.id.item_list_restaurant_illustration_image, "field 'illustration'", ImageView.class);
    view = Utils.findRequiredView(source, R.id.item_list_restaurant_card_view, "method 'onClickItem'");
    view7f0800f2 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClickItem();
      }
    });
  }

  @Override
  @CallSuper
  public void unbind() {
    ListRestaurantsAdapter.ListRestaurantsViewHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.name = null;
    target.address = null;
    target.hours = null;
    target.distance = null;
    target.numberWorkmatesTxt = null;
    target.peopleWorkmatesImage = null;
    target.star1 = null;
    target.star2 = null;
    target.star3 = null;
    target.illustration = null;

    view7f0800f2.setOnClickListener(null);
    view7f0800f2 = null;
  }
}
