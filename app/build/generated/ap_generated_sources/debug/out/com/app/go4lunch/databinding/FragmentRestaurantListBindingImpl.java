package com.app.go4lunch.databinding;
import com.app.go4lunch.R;
import com.app.go4lunch.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class FragmentRestaurantListBindingImpl extends FragmentRestaurantListBinding  {

    @Nullable
    private static final androidx.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.fragment_list_restaurants_constraint_layout, 1);
        sViewsWithIds.put(R.id.fragment_list_restaurants_recycler_view, 2);
        sViewsWithIds.put(R.id.fragment_list_restaurants_menu_fab, 3);
        sViewsWithIds.put(R.id.fragment_list_restaurants_near_me_fab, 4);
        sViewsWithIds.put(R.id.fragment_list_restaurants_rating_fab, 5);
        sViewsWithIds.put(R.id.fragment_list_restaurants_name_fab, 6);
        sViewsWithIds.put(R.id.progressBar, 7);
        sViewsWithIds.put(R.id.fragment_list_restaurants_refresh_fab, 8);
    }
    // views
    @NonNull
    private final android.widget.FrameLayout mboundView0;
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public FragmentRestaurantListBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 9, sIncludes, sViewsWithIds));
    }
    private FragmentRestaurantListBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0
            , (androidx.constraintlayout.widget.ConstraintLayout) bindings[1]
            , (com.github.clans.fab.FloatingActionMenu) bindings[3]
            , (com.github.clans.fab.FloatingActionButton) bindings[6]
            , (com.github.clans.fab.FloatingActionButton) bindings[4]
            , (com.github.clans.fab.FloatingActionButton) bindings[5]
            , (androidx.recyclerview.widget.RecyclerView) bindings[2]
            , (com.google.android.material.floatingactionbutton.FloatingActionButton) bindings[8]
            , (android.widget.ProgressBar) bindings[7]
            );
        this.mboundView0 = (android.widget.FrameLayout) bindings[0];
        this.mboundView0.setTag(null);
        setRootTag(root);
        // listeners
        invalidateAll();
    }

    @Override
    public void invalidateAll() {
        synchronized(this) {
                mDirtyFlags = 0x1L;
        }
        requestRebind();
    }

    @Override
    public boolean hasPendingBindings() {
        synchronized(this) {
            if (mDirtyFlags != 0) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean setVariable(int variableId, @Nullable Object variable)  {
        boolean variableSet = true;
            return variableSet;
    }

    @Override
    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        switch (localFieldId) {
        }
        return false;
    }

    @Override
    protected void executeBindings() {
        long dirtyFlags = 0;
        synchronized(this) {
            dirtyFlags = mDirtyFlags;
            mDirtyFlags = 0;
        }
        // batch finished
    }
    // Listener Stub Implementations
    // callback impls
    // dirty flag
    private  long mDirtyFlags = 0xffffffffffffffffL;
    /* flag mapping
        flag 0 (0x1L): null
    flag mapping end*/
    //end
}