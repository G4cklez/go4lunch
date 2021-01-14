package com.app.go4lunch.view.adapter;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.app.go4lunch.R;
import com.app.go4lunch.databinding.ItemRestaurantsBinding;
import com.app.go4lunch.helpers.UtilsListRestaurant;
import com.app.go4lunch.model.Restaurant;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.request.RequestOptions;


import java.util.ArrayList;
import java.util.List;


public class RestaurantRecyclerAdapter extends RecyclerView.Adapter<RestaurantRecyclerAdapter.ListRestaurantsViewHolder>
{
    // FOR DATA
    private ItemClickListener itemClickListener;
    private List<Restaurant> restaurantsFromPlaces;
    private RequestManager glide;
    private Context context;

    public RestaurantRecyclerAdapter(RequestManager glide, ItemClickListener itemClickListener)
    {
        this.glide = glide;
        this.itemClickListener = itemClickListener;
        this.restaurantsFromPlaces = new ArrayList<>();
    }

    @NonNull
    @Override
    public ListRestaurantsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        context = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        ItemRestaurantsBinding binding = DataBindingUtil.inflate(layoutInflater,R.layout.item_restaurants, parent, false);
        return new ListRestaurantsViewHolder(binding, itemClickListener, context);
    }

    @Override
    public void onBindViewHolder(@NonNull ListRestaurantsViewHolder holder, int position)
    {
        holder.bindData(restaurantsFromPlaces.get(position), glide);
    }


    @Override
    public int getItemCount() {
        return this.restaurantsFromPlaces.size();
    }

    public void updateList(List<Restaurant> restaurantList)
    {
        this.restaurantsFromPlaces = restaurantList;
        this.notifyDataSetChanged();
    }

    static class ListRestaurantsViewHolder extends RecyclerView.ViewHolder
    {

        private ItemClickListener itemClickListener;
        private Context context;
        private int numberWorkmates = 0;
        ItemRestaurantsBinding binding;

        private ListRestaurantsViewHolder(@NonNull ItemRestaurantsBinding binding, ItemClickListener itemClickListener, Context context) {
            super(binding.getRoot());
            this.itemClickListener = itemClickListener;
            this.context = context;
            this.binding = binding;
        }

        private void bindData(Restaurant restaurant, RequestManager glide)
        {
            binding.tvRestaurantName.setText(restaurant.getName());
            binding.tvRestaurantAddress.setText(restaurant.getAddress());
            glide.load(restaurant.getIllustration()).apply(RequestOptions.centerCropTransform()).into(binding.imgRestaurant);
            displayWorkmates();
            updateHours(restaurant);
            updateNumberWorkmates(restaurant);

            updateDistance(restaurant);
            UtilsListRestaurant.updateRating(binding.imgStar1, binding.imgStar2, binding.imgStar3, restaurant);

            binding.parentCard.setOnClickListener(v->{
                itemClickListener.onItemSelected(getAdapterPosition());
            });
        }

        private void updateDistance(Restaurant restaurant)
        {
            String distanceString = restaurant.getDistanceCurrentUser() + "m";
            binding.tvRestaurantDistance.setText(distanceString);
        }


        private void updateHours(Restaurant restaurant)
        {
           if (restaurant.getOpenNow() != null && restaurant.getOpenNow())
           {
               binding.tvRestaurantHours.setText(context.getString(R.string.open_now));
               if (Build.VERSION.SDK_INT < 23) {
                   binding.tvRestaurantHours.setTextAppearance(context.getApplicationContext(), R.style.item_list_restaurant_hours_open_txt);
               } else {
                   binding.tvRestaurantHours.setTextAppearance(R.style.item_list_restaurant_hours_open_txt);
               }
           }
           else
           {
               binding.tvRestaurantHours.setText(context.getString(R.string.close_now));
               if (Build.VERSION.SDK_INT < 23) {
                   binding.tvRestaurantHours.setTextAppearance(context.getApplicationContext(), R.style.item_list_restaurant_hours_close_txt);
               } else {
                   binding.tvRestaurantHours.setTextAppearance(R.style.item_list_restaurant_hours_close_txt);
               }
           }
        }


        private void updateNumberWorkmates (Restaurant restaurant)
        {
            numberWorkmates = 0;
            if (restaurant.getUserList() != null && restaurant.getUserList().size() > 0)
            {
                numberWorkmates = restaurant.getUserList().size();
                String numberWorkmatesString = "(" + numberWorkmates + ")";
                binding.tvNumberMates.setText(numberWorkmatesString);
            }
            displayWorkmates();
        }

        private void displayWorkmates()
        {
            if (numberWorkmates > 0)
            {
                binding.tvNumberMates.setVisibility(View.VISIBLE);
                binding.imgRestaurantRating.setVisibility(View.VISIBLE);
            }
            else
            {
                binding.tvNumberMates.setVisibility(View.INVISIBLE);
                binding.imgRestaurantRating.setVisibility(View.INVISIBLE);
            }
        }
    }

    public interface ItemClickListener
    {
        void onItemSelected(int position);
    }
}




