package com.app.go4lunch.view.activities.fragments;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.go4lunch.R;
import com.app.go4lunch.model.Restaurant;
import com.bumptech.glide.Glide;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.AutocompleteSessionToken;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.model.RectangularBounds;
import com.google.android.libraries.places.api.model.TypeFilter;
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsRequest;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.Autocomplete;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RestaurantListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RestaurantListFragment extends Fragment {

    //FOR DATA
    private List<Restaurant> restaurantListFromPlaces;
    private ListRestaurantsAdapter adapter;
    private Location currentLocation;
    private ViewModelGo4Lunch viewModelGo4Lunch;
    private Disposable disposable;
    private int radius;
    private List<Restaurant> restaurantListAutocomplete = new ArrayList<>();
    private List<String> placeIdList = new ArrayList<>();

    public ListRestaurantsFragment() {
    }

    private ListRestaurantsFragment(Location location) {
        this.currentLocation = location;
    }

    public static ListRestaurantsFragment newInstance(Location location) {
        return new ListRestaurantsFragment(location);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        restaurantListFromPlaces = new ArrayList<>();
        radius = 500;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_list_restaurants, container, false);
        ButterKnife.bind(this, v);
        this.progressBarLayout.setVisibility(View.VISIBLE);
        this.floatingActionButtonSort.setVisibility(View.INVISIBLE);
        this.floatingActionButtonRefresh.setVisibility(View.INVISIBLE);
        this.configRecyclerView();
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (viewModelGo4Lunch == null)
        {
            this.configViewModel();
        }
    }

    ////////////////////////////////////////// VIEW MODEL ///////////////////////////////////////////

    private void configViewModel() {
        ViewModelFactoryGo4Lunch viewModelFactoryGo4Lunch = Injection.viewModelFactoryGo4Lunch();
        viewModelGo4Lunch = ViewModelProviders.of(this, viewModelFactoryGo4Lunch).get(ViewModelGo4Lunch.class);
        this.getRestaurantListFromPlaces();
    }

    private void getRestaurantListFromPlaces() {
        String key = getResources().getString(R.string.google_maps_key);
        this.viewModelGo4Lunch.getRestaurantsListPlacesMutableLiveData(currentLocation.getLatitude(), currentLocation.getLongitude(), radius, key)
                .observe(this, listObservable -> disposable = listObservable
                        .subscribeWith(new DisposableObserver<List<Restaurant>>() {
                            @Override
                            public void onNext(List<Restaurant> restaurantList) {
                                restaurantListFromPlaces = restaurantList;
                                UtilsListRestaurant.updateDistanceToCurrentLocation(currentLocation, restaurantListFromPlaces);
                                getRestaurantListFromFirebase();
                            }
                            @Override
                            public void onError(Throwable e) {}
                            @Override
                            public void onComplete() {}
                        }));

    }

    private void getRestaurantListFromFirebase() {
        this.viewModelGo4Lunch.getRestaurantsListFirebaseMutableLiveData().observe(this, restaurantList -> {

            int size = restaurantList.size();
            for (int i = 0; i < size; i++) {
                Restaurant restaurant = restaurantList.get(i);
                if (restaurant.getUserList().size() > 0) {
                    if (restaurantListFromPlaces.contains(restaurant)) {
                        int index = restaurantListFromPlaces.indexOf(restaurant);
                        restaurantListFromPlaces.get(index).setUserList(restaurant.getUserList());
                    }
                }
            }
            adapter.updateList(restaurantListFromPlaces);
            this.progressBarLayout.setVisibility(View.INVISIBLE);
            this.floatingActionButtonSort.setVisibility(View.VISIBLE);
        });
    }

    ////////////////////////////////////////// ON CLICK  ///////////////////////////////////////////

    @OnClick(R.id.fragment_list_restaurants_near_me_fab)
    void triProximity() {
        UtilsListRestaurant.sortProximity(restaurantListFromPlaces);
        this.adapter.notifyDataSetChanged();
    }

    @OnClick(R.id.fragment_list_restaurants_rating_fab)
    void triRate() {
        UtilsListRestaurant.sortRatingReverse(restaurantListFromPlaces);
        this.adapter.notifyDataSetChanged();
    }


    @OnClick(R.id.fragment_list_restaurants_name_fab)
    void triName() {
        UtilsListRestaurant.sortName(restaurantListFromPlaces);
        this.adapter.notifyDataSetChanged();
    }

    @OnClick (R.id.fragment_list_restaurants_refresh_fab)
    void refresh()
    {
        this.viewModelGo4Lunch = null;
        this.onResume();
        this.floatingActionButtonRefresh.setVisibility(View.INVISIBLE);
    }

    ////////////////////////////////////////// CONFIGURE ///////////////////////////////////////////

    /**
     * Configure the RecyclerView
     */
    private void configRecyclerView() {
        this.adapter = new ListRestaurantsAdapter(Glide.with(this), this, getActivity());
        this.recyclerView.setAdapter(adapter);
        this.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    /**
     * Unsubscribe of the HTTP Request
     */
    private void unsubscribe() {
        if (this.disposable != null && !this.disposable.isDisposed()) {
            this.disposable.dispose();
        }
    }

    ////////////////////////////////////////// OVERRIDE METHODS ///////////////////////////////////////////

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        this.unsubscribe();
    }


    @Override
    public void onStop()
    {
        super.onStop();
        this.viewModelGo4Lunch = null;
    }

    @Override
    public void onClickListener(int position) {
        Intent intent = new Intent(getContext(), DetailsActivity.class);
        intent.putExtra("placeId", restaurantListFromPlaces.get(position).getPlaceId());
        startActivity(intent);
    }

    //----------------- V3 WITH WIDGET TODO
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        if (data != null)
        {
            // Take info from data
            Place place = Autocomplete.getPlaceFromIntent(data);
            String placeId = place.getId();
            LatLng latLng = place.getLatLng();
            String name = place.getName();
            RestaurantPOJO.Location location= new RestaurantPOJO.Location();
            location.setLat(Objects.requireNonNull(latLng).latitude);
            location.setLng(latLng.longitude);
            String illustration = Objects.requireNonNull(place.getPhotoMetadatas()).get(0).getAttributions();
            double rating = Objects.requireNonNull(place.getRating());
            String address = Objects.requireNonNull(place.getAddress());

            // Create a Restaurant with this info
            Restaurant restaurantAutocomplete = new Restaurant(name, address, illustration, placeId, rating, false,location);
            restaurantListFromPlaces = new ArrayList<>();
            restaurantListFromPlaces.add(restaurantAutocomplete);
            UtilsListRestaurant.updateDistanceToCurrentLocation(currentLocation, restaurantListFromPlaces);
            this.getRestaurantListFromFirebase();
            this.floatingActionButtonSort.setVisibility(View.INVISIBLE);
            this.floatingActionButtonRefresh.setVisibility(View.VISIBLE);
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    //----------------- V1 + V2 WITHOUT WIDGET TODO
    private RectangularBounds getRectangularBounds(LatLng currentLatLng)
    {
        double temp = 0.01;
        LatLng latLng1 = new LatLng(currentLatLng.latitude-temp, currentLatLng.longitude-temp);
        LatLng latLng2 = new LatLng(currentLatLng.latitude+temp, currentLatLng.longitude+temp);
        return RectangularBounds.newInstance(latLng1, latLng2);
    }


    //----------------- V1 + V2 WITHOUT WIDGET TODO
    public void autocompleteSearch(String input) {
        PlacesClient placesClient = Places.createClient(Objects.requireNonNull(getContext()));
        AutocompleteSessionToken sessionToken = AutocompleteSessionToken.newInstance();
        LatLng currentLatLng = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());

        FindAutocompletePredictionsRequest request = FindAutocompletePredictionsRequest.builder()
                .setLocationRestriction(getRectangularBounds(currentLatLng))
                .setOrigin(currentLatLng)
                .setTypeFilter(TypeFilter.ESTABLISHMENT)
                .setSessionToken(sessionToken)
                .setQuery(input)
                .build();

        placesClient.findAutocompletePredictions(request).addOnSuccessListener(findAutocompletePredictionsResponse ->
        {
            int size = findAutocompletePredictionsResponse.getAutocompletePredictions().size();
            for (int i = 0; i < size; i ++)
            {
                String placeId = findAutocompletePredictionsResponse.getAutocompletePredictions().get(i).getPlaceId();
                //----------------- V2 WITHOUT WIDGET NEW REQUEST TODO
                placeIdList.add(placeId);

                //----------------- V1 WITH WIDGET IN LIST TODO
                /*Restaurant toCompare = new Restaurant();
                toCompare.setPlaceId(placeId);
                if (restaurantListFromPlaces.contains(toCompare))
                {
                    int index = restaurantListFromPlaces.indexOf(toCompare);
                    restaurantListAutocomplete.add(restaurantListFromPlaces.get(index));
                }*/
            }
            getRestaurantFromPlaces();

            //----------------- V1 WITH WIDGET IN LIST TODO
            /*restaurantListFromPlaces = restaurantListAutocomplete;
            adapter.updateList(restaurantListFromPlaces);*/
        });
    }

    //----------------- V2 WITHOUT WIDGET NEW REQUEST TODO
    private void getRestaurantFromPlaces()
    {
        String key = getResources().getString(R.string.google_maps_key);

        for (int i = 0; i < placeIdList.size(); i ++)
        {
            String placeId = placeIdList.get(i);
            this.viewModelGo4Lunch.getRestaurantDetailPlacesMutableLiveData(placeId, key)
                    .observe(this, restaurantObservable -> {
                        disposable = restaurantObservable.subscribeWith(new DisposableObserver<Restaurant>() {
                            @Override
                            public void onNext(Restaurant restaurant)
                            {
                                if (!restaurant.getName().equals("NO_RESTAURANT"))
                                {
                                    if (!restaurantListAutocomplete.contains(restaurant))
                                    {
                                        restaurantListAutocomplete.add(restaurant);
                                        restaurantListFromPlaces = restaurantListAutocomplete;
                                        getRestaurantListFromFirebase();
                                        UtilsListRestaurant.updateDistanceToCurrentLocation(currentLocation, restaurantListFromPlaces);
                                    }
                                }
                            }
                            @Override
                            public void onError(Throwable e) {}
                            @Override
                            public void onComplete() {}
                        });
                    });
        }

    }
}
