package com.app_republic.test_app.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.app_republic.test_app.R;
import com.app_republic.test_app.model.Card;
import com.app_republic.test_app.model.Post;

import java.util.ArrayList;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.viewHolder> {


    Context context;
    ArrayList<Post> list;

    public PostAdapter(Context context, ArrayList<Post> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.item_post, viewGroup, false);

        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder viewHolder, int i) {

        Post post = list.get(i);

        viewHolder.avatar.setImageDrawable(context.getResources().getDrawable(post.getAvatar()));

        if (post.isLiked())
            viewHolder.isLiked.setImageDrawable(context.getResources().getDrawable(R.drawable.favorite_ac));
        else
            viewHolder.isLiked.setImageDrawable(context.getResources().getDrawable(R.drawable.favorite_dis));

        if (post.getImage() != 0){
            viewHolder.image_layout.setVisibility(View.VISIBLE);
            viewHolder.image.setImageDrawable(context.getResources().getDrawable(post.getImage()));
        } else {
            viewHolder.image_layout.setVisibility(View.GONE);
        }
        viewHolder.name.setText(post.getName());
        viewHolder.description.setText(post.getDescription());
        viewHolder.likes.setText(Integer.toString(post.getLikes()));
        viewHolder.views.setText(Integer.toString(post.getViews()));

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void deleteItem(int position) {
        list.remove(position);
        notifyItemRemoved(position);
    }

    class viewHolder extends RecyclerView.ViewHolder {

        ImageView avatar, image, isLiked;
        TextView name, description, likes, views;
        RelativeLayout image_layout;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            avatar = itemView.findViewById(R.id.avatar);
            image = itemView.findViewById(R.id.image);
            name = itemView.findViewById(R.id.name);
            description = itemView.findViewById(R.id.description);
            image_layout = itemView.findViewById(R.id.image_layout);
            isLiked = itemView.findViewById(R.id.isLiked);
            likes = itemView.findViewById(R.id.likes);
            views = itemView.findViewById(R.id.views);

            isLiked.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Post post = list.get(getAdapterPosition());
                    if (post.isLiked()) {
                        post.setLiked(false);
                        hideHeart(isLiked);
                    } else {
                        post.setLiked(true);
                        showHeart(isLiked);
                    }
                }
            });


        }
    }

    private void showHeart(final ImageView view) {
        view.setImageResource(R.drawable.favorite_ac);

        ScaleAnimation scale = new ScaleAnimation(1.0f, 1.5f, 1.0f, 1.5f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        scale.setDuration(100);
        prepareAnimation(scale);

        AnimationSet animation = new AnimationSet(true);
        animation.addAnimation(scale);
        animation.setFillAfter(true);

        view.startAnimation(animation);

    }

    private void hideHeart(final ImageView view) {
        view.setImageResource(R.drawable.favorite_dis);
    }
    private void prepareAnimation(Animation animation){
        animation.setRepeatCount(1);
        animation.setRepeatMode(Animation.REVERSE);
    }
}

