package com.centennial.healthtab.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.Adapter;

import com.centennial.healthtab.R;
import com.centennial.healthtab.fragment.HomeFragment;

public class CustomBottleAdapter extends Adapter<CustomBottleAdapter.ViewHolder> {
    HomeFragment homeFragment;
    Activity mContext;
    int[] mList;
    int pos = 0;

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivImg;
        ImageView ivSelected;
        RelativeLayout lout_main;

        public ViewHolder(View view) {
            super(view);
            this.lout_main = view.findViewById(R.id.lout_main);
            this.ivImg = view.findViewById(R.id.ivImg);
            this.ivSelected = view.findViewById(R.id.ivSelected);
        }
    }

    public CustomBottleAdapter(Activity activity, int[] iArr, HomeFragment homeFragment) {
        this.mContext = activity;
        this.mList = iArr;
        this.homeFragment = homeFragment;
    }

    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item_custom_bottle, viewGroup, false));
    }

    public void onBindViewHolder(final ViewHolder viewHolder, final int i) {
        try {
            viewHolder.ivImg.setImageResource(this.mList[i]);
            if (this.pos == i) {
                viewHolder.ivSelected.setVisibility(0);
            } else {
                viewHolder.ivSelected.setVisibility(8);
            }
            viewHolder.lout_main.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    viewHolder.ivSelected.setVisibility(0);
                    CustomBottleAdapter.this.pos = i;
                    CustomBottleAdapter.this.notifyDataSetChanged();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int getGlass() {
        return this.pos;
    }

    public int getItemCount() {
        return this.mList.length;
    }
}
