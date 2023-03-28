package com.centennial.healthtab.fragment;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AlertDialog.Builder;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.centennial.healthtab.R;
import com.centennial.healthtab.adapter.BottleAdapter;
import com.centennial.healthtab.adapter.CustomBottleAdapter;
import com.centennial.healthtab.adapter.GlassAdapter;
import com.centennial.healthtab.object.Bottle_Data;
import com.centennial.healthtab.object.Glass_Data;
import com.centennial.healthtab.utils.Constant;
import com.centennial.healthtab.utils.Pref_Utils;
import com.centennial.healthtab.utils.Utils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import me.zhouzhuo.zzhorizontalprogressbar.ZzHorizontalProgressBar;

@SuppressLint({"ValidFragment"})
public class HomeFragment extends Fragment {
    ArrayList<Bottle_Data> bottleDataArrayList;
    BottleAdapter bottleListAdapter;
    CustomBottleAdapter customBottleListAdapter;
    RelativeLayout empty_recycler_lout;
    EditText etWeight;
    ArrayList<Glass_Data> glassDataArrayList;
    GlassAdapter glassListAdapter;
    ImageView ivDone;
    ImageView iv_add_bottle;
    ImageView iv_select_bottle;
    RecyclerView listBottle;
    RecyclerView listCustomBottle;
    RecyclerView listGlass;
    RelativeLayout lout_bottle;
    LinearLayout lout_custom;
    LinearLayout lout_default;
    Calendar mCalendar;
    GridLayoutManager mLayoutManager;
    private final BroadcastReceiver onNotice = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            if (rr1.getVisibility() == 0) {
                rr1.setVisibility(8);
            } else if (lout_bottle.getVisibility() == 0) {
                lout_bottle.setVisibility(8);
            } else {
                FragmentActivity activity = getActivity();
                getActivity();
                activity.setResult(-1);
                getActivity().finish();
            }
        }
    };
    ZzHorizontalProgressBar pb;
    boolean showDialogTrue;
    private String timeSet;
    TextView tvUnitType;
    TextView tvtargetWater;
    Type type = new TypeToken<List<Bottle_Data>>() {
    }.getType();
    Type type1 = new TypeToken<List<Glass_Data>>() {
    }.getType();
    View view;
    private RelativeLayout rr1;

    public HomeFragment(boolean z) {
        this.showDialogTrue = z;
    }

    @Nullable
    public View onCreateView(LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, @Nullable Bundle bundle) {
        this.view = layoutInflater.inflate(R.layout.fragment_home, viewGroup, false);
        init();
        return this.view;
    }

    public void init() {
        this.iv_add_bottle = this.view.findViewById(R.id.iv_add_bottle);
        this.iv_select_bottle = this.view.findViewById(R.id.iv_select_bottle);
        this.ivDone = this.view.findViewById(R.id.ivDone);
        this.etWeight = this.view.findViewById(R.id.etWeight);
        this.pb = this.view.findViewById(R.id.pb);
        this.lout_default = this.view.findViewById(R.id.lout_default);
        this.lout_custom = this.view.findViewById(R.id.lout_custom);
        this.rr1 = this.view.findViewById(R.id.rr1);
        this.lout_bottle = this.view.findViewById(R.id.lout_bottle);
        this.empty_recycler_lout = this.view.findViewById(R.id.empty_recycler_lout);
        this.tvtargetWater = this.view.findViewById(R.id.tvtargetWater);
        this.tvUnitType = this.view.findViewById(R.id.tvUnitType);
        this.listBottle = this.view.findViewById(R.id.listBottle);
        refresh();
        this.listGlass = this.view.findViewById(R.id.listGlass);
        this.mLayoutManager = new GridLayoutManager(getActivity(), 3, 1, false);
        this.listGlass.setLayoutManager(this.mLayoutManager);
        if (Pref_Utils.getDefaultGlassListInfo(getActivity()).equals("")) {
            this.glassDataArrayList = new ArrayList();
        } else {
            this.glassDataArrayList = new Gson().fromJson(Pref_Utils.getDefaultGlassListInfo(getActivity()), this.type1);
        }
        this.glassListAdapter = new GlassAdapter(getActivity(), this.glassDataArrayList, this, this.empty_recycler_lout);
        this.listGlass.setAdapter(this.glassListAdapter);
        if (this.glassDataArrayList.size() > 0) {
            this.listGlass.scrollToPosition(this.glassDataArrayList.size() - 1);
        }
        if (this.showDialogTrue) {
            this.lout_bottle.setVisibility(0);
            this.rr1.setVisibility(8);
            this.lout_default.setVisibility(0);
            this.listBottle.setLayoutManager(new LinearLayoutManager(getActivity(), 0, false));
            if (!Pref_Utils.getDefaultBottleListInfo(getActivity()).equals("")) {
                this.bottleDataArrayList = new Gson().fromJson(Pref_Utils.getDefaultBottleListInfo(getActivity()), this.type);
            }
            this.bottleListAdapter = new BottleAdapter(getActivity(), this.bottleDataArrayList, this);
            this.listBottle.setAdapter(this.bottleListAdapter);
        }
        clickEvent();
    }

    public void refresh() {
        resetWater();
    }

    public void resetWater() {
        double d;
        double intDefaults = Utils.getIntDefaults(getActivity(), Constant.PARAM_VALID_WEIGHT);
        if (!Utils.getFromUserDefaults(getActivity(), Constant.PARAM_VALID_WEIGHT_TYPE).equals("kg")) {
            d = Constant.lbs;
            Double.isNaN(intDefaults);
            intDefaults /= d;
        }
        if (Utils.getFromUserDefaults(getActivity(), Constant.PARAM_VALID_GENDER_TYPE).equals("Female")) {
            intDefaults = (double) Math.round(intDefaults * 35.0d);
        } else {
            intDefaults = (double) Math.round(intDefaults * 50.0d);
        }
        String wakeupTimeDefaults = Utils.getWakeupTimeDefaults(getActivity(), Constant.PARAM_VALID_WAKE_UP_TIME);
        String[] split = wakeupTimeDefaults.split(":");
        int parseInt = Integer.parseInt(split[0]);
        Integer.parseInt(split[1]);
        split = Utils.getSleepTimeDefaults(getActivity(), Constant.PARAM_VALID_SLEEP_TIME).split(":");
        int parseInt2 = Integer.parseInt(split[0]);
        Integer.parseInt(split[1]);
        TextView textView;
        StringBuilder stringBuilder;
        double intDefaults2;
        StringBuilder stringBuilder2;
        if (wakeupTimeDefaults.equals("08:00")) {
            Utils.clearIntDefaults(getActivity(), Constant.DAILY_WATER_DRINK);
            Utils.saveIntDefaults(getActivity(), Constant.DAILY_WATER_DRINK, (int) intDefaults);
            Log.e("print", "resetWater: " + (int) intDefaults);
            if (Utils.getFromUserDefaults(getActivity(), Constant.PARAM_VALID_WATER_TYPE).equals("ml")) {
                textView = this.tvtargetWater;
                stringBuilder = new StringBuilder();
                stringBuilder.append(Math.round(Utils.getWaterDrunkFromDefaults(getActivity(), Constant.PARAM_VALID_DRUNK_WATER)));
                stringBuilder.append("/");
                intDefaults2 = Utils.getIntDefaults(getContext(), Constant.ADD_CUSTOM_WATER);
                Double.isNaN(intDefaults2);
                stringBuilder.append(Math.round(intDefaults + intDefaults2));
                stringBuilder.append(" ");
                stringBuilder.append(Utils.getFromUserDefaults(getActivity(), Constant.PARAM_VALID_WATER_TYPE));
                textView.setText(stringBuilder.toString());
                Log.e("print", "222: " + textView);
            } else {
                intDefaults2 = Utils.getIntDefaults(getContext(), Constant.ADD_CUSTOM_WATER);
                Double.isNaN(intDefaults2);
                intDefaults = (intDefaults + intDefaults2) * Constant.oz;
                if (Utils.getWaterDrunkFromDefaults(getActivity(), Constant.PARAM_VALID_DRUNK_WATER) == 0.0f) {
                    d = Utils.getWaterDrunkFromDefaults(getActivity(), Constant.PARAM_VALID_DRUNK_WATER);
                } else {
                    d = Utils.getWaterDrunkFromDefaults(getActivity(), Constant.PARAM_VALID_DRUNK_WATER);
                    intDefaults2 = Constant.oz;
                    Double.isNaN(d);
                    d *= intDefaults2;
                }
                DecimalFormat decimalFormat = new DecimalFormat("##.#");
                TextView textView2 = this.tvtargetWater;
                stringBuilder2 = new StringBuilder();
                stringBuilder2.append(decimalFormat.format(d));
                stringBuilder2.append("/");
                stringBuilder2.append(Math.round(intDefaults));
                stringBuilder2.append(" ");
                stringBuilder2.append(Utils.getFromUserDefaults(getActivity(), Constant.PARAM_VALID_WATER_TYPE));
                textView2.setText(stringBuilder2.toString());
            }
        } else {
            double round = (double) Math.round(intDefaults / 15.0d);
            StringBuilder stringBuilder3 = new StringBuilder();
            stringBuilder3.append(parseInt2);
            stringBuilder3.append("");
            double parseDouble = Double.parseDouble(stringBuilder3.toString());
            stringBuilder3 = new StringBuilder();
            stringBuilder3.append(parseInt);
            stringBuilder3.append("");
            parseDouble -= Double.parseDouble(stringBuilder3.toString());
            DecimalFormat decimalFormat2 = new DecimalFormat("#.##");
            Double.isNaN(round);
            intDefaults2 = (double) Math.round(round * parseDouble);
            Utils.clearIntDefaults(getActivity(), Constant.DAILY_WATER_DRINK);
            Utils.saveIntDefaults(getActivity(), Constant.DAILY_WATER_DRINK, (int) intDefaults2);
            Log.e("print", "resetWater77: " + intDefaults2);
            Log.e("print", "resetWater77: " + (int) intDefaults);
            if (Utils.getFromUserDefaults(getActivity(), Constant.PARAM_VALID_WATER_TYPE).equals("ml")) {
                textView = this.tvtargetWater;
                stringBuilder = new StringBuilder();
                stringBuilder.append(Math.round(Utils.getWaterDrunkFromDefaults(getActivity(), Constant.PARAM_VALID_DRUNK_WATER)));
                stringBuilder.append("/");
//                intDefaults2 = (double) Utils.getIntDefaults(getContext(), Constant.ADD_CUSTOM_WATER);
//                Double.isNaN(intDefaults2);
                stringBuilder.append(Math.round(intDefaults2));
                stringBuilder.append(" ");
                stringBuilder.append(Utils.getFromUserDefaults(getActivity(), Constant.PARAM_VALID_WATER_TYPE));
                textView.setText(stringBuilder.toString());
            } else {
                intDefaults = Utils.getIntDefaults(getContext(), Constant.ADD_CUSTOM_WATER);
                Double.isNaN(intDefaults2);
                Double.isNaN(intDefaults);
                intDefaults2 = (intDefaults2 + intDefaults) * Constant.oz;
                if (Utils.getWaterDrunkFromDefaults(getActivity(), Constant.PARAM_VALID_DRUNK_WATER) == 0.0f) {
                    intDefaults = Utils.getWaterDrunkFromDefaults(getActivity(), Constant.PARAM_VALID_DRUNK_WATER);
                } else {
                    intDefaults = Utils.getWaterDrunkFromDefaults(getActivity(), Constant.PARAM_VALID_DRUNK_WATER);
                    d = Constant.oz;
                    Double.isNaN(intDefaults);
                    intDefaults *= d;
                }
                decimalFormat2 = new DecimalFormat("##.#");
                TextView textView3 = this.tvtargetWater;
                stringBuilder2 = new StringBuilder();
                stringBuilder2.append(decimalFormat2.format(intDefaults));
                stringBuilder2.append("/");
                stringBuilder2.append(Math.round(intDefaults2));
                stringBuilder2.append(" ");
                stringBuilder2.append(Utils.getFromUserDefaults(getActivity(), Constant.PARAM_VALID_WATER_TYPE));
                textView3.setText(stringBuilder2.toString());

            }
        }
        if (Math.round(Utils.getWaterDrunkFromDefaults(getActivity(), Constant.PARAM_VALID_DRUNK_WATER)) <= Math.round((float) Utils.getIntDefaults(getActivity(), Constant.DAILY_WATER_DRINK))) {
            this.pb.setProgress(Math.round(Utils.getWaterDrunkFromDefaults(getActivity(), Constant.PARAM_VALID_DRUNK_WATER)));
        } else {
            this.pb.setProgress(Math.round((float) Utils.getIntDefaults(getActivity(), Constant.DAILY_WATER_DRINK)));
        }
        this.pb.setMax(Math.round((float) Utils.getIntDefaults(getActivity(), Constant.DAILY_WATER_DRINK)));
    }

    public void setGoalReachedDialogue() {
        Builder builder = new Builder(getActivity());
        View inflate = getLayoutInflater().inflate(R.layout.dialog_goal_reached, null);
        builder.setView(inflate);
        LinearLayout linearLayout = inflate.findViewById(R.id.lout_goal_reached);
        final AlertDialog create = builder.create();
        create.show();
        create.setCanceledOnTouchOutside(false);
        linearLayout.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                Utils.saveToUserDefaults(getActivity(), Constant.GOAL_REACHED, "1");
                create.dismiss();
            }
        });
    }

    public void clickEvent() {
        this.iv_add_bottle.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                if (glassDataArrayList.size() == 0) {
                    lout_bottle.setVisibility(0);
                    rr1.setVisibility(8);
                    lout_default.setVisibility(0);
                    listBottle.setLayoutManager(new LinearLayoutManager(getActivity(), 0, false));
                    if (!Pref_Utils.getDefaultBottleListInfo(getActivity()).equals("")) {
                        bottleDataArrayList = new Gson().fromJson(Pref_Utils.getDefaultBottleListInfo(getActivity()), type);
                    }
                    bottleListAdapter = new BottleAdapter(getActivity(), bottleDataArrayList, HomeFragment.this);
                    listBottle.setAdapter(bottleListAdapter);
                    return;
                }
                addBottle();
            }
        });
        this.iv_select_bottle.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                lout_bottle.setVisibility(0);
                rr1.setVisibility(8);
                lout_default.setVisibility(0);
                listBottle.setLayoutManager(new LinearLayoutManager(getActivity(), 0, false));
                if (!Pref_Utils.getDefaultBottleListInfo(getActivity()).equals("")) {
                    bottleDataArrayList = new Gson().fromJson(Pref_Utils.getDefaultBottleListInfo(getActivity()), type);
                }
                bottleListAdapter = new BottleAdapter(getActivity(), bottleDataArrayList, HomeFragment.this);
                listBottle.setAdapter(bottleListAdapter);
            }
        });
        this.ivDone.setOnClickListener(new OnClickListener() {
            @SuppressLint("WrongConstant")
            public void onClick(View view) {
                if (etWeight.getText().length() > 0) {
                    Utils.saveGlassToDefaults(getActivity(), Constant.PARAM_VALID_GLASS, customBottleListAdapter.getGlass());
                    Bottle_Data bottleData = new Bottle_Data();
                    if (Utils.getFromUserDefaults(getActivity(), Constant.PARAM_VALID_WATER_TYPE).equals("ml")) {
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append("");
                        stringBuilder.append(Float.parseFloat(etWeight.getText().toString()));
                        bottleData.setUnit(stringBuilder.toString());
                        Utils.saveUnitToDefaults(getActivity(), Constant.PARAM_VALID_UNIT, Float.parseFloat(etWeight.getText().toString()));
                    } else {
                        double parseFloat = Float.parseFloat(etWeight.getText().toString());
                        double d = Constant.oz;
                        Double.isNaN(parseFloat);
                        float f = (float) (parseFloat / d);
                        StringBuilder stringBuilder2 = new StringBuilder();
                        stringBuilder2.append("");
                        stringBuilder2.append(f);
                        bottleData.setUnit(stringBuilder2.toString());
                        Utils.saveUnitToDefaults(getActivity(), Constant.PARAM_VALID_UNIT, f);
                    }
                    bottleData.setGlass(customBottleListAdapter.getGlass());
                    bottleData.setType(1);
                    if (bottleDataArrayList.size() > 6) {
                        bottleDataArrayList.remove(1);
                        bottleDataArrayList.add(1, bottleData);
                    } else {
                        bottleDataArrayList.add(1, bottleData);
                    }
                    Utils.saveGlassToDefaults(getActivity(), Constant.PARAM_VALID_TYPE, 1);
                    Pref_Utils.setDefaultBottileInfo(getActivity(), new Gson().toJson(bottleDataArrayList));
                    view = getActivity().getCurrentFocus();
                    if (view != null) {
                        FragmentActivity activity = getActivity();
                        getActivity();
                        ((InputMethodManager) activity.getSystemService("input_method")).hideSoftInputFromWindow(view.getWindowToken(), 0);
                    }
                    etWeight.setText("");
                    rr1.setVisibility(8);
                    lout_default.setVisibility(8);
                    lout_bottle.setVisibility(8);
                    addBottle();
                    return;
                }
                Toast.makeText(getContext(), "Please Enter Weight..!", 0).show();
            }
        });
        this.lout_bottle.setOnClickListener(new OnClickListener() {
            @SuppressLint("WrongConstant")
            public void onClick(View view) {
                if (lout_bottle.getVisibility() == 0) {
                    lout_bottle.setVisibility(8);
                }
            }
        });
    }

    @SuppressLint("WrongConstant")
    public void selectDefaultBottle(int i, String str, int i2) {
        Utils.saveUnitToDefaults(getActivity(), Constant.PARAM_VALID_UNIT, Float.parseFloat(str));
        Utils.saveGlassToDefaults(getActivity(), Constant.PARAM_VALID_GLASS, i2);
        if (this.bottleDataArrayList.size() <= 7) {
            Utils.saveGlassToDefaults(getActivity(), Constant.PARAM_VALID_TYPE, 0);
        } else if (i == 1) {
            Utils.saveGlassToDefaults(getActivity(), Constant.PARAM_VALID_TYPE, 1);
        } else {
            Utils.saveGlassToDefaults(getActivity(), Constant.PARAM_VALID_TYPE, 0);
        }
        this.lout_default.setVisibility(8);
        this.lout_bottle.setVisibility(8);
        addBottle();
    }

    public void addBottle() {
        if (this.glassDataArrayList.size() >= 1) {
            this.glassDataArrayList.remove(this.glassDataArrayList.size() - 1);
        }
        Glass_Data glassData = new Glass_Data();
        Calendar instance = Calendar.getInstance();
        @SuppressLint("WrongConstant") int i = instance.get(11);
        @SuppressLint("WrongConstant") int i2 = instance.get(12);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("");
        stringBuilder.append(i);
        glassData.setHour(stringBuilder.toString());
        StringBuilder stringBuilder2 = new StringBuilder();
        stringBuilder2.append("");
        stringBuilder2.append(i2);
        glassData.setMinute(stringBuilder2.toString());
        StringBuilder stringBuilder3 = new StringBuilder();
        stringBuilder3.append("");
        stringBuilder3.append(Utils.getUnitFromDefaults(getActivity(), Constant.PARAM_VALID_UNIT));
        glassData.setUnit(stringBuilder3.toString());
        glassData.setGlass(Utils.getGlassFromDefaults(getActivity(), Constant.PARAM_VALID_GLASS));
        this.glassDataArrayList.add(glassData);
        glassData = new Glass_Data();
        instance = Calendar.getInstance();
        int i3 = instance.get(11);
        i2 = instance.get(12);
        stringBuilder2 = new StringBuilder();
        stringBuilder2.append("");
        stringBuilder2.append(i3);
        glassData.setHour(stringBuilder2.toString());
        StringBuilder stringBuilder4 = new StringBuilder();
        stringBuilder4.append("");
        stringBuilder4.append(i2);
        glassData.setMinute(stringBuilder4.toString());
        stringBuilder3 = new StringBuilder();
        stringBuilder3.append("");
        stringBuilder3.append(Utils.getUnitFromDefaults(getActivity(), Constant.PARAM_VALID_UNIT));
        glassData.setUnit(stringBuilder3.toString());
        glassData.setGlass(Utils.getGlassFromDefaults(getActivity(), Constant.PARAM_VALID_GLASS));
        this.glassDataArrayList.add(glassData);
        Pref_Utils.setDefaultGlassInfo(getActivity(), new Gson().toJson(this.glassDataArrayList));
        this.glassListAdapter.notifyDataSetChanged();
        this.listGlass.scrollToPosition(this.glassDataArrayList.size() - 1);
        Utils.saveWaterDrunkDefaults(getActivity(), Constant.PARAM_VALID_DRUNK_WATER, Utils.getUnitFromDefaults(getActivity(), Constant.PARAM_VALID_UNIT).floatValue() + Utils.getWaterDrunkFromDefaults(getActivity(), Constant.PARAM_VALID_DRUNK_WATER));
        if (Math.round(Utils.getWaterDrunkFromDefaults(getActivity(), Constant.PARAM_VALID_DRUNK_WATER)) <= Math.round((float) Utils.getIntDefaults(getActivity(), Constant.DAILY_WATER_DRINK))) {
            this.pb.setProgress(Math.round(Utils.getWaterDrunkFromDefaults(getActivity(), Constant.PARAM_VALID_DRUNK_WATER)));
        } else {
            this.pb.setProgress(Math.round((float) Utils.getIntDefaults(getActivity(), Constant.DAILY_WATER_DRINK)));
        }
        if (Utils.getFromUserDefaults(getActivity(), Constant.GOAL_REACHED).equals("")) {
            if (Math.round(Utils.getWaterDrunkFromDefaults(getActivity(), Constant.PARAM_VALID_DRUNK_WATER)) >= Math.round((float) Utils.getIntDefaults(getActivity(), Constant.DAILY_WATER_DRINK))) {
                setGoalReachedDialogue();
//                Log.e("print", "addBottle: " + pb);
            }
        } else if (Math.round(Utils.getWaterDrunkFromDefaults(getActivity(), Constant.PARAM_VALID_DRUNK_WATER)) <= Math.round((float) Utils.getIntDefaults(getActivity(), Constant.DAILY_WATER_DRINK))) {
            Utils.saveToUserDefaults(getActivity(), Constant.GOAL_REACHED, "");
        }
        refresh();
    }

    public void showCustomLayout() {
        this.lout_default.setVisibility(8);
        this.rr1.setVisibility(0);
        if (Utils.getFromUserDefaults(getActivity(), Constant.PARAM_VALID_WATER_TYPE).equals("ml")) {
            this.tvUnitType.setText("ml");
        } else {
            this.tvUnitType.setText("fl.oz");
        }
        this.listCustomBottle = this.view.findViewById(R.id.listCustomBottle);
        this.listCustomBottle.setLayoutManager(new LinearLayoutManager(getActivity(), 0, false));
        this.customBottleListAdapter = new CustomBottleAdapter(getActivity(), Constant.bottle, this);
        this.listCustomBottle.setAdapter(this.customBottleListAdapter);
    }

    public void setDrunkWater(float f) {
        Utils.saveWaterDrunkDefaults(getActivity(), Constant.PARAM_VALID_DRUNK_WATER, (float) Math.round(Utils.getWaterDrunkFromDefaults(getActivity(), Constant.PARAM_VALID_DRUNK_WATER) - f));
        this.pb.setProgress(Math.round(Utils.getWaterDrunkFromDefaults(getActivity(), Constant.PARAM_VALID_DRUNK_WATER)));
        resetWater();
    }

    public void onResume() {
        super.onResume();
        init();
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(this.onNotice, new IntentFilter("hidelayout"));
    }

    public void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(this.onNotice);
    }
}
