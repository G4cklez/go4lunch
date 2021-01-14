package com.app.go4lunch.di;


import com.app.go4lunch.viewModel.factory.ViewModelFactory;
import com.app.go4lunch.repositories.RestaurantRepository;
import com.app.go4lunch.repositories.RestaurantPlacesRepository;
import com.app.go4lunch.repositories.UserRepository;

public class DI
{

    private static RestaurantRepository createRestaurantFirebaseRepository()
    {
        return new RestaurantRepository();
    }

    private static UserRepository createUserFirebaseRepository ()
    {
        return new UserRepository();
    }

    private static RestaurantPlacesRepository createRestaurantPlacesRepository ()
    {
        return new RestaurantPlacesRepository();
    }

    public static ViewModelFactory getViewModelFactory()
    {
        return new ViewModelFactory(createRestaurantFirebaseRepository(), createUserFirebaseRepository(), createRestaurantPlacesRepository());
    }

}
