// Generated code from Butter Knife. Do not modify!
package com.app.go4lunch.view.fragments;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.app.go4lunch.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.lang.IllegalStateException;
import java.lang.Override;

public class DetailsFragment_ViewBinding implements Unbinder {
  private DetailsFragment target;

  private View view7f0800a4;

  private View view7f08009e;

  private View view7f08009c;

  private View view7f0800ab;

  @UiThread
  public DetailsFragment_ViewBinding(final DetailsFragment target, View source) {
    this.target = target;

    View view;
    target.name = Utils.findRequiredViewAsType(source, R.id.details_fragment_name_restaurant_txt, "field 'name'", TextView.class);
    target.illustration = Utils.findRequiredViewAsType(source, R.id.details_fragment_illustration_image, "field 'illustration'", ImageView.class);
    target.address = Utils.findRequiredViewAsType(source, R.id.details_fragment_address_txt, "field 'address'", TextView.class);
    target.star1 = Utils.findRequiredViewAsType(source, R.id.details_fragment_star_1_image, "field 'star1'", ImageView.class);
    target.star2 = Utils.findRequiredViewAsType(source, R.id.details_fragment_star_2_image, "field 'star2'", ImageView.class);
    target.star3 = Utils.findRequiredViewAsType(source, R.id.details_fragment_star_3_image, "field 'star3'", ImageView.class);
    view = Utils.findRequiredView(source, R.id.details_fragment_like_button, "field 'likeButton' and method 'onClickLikeButton'");
    target.likeButton = Utils.castView(view, R.id.details_fragment_like_button, "field 'likeButton'", ImageView.class);
    view7f0800a4 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClickLikeButton();
      }
    });
    target.workmatesRecyclerView = Utils.findRequiredViewAsType(source, R.id.details_fragment_workmates_recycler_view, "field 'workmatesRecyclerView'", RecyclerView.class);
    view = Utils.findRequiredView(source, R.id.details_fragment_choose_button, "field 'floatingActionButton' and method 'onClickChooseButton'");
    target.floatingActionButton = Utils.castView(view, R.id.details_fragment_choose_button, "field 'floatingActionButton'", FloatingActionButton.class);
    view7f08009e = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClickChooseButton();
      }
    });
    target.progressBarLayout = Utils.findRequiredViewAsType(source, R.id.progress_bar_layout, "field 'progressBarLayout'", ConstraintLayout.class);
    target.noRestaurant = Utils.findRequiredViewAsType(source, R.id.details_fragment_no_restaurant_txt, "field 'noRestaurant'", TextView.class);
    view = Utils.findRequiredView(source, R.id.details_fragment_call_button, "method 'onClickCallButton'");
    view7f08009c = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClickCallButton();
      }
    });
    view = Utils.findRequiredView(source, R.id.details_fragment_website_button, "method 'onClickWebsiteButton'");
    view7f0800ab = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClickWebsiteButton();
      }
    });
  }

  @Override
  @CallSuper
  public void unbind() {
    DetailsFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.name = null;
    target.illustration = null;
    target.address = null;
    target.star1 = null;
    target.star2 = null;
    target.star3 = null;
    target.likeButton = null;
    target.workmatesRecyclerView = null;
    target.floatingActionButton = null;
    target.progressBarLayout = null;
    target.noRestaurant = null;

    view7f0800a4.setOnClickListener(null);
    view7f0800a4 = null;
    view7f08009e.setOnClickListener(null);
    view7f08009e = null;
    view7f08009c.setOnClickListener(null);
    view7f08009c = null;
    view7f0800ab.setOnClickListener(null);
    view7f0800ab = null;
  }
}
