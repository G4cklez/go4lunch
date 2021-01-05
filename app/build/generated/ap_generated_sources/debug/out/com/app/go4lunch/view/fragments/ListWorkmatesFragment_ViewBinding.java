// Generated code from Butter Knife. Do not modify!
package com.app.go4lunch.view.fragments;

import android.view.View;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.app.go4lunch.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class ListWorkmatesFragment_ViewBinding implements Unbinder {
  private ListWorkmatesFragment target;

  @UiThread
  public ListWorkmatesFragment_ViewBinding(ListWorkmatesFragment target, View source) {
    this.target = target;

    target.recyclerView = Utils.findRequiredViewAsType(source, R.id.fragment_list_workmates_recycler_view, "field 'recyclerView'", RecyclerView.class);
    target.progressBarLayout = Utils.findRequiredViewAsType(source, R.id.progress_bar_layout, "field 'progressBarLayout'", ConstraintLayout.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    ListWorkmatesFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.recyclerView = null;
    target.progressBarLayout = null;
  }
}
