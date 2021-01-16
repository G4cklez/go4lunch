package com.app.go4lunch.view.adapter;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.app.go4lunch.R;
import com.app.go4lunch.databinding.ItemFriendBinding;
import com.app.go4lunch.model.User;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.List;


public class FriendsRecyclerAdapter extends RecyclerView.Adapter<FriendsRecyclerAdapter.ViewHolder>  
{

    private RequestManager glide;
    private Context context;
    private List<User> usersList = new ArrayList<>();
    private ItemClickListener listener;
    private boolean isDetails;

    public FriendsRecyclerAdapter(RequestManager glide, ItemClickListener listener, boolean isDetails)
    {
        this.glide = glide;
        this.listener = listener;
        this.isDetails = isDetails;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        this.context = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        ItemFriendBinding binding = DataBindingUtil.inflate(layoutInflater,R.layout.item_friend, parent, false);
        return new ViewHolder(binding, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position)
    {
        holder.bindData(usersList.get(position), glide, context);
    }

    @Override
    public int getItemCount()
    {
        return usersList.size();
    }

    public void updateList(List<User> userList)
    {
        this.usersList = userList;
        this.notifyDataSetChanged();
    }

     class ViewHolder extends RecyclerView.ViewHolder
    {
        private ItemClickListener listener;
        private ItemFriendBinding binding;
        private ViewHolder(@NonNull ItemFriendBinding binding, ItemClickListener listener)
        {
            super(binding.getRoot());
            this.binding = binding;
            this.listener = listener;
        }

    
        private void bindData(User user, RequestManager glide, Context context)
        {
            glide.load(user.getIllustration()).apply(RequestOptions.circleCropTransform()).into(binding.imgUser);
            String textString;
            String finalText;
            String firstName = user.getName().split(" ")[0];

            if (user.isChooseRestaurant()) {

                textString = context.getString(R.string.is_eating);

                finalText = firstName + " " + textString +" "+ user.getRestaurantChoose().getName();

                binding.tvUserName.setText(finalText);

                if (Build.VERSION.SDK_INT < 23) {
                    binding.tvUserName.setTextAppearance(context, R.style.item_list_workmates_choose_txt);
                } else {
                    binding.tvUserName.setTextAppearance(R.style.item_list_workmates_choose_txt);
                }
            }
            else if(!isDetails)
            {
                textString = " " + context.getString(R.string.pending);
                finalText = firstName + textString;
                binding.tvUserName.setText(finalText);

                if (Build.VERSION.SDK_INT < 23) {
                    binding.tvUserName.setTextAppearance(context, R.style.item_list_workmates_no_choose_txt);
                } else {
                    binding.tvUserName.setTextAppearance(R.style.item_list_workmates_no_choose_txt);
                }
            }else {
                binding.tvUserName.setText(firstName);
            }

            binding.parentCard.setOnClickListener(v->{
                listener.onItemSelected(getAdapterPosition());
            });
        }

    }
    public interface ItemClickListener
    {
        void onItemSelected(int position);
    }
}
