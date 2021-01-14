package com.app.go4lunch.view.activities.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.go4lunch.R;
import com.app.go4lunch.databinding.FragmentFriendsListBinding;
import com.app.go4lunch.di.DI;
import com.app.go4lunch.model.User;
import com.app.go4lunch.view.activities.RestaurantDetailActivity;
import com.app.go4lunch.view.adapter.FriendsRecyclerAdapter;
import com.app.go4lunch.viewModel.AppViewModel;
import com.app.go4lunch.viewModel.factory.ViewModelFactory;
import com.bumptech.glide.Glide;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FriendsListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FriendsListFragment extends Fragment {

    private AppViewModel appViewModel;
    private List<User> usersList;
    private FriendsRecyclerAdapter adapter;
    private FragmentFriendsListBinding binding;

    public FriendsListFragment() {}

    public static FriendsListFragment newInstance()
    {
        return new FriendsListFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_friends_list, container, false);
        binding.progressBar.setVisibility(View.VISIBLE);
        initRecyclerView();
        return binding.getRoot();
    }

    @Override
    public void onResume()
    {
        super.onResume();
        initViewModel();
    }

    ////////////////////////////////////////// VIEW MODEL ///////////////////////////////////////////

    private void initViewModel()
    {
        ViewModelFactory viewModelFactory = DI.getViewModelFactory();
        appViewModel = ViewModelProviders.of(this, viewModelFactory).get(AppViewModel.class);
        getUserList();
    }

    private void getUserList()
    {
        this.appViewModel.getUserListMutableLiveData().observe(this, userList ->
        {
            this.usersList = userList;
            adapter.updateList(usersList);
            binding.progressBar.setVisibility(View.INVISIBLE);
        });
    }

    ////////////////////////////////////////// CONFIGURE ///////////////////////////////////////////

    private void initRecyclerView()
    {
        adapter = new FriendsRecyclerAdapter(Glide.with(this), new FriendsRecyclerAdapter.ItemClickListener() {
            @Override
            public void onItemSelected(int position) {
                if (usersList.get(position).isChooseRestaurant())
                {
                    Intent intent = new Intent(getContext(), RestaurantDetailActivity.class);
                    intent.putExtra("placeId", usersList.get(position).getRestaurantChoose().getPlaceId());
                    startActivity(intent);
                }
            }
        });
        binding.rvFriends.setAdapter(adapter);
    }
}

