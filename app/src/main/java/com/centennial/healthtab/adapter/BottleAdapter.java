package com.centennial.healthtab.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.Adapter;

import com.centennial.healthtab.R;
import com.centennial.healthtab.fragment.HomeFragment;
import com.centennial.healthtab.object.Bottle_Data;
import com.centennial.healthtab.utils.Constant;
import com.centennial.healthtab.utils.Utils;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class BottleAdapter extends Adapter<BottleAdapter.ViewHolder> {
    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;
    HomeFragment homeFragment;
    Activity mContext;
    ArrayList<Bottle_Data> mList = new ArrayList();

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivImg;
        ImageView ivImgplus;
        ImageView ivSelected;
        RelativeLayout lout_main;
        TextView tvTime;
        TextView tvUnit;

        public ViewHolder(View view) {
            super(view);
            this.lout_main = view.findViewById(R.id.lout_main);
            this.tvUnit = view.findViewById(R.id.tvUnit);
            this.tvTime = view.findViewById(R.id.tvTime);
            this.ivImg = view.findViewById(R.id.ivImg);
            this.ivImgplus = view.findViewById(R.id.ivImgplus);
            this.ivSelected = view.findViewById(R.id.ivSelected);
        }
    }

    public int getItemViewType(int i) {
        return 1;
    }

    public BottleAdapter(Activity activity, ArrayList<Bottle_Data> arrayList, HomeFragment homeFragment) {
        this.mContext = activity;
        this.mList = arrayList;
        this.homeFragment = homeFragment;
    }

    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item_bottle, viewGroup, false));
    }

    public void onBindViewHolder(ViewHolder viewHolder, final int i) {
        try {
            if (Utils.getGlassFromDefaults(this.mContext, Constant.PARAM_VALID_GLASS) != this.mList.get(i).getGlass()) {
                viewHolder.ivSelected.setVisibility(8);
            } else if (Utils.getGlassFromDefaults(this.mContext, Constant.PARAM_VALID_TYPE) == 1) {
                if (this.mList.get(i).getType() == 1) {
                    viewHolder.ivSelected.setVisibility(0);
                } else {
                    viewHolder.ivSelected.setVisibility(8);
                }
            } else if (this.mList.get(i).getType() == 0) {
                viewHolder.ivSelected.setVisibility(0);
            } else {
                viewHolder.ivSelected.setVisibility(8);
            }
            if (i == 0) {
                viewHolder.ivImgplus.setVisibility(0);
                viewHolder.ivImg.setVisibility(8);
                viewHolder.ivImgplus.setImageResource(R.drawable.iv_add_bottle);
            } else {
                viewHolder.ivImgplus.setVisibility(8);
                viewHolder.ivImg.setVisibility(0);
                viewHolder.ivImg.setImageResource(Constant.bottle[this.mList.get(i).getGlass()]);
            }
            if (i == 0) {
                viewHolder.tvUnit.setText("");
            } else if (Utils.getFromUserDefaults(this.mContext, Constant.PARAM_VALID_WATER_TYPE).equals("ml")) {
                TextView textView = viewHolder.tvUnit;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("");
                stringBuilder.append(Math.round(Float.parseFloat(this.mList.get(i).getUnit())));
                stringBuilder.append("");
                stringBuilder.append(Utils.getFromUserDefaults(this.mContext, Constant.PARAM_VALID_WATER_TYPE));
                textView.setText(stringBuilder.toString());
            } else {
                double parseDouble = Double.parseDouble(this.mList.get(i).getUnit()) * Constant.oz;
                DecimalFormat decimalFormat = new DecimalFormat("##.##");
                TextView textView2 = viewHolder.tvUnit;
                StringBuilder stringBuilder2 = new StringBuilder();
                stringBuilder2.append("");
                stringBuilder2.append(decimalFormat.format(parseDouble));
                stringBuilder2.append("");
                stringBuilder2.append(Utils.getFromUserDefaults(this.mContext, Constant.PARAM_VALID_WATER_TYPE));
                textView2.setText(stringBuilder2.toString());
            }
            viewHolder.lout_main.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    if (i == 0) {
                        BottleAdapter.this.homeFragment.showCustomLayout();
                        return;
                    }
                    BottleAdapter.this.mList.get(i).setType(1);
                    BottleAdapter.this.homeFragment.selectDefaultBottle(i, BottleAdapter.this.mList.get(i).getUnit(), BottleAdapter.this.mList.get(i).getGlass());
                    BottleAdapter.this.notifyDataSetChanged();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int getItemCount() {
        return this.mList.size();
    }
}
