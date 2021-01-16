package com.app.go4lunch.notificationWorkManager;


import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.app.go4lunch.R;
import com.app.go4lunch.model.Restaurant;
import com.app.go4lunch.model.User;
import com.app.go4lunch.repositories.RestaurantRepository;
import com.app.go4lunch.repositories.UserRepository;
import com.app.go4lunch.view.activities.RestaurantDetailActivity;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.app.go4lunch.constants.Constants.PLACE_ID;

public class NotificationWorker extends Worker
{

    private UserRepository userRepository;
    private RestaurantRepository restaurantRepository;
    private User currentUser;
    private Restaurant currentRestaurant;
    private Context context;

    public NotificationWorker(@NonNull Context context, @NonNull WorkerParameters workerParams)
    {
        super(context, workerParams);
        this.context = context;
        this.userRepository = new UserRepository();
        this.restaurantRepository = new RestaurantRepository();
    }

    @NonNull
    @Override
    public Result doWork()
    {
        this.getCurrentUserFromFirebase();
        return Result.success();
    }

    private void getCurrentUserFromFirebase()
    {
        String uid = FirebaseAuth.getInstance().getUid();
        userRepository.getUser(uid).addOnSuccessListener(documentSnapshot ->
        {
            currentUser = documentSnapshot.toObject(User.class);
            if (Objects.requireNonNull(currentUser).isChooseRestaurant())
            {
                getCurrentRestaurantFromFirebase(currentUser.getRestaurantChoose().getPlaceId());
            }
        });
    }

    private void getCurrentRestaurantFromFirebase(String placeId)
    {
        restaurantRepository.getRestaurant(placeId).addOnSuccessListener(documentSnapshot -> {
            currentRestaurant = documentSnapshot.toObject(Restaurant.class);
            sendNotification();
        });
    }


    private void sendNotification()
    {
        showNotification(createMessage(currentRestaurant, currentUser, context.getString(R.string.with)));
    }

    public static List<String> createMessage (Restaurant currentRestaurant, User currentUser, String with)
    {
        List<String> stringForNotification = new ArrayList<>();
        StringBuilder friendsListString = new StringBuilder();

        String name = currentRestaurant.getName();
        String address = currentRestaurant.getAddress();
        stringForNotification.add(name);
        stringForNotification.add(address);

        List<User> friendsList = currentRestaurant.getUserList();

        if (friendsList.size() > 1)
        {
            friendsListString.append(with);
            int size = friendsList.size();
            for (int i = 0; i < size; i ++)
            {
                User userToCompare = friendsList.get(i);
                if (!userToCompare.getIllustration().equals(currentUser.getIllustration()) || !userToCompare.getName().equals(currentUser.getName()))
                {
                    String friendName = userToCompare.getName();
                    friendsListString.append(" ").append(friendName).append(",");
                }
            }
            //Remove the last ","
            friendsListString.deleteCharAt(friendsListString.length()-1);

            stringForNotification.add(friendsListString.toString());
        }
        return stringForNotification;
    }


    private void showNotification(List<String> friendListString)
    {
        // 1 - Create an Intent that will be shown when user will click on the Notification
        Intent intent = new Intent(context, RestaurantDetailActivity.class);
        intent.putExtra(PLACE_ID, currentRestaurant.getPlaceId());
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_ONE_SHOT);

        // 2 - Create a Channel (Android 8)
        String channelId = "task_channel";
        String channelName = "task_name";

        // 3 - Build a Notification object
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), channelId)
                .setContentTitle(context.getString(R.string.lunch_time))
                .setContentText(context.getString(R.string.you_have_lunch_scheduled))
                .setSmallIcon(R.mipmap.ic_launcher)
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);

        if (friendListString.size() == 2)
        {
            builder.setStyle(new NotificationCompat.InboxStyle()
                    .addLine(friendListString.get(0))
                    .addLine(friendListString.get(1)));
        }
        else if (friendListString.size() == 3)
        {
            builder.setStyle(new NotificationCompat.InboxStyle()
                    .addLine(friendListString.get(0))
                    .addLine(friendListString.get(1))
                    .addLine(friendListString.get(2)));

        }

        // 5 - Add the Notification to the Notification Manager and show it
        NotificationManager manager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);


        // 6 - Support Version >= Android 8
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            NotificationChannel channel = new NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_HIGH);
            Objects.requireNonNull(manager).createNotificationChannel(channel);
        }

        Objects.requireNonNull(manager).notify(1, builder.build());
    }

}
