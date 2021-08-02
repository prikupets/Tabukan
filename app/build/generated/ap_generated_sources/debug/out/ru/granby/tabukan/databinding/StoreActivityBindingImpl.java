package ru.granby.tabukan.databinding;
import ru.granby.tabukan.R;
import ru.granby.tabukan.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class StoreActivityBindingImpl extends StoreActivityBinding  {

    @Nullable
    private static final androidx.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = new androidx.databinding.ViewDataBinding.IncludedLayouts(16);
        sIncludes.setIncludes(0, 
            new String[] {"store_coins_pack", "store_coins_pack", "store_coins_pack", "store_coins_pack"},
            new int[] {1, 2, 3, 4},
            new int[] {ru.granby.tabukan.R.layout.store_coins_pack,
                ru.granby.tabukan.R.layout.store_coins_pack,
                ru.granby.tabukan.R.layout.store_coins_pack,
                ru.granby.tabukan.R.layout.store_coins_pack});
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.back_button_background, 5);
        sViewsWithIds.put(R.id.back_button_icon, 6);
        sViewsWithIds.put(R.id.coins_button_background, 7);
        sViewsWithIds.put(R.id.coins_button_text, 8);
        sViewsWithIds.put(R.id.coins_button_icon, 9);
        sViewsWithIds.put(R.id.coins_packs, 10);
        sViewsWithIds.put(R.id.coins_for_ad_background, 11);
        sViewsWithIds.put(R.id.coins_for_ad_header, 12);
        sViewsWithIds.put(R.id.coins_for_ad_button_background, 13);
        sViewsWithIds.put(R.id.coins_for_ad_button_text, 14);
        sViewsWithIds.put(R.id.coins_for_ad_button_icon, 15);
    }
    // views
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public StoreActivityBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 16, sIncludes, sViewsWithIds));
    }
    private StoreActivityBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 4
            , (android.view.View) bindings[5]
            , (android.view.View) bindings[6]
            , (android.view.View) bindings[7]
            , (android.view.View) bindings[9]
            , (android.widget.TextView) bindings[8]
            , (android.view.View) bindings[11]
            , (android.view.View) bindings[13]
            , (android.view.View) bindings[15]
            , (android.widget.TextView) bindings[14]
            , (android.widget.TextView) bindings[12]
            , (ru.granby.tabukan.databinding.StoreCoinsPackBinding) bindings[2]
            , (ru.granby.tabukan.databinding.StoreCoinsPackBinding) bindings[4]
            , (ru.granby.tabukan.databinding.StoreCoinsPackBinding) bindings[3]
            , (ru.granby.tabukan.databinding.StoreCoinsPackBinding) bindings[1]
            , (androidx.constraintlayout.helper.widget.Flow) bindings[10]
            , (androidx.constraintlayout.widget.ConstraintLayout) bindings[0]
            );
        setContainedBinding(this.coinsPackBag);
        setContainedBinding(this.coinsPackBigChest);
        setContainedBinding(this.coinsPackChest);
        setContainedBinding(this.coinsPackPouch);
        this.storeRoot.setTag(null);
        setRootTag(root);
        // listeners
        invalidateAll();
    }

    @Override
    public void invalidateAll() {
        synchronized(this) {
                mDirtyFlags = 0x10L;
        }
        coinsPackPouch.invalidateAll();
        coinsPackBag.invalidateAll();
        coinsPackChest.invalidateAll();
        coinsPackBigChest.invalidateAll();
        requestRebind();
    }

    @Override
    public boolean hasPendingBindings() {
        synchronized(this) {
            if (mDirtyFlags != 0) {
                return true;
            }
        }
        if (coinsPackPouch.hasPendingBindings()) {
            return true;
        }
        if (coinsPackBag.hasPendingBindings()) {
            return true;
        }
        if (coinsPackChest.hasPendingBindings()) {
            return true;
        }
        if (coinsPackBigChest.hasPendingBindings()) {
            return true;
        }
        return false;
    }

    @Override
    public boolean setVariable(int variableId, @Nullable Object variable)  {
        boolean variableSet = true;
            return variableSet;
    }

    @Override
    public void setLifecycleOwner(@Nullable androidx.lifecycle.LifecycleOwner lifecycleOwner) {
        super.setLifecycleOwner(lifecycleOwner);
        coinsPackPouch.setLifecycleOwner(lifecycleOwner);
        coinsPackBag.setLifecycleOwner(lifecycleOwner);
        coinsPackChest.setLifecycleOwner(lifecycleOwner);
        coinsPackBigChest.setLifecycleOwner(lifecycleOwner);
    }

    @Override
    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        switch (localFieldId) {
            case 0 :
                return onChangeCoinsPackChest((ru.granby.tabukan.databinding.StoreCoinsPackBinding) object, fieldId);
            case 1 :
                return onChangeCoinsPackBigChest((ru.granby.tabukan.databinding.StoreCoinsPackBinding) object, fieldId);
            case 2 :
                return onChangeCoinsPackPouch((ru.granby.tabukan.databinding.StoreCoinsPackBinding) object, fieldId);
            case 3 :
                return onChangeCoinsPackBag((ru.granby.tabukan.databinding.StoreCoinsPackBinding) object, fieldId);
        }
        return false;
    }
    private boolean onChangeCoinsPackChest(ru.granby.tabukan.databinding.StoreCoinsPackBinding CoinsPackChest, int fieldId) {
        if (fieldId == BR._all) {
            synchronized(this) {
                    mDirtyFlags |= 0x1L;
            }
            return true;
        }
        return false;
    }
    private boolean onChangeCoinsPackBigChest(ru.granby.tabukan.databinding.StoreCoinsPackBinding CoinsPackBigChest, int fieldId) {
        if (fieldId == BR._all) {
            synchronized(this) {
                    mDirtyFlags |= 0x2L;
            }
            return true;
        }
        return false;
    }
    private boolean onChangeCoinsPackPouch(ru.granby.tabukan.databinding.StoreCoinsPackBinding CoinsPackPouch, int fieldId) {
        if (fieldId == BR._all) {
            synchronized(this) {
                    mDirtyFlags |= 0x4L;
            }
            return true;
        }
        return false;
    }
    private boolean onChangeCoinsPackBag(ru.granby.tabukan.databinding.StoreCoinsPackBinding CoinsPackBag, int fieldId) {
        if (fieldId == BR._all) {
            synchronized(this) {
                    mDirtyFlags |= 0x8L;
            }
            return true;
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
        if ((dirtyFlags & 0x10L) != 0) {
            // api target 1

            this.coinsPackBag.setCoinsAmount("100");
            this.coinsPackBag.setCoinsPrice("200$");
            this.coinsPackBag.setCoinsPackDrawable(androidx.appcompat.content.res.AppCompatResources.getDrawable(getRoot().getContext(), R.drawable.coins_bag_icon));
            this.coinsPackBigChest.setCoinsAmount("100");
            this.coinsPackBigChest.setCoinsPrice("200$");
            this.coinsPackBigChest.setCoinsPackDrawable(androidx.appcompat.content.res.AppCompatResources.getDrawable(getRoot().getContext(), R.drawable.coins_big_chest_icon));
            this.coinsPackChest.setCoinsAmount("100");
            this.coinsPackChest.setCoinsPrice("200$");
            this.coinsPackChest.setCoinsPackDrawable(androidx.appcompat.content.res.AppCompatResources.getDrawable(getRoot().getContext(), R.drawable.coins_chest_icon));
            this.coinsPackPouch.setCoinsAmount("100");
            this.coinsPackPouch.setCoinsPrice("200$");
            this.coinsPackPouch.setCoinsPackDrawable(androidx.appcompat.content.res.AppCompatResources.getDrawable(getRoot().getContext(), R.drawable.coins_pouch_icon));
        }
        executeBindingsOn(coinsPackPouch);
        executeBindingsOn(coinsPackBag);
        executeBindingsOn(coinsPackChest);
        executeBindingsOn(coinsPackBigChest);
    }
    // Listener Stub Implementations
    // callback impls
    // dirty flag
    private  long mDirtyFlags = 0xffffffffffffffffL;
    /* flag mapping
        flag 0 (0x1L): coinsPackChest
        flag 1 (0x2L): coinsPackBigChest
        flag 2 (0x3L): coinsPackPouch
        flag 3 (0x4L): coinsPackBag
        flag 4 (0x5L): null
    flag mapping end*/
    //end
}