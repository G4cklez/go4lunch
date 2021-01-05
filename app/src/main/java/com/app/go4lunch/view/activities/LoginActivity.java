package com.app.go4lunch.view.activities;

import android.content.Intent;
import android.os.Bundle;
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
import com.app.go4lunch.viewModel.factory.ViewModelFactory;
import com.app.go4lunch.viewModel.injection.Injection;
import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Collections;
import java.util.List;
import java.util.Objects;


/**
 * Created by Charlotte Judon - 02/18
 * Activity for user authentication
 */
public class LoginActivity extends AppCompatActivity {

    //FOR DATA
    private final static int FIREBASE_UI = 100;
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
        this.showProgressBar();
        this.configViewModel();
        setOnClickListener();
    }

    private void setOnClickListener() {
        binding.btnGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startSignInWithGoogle();
            }
        });
        binding.btnFacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startSignInWithFacebook();
            }
        });
    }

    ///////////////////////////////////VIEW MODEL///////////////////////////////////

    private void configViewModel()
    {
        ViewModelFactory viewModelFactory = Injection.viewModelFactoryGo4Lunch();
        appViewModel = ViewModelProviders.of(this, viewModelFactory).get(AppViewModel.class);
        this.getUsersList();
    }

    private void getUsersList()
    {
        this.appViewModel.getUserListMutableLiveData().observe(this, userList ->
        {
            usersList = userList;
            createOrSignin();
        });
    }



    ///////////////////////////////////SIGN IN///////////////////////////////////

    /**
     * Sign in With Google
     */
    private void startSignInWithGoogle()
    {
        startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setAvailableProviders(Collections.singletonList(
                                        new AuthUI.IdpConfig.GoogleBuilder().build()))
                        .setIsSmartLockEnabled(false, true)
                        .build(),FIREBASE_UI);
    }

    /**
     * Sign in With Facebook
     */
    private void startSignInWithFacebook()
    {
        startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setAvailableProviders(Collections.singletonList(
                                        new AuthUI.IdpConfig.FacebookBuilder().build()))
                        .setIsSmartLockEnabled(false, true)
                        .build(),FIREBASE_UI);
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
                if (userExists)
                {
                    lunchMainActivity();
                }
                else
                {
                    String email = FirebaseAuth.getInstance().getCurrentUser().getEmail();
                    String name = FirebaseAuth.getInstance().getCurrentUser().getDisplayName();
                    String urlPicture = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser().getPhotoUrl()).toString();

                    appViewModel.createUser(uid, email, name, urlPicture);
                    this.lunchMainActivity();
                }
            }else {
                String email = FirebaseAuth.getInstance().getCurrentUser().getEmail();
                String name = FirebaseAuth.getInstance().getCurrentUser().getDisplayName();
                String urlPicture = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser().getPhotoUrl()).toString();

                appViewModel.createUser(uid, email, name, urlPicture);
                this.lunchMainActivity();
            }
        }
        else
        {
            binding.progressBar.setVisibility(View.INVISIBLE);
        }
    }

    private void lunchMainActivity()
    {
//        Intent intent = new Intent(this, MainActivity.class);
//        startActivity(intent);
    }

    /**
     * Use it for the Response of SignIn
     */
    private void responseSignIn(int requestCode, int resultCode, Intent data)
    {
        if (requestCode == FIREBASE_UI)
        {
            if (resultCode == RESULT_OK)
            {
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.response_sign_in_success),Toast.LENGTH_SHORT ).show();
                getUsersList();
            }
            else
            {
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.response_sign_in_error),Toast.LENGTH_SHORT ).show();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        this.responseSignIn(requestCode, resultCode, data);
    }

    /**
     * Suppress super because we don't want that the User can press Back
     */
    @Override
    public void onBackPressed() {}
}
