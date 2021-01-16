package com.app.go4lunch.repositories;

import com.app.go4lunch.model.Restaurant;
import com.app.go4lunch.model.User;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.List;

public class RestaurantRepository
{

    
    public CollectionReference getCollectionRestaurant() {
        return FirebaseFirestore.getInstance().collection("restaurant");
    }

    
    public Task<Void> createRestaurant(String placeId, List<User> userList, String name, String address) {
        Restaurant toCreate = new Restaurant(placeId, userList, name, address);
        return getCollectionRestaurant().document(placeId).set(toCreate);
    }

    
    public Task<DocumentSnapshot> getRestaurant(String placeId) {
        return getCollectionRestaurant().document(placeId).get();
    }

    
    public Query getListRestaurants() {
        return getCollectionRestaurant().orderBy("name");
    }

    public Query getListRestaurantsWithFriends()
    {
        return getCollectionRestaurant().whereGreaterThanOrEqualTo("userList", 1);
    }

    
    public Task<Void> updateRestaurantUserList(String placeId, List<User> userList) {
        return getCollectionRestaurant().document(placeId).update("userList", userList);
    }
}
