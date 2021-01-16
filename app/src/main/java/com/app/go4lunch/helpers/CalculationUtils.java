package com.app.go4lunch.helpers;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

public abstract class CalculationUtils
{


    /**
     * Calculate the RectangularBounds for Autocomplete Search from the CurrentLocation according to the radius
     * @param radius from the PlacesRequest
     * @param currentLocation of the CurrentUser
     * @return a List with 2 LatLng
     */
    public static List<LatLng> calculateRectangularBoundsAccordingToCurrentLocation(double radius, LatLng currentLocation)
    {
        List<LatLng> list = new ArrayList<>();

        double latA = currentLocation.latitude - (float) (radius/111);
        double lngA = currentLocation.longitude - (float) (radius/(111 * Math.cos(latA * (Math.PI/180.0f))));
        LatLng pointA = new LatLng(latA, lngA);
        list.add(pointA);

        double latB = currentLocation.latitude + (float) (radius/111) ;
        double lngB = currentLocation.longitude + (float) (radius/(111 * Math.cos(latB * (Math.PI/180.0f))));
        LatLng pointB = new LatLng(latB, lngB);
        list.add(pointB);

        return list;
    }

}
