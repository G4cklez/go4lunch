// Generated code from Butter Knife. Do not modify!
package com.app.go4lunch.view.adapters;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.app.go4lunch.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class ListWorkmatesDetailsFragmentAdapter$ListWorkmatesViewHolder_ViewBinding implements Unbinder {
  private ListWorkmatesDetailsFragmentAdapter.ListWorkmatesViewHolder target;

  @UiThread
  public ListWorkmatesDetailsFragmentAdapter$ListWorkmatesViewHolder_ViewBinding(
      ListWorkmatesDetailsFragmentAdapter.ListWorkmatesViewHolder target, View source) {
    this.target = target;

    target.imageView = Utils.findRequiredViewAsType(source, R.id.item_list_workmates_image, "field 'imageView'", ImageView.class);
    target.textView = Utils.findRequiredViewAsType(source, R.id.item_list_workmates_txt, "field 'textView'", TextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    ListWorkmatesDetailsFragmentAdapter.ListWorkmatesViewHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.imageView = null;
    target.textView = null;
  }
}
