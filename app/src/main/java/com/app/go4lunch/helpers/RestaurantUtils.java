package com.app.go4lunch.helpers;

import android.location.Location;
import android.view.View;
import android.widget.ImageView;


import com.app.go4lunch.model.Restaurant;

import java.util.Collections;
import java.util.List;

public abstract class RestaurantUtils
{

    /**
     * Sort the restaurants according to their distance from the CurrentUser
     */
    public static void sortByDistance(List<Restaurant> restaurantList)
    {
        Collections.sort(restaurantList, (o1, o2) -> {
            Integer o1DistanceCurrentUser = o1.getDistanceCurrentUser();
            Integer o2DistanceCurrentUser = o2.getDistanceCurrentUser();
            return o1DistanceCurrentUser.compareTo(o2DistanceCurrentUser);
        });
    }

    /**
     * Sort the restaurants according to their rating
     */
    public static void sortByRating(List<Restaurant> restaurantList)
    {
        Collections.sort(restaurantList, (o1, o2) -> {
            Double o1Rating = o1.getRating();
            Double o2Rating = o2.getRating();
            return o1Rating.compareTo(o2Rating);
        });
        Collections.reverse(restaurantList);
    }

    /**
     * Sort the restaurants according to their name
     */
    public static void sortByName(List<Restaurant> restaurantList)
    {
        Collections.sort(restaurantList, (o1, o2) -> {
            String o1Name = o1.getName();
            String o2Name = o2.getName();
            return o1Name.compareTo(o2Name);
        });
    }

    /**
     * Update the attribute DistanceCurrentUser for each restaurant
     */
    public static void updateDistanceToCurrentLocation(Location currentLocation, List<Restaurant> restaurantList)
    {
        Location restaurantLocation = new Location("fusedLocationProvider");
        int size = restaurantList.size();
        for (int i = 0; i < size; i++)
        {
            //Get the restaurant's location
            restaurantLocation.setLatitude(restaurantList.get(i).getLocation().getLat());
            restaurantLocation.setLongitude(restaurantList.get(i).getLocation().getLng());
            //Get the distance between currentLocation and restaurantLocation
            int distanceLocation = (int) currentLocation.distanceTo(restaurantLocation);
            restaurantList.get(i).setDistanceCurrentUser(distanceLocation);
        }
    }




}
