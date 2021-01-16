package com.app.go4lunch.databinding;
import com.app.go4lunch.R;
import com.app.go4lunch.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class ActivityRestaurantDetailBindingImpl extends ActivityRestaurantDetailBinding  {

    @Nullable
    private static final androidx.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.toolBar, 1);
        sViewsWithIds.put(R.id.imgRestaurant, 2);
        sViewsWithIds.put(R.id.tvRestaurantName, 3);
        sViewsWithIds.put(R.id.tvRestaurantAddress, 4);
        sViewsWithIds.put(R.id.ratingBar, 5);
        sViewsWithIds.put(R.id.btnCall, 6);
        sViewsWithIds.put(R.id.btnLike, 7);
        sViewsWithIds.put(R.id.btnWebsite, 8);
        sViewsWithIds.put(R.id.tvCallTxt, 9);
        sViewsWithIds.put(R.id.tvLikeTxt, 10);
        sViewsWithIds.put(R.id.tvWebsiteTxt, 11);
        sViewsWithIds.put(R.id.rvFriendsRestaurant, 12);
        sViewsWithIds.put(R.id.guideline_1, 13);
        sViewsWithIds.put(R.id.details_fragment_guideline_2, 14);
        sViewsWithIds.put(R.id.guideline_2_bis, 15);
        sViewsWithIds.put(R.id.guideline_3, 16);
        sViewsWithIds.put(R.id.btnSelect, 17);
        sViewsWithIds.put(R.id.tvNoRestaurant, 18);
        sViewsWithIds.put(R.id.progressBar, 19);
    }
    // views
    @NonNull
    private final androidx.constraintlayout.widget.ConstraintLayout mboundView0;
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public ActivityRestaurantDetailBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 20, sIncludes, sViewsWithIds));
    }
    private ActivityRestaurantDetailBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0
            , (android.widget.ImageButton) bindings[6]
            , (android.widget.ImageButton) bindings[7]
            , (com.google.android.material.floatingactionbutton.FloatingActionButton) bindings[17]
            , (android.widget.ImageButton) bindings[8]
            , (androidx.constraintlayout.widget.Guideline) bindings[14]
            , (androidx.constraintlayout.widget.Guideline) bindings[13]
            , (androidx.constraintlayout.widget.Guideline) bindings[15]
            , (androidx.constraintlayout.widget.Guideline) bindings[16]
            , (android.widget.ImageView) bindings[2]
            , (android.widget.ProgressBar) bindings[19]
            , (android.widget.RatingBar) bindings[5]
            , (androidx.recyclerview.widget.RecyclerView) bindings[12]
            , (androidx.appcompat.widget.Toolbar) bindings[1]
            , (android.widget.TextView) bindings[9]
            , (android.widget.TextView) bindings[10]
            , (android.widget.TextView) bindings[18]
            , (android.widget.TextView) bindings[4]
            , (android.widget.TextView) bindings[3]
            , (android.widget.TextView) bindings[11]
            );
        this.mboundView0 = (androidx.constraintlayout.widget.ConstraintLayout) bindings[0];
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