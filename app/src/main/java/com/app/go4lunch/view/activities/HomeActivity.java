package com.app.go4lunch.view.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.core.view.GravityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.go4lunch.R;
import com.app.go4lunch.constants.Constants;
import com.app.go4lunch.databinding.ActivityHomeBinding;
import com.app.go4lunch.helpers.LocationActivity;
import com.app.go4lunch.helpers.SharedPrefManager;
import com.app.go4lunch.helpers.CalculationUtils;
import com.app.go4lunch.model.User;
import com.app.go4lunch.notificationWorkManager.WorkerNotificationController;
import com.app.go4lunch.view.activities.fragments.FriendsListFragment;
import com.app.go4lunch.view.activities.fragments.MapFragment;
import com.app.go4lunch.view.activities.fragments.RestaurantListFragment;
import com.app.go4lunch.viewModel.AppViewModel;
import com.app.go4lunch.viewModel.ViewModelFactory;
import com.app.go4lunch.di.DI;
import com.bumptech.glide.Glide;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.model.RectangularBounds;
import com.google.android.libraries.places.api.model.TypeFilter;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class HomeActivity extends LocationActivity implements NavigationView.OnNavigationItemSelectedListener {

    private MapFragment mapFragment;
    private RestaurantListFragment restaurantListFragment;
    private FriendsListFragment friendsListFragment;

    private Location currentLocation;
    private AppViewModel appViewModel;
    private User user;

    private static final int AUTOCOMPLETE_REQUEST_CODE = 15;

    Fragment currentFragment;
    ActivityHomeBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home);
        setSupportActionBar(binding.toolBar);
        fetchLocation();
        initBottomNav();
        initDrawerLayout();
        initNavView();

        if (!Places.isInitialized()) {
            Places.initialize(this, getString(R.string.google_maps_key), Locale.US);
        }
        binding.searchBar.setVisibility(View.VISIBLE);
        binding.searchBar.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                String input = binding.searchBar.getText().toString();
                if (currentFragment == restaurantListFragment) {
                    restaurantListFragment.searchOnList(input);
                }else  if (currentFragment == mapFragment) {
                    mapFragment.searchOnMap(input);
                }
                return true;
            } else {
                return false;
            }
        });

        binding.searchBar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String input = s.toString();
                if (currentFragment == mapFragment && s != null) {
                    mapFragment.searchOnMap(input);
                }else if(currentFragment == restaurantListFragment && s!=null){
                   restaurantListFragment.searchOnList(input);
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        this.configViewModel();
    }

    ///////////////////////////////////VIEW MODEL///////////////////////////////////

    private void configViewModel() {
        ViewModelFactory viewModelFactoryGo4Lunch = DI.getViewModelFactory();
        appViewModel = ViewModelProviders.of(this, viewModelFactoryGo4Lunch).get(AppViewModel.class);
        getuser();
    }

    private void getuser() {
        String uidUser = FirebaseAuth.getInstance().getUid();
        this.appViewModel.getUserCurrentMutableLiveData(uidUser).observe(this, user -> {
            updateNavigationHeader(user);
            this.user = user;
        });
    }


    private void initDrawerLayout() {
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, binding.navigationDrawer, binding.toolBar, R.string.open_drawer, R.string.close_drawer);
        binding.navigationDrawer.addDrawerListener(toggle);
        toggle.syncState();
    }

    private void initNavView() {
        binding.navigationDrawerNavView.setNavigationItemSelectedListener(this);
    }

    /**
     * Configure the BottomView {@link BottomNavigationView}
     */
    private void initBottomNav() {
        binding.bottomNavigation.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.action_mapview:
                    displayFragment(displayMapViewFragment());
                    binding.toolBar.getMenu().findItem(R.id.toolbar_menu_search).setVisible(true);
                    binding.searchBar.setVisibility(View.VISIBLE);
                    binding.searchBar.setText("");
                    return true;
                case R.id.action_listview:
                    displayFragment(displayRestaurantListFragment());
                    binding.toolBar.getMenu().findItem(R.id.toolbar_menu_search).setVisible(true);
                    binding.searchBar.setVisibility(View.VISIBLE);
                    binding.searchBar.setText("");
                    return true;
                case R.id.action_friends:
                    displayFragment(displayFriendListFragment());
                    binding.toolBar.getMenu().findItem(R.id.toolbar_menu_search).setVisible(false);
                    binding.searchBar.setVisibility(View.INVISIBLE);
                    return true;
                default:
                    return false;
            }
        });
    }

    /**
     * Update the NavigationView's info {@link NavigationView}
     */
    private void updateNavigationHeader(User user) {
        final View headerView = binding.navigationDrawerNavView.getHeaderView(0);
        TextView nameUser = headerView.findViewById(R.id.nav_header_name_txt);
        TextView emailUser = headerView.findViewById(R.id.nav_header_email_txt);
        ImageView illustrationUser = headerView.findViewById(R.id.nav_header_image_view);
        nameUser.setText(user.getName());
        emailUser.setText(user.getEmail());
        if (user.getIllustration() != null) {
            Glide.with(this).load(user.getIllustration()).circleCrop().into(illustrationUser);
        }
    }

    /**
     * Display a fragment
     */
    private void displayFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.navigation_drawer_frame_layout, fragment).addToBackStack("backstack").commit();
        currentFragment = fragment;
    }


    private MapFragment displayMapViewFragment() {
        if (this.mapFragment == null) {
            this.mapFragment = MapFragment.newInstance(currentLocation);
        }
        return this.mapFragment;
    }

    /**
     * Display the RestaurantListFragment {@link com.app.go4lunch.view.activities.fragments.RestaurantListFragment}
     */
    private com.app.go4lunch.view.activities.fragments.RestaurantListFragment displayRestaurantListFragment() {
        if (this.restaurantListFragment == null) {
            this.restaurantListFragment = restaurantListFragment.newInstance(currentLocation);
        }
        return this.restaurantListFragment;
    }


    private FriendsListFragment displayFriendListFragment() {
        if (this.friendsListFragment == null) {
            this.friendsListFragment = FriendsListFragment.newInstance();
        }
        return friendsListFragment;
    }


    /**
     * Configure the toolbar search with {@link Autocomplete}
     */
    private void configureAutocompleteSearchToolbar(double radius) {
        List<Place.Field> fields = Arrays.asList(Place.Field.ID, Place.Field.NAME,
                Place.Field.LAT_LNG, Place.Field.RATING, Place.Field.ADDRESS,
                Place.Field.OPENING_HOURS, Place.Field.PHOTO_METADATAS);
        LatLng currentLatLng = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
        List<LatLng> latLngForRectangularBounds = CalculationUtils.
                calculateRectangularBoundsAccordingToCurrentLocation(radius, currentLatLng);
        RectangularBounds rectangularBounds = RectangularBounds.newInstance
                (latLngForRectangularBounds.get(0), latLngForRectangularBounds.get(1));
        Intent intent = new Autocomplete.IntentBuilder(AutocompleteActivityMode.OVERLAY, fields)
                .setLocationRestriction(rectangularBounds)
                .setTypeFilter(TypeFilter.ESTABLISHMENT)
                .build(getApplicationContext());
        startActivityForResult(intent, AUTOCOMPLETE_REQUEST_CODE);
    }

    private void fetchLocation() {
        requestLocation(new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                currentLocation = location;
                displayFragment(displayMapViewFragment());
            }
        });
    }


    /////////////////////////////////// METHODS FOR MENU'S NAVIGATION VIEW ONCLICK ///////////////////////////////////


    private void navigateToDetails() {
        if (user.isChooseRestaurant()) {
            Intent intent = new Intent(this, RestaurantDetailActivity.class);
            intent.putExtra(Constants.PLACE_ID, user.getRestaurantChoose().getPlaceId());
            startActivity(intent);
        }
    }


    private void logOut() {
        AuthUI.getInstance().signOut(this).addOnSuccessListener(this, aVoid ->
        {
            if (FirebaseAuth.getInstance().getCurrentUser() == null) {
                Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }

    private void showSettingsDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.settings));
        builder.setMessage(getString(R.string.setting_dialog_msg));
        builder.setPositiveButton(getString(R.string.yes),
                (dialogInterface, i) -> {
                    SharedPrefManager.getInstance(this).saveBoolean(Constants.NOTIFICATION_ENABLED, true);
                    WorkerNotificationController.startWorkRequest(getApplicationContext());
                });
        builder.setNegativeButton(getString(R.string.no),
                (dialog, which) -> {
                    SharedPrefManager.getInstance(this).saveBoolean(Constants.NOTIFICATION_ENABLED, false);
                    WorkerNotificationController.stopWorkRequest(getApplicationContext());
                });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }


    @Override
    public void onBackPressed() {
        if (binding.navigationDrawer.isDrawerOpen(GravityCompat.START)) {
            binding.navigationDrawer.closeDrawer(GravityCompat.START);
        } else {
            onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.menu_drawer_lunch:
                navigateToDetails();
                break;
            case R.id.menu_drawer_settings:
                showSettingsDialog();
                break;
            case R.id.menu_drawer_logout:
                logOut();
                break;
        }
        binding.navigationDrawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.toolbar_menu_search) {
            int radius = mapFragment.getRadius() / 100;
            configureAutocompleteSearchToolbar(radius);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                if (data != null) {
                    currentFragment.onActivityResult(requestCode, resultCode, data);
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        this.fetchLocation();
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}