package com.app.go4lunch.view.activities.fragments;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.go4lunch.R;
import com.app.go4lunch.model.Restaurant;
import com.app.go4lunch.model.RestaurantResponse;
import com.app.go4lunch.viewModel.AppViewModel;
import com.app.go4lunch.viewModel.factory.ViewModelFactory;
import com.app.go4lunch.viewModel.injection.Injection;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
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


public class MapFragment extends Fragment implements OnMapReadyCallback {


    private Location currentLocation;
    private List<Restaurant> restaurantListFromPlaces;
    private Disposable disposable;
    private AppViewModel appViewModel;
    private GoogleMap googleMap;
    private int radius;
    private float zoom;
    private String key;
    private List<Restaurant> restaurantListAutocomplete = new ArrayList<>();
    private List<String> placeIdList = new ArrayList<>();

    public MapFragment() {
    }
    private MapFragment(Location location) {
        this.currentLocation = location;
    }

    public static MapFragment newInstance(Location location) {
        return new MapFragment(location);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_map, container, false);
        SupportMapFragment supportMapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (supportMapFragment != null) {
            supportMapFragment.getMapAsync(MapFragment.this);
        }
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (appViewModel == null) {
            this.configViewModel();
        }

    }

    public int getRadius() {
        return this.radius;
    }

    ////////////////////////////////////////// VIEW MODEL ///////////////////////////////////////////

    private void configViewModel() {
        ViewModelFactory viewModelFactory = Injection.viewModelFactoryGo4Lunch();
        appViewModel = ViewModelProviders.of(this, viewModelFactory).get(AppViewModel.class);
        this.getRestaurantListFromPlaces();
    }

    private void getRestaurantListFromPlaces() {
        this.appViewModel.getRestaurantsListPlacesMutableLiveData(currentLocation.getLatitude(), currentLocation.getLongitude(), radius, key)
                .observe(this, listObservable -> disposable = listObservable
                        .subscribeWith(new DisposableObserver<List<Restaurant>>() {
                            @Override
                            public void onNext(List<Restaurant> restaurantList) {
                                restaurantListFromPlaces = restaurantList;
                                getRestaurantListFromFirebase(false);

                            }

                            @Override
                            public void onError(Throwable e) {
                            }

                            @Override
                            public void onComplete() {
                            }
                        }));
    }

    private void getRestaurantListFromFirebase(boolean isAutocomplete) {
        this.appViewModel.getRestaurantsListMutableLiveData().observe(this, restaurantList ->
        {
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
            setMarker(isAutocomplete);

        });
    }

    ////////////////////////////////////////// CONFIGURE ///////////////////////////////////////////

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
        MapStyleOptions mapStyleOptions = MapStyleOptions.loadRawResourceStyle
                (Objects.requireNonNull(getContext()), R.raw.google_style);
        this.googleMap.setMapStyle(mapStyleOptions);
    }

    /**
     * Lunch Details Activity when we click on a Restaurant Marker
     *
     * @param marker from the Google Map
     */
    private void lunchDetailsActivity(Marker marker) {
        String placeId = (String) marker.getTag();
//        Intent intent = new Intent(getContext(), DetailsActivity.class);
//        intent.putExtra("placeId", placeId);
//        startActivity(intent);
    }

    /**
     * Set the markers on GoogleMap
     */
    private void setMarker(boolean isAutocomplete)
    {
        int size = restaurantListFromPlaces.size();
        for (int i = 0; i < size; i++) {
            Restaurant restaurantTemp = restaurantListFromPlaces.get(i);
            LatLng tempLatLng = new LatLng(restaurantTemp.getLocation().getLat(), restaurantTemp.getLocation().getLng());
            MarkerOptions tempMarker = new MarkerOptions().position(tempLatLng).title(restaurantTemp.getName());

            if (restaurantTemp.getUserList() != null && restaurantTemp.getUserList().size() > 0) {
                tempMarker.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_marker_green));
            } else {
                tempMarker.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_marker_orange));
            }

            Marker markerFinal = googleMap.addMarker(tempMarker);
            markerFinal.setTag(restaurantTemp.getPlaceId());
            this.googleMap.setOnInfoWindowClickListener(this::lunchDetailsActivity);
        }


        if (!isAutocomplete)
        {
            LatLng latLng = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
            MarkerOptions markerOptions = new MarkerOptions().position(latLng).title(getString(R.string.current_location));
            if (this.zoom == 0) {
                this.zoom = 16;
                this.googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));
            }
            this.googleMap.addMarker(markerOptions);
        }
        else
        {
            LatLng tempLatLng = new LatLng(restaurantListFromPlaces.get(0).getLocation().getLat(), restaurantListFromPlaces.get(0).getLocation().getLng());
            this.googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(tempLatLng, 20));
        }

    }


    /////////////////////////////////// DESTROY METHODS ///////////////////////////////////

    /**
     * Unsubscribe of the HTTP Request
     */
    private void unsubscribe() {
        if (this.disposable != null && !this.disposable.isDisposed()) {
            this.disposable.dispose();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.unsubscribe();
    }

    @Override
    public void onStop()
    {
        super.onStop();
        this.appViewModel = null;
        this.restaurantListAutocomplete = new ArrayList<>();
    }

    //----------------- V1 WITH WIDGET TODO
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (data != null)
        {
            // Take info from data
            Place place = Autocomplete.getPlaceFromIntent(data);
            String placeId = place.getId();
            LatLng latLng = place.getLatLng();
            String name = place.getName();
            RestaurantResponse.Location location= new RestaurantResponse.Location();
            location.setLat(Objects.requireNonNull(latLng).latitude);
            location.setLng(Objects.requireNonNull(latLng).longitude);

            // Create a Restaurant with this info
            Restaurant restaurantAutocomplete = new Restaurant();
            restaurantAutocomplete.setPlaceId(placeId);
            restaurantAutocomplete.setLocation(location);
            restaurantAutocomplete.setName(name);
            restaurantListFromPlaces = new ArrayList<>();
            restaurantListFromPlaces.add(restaurantAutocomplete);

            // Load the request in Firebase
            this.getRestaurantListFromFirebase(true);
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
                //placeIdList.add(placeId);

                //----------------- V1 WITH WIDGET IN LIST TODO
                Restaurant toCompare = new Restaurant();
                toCompare.setPlaceId(placeId);
                if (restaurantListFromPlaces.contains(toCompare))
                {
                    int index = restaurantListFromPlaces.indexOf(toCompare);
                    restaurantListAutocomplete.add(restaurantListFromPlaces.get(index));
                }
            }
            //----------------- V2 WITHOUT WIDGET NEW REQUEST TODO
            //getRestaurantFromPlaces();

            if (restaurantListAutocomplete.size() > 0)
            {
                googleMap.clear();
                restaurantListFromPlaces = restaurantListAutocomplete;
                setMarker(false);
            }

        });
    }
}
