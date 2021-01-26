package com.app.go4lunch.view.activities.fragments;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.go4lunch.R;
import com.app.go4lunch.constants.Constants;
import com.app.go4lunch.databinding.FragmentRestaurantListBinding;
import com.app.go4lunch.helpers.RestaurantUtils;
import com.app.go4lunch.model.Restaurant;
import com.app.go4lunch.model.RestaurantResponse;
import com.app.go4lunch.view.activities.RestaurantDetailActivity;
import com.app.go4lunch.view.adapter.RestaurantRecyclerAdapter;
import com.app.go4lunch.viewModel.AppViewModel;
import com.app.go4lunch.viewModel.ViewModelFactory;
import com.app.go4lunch.di.DI;
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

    private List<Restaurant> mRestaurantList = new ArrayList();
    ;
    private RestaurantRecyclerAdapter adapter;
    private Location currentLocation;
    private AppViewModel appViewModel;
    private Disposable disposable;
    private int radius;
    private List<Restaurant> restaurantListAutocomplete = new ArrayList<>();
    private List<String> placeIdList = new ArrayList<>();
    FragmentRestaurantListBinding binding;

    public RestaurantListFragment() {
    }

    private RestaurantListFragment(Location location) {
        currentLocation = location;
    }

    public static RestaurantListFragment newInstance(Location location) {
        return new RestaurantListFragment(location);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        radius = 5000;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_restaurant_list, container, false);
        binding.progressBar.setVisibility(View.VISIBLE);
        binding.fragmentListRestaurantsMenuFab.setVisibility(View.INVISIBLE);
        binding.fragmentListRestaurantsRefreshFab.setVisibility(View.INVISIBLE);
        initRecyclerView();
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setOnClickListeners();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (appViewModel == null) {
            initViewModel();
        }
    }

    ////////////////////////////////////////// VIEW MODEL ///////////////////////////////////////////

    private void initViewModel() {
        ViewModelFactory viewModelFactoryGo4Lunch = DI.getViewModelFactory();
        appViewModel = ViewModelProviders.of(this, viewModelFactoryGo4Lunch).get(AppViewModel.class);
        getRestaurantList();
    }

    private void getRestaurantList() {
        String key = getResources().getString(R.string.google_maps_key);
        appViewModel.getRestaurantsListPlacesMutableLiveData(currentLocation.getLatitude(), currentLocation.getLongitude(), radius, key)
                .observe(this, listObservable -> disposable = listObservable
                        .subscribeWith(new DisposableObserver<List<Restaurant>>() {
                            @Override
                            public void onNext(List<Restaurant> restaurantList) {
                                mRestaurantList = restaurantList;
                                RestaurantUtils.updateDistanceToCurrentLocation(currentLocation, mRestaurantList);
                                getRestaurantListFromFirebase();
                            }

                            @Override
                            public void onError(Throwable e) {
                            }

                            @Override
                            public void onComplete() {
                            }
                        }));

    }

    private void getRestaurantListFromFirebase() {
        appViewModel.getRestaurantsListMutableLiveData().observe(this, restaurantList -> {
            if (restaurantList != null) {
                int size = restaurantList.size();
                for (int i = 0; i < size; i++) {
                    Restaurant restaurant = restaurantList.get(i);
                    if (restaurant.getUserList().size() > 0) {
                        if (mRestaurantList.contains(restaurant)) {
                            int index = mRestaurantList.indexOf(restaurant);
                            mRestaurantList.get(index).setUserList(restaurant.getUserList());
                        }
                    }
                }
            }
            adapter.updateList(mRestaurantList);
            binding.progressBar.setVisibility(View.INVISIBLE);
            binding.fragmentListRestaurantsMenuFab.setVisibility(View.VISIBLE);

        });
    }


    void setOnClickListeners() {
        binding.fragmentListRestaurantsNearMeFab.setOnClickListener(v -> {
            RestaurantUtils.sortByDistance(mRestaurantList);
            adapter.notifyDataSetChanged();
        });
        binding.fragmentListRestaurantsRatingFab.setOnClickListener(v -> {
            RestaurantUtils.sortByRating(mRestaurantList);
            adapter.notifyDataSetChanged();
        });
        binding.fragmentListRestaurantsNameFab.setOnClickListener(v -> {
            RestaurantUtils.sortByName(mRestaurantList);
            adapter.notifyDataSetChanged();
        });
        binding.fragmentListRestaurantsRefreshFab.setOnClickListener(v -> {
            appViewModel = null;
            onResume();
            binding.fragmentListRestaurantsRefreshFab.setVisibility(View.INVISIBLE);
        });

    }


    private void initRecyclerView() {
        adapter = new RestaurantRecyclerAdapter(Glide.with(this), position -> {
            Intent intent = new Intent(getContext(), RestaurantDetailActivity.class);
            intent.putExtra(Constants.PLACE_ID, mRestaurantList.get(position).getPlaceId());
            startActivity(intent);
        });
        binding.fragmentListRestaurantsRecyclerView.setAdapter(adapter);
        binding.fragmentListRestaurantsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    private void unsubscribeObservers() {
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        unsubscribeObservers();
    }


    @Override
    public void onStop() {
        super.onStop();
        appViewModel = null;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (data != null) {
            // Take info from data
            Place place = Autocomplete.getPlaceFromIntent(data);
            String placeId = place.getId();
            LatLng latLng = place.getLatLng();
            String name = place.getName();
            RestaurantResponse.Location location = new RestaurantResponse.Location();
            location.setLat(Objects.requireNonNull(latLng).latitude);
            location.setLng(latLng.longitude);
            String illustration = Objects.requireNonNull(place.getPhotoMetadatas()).get(0).getAttributions();
            double rating = Objects.requireNonNull(place.getRating());
            String address = Objects.requireNonNull(place.getAddress());

            Restaurant restaurantAutocomplete = new Restaurant(name, address, illustration, placeId, rating, false, location);
            mRestaurantList = new ArrayList<>();
            mRestaurantList.add(restaurantAutocomplete);
            RestaurantUtils.updateDistanceToCurrentLocation(currentLocation, mRestaurantList);
            getRestaurantListFromFirebase();
            binding.fragmentListRestaurantsMenuFab.setVisibility(View.INVISIBLE);
            binding.fragmentListRestaurantsRefreshFab.setVisibility(View.VISIBLE);
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    private RectangularBounds getRectangularBounds(LatLng currentLatLng) {
        double temp = 0.01;
        LatLng latLng1 = new LatLng(currentLatLng.latitude - temp, currentLatLng.longitude - temp);
        LatLng latLng2 = new LatLng(currentLatLng.latitude + temp, currentLatLng.longitude + temp);
        return RectangularBounds.newInstance(latLng1, latLng2);
    }

    public void searchOnList(String input) {
        if (getContext() != null) {
            if (input != null && input.length() > 0) {
                List<Restaurant> temp = new ArrayList();
                for (Restaurant d : mRestaurantList) {
                    //or use .equal(text) with you want equal match
                    //use .toLowerCase() for better matches
                    if (d.getName().toLowerCase().contains(input)) {
                        temp.add(d);
                    }
                }
                //update recyclerview
                adapter.updateList(temp);
            } else {
                adapter.updateList(mRestaurantList);
            }
        }
    }

    public void performAutoCompleteSearch(String input) {
        if (getContext() != null) {
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
                for (int i = 0; i < size; i++) {
                    String placeId = findAutocompletePredictionsResponse.getAutocompletePredictions().get(i).getPlaceId();
                    placeIdList.add(placeId);

                }
                getRestaurantFromPlaces();
            });
        }
    }

    private void getRestaurantFromPlaces() {
        String key = getResources().getString(R.string.google_maps_key);

        for (int i = 0; i < placeIdList.size(); i++) {
            String placeId = placeIdList.get(i);
            appViewModel.getRestaurantDetailPlacesMutableLiveData(placeId, key)
                    .observe(this, restaurantObservable -> {
                        disposable = restaurantObservable.subscribeWith(new DisposableObserver<Restaurant>() {
                            @Override
                            public void onNext(Restaurant restaurant) {
                                if (!restaurant.getName().equals("NO_RESTAURANT")) {
                                    if (!restaurantListAutocomplete.contains(restaurant)) {
                                        restaurantListAutocomplete.add(restaurant);
                                        mRestaurantList = restaurantListAutocomplete;
                                        getRestaurantListFromFirebase();
                                        RestaurantUtils.updateDistanceToCurrentLocation(currentLocation, mRestaurantList);
                                    }
                                }
                            }

                            @Override
                            public void onError(Throwable e) {
                            }

                            @Override
                            public void onComplete() {
                            }
                        });
                    });
        }

    }
}
