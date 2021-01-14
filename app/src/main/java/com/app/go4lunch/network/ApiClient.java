package com.app.go4lunch.network;

import com.app.go4lunch.model.RestaurantDetailResponse;
import com.app.go4lunch.model.RestaurantResponse;

import io.reactivex.Observable;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiClient
{

    @GET("queryautocomplete/json?")
    Observable<RestaurantResponse> getPlacesAutocompleteResult(@Query("key") String key,
                                                               @Query("input") String input,
                                                               @Query("location") String location,
                                                               @Query("radius") int radius);
    @GET("nearbysearch/json?")
    Observable<RestaurantResponse> getNearbyRestaurants (@Query("location") String location,
                                                         @Query("radius") int radius,
                                                         @Query ("type") String type,
                                                         @Query("opening_hours") Boolean openingHours,
                                                         @Query("key") String key);


    @GET("details/json?")
    Observable<RestaurantDetailResponse> getRestaurantDetails(@Query("place_id") String placeId,
                                                              @Query("key") String key);



    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://maps.googleapis.com/maps/api/place/")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build();

}
