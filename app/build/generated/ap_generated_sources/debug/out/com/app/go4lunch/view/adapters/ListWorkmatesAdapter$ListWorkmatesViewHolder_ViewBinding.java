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

public class ListWorkmatesAdapter$ListWorkmatesViewHolder_ViewBinding implements Unbinder {
  private ListWorkmatesAdapter.ListWorkmatesViewHolder target;

  private View view7f0800fc;

  @UiThread
  public ListWorkmatesAdapter$ListWorkmatesViewHolder_ViewBinding(
      final ListWorkmatesAdapter.ListWorkmatesViewHolder target, View source) {
    this.target = target;

    View view;
    target.imageView = Utils.findRequiredViewAsType(source, R.id.item_list_workmates_image, "field 'imageView'", ImageView.class);
    target.textView = Utils.findRequiredViewAsType(source, R.id.item_list_workmates_txt, "field 'textView'", TextView.class);
    view = Utils.findRequiredView(source, R.id.item_list_workmates_card_view, "method 'onClickItem'");
    view7f0800fc = view;
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
    ListWorkmatesAdapter.ListWorkmatesViewHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.imageView = null;
    target.textView = null;

    view7f0800fc.setOnClickListener(null);
    view7f0800fc = null;
  }
}
