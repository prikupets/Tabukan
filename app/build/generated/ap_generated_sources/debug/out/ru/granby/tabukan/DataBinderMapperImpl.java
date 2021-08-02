package ru.granby.tabukan;

import android.util.SparseArray;
import android.util.SparseIntArray;
import android.view.View;
import androidx.databinding.DataBinderMapper;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.ViewDataBinding;
import java.lang.IllegalArgumentException;
import java.lang.Integer;
import java.lang.Object;
import java.lang.Override;
import java.lang.RuntimeException;
import java.lang.String;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import ru.granby.tabukan.databinding.StoreActivityBindingImpl;
import ru.granby.tabukan.databinding.StoreCoinsPackBindingImpl;

public class DataBinderMapperImpl extends DataBinderMapper {
  private static final int LAYOUT_STOREACTIVITY = 1;

  private static final int LAYOUT_STORECOINSPACK = 2;

  private static final SparseIntArray INTERNAL_LAYOUT_ID_LOOKUP = new SparseIntArray(2);

  static {
    INTERNAL_LAYOUT_ID_LOOKUP.put(ru.granby.tabukan.R.layout.store_activity, LAYOUT_STOREACTIVITY);
    INTERNAL_LAYOUT_ID_LOOKUP.put(ru.granby.tabukan.R.layout.store_coins_pack, LAYOUT_STORECOINSPACK);
  }

  @Override
  public ViewDataBinding getDataBinder(DataBindingComponent component, View view, int layoutId) {
    int localizedLayoutId = INTERNAL_LAYOUT_ID_LOOKUP.get(layoutId);
    if(localizedLayoutId > 0) {
      final Object tag = view.getTag();
      if(tag == null) {
        throw new RuntimeException("view must have a tag");
      }
      switch(localizedLayoutId) {
        case  LAYOUT_STOREACTIVITY: {
          if ("layout/store_activity_0".equals(tag)) {
            return new StoreActivityBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for store_activity is invalid. Received: " + tag);
        }
        case  LAYOUT_STORECOINSPACK: {
          if ("layout/store_coins_pack_0".equals(tag)) {
            return new StoreCoinsPackBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for store_coins_pack is invalid. Received: " + tag);
        }
      }
    }
    return null;
  }

  @Override
  public ViewDataBinding getDataBinder(DataBindingComponent component, View[] views, int layoutId) {
    if(views == null || views.length == 0) {
      return null;
    }
    int localizedLayoutId = INTERNAL_LAYOUT_ID_LOOKUP.get(layoutId);
    if(localizedLayoutId > 0) {
      final Object tag = views[0].getTag();
      if(tag == null) {
        throw new RuntimeException("view must have a tag");
      }
      switch(localizedLayoutId) {
      }
    }
    return null;
  }

  @Override
  public int getLayoutId(String tag) {
    if (tag == null) {
      return 0;
    }
    Integer tmpVal = InnerLayoutIdLookup.sKeys.get(tag);
    return tmpVal == null ? 0 : tmpVal;
  }

  @Override
  public String convertBrIdToString(int localId) {
    String tmpVal = InnerBrLookup.sKeys.get(localId);
    return tmpVal;
  }

  @Override
  public List<DataBinderMapper> collectDependencies() {
    ArrayList<DataBinderMapper> result = new ArrayList<DataBinderMapper>(1);
    result.add(new androidx.databinding.library.baseAdapters.DataBinderMapperImpl());
    return result;
  }

  private static class InnerBrLookup {
    static final SparseArray<String> sKeys = new SparseArray<String>(4);

    static {
      sKeys.put(0, "_all");
      sKeys.put(1, "coinsAmount");
      sKeys.put(2, "coinsPackDrawable");
      sKeys.put(3, "coinsPrice");
    }
  }

  private static class InnerLayoutIdLookup {
    static final HashMap<String, Integer> sKeys = new HashMap<String, Integer>(2);

    static {
      sKeys.put("layout/store_activity_0", ru.granby.tabukan.R.layout.store_activity);
      sKeys.put("layout/store_coins_pack_0", ru.granby.tabukan.R.layout.store_coins_pack);
    }
  }
}
