package com.app.go4lunch;

import android.util.SparseArray;
import android.util.SparseIntArray;
import android.view.View;
import androidx.databinding.DataBinderMapper;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.ViewDataBinding;
import com.app.go4lunch.databinding.ActivityHomeBindingImpl;
import com.app.go4lunch.databinding.ActivityLoginBindingImpl;
import com.app.go4lunch.databinding.FragmentMapBindingImpl;
import com.app.go4lunch.databinding.FragmentRestaurantListBindingImpl;
import com.app.go4lunch.databinding.ItemRestaurantsBindingImpl;
import com.app.go4lunch.databinding.NavigationHeaderBindingImpl;
import java.lang.IllegalArgumentException;
import java.lang.Integer;
import java.lang.Object;
import java.lang.Override;
import java.lang.RuntimeException;
import java.lang.String;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DataBinderMapperImpl extends DataBinderMapper {
  private static final int LAYOUT_ACTIVITYHOME = 1;

  private static final int LAYOUT_ACTIVITYLOGIN = 2;

  private static final int LAYOUT_FRAGMENTMAP = 3;

  private static final int LAYOUT_FRAGMENTRESTAURANTLIST = 4;

  private static final int LAYOUT_ITEMRESTAURANTS = 5;

  private static final int LAYOUT_NAVIGATIONHEADER = 6;

  private static final SparseIntArray INTERNAL_LAYOUT_ID_LOOKUP = new SparseIntArray(6);

  static {
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.app.go4lunch.R.layout.activity_home, LAYOUT_ACTIVITYHOME);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.app.go4lunch.R.layout.activity_login, LAYOUT_ACTIVITYLOGIN);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.app.go4lunch.R.layout.fragment_map, LAYOUT_FRAGMENTMAP);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.app.go4lunch.R.layout.fragment_restaurant_list, LAYOUT_FRAGMENTRESTAURANTLIST);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.app.go4lunch.R.layout.item_restaurants, LAYOUT_ITEMRESTAURANTS);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.app.go4lunch.R.layout.navigation_header, LAYOUT_NAVIGATIONHEADER);
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
        case  LAYOUT_ACTIVITYHOME: {
          if ("layout/activity_home_0".equals(tag)) {
            return new ActivityHomeBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for activity_home is invalid. Received: " + tag);
        }
        case  LAYOUT_ACTIVITYLOGIN: {
          if ("layout/activity_login_0".equals(tag)) {
            return new ActivityLoginBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for activity_login is invalid. Received: " + tag);
        }
        case  LAYOUT_FRAGMENTMAP: {
          if ("layout/fragment_map_0".equals(tag)) {
            return new FragmentMapBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for fragment_map is invalid. Received: " + tag);
        }
        case  LAYOUT_FRAGMENTRESTAURANTLIST: {
          if ("layout/fragment_restaurant_list_0".equals(tag)) {
            return new FragmentRestaurantListBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for fragment_restaurant_list is invalid. Received: " + tag);
        }
        case  LAYOUT_ITEMRESTAURANTS: {
          if ("layout/item_restaurants_0".equals(tag)) {
            return new ItemRestaurantsBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for item_restaurants is invalid. Received: " + tag);
        }
        case  LAYOUT_NAVIGATIONHEADER: {
          if ("layout/navigation_header_0".equals(tag)) {
            return new NavigationHeaderBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for navigation_header is invalid. Received: " + tag);
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
    static final SparseArray<String> sKeys = new SparseArray<String>(1);

    static {
      sKeys.put(0, "_all");
    }
  }

  private static class InnerLayoutIdLookup {
    static final HashMap<String, Integer> sKeys = new HashMap<String, Integer>(6);

    static {
      sKeys.put("layout/activity_home_0", com.app.go4lunch.R.layout.activity_home);
      sKeys.put("layout/activity_login_0", com.app.go4lunch.R.layout.activity_login);
      sKeys.put("layout/fragment_map_0", com.app.go4lunch.R.layout.fragment_map);
      sKeys.put("layout/fragment_restaurant_list_0", com.app.go4lunch.R.layout.fragment_restaurant_list);
      sKeys.put("layout/item_restaurants_0", com.app.go4lunch.R.layout.item_restaurants);
      sKeys.put("layout/navigation_header_0", com.app.go4lunch.R.layout.navigation_header);
    }
  }
}
