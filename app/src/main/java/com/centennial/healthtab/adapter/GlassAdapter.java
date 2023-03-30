package com.centennial.healthtab.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.RemoteViews;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AlertDialog.Builder;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.Adapter;

import com.centennial.healthtab.R;
import com.centennial.healthtab.fragment.HomeFragment;
import com.centennial.healthtab.object.Glass_Data;
import com.centennial.healthtab.object.Notification_Data;
import com.centennial.healthtab.service.Alarm_Receiver;
import com.centennial.healthtab.utils.Constant;
import com.centennial.healthtab.utils.Pref_Utils;
import com.centennial.healthtab.utils.Utils;
import com.centennial.healthtab.widget.WidgetNotification_Mode;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kofigyan.stateprogressbar.StateProgressBar;
import com.kofigyan.stateprogressbar.StateProgressBar.StateNumber;
import com.kofigyan.stateprogressbar.components.StateItem;
import com.kofigyan.stateprogressbar.listeners.OnStateItemClickListener;

import java.lang.reflect.Type;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class GlassAdapter extends Adapter<GlassAdapter.ViewHolder> {
    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;
    double calculatedWater;
    RelativeLayout cancel_notification_mode;
    RelativeLayout empty_recycler_lout;
    String hTime = "";
    HomeFragment homeFragment;
    private int hour;
    ImageView ivDelete;
    LinearLayout lout_notification_minuse;
    LinearLayout lout_notification_mute;
    LinearLayout lout_notification_vibrate;
    LinearLayout lout_notification_volume;
    Activity mContext;
    ArrayList<Glass_Data> mList = new ArrayList();
    String mTime = "";
    ImageView minuse_icon;
    private int minute;
    ImageView mute_icon;
    ArrayList<Notification_Data> notificationDataArrayList;
    double saveWater;
    LinearLayout select_notification_background;
    TextView select_notification_mode;
    StateProgressBar stateprogressbar;
    String timeSet = "";
    TextView tvWater;
    Type type = new TypeToken<List<Notification_Data>>() {
    }.getType();
    Type type1 = new TypeToken<List<Glass_Data>>() {
    }.getType();
    ImageView vibrate_icon;
    ImageView volume_icon;

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivImg;
        LinearLayout lout_main;
        TextView tvTime;
        TextView tvUnit;

        public ViewHolder(View view) {
            super(view);
            this.lout_main = view.findViewById(R.id.lout_main);
            this.tvUnit = view.findViewById(R.id.tvUnit);
            this.tvTime = view.findViewById(R.id.tvTime);
            this.ivImg = view.findViewById(R.id.ivImg);
        }
    }

    public int getItemViewType(int i) {
        return 1;
    }

    public GlassAdapter(Activity activity, ArrayList<Glass_Data> arrayList, HomeFragment homeFragment, RelativeLayout relativeLayout) {
        this.mContext = activity;
        this.mList = arrayList;
        this.homeFragment = homeFragment;
        this.empty_recycler_lout = relativeLayout;
        this.notificationDataArrayList = new Gson().fromJson(Pref_Utils.getDefaultNotificationListInfo(activity), this.type);
    }

    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item_glass, viewGroup, false));
    }

    @SuppressLint("WrongConstant")
    public void onBindViewHolder(final ViewHolder viewHolder, final int i) {
        try {
            if (this.mList.size() <= 1) {
                this.empty_recycler_lout.setVisibility(0);
                return;
            }
            StringBuilder stringBuilder;
            this.empty_recycler_lout.setVisibility(8);
            this.hour = Integer.parseInt(this.mList.get(i).getHour());
            this.minute = Integer.parseInt(this.mList.get(i).getMinute());
            StringBuilder stringBuilder2 = new StringBuilder();
            stringBuilder2.append(this.hour);
            stringBuilder2.append(":");
            stringBuilder2.append(this.minute);
            if (Utils.getTimerDefaults(this.mContext, Constant.PARAM_VALID_TIMER).equals("12 hour")) {
                if (this.hour > 12) {
                    this.hour -= 12;
                    this.timeSet = " PM";
                } else if (this.hour == 0) {
                    this.hour += 12;
                    this.timeSet = " AM";
                } else if (this.hour == 12) {
                    this.timeSet = " PM";
                } else {
                    this.timeSet = " AM";
                }
            }
            if (this.hour < 1 || this.hour > 9) {
                this.hTime = String.valueOf(this.hour);
            } else {
                stringBuilder = new StringBuilder();
                stringBuilder.append("0");
                stringBuilder.append(this.hour);
                this.hTime = stringBuilder.toString();
            }
            if (this.minute < 1 || this.minute > 9) {
                this.mTime = String.valueOf(this.minute);
            } else {
                stringBuilder = new StringBuilder();
                stringBuilder.append("0");
                stringBuilder.append(this.minute);
                this.mTime = stringBuilder.toString();
            }
            if (i == this.mList.size() - 1) {
                for (int i2 = 0; i2 < this.notificationDataArrayList.size(); i2++) {
                    Calendar instance = Calendar.getInstance();
                    String[] split = this.notificationDataArrayList.get(i2).getDate().split(" ")[1].split(":");
                    if (Integer.parseInt(split[0]) <= instance.get(11) && Integer.parseInt(split[1]) <= instance.get(12)) {
                        String valueOf;
                        StringBuilder stringBuilder3;
                        String valueOf2;
                        String[] split2 = this.notificationDataArrayList.get(i2 + 1).getDate().split(" ")[1].split(":");
                        String str = "";
                        int parseInt = Integer.parseInt(split2[0]);
                        int parseInt2 = Integer.parseInt(split2[1]);
                        if (Utils.getTimerDefaults(this.mContext, Constant.PARAM_VALID_TIMER).equals("12 hour")) {
                            if (parseInt > 12) {
                                parseInt -= 12;
                                str = " PM";
                            } else if (parseInt == 0) {
                                parseInt += 12;
                                str = " AM";
                            } else {
                                str = parseInt == 12 ? " PM" : " AM";
                            }
                        }
                        if (parseInt < 1 || parseInt > 9) {
                            valueOf = parseInt == 0 ? "00" : String.valueOf(parseInt);
                        } else {
                            stringBuilder3 = new StringBuilder();
                            stringBuilder3.append("0");
                            stringBuilder3.append(parseInt);
                            valueOf = stringBuilder3.toString();
                        }
                        if (parseInt2 < 1 || parseInt2 > 9) {
                            valueOf2 = parseInt2 == 0 ? "00" : String.valueOf(parseInt2);
                        } else {
                            stringBuilder3 = new StringBuilder();
                            stringBuilder3.append("0");
                            stringBuilder3.append(parseInt2);
                            valueOf2 = stringBuilder3.toString();
                        }
                        TextView textView = viewHolder.tvTime;
                        StringBuilder stringBuilder4 = new StringBuilder();
                        stringBuilder4.append(valueOf);
                        stringBuilder4.append(":");
                        stringBuilder4.append(valueOf2);
                        stringBuilder4.append(str);
                        textView.setText(stringBuilder4.toString());
                        stringBuilder4 = new StringBuilder();
                        stringBuilder4.append(valueOf);
                        stringBuilder4.append(":");
                        stringBuilder4.append(valueOf2);
                        stringBuilder4.append("--");
                        stringBuilder4.append(str);
                    }
                }
                viewHolder.tvUnit.setVisibility(8);
                if (Utils.getNotificationModeDefaults(this.mContext, Constant.NOTIFICATION_MODE).equals("mute")) {
                    viewHolder.ivImg.setImageResource(R.drawable.iv_notification_mute);
                } else if (Utils.getNotificationModeDefaults(this.mContext, Constant.NOTIFICATION_MODE).equals("minuse")) {
                    viewHolder.ivImg.setImageResource(R.drawable.iv_notification_minus);
                } else if (Utils.getNotificationModeDefaults(this.mContext, Constant.NOTIFICATION_MODE).equals("vibrate")) {
                    viewHolder.ivImg.setImageResource(R.drawable.iv_notification_vibrate);
                } else if (Utils.getNotificationModeDefaults(this.mContext, Constant.NOTIFICATION_MODE).equals("volume")) {
                    viewHolder.ivImg.setImageResource(R.drawable.iv_notification_volume);
                }
                viewHolder.ivImg.setPadding(15, 15, 15, 15);
            } else {
                TextView textView2;
                StringBuilder stringBuilder5;
                viewHolder.ivImg.setPadding(0, 0, 0, 0);
                viewHolder.tvUnit.setVisibility(0);
                if (Utils.getFromUserDefaults(this.mContext, "choose_water_type").equals("ml")) {
                    textView2 = viewHolder.tvUnit;
                    stringBuilder5 = new StringBuilder();
                    stringBuilder5.append("");
                    stringBuilder5.append(Math.round(Float.parseFloat(this.mList.get(i).getUnit())));
                    stringBuilder5.append("");
                    stringBuilder5.append(Utils.getFromUserDefaults(this.mContext, "choose_water_type"));
                    textView2.setText(stringBuilder5.toString());
                } else {
                    double parseDouble = Double.parseDouble(this.mList.get(i).getUnit()) * Constant.oz;
                    DecimalFormat decimalFormat = new DecimalFormat("##.#");
                    TextView textView3 = viewHolder.tvUnit;
                    stringBuilder2 = new StringBuilder();
                    stringBuilder2.append("");
                    stringBuilder2.append(decimalFormat.format(parseDouble));
                    stringBuilder2.append("");
                    stringBuilder2.append(Utils.getFromUserDefaults(this.mContext, "choose_water_type"));
                    textView3.setText(stringBuilder2.toString());
                }
                textView2 = viewHolder.tvTime;
                stringBuilder5 = new StringBuilder();
                stringBuilder5.append(this.hTime);
                stringBuilder5.append(":");
                stringBuilder5.append(this.mTime);
                stringBuilder5.append(this.timeSet);
                textView2.setText(stringBuilder5.toString());
                viewHolder.ivImg.setImageResource(Constant.bottle[this.mList.get(i).getGlass()]);
            }
            viewHolder.lout_main.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    if (i == GlassAdapter.this.mList.size() - 1) {
                        GlassAdapter.this.showNotificationModeDialogue();
                    } else {
                        GlassAdapter.this.showDeleteDialogue(viewHolder.tvUnit.getText().toString(), i);
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showNotificationModeDialogue() {
        final Dialog dialog = new Dialog(this.mContext);
        dialog.requestWindowFeature(1);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_notification_mode);
        this.lout_notification_mute = dialog.findViewById(R.id.lout_notification_mute);
        this.lout_notification_minuse = dialog.findViewById(R.id.lout_notification_minuse);
        this.lout_notification_vibrate = dialog.findViewById(R.id.lout_notification_vibrate);
        this.lout_notification_volume = dialog.findViewById(R.id.lout_notification_volume);
        this.select_notification_mode = dialog.findViewById(R.id.select_notification_mode);
        this.cancel_notification_mode = dialog.findViewById(R.id.cancel_notification_mode);
        this.select_notification_background = dialog.findViewById(R.id.select_notification_background);
        this.mute_icon = dialog.findViewById(R.id.mute_icon);
        this.minuse_icon = dialog.findViewById(R.id.minuse_icon);
        this.vibrate_icon = dialog.findViewById(R.id.vibrate_icon);
        this.volume_icon = dialog.findViewById(R.id.volume_icon);
        NotificationMode();
        this.lout_notification_mute.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                Utils.saveNotificationModeDefaults(GlassAdapter.this.mContext, Constant.NOTIFICATION_MODE, "mute");
                GlassAdapter.this.NotificationMode();
            }
        });
        this.lout_notification_minuse.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                Utils.saveNotificationModeDefaults(GlassAdapter.this.mContext, Constant.NOTIFICATION_MODE, "minuse");
                GlassAdapter.this.NotificationMode();
            }
        });
        this.lout_notification_vibrate.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                Utils.saveNotificationModeDefaults(GlassAdapter.this.mContext, Constant.NOTIFICATION_MODE, "vibrate");
                GlassAdapter.this.NotificationMode();
            }
        });
        this.lout_notification_volume.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                Utils.saveNotificationModeDefaults(GlassAdapter.this.mContext, Constant.NOTIFICATION_MODE, "volume");
                GlassAdapter.this.NotificationMode();
            }
        });
        this.cancel_notification_mode.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    @SuppressLint({"NewApi", "ResourceAsColor"})
    private void NotificationMode() {
        if (Utils.getNotificationModeDefaults(this.mContext, Constant.NOTIFICATION_MODE).equals("mute")) {
            this.select_notification_mode.setText(this.mContext.getResources().getString(R.string.mute));
            this.mute_icon.setImageDrawable(this.mContext.getResources().getDrawable(R.drawable.iv_mute_select));
            this.minuse_icon.setImageDrawable(this.mContext.getResources().getDrawable(R.drawable.iv_notification_minus));
            this.vibrate_icon.setImageDrawable(this.mContext.getResources().getDrawable(R.drawable.iv_notification_vibrate));
            this.volume_icon.setImageDrawable(this.mContext.getResources().getDrawable(R.drawable.iv_notification_volume));
            this.lout_notification_mute.setBackground(this.mContext.getResources().getDrawable(R.drawable.round_fillcolor));
            this.lout_notification_minuse.setBackground(this.mContext.getResources().getDrawable(R.drawable.round));
            this.lout_notification_vibrate.setBackground(this.mContext.getResources().getDrawable(R.drawable.round));
            this.lout_notification_volume.setBackground(this.mContext.getResources().getDrawable(R.drawable.round));
            new Alarm_Receiver().setRepeatAlarm(this.mContext, 11, Calendar.getInstance(), "");
        } else if (Utils.getNotificationModeDefaults(this.mContext, Constant.NOTIFICATION_MODE).equals("minuse")) {
            this.select_notification_mode.setText(this.mContext.getResources().getString(R.string.minuse));
            this.mute_icon.setImageDrawable(this.mContext.getResources().getDrawable(R.drawable.iv_notification_mute));
            this.minuse_icon.setImageDrawable(this.mContext.getResources().getDrawable(R.drawable.iv_minuse_select));
            this.vibrate_icon.setImageDrawable(this.mContext.getResources().getDrawable(R.drawable.iv_notification_vibrate));
            this.volume_icon.setImageDrawable(this.mContext.getResources().getDrawable(R.drawable.iv_notification_volume));
            this.lout_notification_mute.setBackground(this.mContext.getResources().getDrawable(R.drawable.round));
            this.lout_notification_minuse.setBackground(this.mContext.getResources().getDrawable(R.drawable.round_fillcolor));
            this.lout_notification_vibrate.setBackground(this.mContext.getResources().getDrawable(R.drawable.round));
            this.lout_notification_volume.setBackground(this.mContext.getResources().getDrawable(R.drawable.round));
            new Alarm_Receiver().setRepeatAlarm(this.mContext, 11, Calendar.getInstance(), "");
        } else if (Utils.getNotificationModeDefaults(this.mContext, Constant.NOTIFICATION_MODE).equals("vibrate")) {
            this.select_notification_mode.setText(this.mContext.getResources().getString(R.string.vibrate));
            this.mute_icon.setImageDrawable(this.mContext.getResources().getDrawable(R.drawable.iv_notification_mute));
            this.minuse_icon.setImageDrawable(this.mContext.getResources().getDrawable(R.drawable.iv_notification_minus));
            this.vibrate_icon.setImageDrawable(this.mContext.getResources().getDrawable(R.drawable.ic_voluse_vibret));
            this.volume_icon.setImageDrawable(this.mContext.getResources().getDrawable(R.drawable.iv_notification_volume));
            this.lout_notification_mute.setBackground(this.mContext.getResources().getDrawable(R.drawable.round));
            this.lout_notification_minuse.setBackground(this.mContext.getResources().getDrawable(R.drawable.round));
            this.lout_notification_vibrate.setBackground(this.mContext.getResources().getDrawable(R.drawable.round_fillcolor));
            this.lout_notification_volume.setBackground(this.mContext.getResources().getDrawable(R.drawable.round));
            new Alarm_Receiver().setRepeatAlarm(this.mContext, 11, Calendar.getInstance(), "");
        } else if (Utils.getNotificationModeDefaults(this.mContext, Constant.NOTIFICATION_MODE).equals("volume")) {
            this.select_notification_mode.setText(this.mContext.getResources().getString(R.string.volume));
            this.mute_icon.setImageDrawable(this.mContext.getResources().getDrawable(R.drawable.iv_notification_mute));
            this.minuse_icon.setImageDrawable(this.mContext.getResources().getDrawable(R.drawable.iv_notification_minus));
            this.vibrate_icon.setImageDrawable(this.mContext.getResources().getDrawable(R.drawable.iv_notification_vibrate));
            this.volume_icon.setImageDrawable(this.mContext.getResources().getDrawable(R.drawable.ic_voluse_select));
            this.lout_notification_mute.setBackground(this.mContext.getResources().getDrawable(R.drawable.round));
            this.lout_notification_minuse.setBackground(this.mContext.getResources().getDrawable(R.drawable.round));
            this.lout_notification_vibrate.setBackground(this.mContext.getResources().getDrawable(R.drawable.round));
            this.lout_notification_volume.setBackground(this.mContext.getResources().getDrawable(R.drawable.round_fillcolor));
            new Alarm_Receiver().setRepeatAlarm(this.mContext, 11, Calendar.getInstance(), "");
        }
        new WidgetNotification_Mode().NotificationMode(this.mContext, new RemoteViews(this.mContext.getPackageName(), R.layout.widget_notification_mode));
        notifyDataSetChanged();
    }

    public void showDeleteDialogue(String str, final int i) {
        String[] strArr = new String[]{"1/4", "1/2", "1/4", "1"};
        Builder builder = new Builder(this.mContext);
        View inflate = this.mContext.getLayoutInflater().inflate(R.layout.dialog_edit_water_bottal, null);
        builder.setView(inflate);
        this.stateprogressbar = inflate.findViewById(R.id.usage_stateprogressbar);
        this.stateprogressbar.setStateDescriptionData(strArr);
        this.stateprogressbar.setCurrentStateNumber(StateNumber.FOUR);
        this.tvWater = inflate.findViewById(R.id.tvWater);
        this.tvWater.setText(str);
        this.ivDelete = inflate.findViewById(R.id.ivDelete);
        final DecimalFormat decimalFormat = new DecimalFormat("##.#");
        this.calculatedWater = Double.parseDouble(this.mList.get(i).getUnit());
        this.stateprogressbar.setOnStateItemClickListener(new OnStateItemClickListener() {
            public void onStateItemClick(StateProgressBar stateProgressBar, StateItem stateItem, int i1, boolean z) {
                TextView textView;
                StringBuilder stringBuilder;
                if (i1 == 1) {
                    if (Utils.getFromUserDefaults(GlassAdapter.this.mContext, "choose_water_type").equals("ml")) {
                        GlassAdapter.this.calculatedWater = Double.parseDouble(GlassAdapter.this.mList.get(i).getUnit()) / 4.0d;
                        textView = GlassAdapter.this.tvWater;
                        stringBuilder = new StringBuilder();
                        stringBuilder.append("");
                        stringBuilder.append(Math.round(GlassAdapter.this.calculatedWater));
                        stringBuilder.append(" ");
                        stringBuilder.append(Utils.getFromUserDefaults(GlassAdapter.this.mContext, "choose_water_type"));
                        textView.setText(stringBuilder.toString());
                    } else {
                        GlassAdapter.this.calculatedWater = (Double.parseDouble(GlassAdapter.this.mList.get(i).getUnit()) / 4.0d) * Constant.oz;
                        textView = GlassAdapter.this.tvWater;
                        stringBuilder = new StringBuilder();
                        stringBuilder.append("");
                        stringBuilder.append(decimalFormat.format(GlassAdapter.this.calculatedWater));
                        stringBuilder.append(" ");
                        stringBuilder.append(Utils.getFromUserDefaults(GlassAdapter.this.mContext, "choose_water_type"));
                        textView.setText(stringBuilder.toString());
                    }
                    GlassAdapter.this.stateprogressbar.setCurrentStateNumber(StateNumber.ONE);
                } else if (i1 == 2) {
                    if (Utils.getFromUserDefaults(GlassAdapter.this.mContext, "choose_water_type").equals("ml")) {
                        GlassAdapter.this.calculatedWater = Double.parseDouble(GlassAdapter.this.mList.get(i).getUnit()) / 2.0d;
                        textView = GlassAdapter.this.tvWater;
                        stringBuilder = new StringBuilder();
                        stringBuilder.append("");
                        stringBuilder.append(Math.round(GlassAdapter.this.calculatedWater));
                        stringBuilder.append(" ");
                        stringBuilder.append(Utils.getFromUserDefaults(GlassAdapter.this.mContext, "choose_water_type"));
                        textView.setText(stringBuilder.toString());
                    } else {
                        GlassAdapter.this.calculatedWater = (Double.parseDouble(GlassAdapter.this.mList.get(i).getUnit()) / 2.0d) * Constant.oz;
                        textView = GlassAdapter.this.tvWater;
                        stringBuilder = new StringBuilder();
                        stringBuilder.append("");
                        stringBuilder.append(decimalFormat.format(GlassAdapter.this.calculatedWater));
                        stringBuilder.append(" ");
                        stringBuilder.append(Utils.getFromUserDefaults(GlassAdapter.this.mContext, "choose_water_type"));
                        textView.setText(stringBuilder.toString());
                    }
                    GlassAdapter.this.stateprogressbar.setCurrentStateNumber(StateNumber.TWO);
                } else if (i1 == 3) {
                    if (Utils.getFromUserDefaults(GlassAdapter.this.mContext, "choose_water_type").equals("ml")) {
                        GlassAdapter.this.calculatedWater = (Double.parseDouble(GlassAdapter.this.mList.get(i).getUnit()) * 3.0d) / 4.0d;
                        textView = GlassAdapter.this.tvWater;
                        stringBuilder = new StringBuilder();
                        stringBuilder.append("");
                        stringBuilder.append(Math.round(GlassAdapter.this.calculatedWater));
                        stringBuilder.append(" ");
                        stringBuilder.append(Utils.getFromUserDefaults(GlassAdapter.this.mContext, "choose_water_type"));
                        textView.setText(stringBuilder.toString());
                    } else {
                        GlassAdapter.this.calculatedWater = ((Double.parseDouble(GlassAdapter.this.mList.get(i).getUnit()) * 3.0d) / 4.0d) * Constant.oz;
                        textView = GlassAdapter.this.tvWater;
                        stringBuilder = new StringBuilder();
                        stringBuilder.append("");
                        stringBuilder.append(decimalFormat.format(GlassAdapter.this.calculatedWater));
                        stringBuilder.append(" ");
                        stringBuilder.append(Utils.getFromUserDefaults(GlassAdapter.this.mContext, "choose_water_type"));
                        textView.setText(stringBuilder.toString());
                    }
                    GlassAdapter.this.stateprogressbar.setCurrentStateNumber(StateNumber.THREE);
                } else if (i1 == 4) {
                    if (Utils.getFromUserDefaults(GlassAdapter.this.mContext, "choose_water_type").equals("ml")) {
                        GlassAdapter.this.calculatedWater = Double.parseDouble(GlassAdapter.this.mList.get(i).getUnit());
                        textView = GlassAdapter.this.tvWater;
                        stringBuilder = new StringBuilder();
                        stringBuilder.append("");
                        stringBuilder.append(Math.round(GlassAdapter.this.calculatedWater));
                        stringBuilder.append(" ");
                        stringBuilder.append(Utils.getFromUserDefaults(GlassAdapter.this.mContext, "choose_water_type"));
                        textView.setText(stringBuilder.toString());
                    } else {
                        GlassAdapter.this.calculatedWater = Double.parseDouble(GlassAdapter.this.mList.get(i).getUnit()) * Constant.oz;
                        textView = GlassAdapter.this.tvWater;
                        stringBuilder = new StringBuilder();
                        stringBuilder.append("");
                        stringBuilder.append(decimalFormat.format(GlassAdapter.this.calculatedWater));
                        stringBuilder.append(" ");
                        stringBuilder.append(Utils.getFromUserDefaults(GlassAdapter.this.mContext, "choose_water_type"));
                        textView.setText(stringBuilder.toString());
                    }
                    GlassAdapter.this.stateprogressbar.setCurrentStateNumber(StateNumber.FOUR);
                }
            }
        });
        builder.setPositiveButton("SAVE", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i2
            ) {
                if (!Utils.getFromUserDefaults(GlassAdapter.this.mContext, Constant.PARAM_VALID_WATER_TYPE).equals("ml")) {
                    GlassAdapter.this.calculatedWater /= Constant.oz;
                }
                double parseDouble = Double.parseDouble(GlassAdapter.this.mList.get(i).getUnit()) - GlassAdapter.this.calculatedWater;
                GlassAdapter.this.mList.get(i).setUnit(String.valueOf(Math.round(GlassAdapter.this.calculatedWater)));
                GlassAdapter.this.notifyDataSetChanged();
                ArrayList arrayList = new ArrayList();
                if (!Pref_Utils.getDefaultGlassListInfo(GlassAdapter.this.mContext).equals("")) {
                    ArrayList obj = new Gson().fromJson(Pref_Utils.getDefaultGlassListInfo(GlassAdapter.this.mContext), GlassAdapter.this.type1);
                    ((Glass_Data) obj.get(i)).setUnit(String.valueOf(Math.round(GlassAdapter.this.calculatedWater)));
                    Pref_Utils.setDefaultGlassInfo(GlassAdapter.this.mContext, new Gson().toJson(obj));
                    GlassAdapter.this.homeFragment.setDrunkWater((float) parseDouble);
                }
                dialogInterface.dismiss();
            }
        });
        builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        final AlertDialog create = builder.create();
        create.show();
        create.setCanceledOnTouchOutside(false);
        this.ivDelete.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                final double parseDouble = Double.parseDouble(GlassAdapter.this.mList.get(i).getUnit());
                final Dialog dialog = new Dialog(GlassAdapter.this.mContext);
                dialog.requestWindowFeature(1);
                dialog.setContentView(R.layout.dialog_delete);
                TextView textView = dialog.findViewById(R.id.delete_cancel);
                dialog.findViewById(R.id.delete_done).setOnClickListener(new OnClickListener() {
                    @SuppressLint("WrongConstant")
                    public void onClick(View view) {
                        GlassAdapter.this.mList.remove(i);
                        if (GlassAdapter.this.mList.size() == 1) {
                            GlassAdapter.this.mList.clear();
                            GlassAdapter.this.empty_recycler_lout.setVisibility(0);
                        }
                        GlassAdapter.this.notifyDataSetChanged();
                        ArrayList arrayList = new ArrayList();
                        if (!Pref_Utils.getDefaultGlassListInfo(GlassAdapter.this.mContext).equals("")) {
                            ArrayList obj = new Gson().fromJson(Pref_Utils.getDefaultGlassListInfo(GlassAdapter.this.mContext), GlassAdapter.this.type1);
                            obj.remove(i);
                            Pref_Utils.setDefaultGlassInfo(GlassAdapter.this.mContext, new Gson().toJson(obj));
                            GlassAdapter.this.homeFragment.setDrunkWater((float) parseDouble);
                        }
                        dialog.dismiss();
                    }
                });
                textView.setOnClickListener(new OnClickListener() {
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
                create.dismiss();
            }
        });
    }

    public int getItemCount() {
        return this.mList.size();
    }
}
