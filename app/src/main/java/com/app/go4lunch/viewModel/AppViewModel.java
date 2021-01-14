package com.app.go4lunch.viewModel;

import android.location.Location;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.app.go4lunch.model.Restaurant;
import com.app.go4lunch.model.User;
import com.app.go4lunch.repositories.RestaurantRepository;
import com.app.go4lunch.repositories.RestaurantPlacesRepository;
import com.app.go4lunch.repositories.UserRepository;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

public class AppViewModel extends ViewModel
{
    private RestaurantRepository restaurantRepository;
    private UserRepository userRepository;
    private RestaurantPlacesRepository restaurantPlacesRepository;

    public AppViewModel(RestaurantRepository restaurantRepository,
                        UserRepository userRepository,
                        RestaurantPlacesRepository restaurantPlacesRepository) {
        this.restaurantRepository = restaurantRepository;
        this.userRepository = userRepository;
        this.restaurantPlacesRepository = restaurantPlacesRepository;
    }

    private MutableLiveData<User> userMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<List<User>> userListMutableLiveData = new MutableLiveData<>();

    private MutableLiveData<Restaurant> restaurantMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<List<Restaurant>> restaurantsListMutableLiveData = new MutableLiveData<>();

    private MutableLiveData<Observable<List<Restaurant>>> restaurantsListPlacesMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<Observable<Restaurant>> restaurantDetailPlacesMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<Observable<List<Restaurant>>> restaurantsListPlacesAutocompleteMutableLiveData = new MutableLiveData<>();


    public MutableLiveData<User> getUserCurrentMutableLiveData(String uid)
    {
        if (this.userMutableLiveData != null)
        {
            this.setUserMutableLiveData(uid);
        }

        return this.userMutableLiveData;
    }

    private void setUserMutableLiveData(String uid)
    {
        this.userRepository.getUser(uid).addOnSuccessListener(documentSnapshot ->
        {
            if(documentSnapshot.exists())
            {
                User user = documentSnapshot.toObject(User.class);
                userMutableLiveData.setValue(user);
            }
        });
    }

    public MutableLiveData<List<User>> getUserListMutableLiveData()
    {
        if (this.userListMutableLiveData != null)
        {
            this.setUsersListMutableLiveData();
        }

        return this.userListMutableLiveData;
    }

    private void setUsersListMutableLiveData()
    {
        this.userRepository.getListUsers().addSnapshotListener((queryDocumentSnapshots, e) ->
        {
            if (queryDocumentSnapshots != null)
            {
                List<DocumentSnapshot> userList = queryDocumentSnapshots.getDocuments();
                List<User> users = new ArrayList<>();
                int size = userList.size();
                for (int i = 0; i < size; i ++)
                {
                    User user = userList.get(i).toObject(User.class);
                    users.add(user);
                }

                userListMutableLiveData.setValue(users);
            }else {
                userListMutableLiveData.setValue(null);

            }

        });
    }

    public void createUser (String uid, String email, String username, String urlPicture)
    {
        this.userRepository.createUser(uid, email, username, urlPicture);
    }

    public void updateUserIsChooseRestaurant (String uid, Boolean isChooseRestaurant)
    {
        this.userRepository.updateUserIsChooseRestaurant(uid, isChooseRestaurant);
    }

    public void updateUserRestaurant(String uid, Restaurant restaurantChoose)
    {
        this.userRepository.updateUserRestaurant(uid, restaurantChoose);
    }

    public void updateUserRestaurantListFavorites(String uid, List<Restaurant> restaurantList)
    {
        this.userRepository.updateUserRestaurantListFavorites(uid, restaurantList);
    }

    /////////////////////// RESTAURANT FIREBASE ///////////////////////

    public MutableLiveData<Restaurant> getRestaurantFirebaseMutableLiveData(Restaurant restaurant)
    {
        if (this.restaurantMutableLiveData != null)
        {
            this.setRestaurantMutableLiveData(restaurant);
        }

        return this.restaurantMutableLiveData;
    }

    private void setRestaurantMutableLiveData(Restaurant restaurant)
    {
        this.restaurantRepository.getRestaurant(restaurant.getPlaceId()).addOnSuccessListener(documentSnapshot ->
        {
            if(documentSnapshot.exists())
            {
                Restaurant restaurantToLiveData = documentSnapshot.toObject(Restaurant.class);
                restaurantMutableLiveData.setValue(restaurantToLiveData);
            }
        });
    }

    public MutableLiveData<List<Restaurant>> getRestaurantsListMutableLiveData()
    {
        if (this.restaurantsListMutableLiveData != null)
        {
            this.setRestaurantsListFirebaseMutableLiveData();
        }

        return this.restaurantsListMutableLiveData;
    }

    private void setRestaurantsListFirebaseMutableLiveData()
    {
        this.restaurantRepository.getListRestaurants().addSnapshotListener((queryDocumentSnapshots, e) ->
        {
            if (queryDocumentSnapshots != null)
            {
                List<DocumentSnapshot> documents = queryDocumentSnapshots.getDocuments();

                List<Restaurant> restaurantList = new ArrayList<>();
                int size = documents.size();
                for (int i = 0; i < size; i ++)
                {
                    Restaurant restaurant = documents.get(i).toObject(Restaurant.class);
                    restaurantList.add(restaurant);
                }

                restaurantsListMutableLiveData.setValue(restaurantList);
            }else {
                restaurantsListMutableLiveData.setValue(null);

            }

        });
    }

    public void createRestaurant(String placeId, List<User> userList, String name, String address)
    {
        this.restaurantRepository.createRestaurant(placeId, userList, name, address);
    }

    public void updateRestaurantUserList(String placeId, List<User> userList)
    {
        this.restaurantRepository.updateRestaurantUserList(placeId, userList);
    }


    public MutableLiveData<Observable<List<Restaurant>>> getRestaurantsListPlacesMutableLiveData(double lat, double lng, int radius, String key)
    {
        if (this.restaurantsListPlacesMutableLiveData != null)
        {
            this.setRestaurantsListPlacesMutableLiveData(lat, lng, radius, key);
        }

        return this.restaurantsListPlacesMutableLiveData;
    }

    private void setRestaurantsListPlacesMutableLiveData(double lat, double lng, int radius, String key)
    {
        this.restaurantsListPlacesMutableLiveData.setValue(this.restaurantPlacesRepository.streamFetchRestaurantInList(lat, lng, radius, key));
    }

    public MutableLiveData<Observable<Restaurant>> getRestaurantDetailPlacesMutableLiveData(String placeId, String key)
    {
        if (this.restaurantDetailPlacesMutableLiveData != null)
        {
            this.setRestaurantDetailPlacesMutableLiveData(placeId, key);
        }

        return this.restaurantDetailPlacesMutableLiveData;
    }

    private void setRestaurantDetailPlacesMutableLiveData(String placeId, String key)
    {
        this.restaurantDetailPlacesMutableLiveData.setValue(this.restaurantPlacesRepository.streamDetailRestaurantToRestaurant(placeId, key));
    }

    public MutableLiveData<Observable<List<Restaurant>>> getRestaurantsListPlacesAutocompleteMutableLiveData(String key, String input, Location location, int radius)
    {
        String locationToString = location.getLatitude() + "," + location.getLongitude();
        this.restaurantsListPlacesAutocompleteMutableLiveData.setValue(this.restaurantPlacesRepository.streamAutocompleteRestaurantsToRestaurantList(key, input, locationToString, radius));
        return this.restaurantsListPlacesAutocompleteMutableLiveData;
    }



}
