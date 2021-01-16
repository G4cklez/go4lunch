package com.app.go4lunch.viewModel;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.app.go4lunch.viewModel.AppViewModel;
import com.app.go4lunch.repositories.RestaurantRepository;
import com.app.go4lunch.repositories.RestaurantPlacesRepository;
import com.app.go4lunch.repositories.UserRepository;

public class ViewModelFactory implements ViewModelProvider.Factory {

    private RestaurantRepository restaurantRepository;
    private UserRepository userRepository;
    private RestaurantPlacesRepository restaurantPlacesRepository;

    public ViewModelFactory(RestaurantRepository restaurantRepository,
                            UserRepository userRepository,
                            RestaurantPlacesRepository restaurantPlacesRepository) {
        this.restaurantRepository = restaurantRepository;
        this.userRepository = userRepository;
        this.restaurantPlacesRepository = restaurantPlacesRepository;
    }

    @SuppressWarnings("unchecked")
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass)
    {
        if (modelClass.isAssignableFrom(AppViewModel.class))
        {
            return (T) new AppViewModel(this.restaurantRepository, this.userRepository, this.restaurantPlacesRepository);
        }
        throw new IllegalArgumentException("Problem with ViewModelFactory");
    }
}
