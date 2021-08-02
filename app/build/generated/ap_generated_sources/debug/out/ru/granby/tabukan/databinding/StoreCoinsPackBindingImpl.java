package ru.granby.tabukan.databinding;
import ru.granby.tabukan.R;
import ru.granby.tabukan.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class StoreCoinsPackBindingImpl extends StoreCoinsPackBinding  {

    @Nullable
    private static final androidx.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.coins_pack_background, 4);
        sViewsWithIds.put(R.id.coins_amount_icon, 5);
        sViewsWithIds.put(R.id.coins_pack_ripple, 6);
    }
    // views
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public StoreCoinsPackBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 7, sIncludes, sViewsWithIds));
    }
    private StoreCoinsPackBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0
            , (android.view.View) bindings[5]
            , (android.widget.TextView) bindings[2]
            , (android.view.View) bindings[4]
            , (android.widget.ImageView) bindings[1]
            , (android.view.View) bindings[6]
            , (androidx.constraintlayout.widget.ConstraintLayout) bindings[0]
            , (android.widget.TextView) bindings[3]
            );
        this.coinsAmountText.setTag(null);
        this.coinsPackImage.setTag(null);
        this.coinsPackRoot.setTag(null);
        this.coinsPriceText.setTag(null);
        setRootTag(root);
        // listeners
        invalidateAll();
    }

    @Override
    public void invalidateAll() {
        synchronized(this) {
                mDirtyFlags = 0x8L;
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
        if (BR.coinsPrice == variableId) {
            setCoinsPrice((java.lang.String) variable);
        }
        else if (BR.coinsPackDrawable == variableId) {
            setCoinsPackDrawable((android.graphics.drawable.Drawable) variable);
        }
        else if (BR.coinsAmount == variableId) {
            setCoinsAmount((java.lang.String) variable);
        }
        else {
            variableSet = false;
        }
            return variableSet;
    }

    public void setCoinsPrice(@Nullable java.lang.String CoinsPrice) {
        this.mCoinsPrice = CoinsPrice;
        synchronized(this) {
            mDirtyFlags |= 0x1L;
        }
        notifyPropertyChanged(BR.coinsPrice);
        super.requestRebind();
    }
    public void setCoinsPackDrawable(@Nullable android.graphics.drawable.Drawable CoinsPackDrawable) {
        this.mCoinsPackDrawable = CoinsPackDrawable;
        synchronized(this) {
            mDirtyFlags |= 0x2L;
        }
        notifyPropertyChanged(BR.coinsPackDrawable);
        super.requestRebind();
    }
    public void setCoinsAmount(@Nullable java.lang.String CoinsAmount) {
        this.mCoinsAmount = CoinsAmount;
        synchronized(this) {
            mDirtyFlags |= 0x4L;
        }
        notifyPropertyChanged(BR.coinsAmount);
        super.requestRebind();
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
        java.lang.String coinsPrice = mCoinsPrice;
        android.graphics.drawable.Drawable coinsPackDrawable = mCoinsPackDrawable;
        java.lang.String coinsAmount = mCoinsAmount;

        if ((dirtyFlags & 0x9L) != 0) {
        }
        if ((dirtyFlags & 0xaL) != 0) {
        }
        if ((dirtyFlags & 0xcL) != 0) {
        }
        // batch finished
        if ((dirtyFlags & 0xcL) != 0) {
            // api target 1

            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.coinsAmountText, coinsAmount);
        }
        if ((dirtyFlags & 0xaL) != 0) {
            // api target 1

            androidx.databinding.adapters.ImageViewBindingAdapter.setImageDrawable(this.coinsPackImage, coinsPackDrawable);
        }
        if ((dirtyFlags & 0x9L) != 0) {
            // api target 1

            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.coinsPriceText, coinsPrice);
        }
    }
    // Listener Stub Implementations
    // callback impls
    // dirty flag
    private  long mDirtyFlags = 0xffffffffffffffffL;
    /* flag mapping
        flag 0 (0x1L): coinsPrice
        flag 1 (0x2L): coinsPackDrawable
        flag 2 (0x3L): coinsAmount
        flag 3 (0x4L): null
    flag mapping end*/
    //end
}