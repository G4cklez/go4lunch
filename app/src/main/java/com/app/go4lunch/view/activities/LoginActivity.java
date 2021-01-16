package com.app.go4lunch.view.activities;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import com.app.go4lunch.R;
import com.app.go4lunch.databinding.ActivityLoginBinding;
import com.app.go4lunch.model.User;
import com.app.go4lunch.viewModel.AppViewModel;
import com.app.go4lunch.viewModel.ViewModelFactory;
import com.app.go4lunch.di.DI;
import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Collections;
import java.util.List;
import java.util.Objects;


public class LoginActivity extends AppCompatActivity {

    private final int REQUEST_CODE = 100;
    private Boolean userExists = false;
    private AppViewModel appViewModel;
    private List<User> usersList;
    ActivityLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_login);
        binding.progressBar.setVisibility(View.INVISIBLE);
        showProgressBar();
        initViewModel();
        setOnClickListener();
    }

    private void setOnClickListener() {
        binding.btnGoogle.setOnClickListener(v -> signInWithGoogle());
        binding.btnFacebook.setOnClickListener(v -> signInWithFacebook());

        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    "com.app.go4lunch",
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash", "KeyHash:" + Base64.encodeToString(md.digest(),
                        Base64.DEFAULT));
                Toast.makeText(getApplicationContext(), Base64.encodeToString(md.digest(),
                        Base64.DEFAULT), Toast.LENGTH_LONG).show();
            }
        } catch (PackageManager.NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

        }
    }


    private void initViewModel()
    {
        ViewModelFactory viewModelFactory = DI.getViewModelFactory();
        appViewModel = ViewModelProviders.of(this, viewModelFactory).get(AppViewModel.class);
        getUsersList();
    }

    private void getUsersList()
    {
        appViewModel.getUserListMutableLiveData().observe(this, userList ->
        {
            usersList = userList;
            createOrSignin();
        });
    }



    private void signInWithGoogle()
    {
        startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setAvailableProviders(Collections.singletonList(
                                        new AuthUI.IdpConfig.GoogleBuilder().build()))
                        .setIsSmartLockEnabled(false, true)
                        .build(), REQUEST_CODE);
    }

    /**
     * Sign in With Facebook
     */
    private void signInWithFacebook()
    {
        startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setAvailableProviders(Collections.singletonList(
                                        new AuthUI.IdpConfig.FacebookBuilder().build()))
                        .setIsSmartLockEnabled(false, true)
                        .build(), REQUEST_CODE);
    }



    private void showProgressBar()
    {
        if(FirebaseAuth.getInstance().getCurrentUser() != null)
        {
           binding.progressBar.setVisibility(View.VISIBLE);
        }
    }


    private void createOrSignin()
    {
        if (FirebaseAuth.getInstance().getCurrentUser() != null)
        {
            String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
            if(usersList != null)
            {
                int size = usersList.size();
                for (int i = 0; i < size; i ++)
                {
                    if (usersList.get(i).getEmail().equals(FirebaseAuth.getInstance().getCurrentUser().getEmail()))
                    {
                        userExists = true;
                        break;
                    }
                }
                if (!userExists) {
                    String email = FirebaseAuth.getInstance().getCurrentUser().getEmail();
                    String name = FirebaseAuth.getInstance().getCurrentUser().getDisplayName();
                    String urlPicture = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser().getPhotoUrl()).toString();

                    appViewModel.createUser(uid, email, name, urlPicture);
                }
            }else {
                String email = FirebaseAuth.getInstance().getCurrentUser().getEmail();
                String name = FirebaseAuth.getInstance().getCurrentUser().getDisplayName();
                String urlPicture = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser().getPhotoUrl()).toString();

                appViewModel.createUser(uid, email, name, urlPicture);
            }
           goToHome();
        }
        else
        {
            binding.progressBar.setVisibility(View.INVISIBLE);
        }
    }

    private void goToHome()
    {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
        finish();
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE)
        {
            if (resultCode == RESULT_OK)
            {
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.success),Toast.LENGTH_SHORT ).show();
                getUsersList();
            }
            else
            {
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.connection_error),Toast.LENGTH_SHORT ).show();
            }
        }
    }
}
