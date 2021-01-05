package com.app.go4lunch.viewModel.injection;


import com.app.go4lunch.viewModel.factory.ViewModelFactory;
import com.app.go4lunch.viewModel.repositories.RestaurantRepository;
import com.app.go4lunch.viewModel.repositories.RestaurantPlacesRepository;
import com.app.go4lunch.viewModel.repositories.UserRepository;

public class Injection
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

    public static ViewModelFactory viewModelFactoryGo4Lunch ()
    {
        return new ViewModelFactory(createRestaurantFirebaseRepository(), createUserFirebaseRepository(), createRestaurantPlacesRepository());
    }

}
