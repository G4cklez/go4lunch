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
    }


    private void initViewModel()
    {
        ViewModelFactory viewModelFactory = Injection.viewModelFactoryGo4Lunch();
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
            lunchMainActivity();
        }
        else
        {
            binding.progressBar.setVisibility(View.INVISIBLE);
        }
    }

    private void lunchMainActivity()
    {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
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

    /**
     * Suppress super because we don't want that the User can press Back
     */
    @Override
    public void onBackPressed() {}
}
