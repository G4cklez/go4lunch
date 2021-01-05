package com.app.go4lunch.viewModel.repositories;

import com.app.go4lunch.model.Restaurant;
import com.app.go4lunch.model.User;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.List;

public class UserRepository
{
    
    public CollectionReference getCollectionUser() {
        return FirebaseFirestore.getInstance().collection("users");
    }

    
    public Query getListUsers() {
        return getCollectionUser().orderBy("chooseRestaurant", Query.Direction.DESCENDING);

    }

    
    public Task<Void> createUser(String uid, String email, String username, String urlPicture) {
        User toCreate = new User(email, username, urlPicture);
        return getCollectionUser().document(uid).set(toCreate);
    }

    
    public Task<DocumentSnapshot> getUser(String uid) {
        return getCollectionUser().document(uid).get();
    }

    
    public Task<Void> updateUserIsChooseRestaurant(String uid, Boolean isChooseRestaurant) {
        return getCollectionUser().document(uid).update("chooseRestaurant", isChooseRestaurant);

    }

    
    public Task<Void> updateUserRestaurant(String uid, Restaurant restaurantChoose) {
        return getCollectionUser().document(uid).update("restaurantChoose", restaurantChoose);

    }

    
    public Task<Void> updateUserRestaurantListFavorites(String uid, List<Restaurant> restaurantListFavorites) {
        return getCollectionUser().document(uid).update("restaurantListFavorites", restaurantListFavorites);

    }

}
