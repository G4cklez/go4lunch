package com.app.go4lunch.data;

import com.app.go4lunch.model.Restaurant;
import com.app.go4lunch.model.User;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class UserResponseTest {

    private String emailTest = "test@testmail.com";
    private String nameTest = "Name Test";
    private String illustrationTest = "Image Url";
    private Restaurant restaurantForTest = new Restaurant();

    @Test
    public void setRestaurantChoose_Success()
    {
        // GIVEN : Create a new User
        User userForTest = new User(emailTest, nameTest, illustrationTest);
        // Test this User's boolean is false
        Assert.assertFalse(userForTest.isChooseRestaurant());
        // Test User's restaurantChoose is null
        assertNull(userForTest.getRestaurantChoose());

        // WHEN : Add a restaurant with the method setRestaurantChoose()
        userForTest.setRestaurantChoose(restaurantForTest);

        // THEN : Verify boolean and restaurantChoose changed
        Assert.assertTrue(userForTest.isChooseRestaurant());
        assertNotNull(userForTest.getRestaurantChoose());
    }

    @Test
    public void unSetRestaurantChoose_Success()
    {
        // GIVEN : Create a new User and use 1st method to add a restaurant
        User userForTest = new User(emailTest, nameTest, illustrationTest);
        userForTest.setRestaurantChoose(restaurantForTest);
        // Test to verify attributes
        Assert.assertTrue(userForTest.isChooseRestaurant());
        assertNotNull(userForTest.getRestaurantChoose());

        // WHEN : Remove a restaurant with the method unSetRestaurantChoose()
        userForTest.unSetRestaurantChoose();

        // THEN : Verify boolean and restaurantChoose changed
        Assert.assertFalse(userForTest.isChooseRestaurant());
        assertNull(userForTest.getRestaurantChoose());
    }

    @Test
    public void UserEquals_False()
    {
        // GIVEN : Create 2 Users without the same email
        User userForTest = new User(emailTest, nameTest, illustrationTest);
        User userToCompare = new User("test2@testmail.com", nameTest, illustrationTest);

        // THEN : The Users are not equals
        assertNotEquals(userForTest, userToCompare);
    }

    @Test
    public void UserEquals_True()
    {
        // GIVEN : Create 2 Users with the same email
        User userForTest = new User(emailTest, nameTest, illustrationTest);
        User userToCompare = new User(emailTest, "Name2", "Image Url2");

        // THEN : The Users are equals
        assertEquals(userForTest, userToCompare);
    }


}