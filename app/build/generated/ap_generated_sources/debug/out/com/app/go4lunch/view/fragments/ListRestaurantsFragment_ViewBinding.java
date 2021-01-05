// Generated code from Butter Knife. Do not modify!
package com.app.go4lunch.view.fragments;

import android.view.View;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.app.go4lunch.R;
import com.github.clans.fab.FloatingActionMenu;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.lang.IllegalStateException;
import java.lang.Override;

public class ListRestaurantsFragment_ViewBinding implements Unbinder {
  private ListRestaurantsFragment target;

  private View view7f0800d5;

  private View view7f0800d2;

  private View view7f0800d3;

  private View view7f0800d1;

  @UiThread
  public ListRestaurantsFragment_ViewBinding(final ListRestaurantsFragment target, View source) {
    this.target = target;

    View view;
    target.recyclerView = Utils.findRequiredViewAsType(source, R.id.fragment_list_restaurants_recycler_view, "field 'recyclerView'", RecyclerView.class);
    target.progressBarLayout = Utils.findRequiredViewAsType(source, R.id.progress_bar_layout, "field 'progressBarLayout'", ConstraintLayout.class);
    target.floatingActionButtonSort = Utils.findRequiredViewAsType(source, R.id.fragment_list_restaurants_menu_fab, "field 'floatingActionButtonSort'", FloatingActionMenu.class);
    view = Utils.findRequiredView(source, R.id.fragment_list_restaurants_refresh_fab, "field 'floatingActionButtonRefresh' and method 'refresh'");
    target.floatingActionButtonRefresh = Utils.castView(view, R.id.fragment_list_restaurants_refresh_fab, "field 'floatingActionButtonRefresh'", FloatingActionButton.class);
    view7f0800d5 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.refresh();
      }
    });
    view = Utils.findRequiredView(source, R.id.fragment_list_restaurants_near_me_fab, "method 'triProximity'");
    view7f0800d2 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.triProximity();
      }
    });
    view = Utils.findRequiredView(source, R.id.fragment_list_restaurants_rating_fab, "method 'triRate'");
    view7f0800d3 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.triRate();
      }
    });
    view = Utils.findRequiredView(source, R.id.fragment_list_restaurants_name_fab, "method 'triName'");
    view7f0800d1 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.triName();
      }
    });
  }

  @Override
  @CallSuper
  public void unbind() {
    ListRestaurantsFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.recyclerView = null;
    target.progressBarLayout = null;
    target.floatingActionButtonSort = null;
    target.floatingActionButtonRefresh = null;

    view7f0800d5.setOnClickListener(null);
    view7f0800d5 = null;
    view7f0800d2.setOnClickListener(null);
    view7f0800d2 = null;
    view7f0800d3.setOnClickListener(null);
    view7f0800d3 = null;
    view7f0800d1.setOnClickListener(null);
    view7f0800d1 = null;
  }
}
