package com.app_republic.test_app.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.app_republic.test_app.R;
import com.app_republic.test_app.model.Card;

import java.util.ArrayList;

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.viewHolder> {

    Context context;
    ArrayList<Card> list;

    public CardAdapter(Context context, ArrayList<Card> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.item_card, viewGroup, false);

        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder viewHolder, int i) {

        Card card = list.get(i);

        if (card.isLiked())
            viewHolder.isLiked.setImageDrawable(context.getResources().getDrawable(R.drawable.favorite_ac));
        else
            viewHolder.isLiked.setImageDrawable(context.getResources().getDrawable(R.drawable.favorite_dis));

        viewHolder.name.setText(card.getName());
        viewHolder.description.setText(card.getDescription());
        viewHolder.likes.setText(Integer.toString(card.getLikes()));
        viewHolder.views.setText(Integer.toString(card.getViews()));

        viewHolder.view.setBackground(context.getResources().getDrawable(card.getColor()));
    }

    public void deleteItem(int position) {
        list.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    class viewHolder extends RecyclerView.ViewHolder {

        ImageView isLiked;
        View view;
        TextView name, description, likes, views;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            isLiked = itemView.findViewById(R.id.isLiked);
            view = itemView.findViewById(R.id.view);
            name = itemView.findViewById(R.id.name);
            description = itemView.findViewById(R.id.description);
            likes = itemView.findViewById(R.id.likes);
            views = itemView.findViewById(R.id.views);

            isLiked.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Card card = list.get(getAdapterPosition());
                    if (card.isLiked()) {
                        card.setLiked(false);
                        hideHeart(isLiked);
                    } else {
                        card.setLiked(true);
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
