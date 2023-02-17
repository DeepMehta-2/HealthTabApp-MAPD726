package drinkwater.health.waterdrinkalarm.watertracker.drinkwaterreminder.Activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.LayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.github.lzyzsd.circleprogress.ArcProgress;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import drinkwater.health.waterdrinkalarm.watertracker.drinkwaterreminder.Helper.AppConstants;
import drinkwater.health.waterdrinkalarm.watertracker.drinkwaterreminder.Helper.AppPref;
import drinkwater.health.waterdrinkalarm.watertracker.drinkwaterreminder.Helper.Constants;
import drinkwater.health.waterdrinkalarm.watertracker.drinkwaterreminder.Helper.NotificationHelper;
import drinkwater.health.waterdrinkalarm.watertracker.drinkwaterreminder.R;
import drinkwater.health.waterdrinkalarm.watertracker.drinkwaterreminder.ReceiverAndService.AlarmUtill;
import drinkwater.health.waterdrinkalarm.watertracker.drinkwaterreminder.Helper.WaterIntake;
import drinkwater.health.waterdrinkalarm.watertracker.drinkwaterreminder.views.onDialogClick;
import drinkwater.health.waterdrinkalarm.watertracker.drinkwaterreminder.views.RiseNumberTextView;
import drinkwater.health.waterdrinkalarm.watertracker.drinkwaterreminder.views.RiseNumberTextView.RiseListener;

public class Homefragment extends Fragment {
    public static final int DIALOG_FRAGMENT = 1;
    static WaterIntakeAdapter adapter = null;
    static ArcProgress colorArcProgressBar = null;
    public static int currentML = 0;
    public static double floz = 0.033814d;
    public static boolean kgstatus = true;
    static RecyclerView mRecyclerView;
    public static List<WaterIntake> mWaterIntakeList;
    public static int maxML;
    public static TextView nextReminder;
    static RiseNumberTextView textviewcountml;
    public static TextView txtHydradeCount;
    public static TextView txtml;
    public Context context;
    ArrayList<cupmaster> cupmasters;
    DatabaseHelper db;
    boolean listnstop = false;
    private LayoutManager mLayoutManager;
    NotificationHelper notificationHelper;
    RelativeLayout relative1;
    TVShowFragment tv;
    TextView txtcurrentcup;
    TextView txtmlcup;
    private Dialog dialog;

    public interface Value {
        void setValue();
    }

    public static Homefragment newInstance() {
        return new Homefragment();
    }

    public static boolean getkgstaus() {
        return kgstatus;
    }

    public static int getMaxML() {
        return maxML;
    }

    public static int getCurrentML() {
        return currentML;
    }

    public static void setkgstatus(Context context) {
        kgstatus = AppPref.isKglb(context);
    }

    public static int returnfloz(int i) {
        return (int) Math.round(((double) i) * 0.033814d);
    }

    public static int returnml(int i) {
        return (int) Math.round(((double) i) * 0.033814d);
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        TextView textView;
        StringBuilder stringBuilder;
        View inflate = layoutInflater.inflate(R.layout.fragment_homefragment, viewGroup, false);
        RelativeLayout appCompatImageButton = (RelativeLayout) inflate.findViewById(R.id.button_add);
        ImageView appCompatImageButton2 = (ImageView) inflate.findViewById(R.id.imgbutton_add);
        colorArcProgressBar = (ArcProgress) inflate.findViewById(R.id.colorArcProgressBar);
        txtml = (TextView) inflate.findViewById(R.id.textviewml);
        nextReminder = (TextView) inflate.findViewById(R.id.nextReminder);
        textviewcountml = (RiseNumberTextView) inflate.findViewById(R.id.textviewcountml);
        this.txtcurrentcup = (TextView) inflate.findViewById(R.id.txtcurrentcup);
        this.txtmlcup = (TextView) inflate.findViewById(R.id.txtmlcup);
        this.relative1 = (RelativeLayout) inflate.findViewById(R.id.relative1);
        txtHydradeCount = (TextView) inflate.findViewById(R.id.txtHydradeCount);
        ((ScrollView) inflate.findViewById(R.id.scroll)).fullScroll(33);
        this.notificationHelper = new NotificationHelper(getContext());
        this.cupmasters = new ArrayList();
        setkgstatus(getContext());
        this.tv = new TVShowFragment(new onDialogClick() {
            public void onDisimiss() {
                Homefragment.this.tv.dismiss();
                Homefragment.this.tv.show(Homefragment.this.getFragmentManager().beginTransaction(), "TV_tag");
            }
        });
        this.db = new DatabaseHelper(getContext());
        boolean z = HomeActivity.defaultcup;
        if (AppPref.getNextreminderTime(getContext()) != 0) {
            nextReminder.setVisibility(0);
            textView = nextReminder;
            stringBuilder = new StringBuilder();
            stringBuilder.append("Next Reminder : ");
            stringBuilder.append(AlarmUtill.toHHMMSS(AppPref.getNextreminderTime(getContext())));
            textView.setText(stringBuilder.toString());
        } else {
            nextReminder.setVisibility(4);
        }
        this.db.setverable(getContext());
        if (getkgstaus()) {
            this.txtcurrentcup.setText(String.valueOf(this.db.getCurrentCupSize()));
            textviewcountml.setText(String.valueOf(getCurrentML()));
            textView = txtml;
            stringBuilder = new StringBuilder();
            stringBuilder.append(getMaxML());
            stringBuilder.append(Constants.ML);
            textView.setText(String.valueOf(stringBuilder.toString()));
            colorArcProgressBar.setMax(getMaxML());
            colorArcProgressBar.setProgress(getCurrentML() >= getMaxML() ? getMaxML() : getCurrentML());
            this.txtmlcup.setText(Constants.ML);
        } else {
            this.txtcurrentcup.setText(String.valueOf(returnfloz(this.db.getCurrentCupSize())));
            textviewcountml.setText(String.valueOf(returnfloz(getCurrentML())));
            textView = txtml;
            stringBuilder = new StringBuilder();
            stringBuilder.append(returnfloz(getMaxML()));
            stringBuilder.append(Constants.FL);
            textView.setText(stringBuilder.toString());
            colorArcProgressBar.setMax(returnfloz(getMaxML()));
            colorArcProgressBar.setProgress(returnfloz(getCurrentML() >= getMaxML() ? getMaxML() : getCurrentML()));
            this.txtmlcup.setText(Constants.FL);
        }
        appCompatImageButton.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                if (Homefragment.this.tv.getView() == null) {
                    Homefragment.this.tv.setTargetFragment(Homefragment.this, 1);
                    Homefragment.this.tv.show(Homefragment.this.getFragmentManager().beginTransaction(), "TV_tag");
                }
            }
        });
        mRecyclerView = (RecyclerView) inflate.findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        this.mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(this.mLayoutManager);
        if (this.db.getHydrardeCount() > 0) {
            TextView textView2 = txtHydradeCount;
            StringBuilder stringBuilder2 = new StringBuilder();
            stringBuilder2.append(this.db.getHydrardeCount());
            stringBuilder2.append(" DAY");
            textView2.setText(stringBuilder2.toString());
        } else {
            txtHydradeCount.setText("START DRINKING FIRST CUP OF WATER!");
        }
        mWaterIntakeList = this.db.getAllWaterIntake();
        adapter = new WaterIntakeAdapter(mWaterIntakeList, getActivity(), mRecyclerView);
        mRecyclerView.setAdapter(adapter);
        NotificationHelper notificationHelper = new NotificationHelper(getActivity());
        this.relative1.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                Homefragment.this.drinkWater();
            }
        });
        if ((getActivity() instanceof HomeActivity) && ((HomeActivity) getActivity()).aBoolean) {
            HomeActivity.cancelNotification(getContext(), 101);
            drinkWater();
            ((HomeActivity) getActivity()).aBoolean = false;
        }
        return inflate;
    }

    public void setUserVisibleHint(boolean z) {
        if (z) {
            refresh();
        }
        super.setUserVisibleHint(z);
    }

    public void refresh() {
        if (colorArcProgressBar == null) {
            return;
        }
        if (getkgstaus()) {
            colorArcProgressBar.setMax(getMaxML());
            colorArcProgressBar.setProgress(getCurrentML() > getMaxML() ? getMaxML() : getCurrentML());
            return;
        }
        colorArcProgressBar.setMax(returnfloz(getMaxML()));
        colorArcProgressBar.setProgress(returnfloz(getCurrentML() > getMaxML() ? getMaxML() : getCurrentML()));
    }

    public void drinkWater() {
        try {
            String format = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
            String format2 = new SimpleDateFormat("HH:mm:ss").format(new Date());
            int currentCupSize = this.db.getCurrentCupSize();
            int insertWaterIntake = this.db.insertWaterIntake(format, format2, MyAdapter.selectedPosition, currentCupSize, currentCupSize, getContext());
            if (insertWaterIntake != -1) {
                this.listnstop = true;
                mWaterIntakeList.add(0, new WaterIntake(insertWaterIntake, format, format2, MyAdapter.selectedPosition, currentCupSize, 4, currentCupSize));
                adapter.notifyItemInserted(0);
                setRiseupText(Integer.valueOf((String) textviewcountml.getText()).intValue());
                if (getkgstaus()) {
                    colorArcProgressBar.setMax(getMaxML());
                    textviewcountml.setText(String.valueOf(getCurrentML()));
                    colorArcProgressBar.setProgress(getCurrentML() > getMaxML() ? getMaxML() : getCurrentML());
                } else {
                    int maxML;
                    colorArcProgressBar.setMax(returnfloz(getMaxML()));
                    textviewcountml.setText(String.valueOf(returnfloz(getCurrentML())));
                    ArcProgress arcProgress = colorArcProgressBar;
                    if (getCurrentML() > getMaxML()) {
                        maxML = getMaxML();
                    } else {
                        maxML = getCurrentML();
                    }
                    arcProgress.setProgress(returnfloz(maxML));
                }
                if (this.db.getHydrardeCount() > 0) {
                    TextView textView = txtHydradeCount;
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append(this.db.getHydrardeCount());
                    stringBuilder.append(" DAY");
                    textView.setText(stringBuilder.toString());
                } else {
                    txtHydradeCount.setText("START DRINKING FIRST CUP OF WATER!");
                }
                textviewcountml.setOnRiseEndListener(new RiseListener() {
                    public void onRiseEndFinish() {
                        if (Homefragment.this.listnstop) {
                            Homefragment.this.listnstop = false;
                            if (Homefragment.this.getContext() != null) {
                                dialog = new Dialog(Homefragment.this.getContext(), android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
                                dialog.setContentView(R.layout.gotit_dialog);
                                dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
                                dialog.setCanceledOnTouchOutside(false);
                                FrameLayout native_ad_container = (FrameLayout) dialog.findViewById(R.id.native_ad_container1);
                                ((Button) dialog.findViewById(R.id.gotit)).setOnClickListener(new OnClickListener() {
                                    public void onClick(View view) {
                                        try {
                                            dialog.dismiss();
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                        Homefragment.mRecyclerView.setAdapter(Homefragment.adapter);
                                        if (AppPref.getToggle(Homefragment.this.getActivity())) {
                                            Homefragment.this.notificationHelper.getnotification(Homefragment.this.getActivity());
                                        }
                                    }
                                });
                                try {
                                    dialog.show();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setRiseupText(int i) {
        if (getkgstaus()) {
            textviewcountml.setRiseInterval((float) i, (float) getCurrentML()).setDuration(1000).runInt(true).start();
        } else {
            textviewcountml.setRiseInterval((float) i, (float) returnfloz(getCurrentML())).setDuration(1000).runInt(true).start();
        }
    }


    public void onPause() {
        this.tv.getValue();
        super.onPause();
    }

    public void onResume() {
        super.onResume();
        adapter.notifyDataSetChanged();
    }

    public void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        if (i2 == -1) {
            this.txtcurrentcup.setText(String.valueOf(getkgstaus() ? this.db.getCurrentCupSize() : returnfloz(this.db.getCurrentCupSize())));
            intent.getStringExtra("listdata");
        }
    }
}
