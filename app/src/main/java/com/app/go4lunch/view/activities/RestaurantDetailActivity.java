package com.app.go4lunch.view.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.app.go4lunch.R;
import com.app.go4lunch.databinding.ActivityRestaurantDetailBinding;
import com.app.go4lunch.di.DI;
import com.app.go4lunch.helpers.UtilsListRestaurant;
import com.app.go4lunch.model.Restaurant;
import com.app.go4lunch.model.User;
import com.app.go4lunch.view.adapter.FriendsRecyclerAdapter;
import com.app.go4lunch.viewModel.AppViewModel;
import com.app.go4lunch.viewModel.factory.ViewModelFactory;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;

public class RestaurantDetailActivity extends AppCompatActivity {

    private String placeId;
    private Restaurant restaurantFinal;
    private Disposable disposable;
    private User currentUser;
    private String uidUser;
    private FriendsRecyclerAdapter adapter;
    private List<User> workmatesList;
    private List<Restaurant> restaurantsListFromFirebase;
    private AppViewModel appViewModel;

    private final static int REQUEST_CODE_CALL = 13;
    private static final String NO_RESTAURANT = "NO_RESTAURANT";

    ActivityRestaurantDetailBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_restaurant_detail);
        placeId = getIntent().getStringExtra("placeId");

        initToolbar();
        initRecyclerView();
        initViewModel();
        initViews();
        setOnClickListeners();
    }

    private void setOnClickListeners() {
        binding.btnSelect.setOnClickListener(v->{
            onSelectClick();
        });

        binding.btnCall.setOnClickListener(v->{
            onCallClick();
        });

        binding.btnLike.setOnClickListener(v->{
            onLikeClick();
        });

        binding.btnWebsite.setOnClickListener(v->{
            onWebsiteClick();
        });
    }

    private void initViews(){
        binding.progressBar.setVisibility(View.VISIBLE);
        binding.tvNoRestaurant.setVisibility(View.INVISIBLE);
        binding.btnSelect.setVisibility(View.INVISIBLE);
    }
    private void initToolbar()
    {
        setSupportActionBar(binding.toolBar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        binding.toolBar.setNavigationIcon(R.drawable.ic_return);
    }


    private void initViewModel()
    {
        ViewModelFactory viewModelFactory = DI.getViewModelFactory();
        appViewModel = ViewModelProviders.of(this, viewModelFactory).get(AppViewModel.class);
        getRestaurantFromPlaces();
    }

    private void getRestaurantFromPlaces()
    {
        String key = getString(R.string.google_maps_key);
        appViewModel.getRestaurantDetailPlacesMutableLiveData(placeId, key)
                .observe(this, restaurantObservable -> {
                    disposable = restaurantObservable.subscribeWith(new DisposableObserver<Restaurant>() {
                        @Override
                        public void onNext(Restaurant restaurant)
                        {
                            if (restaurant.getName().equals(NO_RESTAURANT))
                            {
                                binding.progressBar.setVisibility(View.INVISIBLE);
                                binding.tvNoRestaurant.setVisibility(View.VISIBLE);
                            }
                            else
                            {
                                restaurantFinal = restaurant;
                                getRestaurantListFromFirebase();
                                getRestaurantFinalFromFirebase();
                            }
                        }
                        @Override
                        public void onError(Throwable e) {}
                        @Override
                        public void onComplete() {}
                    });
                });
    }

    private void getRestaurantFinalFromFirebase()
    {
        appViewModel.getRestaurantFirebaseMutableLiveData(restaurantFinal)
                .observe(this, restaurant -> {

                    workmatesList = restaurant.getUserList();
                    restaurantFinal.setUserList(workmatesList);
                    adapter.updateList(workmatesList);
                });
    }

    private void getRestaurantListFromFirebase()
    {
        appViewModel.getRestaurantsListMutableLiveData().observe(this, restaurantList -> {
            restaurantsListFromFirebase = restaurantList;
            if (!restaurantsListFromFirebase.contains(restaurantFinal))
            {
                workmatesList = new ArrayList<>();
                appViewModel.createRestaurant(restaurantFinal.getPlaceId(),workmatesList, restaurantFinal.getName(), restaurantFinal.getAddress());
            }
            getCurrentUser();
        });
    }

    private void getCurrentUser()
    {
        uidUser = FirebaseAuth.getInstance().getUid();
        appViewModel.getUserCurrentMutableLiveData(uidUser).observe(this, user -> {
            currentUser = user;
            updateRestaurant(restaurantFinal);
        });
    }

    ////////////////////////////////////////// ONCLICK ///////////////////////////////////////////

    void onCallClick()
    {
        if (!restaurantFinal.getPhoneNumber().equals(""))
        {
            String phone = restaurantFinal.getPhoneNumber();
            Uri uri = Uri.parse("tel:"+phone);
            Intent intent = new Intent(Intent.ACTION_CALL, uri);

            if (ActivityCompat.checkSelfPermission(Objects.requireNonNull(this), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED)
            {
                ActivityCompat.requestPermissions(Objects.requireNonNull(this), new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CODE_CALL);
            }
            else
            {
                startActivity(intent);
            }
        }
        else
        {
            Toast.makeText(this, getString(R.string.phone_not_available), Toast.LENGTH_LONG).show();
        }
    }

    void onLikeClick()
    {
        List<Restaurant> restaurantList;
        if (currentUser.getRestaurantListFavorites() == null)
        {
            restaurantList = new ArrayList<>();
        }
        else
        {
            restaurantList = currentUser.getRestaurantListFavorites();
        }
        if (!currentUser.getRestaurantListFavorites().contains(restaurantFinal))
        {
            restaurantList.add(restaurantFinal);
        }
        else
        {
            restaurantList.remove(restaurantFinal);
        }
        appViewModel.updateUserRestaurantListFavorites(uidUser, restaurantList);
        updateLike();
    }

    void onWebsiteClick()
    {
        if (!restaurantFinal.getWebsite().equals(""))
        {
            String url = restaurantFinal.getWebsite();
            if(!url.startsWith("https://") && !url.startsWith("http://"))
            {
                url = "http://" + url;
            }
            Uri uri = Uri.parse(url);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);

            if (intent.resolveActivity(Objects.requireNonNull(this).getPackageManager()) != null)
            {
                startActivity(intent);
            }
            else
            {
                Toast.makeText(this, getString(R.string.unable_to_find_compatible_application), Toast.LENGTH_LONG).show();
            }
        }
        else
        {
            Toast.makeText(this, getString(R.string.no_website_available), Toast.LENGTH_LONG).show();
        }
    }

    void onSelectClick()
    {
        User UserPushOnFirebase = new User(currentUser.getEmail(),currentUser.getName(), currentUser.getIllustration());
        if(!currentUser.isChooseRestaurant() || !currentUser.getRestaurantChoose().equals(restaurantFinal))
        {
            if (currentUser.isChooseRestaurant())
            {
                updateOtherRestaurantInFirebase(currentUser.getRestaurantChoose());
            }
            currentUser.setRestaurantChoose(restaurantFinal);
            binding.btnSelect.setImageResource(R.drawable.ic_choose_restaurant);
            workmatesList.add(UserPushOnFirebase);
        }
        else
        {
            currentUser.unSetRestaurantChoose();
            binding.btnSelect.setImageResource(R.drawable.ic_choose_not_restaurant);
            workmatesList.remove(UserPushOnFirebase);
        }
        appViewModel.updateRestaurantUserList(restaurantFinal.getPlaceId(), workmatesList);
        appViewModel.updateUserRestaurant(uidUser, currentUser.getRestaurantChoose());
        appViewModel.updateUserIsChooseRestaurant(uidUser, currentUser.isChooseRestaurant());
        adapter.notifyDataSetChanged();
    }

    ////////////////////////////////////////// UPDATE AND CONFIG ///////////////////////////////////////////

    /**
     * Update the restaurant when we have all info from Firebase and Places
     * @param restaurant : RestaurantFinal
     */
    private void updateRestaurant(Restaurant restaurant)
    {
        binding.tvRestaurantName.setText(restaurant.getName());
        Glide.with(this).load(restaurant.getIllustration()).apply(RequestOptions.centerCropTransform()).into(binding.imgRestaurant);
        binding.tvRestaurantAddress.setText(restaurant.getAddress());
        configButton();
        updateLike();
        UtilsListRestaurant.updateRating(binding.imgStar1, binding.imgStar2, binding.imgStar3, restaurant);
        binding.progressBar.setVisibility(View.INVISIBLE);
        binding.btnSelect.setVisibility(View.VISIBLE);
    }

    /**
     * Update the star with ListRestaurantFavorites of the User
     */
    private void updateLike()
    {
        boolean favorite = false;

        if (currentUser.getRestaurantListFavorites() != null)
        {
            if (currentUser.getRestaurantListFavorites().contains(restaurantFinal))
            {
                favorite = true;
            }
        }

        if (favorite)
        {
            binding.btnLike.setImageResource(R.drawable.ic_star_yellow_24dp);
        }
        else
        {
            binding.btnLike.setImageResource(R.drawable.ic_star_orange_24dp);
        }
    }

    /**
     * Update an other restaurant in Firebase, if the User has choose it before
     * @param restaurant was choose before
     */
    private void updateOtherRestaurantInFirebase(Restaurant restaurant)
    {
        if (restaurantsListFromFirebase.contains(restaurant))
        {
            User UserPushOnFirebase = new User(currentUser.getEmail(),currentUser.getName(),
                    currentUser.getIllustration());
            int index = restaurantsListFromFirebase.indexOf(restaurant);
            List<User> tempListWorkmates = restaurantsListFromFirebase.get(index).getUserList();
            tempListWorkmates.remove(UserPushOnFirebase);
            appViewModel.updateRestaurantUserList(restaurant.getPlaceId(),
                    tempListWorkmates);
        }
    }

    /**
     * Config the Button with chooseRestaurant of the User
     */
    private void configButton()
    {
        if (!currentUser.isChooseRestaurant() || !currentUser.getRestaurantChoose().equals(restaurantFinal))
        {
            binding.btnSelect.setImageResource(R.drawable.ic_choose_not_restaurant);
        }
        else
        {
            binding.btnSelect.setImageResource(R.drawable.ic_choose_restaurant);
        }
    }

    private void initRecyclerView()
    {
        adapter = new FriendsRecyclerAdapter(Glide.with(this), position -> {

        });
        binding.rvFriendsRestaurant.setAdapter(adapter);
    }

    /////////////////////////////////// DESTROY METHODS ///////////////////////////////////

    /**
     * Unsubscribe of the HTTP Request
     */
    private void unsubscribe()
    {
        if (disposable != null && !disposable.isDisposed())
        {
            disposable.dispose();
        }
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        unsubscribe();
    }
}