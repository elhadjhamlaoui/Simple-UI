package com.app_republic.test_app;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import com.app_republic.test_app.adapter.CardAdapter;
import com.app_republic.test_app.adapter.PostAdapter;

import static com.app_republic.test_app.util.Const.TYPE_HOME;

public class SwipeToDeleteCallback extends ItemTouchHelper.SimpleCallback {
    private RecyclerView.Adapter mAdapter;
    private int type;

    public SwipeToDeleteCallback(RecyclerView.Adapter adapter, int type) {
        super(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT);
        mAdapter = adapter;
        this.type = type;
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
        return false;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        int position = viewHolder.getAdapterPosition();

        if (type == TYPE_HOME)
            ((PostAdapter) mAdapter).deleteItem(position);
        else
            ((CardAdapter) mAdapter).deleteItem(position);

    }

}