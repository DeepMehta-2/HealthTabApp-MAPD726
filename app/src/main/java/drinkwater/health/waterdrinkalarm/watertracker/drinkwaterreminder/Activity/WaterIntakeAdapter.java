package drinkwater.health.waterdrinkalarm.watertracker.drinkwaterreminder.Activity;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.Adapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;

import org.apache.commons.lang3.BooleanUtils;

import java.util.List;

import drinkwater.health.waterdrinkalarm.watertracker.drinkwaterreminder.Helper.AppPref;
import drinkwater.health.waterdrinkalarm.watertracker.drinkwaterreminder.Helper.Constants;
import drinkwater.health.waterdrinkalarm.watertracker.drinkwaterreminder.Helper.NotificationHelper;
import drinkwater.health.waterdrinkalarm.watertracker.drinkwaterreminder.Helper.WaterIntake;
import drinkwater.health.waterdrinkalarm.watertracker.drinkwaterreminder.R;

public class WaterIntakeAdapter extends Adapter<WaterIntakeAdapter.ViewHolder> {
    DatabaseHelper db;
    /* access modifiers changed from: private */
    public Context mContext;
    private RecyclerView mRecyclerV;
    /* access modifiers changed from: private */
    public List<WaterIntake> mWaterIntakeList;
    NotificationHelper notificationHelper;

    public class ViewHolder extends RecyclerView.ViewHolder {
        public LinearLayout li;
        public View layout;
        public TextView txtCupSize;
        public TextView txtWaterTime;

        public ViewHolder(View view) {
            super(view);
            this.layout = view;
            this.txtWaterTime = (TextView) view.findViewById(R.id.txtWaterTime);
            this.txtCupSize = (TextView) view.findViewById(R.id.txtCupSize);
            this.li = (LinearLayout) view.findViewById(R.id.li);
        }
    }

    public WaterIntakeAdapter(List<WaterIntake> list, Context context, RecyclerView recyclerView) {
        this.mWaterIntakeList = list;
        this.mContext = context;
        this.mRecyclerV = recyclerView;
        this.notificationHelper = new NotificationHelper(context);
    }

    public void add(int i, WaterIntake waterIntake) {
        this.mWaterIntakeList.add(i, waterIntake);
        notifyItemInserted(i);
    }

    public void remove(int i) {
        this.mWaterIntakeList.remove(i);
        notifyItemRemoved(i);
    }

    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.single_row, viewGroup, false));
    }

    public void onBindViewHolder(ViewHolder viewHolder, final int i) {
        this.db = new DatabaseHelper(this.mContext);
        final WaterIntake waterIntake = (WaterIntake) this.mWaterIntakeList.get(i);
        viewHolder.txtWaterTime.setText(waterIntake.getWater_time());
        int new_cup_size = waterIntake.getNew_cup_size();
        TextView textView;
        StringBuilder stringBuilder;
        if (Homefragment.getkgstaus()) {
            textView = viewHolder.txtCupSize;
            stringBuilder = new StringBuilder();
            stringBuilder.append(String.valueOf(new_cup_size));
            stringBuilder.append(Constants.ML);
            textView.setText(stringBuilder.toString());
        } else {
            textView = viewHolder.txtCupSize;
            stringBuilder = new StringBuilder();
            stringBuilder.append(String.valueOf(Homefragment.returnfloz(new_cup_size)));
            stringBuilder.append(Constants.FL);
            textView.setText(stringBuilder.toString());
        }
        viewHolder.li.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                CharSequence stringBuilder;
                CharSequence stringBuilder2;
                CharSequence stringBuilder3;
                CharSequence charSequence = null;
                boolean z;
                Integer obj;
                StringBuilder stringBuilder4;
                RadioButton radioButton7;
                final RadioButton radioButton;
                final RadioButton radioButton2;
                Dialog dialog = new Dialog(WaterIntakeAdapter.this.mContext);
                dialog.setContentView(R.layout.rewritewaterintake_dialog);
                RadioGroup radioGroup = (RadioGroup) dialog.findViewById(R.id.redrewite);
                RadioButton radioButton3 = (RadioButton) dialog.findViewById(R.id.rdb1);
                RadioButton radioButton4 = (RadioButton) dialog.findViewById(R.id.rdb2);
                RadioButton radioButton5 = (RadioButton) dialog.findViewById(R.id.rdb3);
                RadioButton radioButton6 = (RadioButton) dialog.findViewById(R.id.rdb4);
                TextView textView = (TextView) dialog.findViewById(R.id.txtml1);
                TextView textView2 = (TextView) dialog.findViewById(R.id.txtml2);
                final TextView textView3 = (TextView) dialog.findViewById(R.id.txtml3);
                TextView textView4 = (TextView) dialog.findViewById(R.id.txtml4);
                StringBuilder stringBuilder5;
                String stringBuilder6;
                StringBuilder stringBuilder7;
                String charSequence2;
                if (Homefragment.getkgstaus()) {
                    stringBuilder5 = new StringBuilder();
                    stringBuilder5.append(String.valueOf(waterIntake.getCup_size() / 4));
                    stringBuilder5.append(Constants.ML);
                    stringBuilder6 = stringBuilder5.toString();
                    stringBuilder7 = new StringBuilder();
                    stringBuilder7.append(String.valueOf(waterIntake.getCup_size() / 2));
                    stringBuilder7.append(Constants.ML);
                    stringBuilder = stringBuilder7.toString();
                    stringBuilder7 = new StringBuilder();
                    stringBuilder7.append(String.valueOf((waterIntake.getCup_size() / 4) * 3));
                    stringBuilder7.append(Constants.ML);
                    stringBuilder2 = stringBuilder7.toString();
                    stringBuilder7 = new StringBuilder();
                    String str = stringBuilder6;
                    stringBuilder7.append(String.valueOf(waterIntake.getCup_size() / 1));
                    stringBuilder7.append(Constants.ML);
                    stringBuilder3 = stringBuilder7.toString();
                    charSequence = str;
                    z = true;
                    obj = Integer.valueOf(4);
                } else {
                    stringBuilder5 = new StringBuilder();
                    stringBuilder5.append(String.valueOf(Homefragment.returnfloz(waterIntake.getCup_size() / 4)));
                    stringBuilder5.append(Constants.FL);
                    stringBuilder6 = stringBuilder5.toString();
                    StringBuilder stringBuilder8 = new StringBuilder();
                    stringBuilder8.append(String.valueOf(Homefragment.returnfloz(waterIntake.getCup_size() / 2)));
                    stringBuilder8.append(Constants.FL);
                    stringBuilder = stringBuilder8.toString();
                    stringBuilder4 = new StringBuilder();
                    obj = Integer.valueOf(4);
                    stringBuilder4.append(String.valueOf(Homefragment.returnfloz((waterIntake.getCup_size() / 4) * 3)));
                    stringBuilder4.append(Constants.FL);
                    stringBuilder2 = stringBuilder4.toString();
                    stringBuilder7 = new StringBuilder();
                    String str2 = stringBuilder6;
                    z = true;
                    stringBuilder7.append(String.valueOf(Homefragment.returnfloz(waterIntake.getCup_size() / 1)));
                    stringBuilder7.append(Constants.FL);
                    stringBuilder3 = stringBuilder7.toString();
                     charSequence2 = str2;
                }
                textView.setText(charSequence);
                textView2.setText(stringBuilder);
                textView3.setText(stringBuilder2);
                textView4.setText(stringBuilder3);
                Integer obj2 = obj;
                Dialog dialog2 = dialog;
                RadioButton radioButton8 = radioButton3;
                boolean z2 = z;
                final TextView textView5 = textView;
                TextView textView6 = textView4;
                TextView textView7 = textView3;
                TextView textView8 = textView2;
                TextView textView9 = textView;
                final RadioButton radioButton9 = radioButton4;
                RadioButton radioButton10 = radioButton6;
                RadioButton radioButton11 = radioButton5;
                final RadioButton finalRadioButton = radioButton3;
                final TextView finalTextView = textView2;
                final TextView finalTextView1 = textView6;
                final RadioButton finalRadioButton1 = radioButton5;
                final RadioButton finalRadioButton2 = radioButton10;
                radioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {
                    public void onCheckedChanged(RadioGroup radioGroup, int i) {
                        if (finalRadioButton.isChecked()) {
                            finalRadioButton.setTextColor(WaterIntakeAdapter.this.mContext.getResources().getColor(R.color.white));
                            textView5.setTextColor(WaterIntakeAdapter.this.mContext.getResources().getColor(R.color.blue));
                            finalTextView.setTextColor(WaterIntakeAdapter.this.mContext.getResources().getColor(R.color.darkgray));
                            textView3.setTextColor(WaterIntakeAdapter.this.mContext.getResources().getColor(R.color.darkgray));
                            finalTextView1.setTextColor(WaterIntakeAdapter.this.mContext.getResources().getColor(R.color.darkgray));
                            radioButton9.setTextColor(WaterIntakeAdapter.this.mContext.getResources().getColor(R.color.blue));
                            finalRadioButton1.setTextColor(WaterIntakeAdapter.this.mContext.getResources().getColor(R.color.blue));
                            finalRadioButton2.setTextColor(WaterIntakeAdapter.this.mContext.getResources().getColor(R.color.blue));

                        }
                        if (radioButton9.isChecked()) {
                            radioButton9.setTextColor(WaterIntakeAdapter.this.mContext.getResources().getColor(R.color.white));
                            textView5.setTextColor(WaterIntakeAdapter.this.mContext.getResources().getColor(R.color.darkgray));
                            finalTextView.setTextColor(WaterIntakeAdapter.this.mContext.getResources().getColor(R.color.blue));
                            textView3.setTextColor(WaterIntakeAdapter.this.mContext.getResources().getColor(R.color.darkgray));
                            finalTextView1.setTextColor(WaterIntakeAdapter.this.mContext.getResources().getColor(R.color.darkgray));
                            finalRadioButton.setTextColor(WaterIntakeAdapter.this.mContext.getResources().getColor(R.color.blue));
                            finalRadioButton1.setTextColor(WaterIntakeAdapter.this.mContext.getResources().getColor(R.color.blue));
                            finalRadioButton2.setTextColor(WaterIntakeAdapter.this.mContext.getResources().getColor(R.color.blue));
                        }
                        if (finalRadioButton1.isChecked()) {
                            finalRadioButton1.setTextColor(WaterIntakeAdapter.this.mContext.getResources().getColor(R.color.white));
                            textView5.setTextColor(WaterIntakeAdapter.this.mContext.getResources().getColor(R.color.darkgray));
                            finalTextView.setTextColor(WaterIntakeAdapter.this.mContext.getResources().getColor(R.color.darkgray));
                            textView3.setTextColor(WaterIntakeAdapter.this.mContext.getResources().getColor(R.color.blue));
                            finalTextView1.setTextColor(WaterIntakeAdapter.this.mContext.getResources().getColor(R.color.darkgray));
                            finalRadioButton.setTextColor(WaterIntakeAdapter.this.mContext.getResources().getColor(R.color.blue));
                            radioButton9.setTextColor(WaterIntakeAdapter.this.mContext.getResources().getColor(R.color.blue));
                            finalRadioButton2.setTextColor(WaterIntakeAdapter.this.mContext.getResources().getColor(R.color.blue));
                        }
                        if (finalRadioButton2.isChecked()) {
                            finalRadioButton2.setTextColor(WaterIntakeAdapter.this.mContext.getResources().getColor(R.color.white));
                            textView5.setTextColor(WaterIntakeAdapter.this.mContext.getResources().getColor(R.color.darkgray));
                            finalTextView.setTextColor(WaterIntakeAdapter.this.mContext.getResources().getColor(R.color.darkgray));
                            textView3.setTextColor(WaterIntakeAdapter.this.mContext.getResources().getColor(R.color.darkgray));
                            finalTextView1.setTextColor(WaterIntakeAdapter.this.mContext.getResources().getColor(R.color.blue));
                            finalRadioButton.setTextColor(WaterIntakeAdapter.this.mContext.getResources().getColor(R.color.blue));
                            radioButton9.setTextColor(WaterIntakeAdapter.this.mContext.getResources().getColor(R.color.blue));
                            finalRadioButton1.setTextColor(WaterIntakeAdapter.this.mContext.getResources().getColor(R.color.blue));

                        }
                    }
                });
                boolean cup_flag = BooleanUtils.toBoolean(((WaterIntake) WaterIntakeAdapter.this.mWaterIntakeList.get(i)).getCup_flag());
                final int water_id = ((WaterIntake) WaterIntakeAdapter.this.mWaterIntakeList.get(i)).getWater_id();
                stringBuilder4 = new StringBuilder();
                stringBuilder4.append("");
                stringBuilder4.append(cup_flag);
                if (cup_flag == z2) {
                    textView9.setTextColor(WaterIntakeAdapter.this.mContext.getResources().getColor(R.color.white));
                    radioButton7 = radioButton8;
                    radioButton7.setChecked(z2);
                } else {
                    radioButton7 = radioButton8;
                }
                if (cup_flag) {
                    textView8.setTextColor(WaterIntakeAdapter.this.mContext.getResources().getColor(R.color.blue));
                    radioButton4.setChecked(z2);
                }
                if (cup_flag) {
                    textView7.setTextColor(WaterIntakeAdapter.this.mContext.getResources().getColor(R.color.blue));
                    radioButton = radioButton11;
                    radioButton.setChecked(z2);
                } else {
                    radioButton = radioButton11;
                }
                if (cup_flag) {
                    textView6.setTextColor(WaterIntakeAdapter.this.mContext.getResources().getColor(R.color.blue));
                    radioButton2 = radioButton10;
                    radioButton2.setChecked(z2);
                } else {
                    radioButton2 = radioButton10;
                }
                Dialog dialog3 = dialog2;
                Button button = (Button) dialog3.findViewById(R.id.buttonok);
                Button button2 = (Button) dialog3.findViewById(R.id.buttoncancel);
                AppCompatImageView appCompatImageView = (AppCompatImageView) dialog3.findViewById(R.id.imagebuttondelete);
                dialog3.getWindow().setBackgroundDrawable(new ColorDrawable(0));
                final RadioButton radioButton12 = radioButton4;
                final Dialog dialog4 = dialog3;
                final RadioButton finalRadioButton3 = radioButton7;
                button.setOnClickListener(new OnClickListener() {
                    public void onClick(View view) {
                        int cup_size = 0;
                        if (finalRadioButton3.isChecked()) {
                            cup_size = waterIntake.getCup_size() / 4;
                            ((WaterIntake) WaterIntakeAdapter.this.mWaterIntakeList.get(i)).setCup_flag(1);
                            WaterIntakeAdapter.this.db.updateml(WaterIntakeAdapter.this.mContext, cup_size, 1, water_id);
                        }
                        if (radioButton12.isChecked()) {
                            cup_size = waterIntake.getCup_size() / 2;
                            ((WaterIntake) WaterIntakeAdapter.this.mWaterIntakeList.get(i)).setCup_flag(2);
                            WaterIntakeAdapter.this.db.updateml(WaterIntakeAdapter.this.mContext, cup_size, 2, water_id);
                        }
                        if (radioButton.isChecked()) {
                            cup_size = (waterIntake.getCup_size() / 4) * 3;
                            ((WaterIntake) WaterIntakeAdapter.this.mWaterIntakeList.get(i)).setCup_flag(3);
                            WaterIntakeAdapter.this.db.updateml(WaterIntakeAdapter.this.mContext, cup_size, 3, water_id);
                        }
                        if (radioButton2.isChecked()) {
                            cup_size = waterIntake.getCup_size();
                            ((WaterIntake) WaterIntakeAdapter.this.mWaterIntakeList.get(i)).setCup_flag(4);
                            WaterIntakeAdapter.this.db.updateml(WaterIntakeAdapter.this.mContext, cup_size, 4, water_id);
                        }
                        dialog4.dismiss();
                        ((WaterIntake) WaterIntakeAdapter.this.mWaterIntakeList.get(i)).setNew_cup_size(cup_size);
                        if (Homefragment.getkgstaus()) {
                            Homefragment.colorArcProgressBar.setMax(Homefragment.getMaxML());
                            Homefragment.textviewcountml.setText(String.valueOf(Homefragment.getCurrentML()));
                            Homefragment.colorArcProgressBar.setProgress(Homefragment.getCurrentML() > Homefragment.getMaxML() ? Homefragment.getMaxML() : Homefragment.getCurrentML());
                        } else {
                            Homefragment.colorArcProgressBar.setMax(Homefragment.returnfloz(Homefragment.getMaxML()));
                            Homefragment.textviewcountml.setText(String.valueOf(Homefragment.returnfloz(Homefragment.getCurrentML())));
                            Homefragment.colorArcProgressBar.setProgress(Homefragment.returnfloz(Homefragment.getCurrentML() > Homefragment.getMaxML() ? Homefragment.getMaxML() : Homefragment.getCurrentML()));
                        }
                        if (AppPref.getToggle(WaterIntakeAdapter.this.mContext)) {
                            WaterIntakeAdapter.this.notificationHelper.getnotification(WaterIntakeAdapter.this.mContext);
                        }
                        WaterIntakeAdapter.this.notifyDataSetChanged();
                    }
                });
                Dialog dialog5 = dialog3;
                final Dialog finalDialog = dialog5;
                button2.setOnClickListener(new OnClickListener() {
                    public void onClick(View view) {
                        finalDialog.dismiss();
                    }
                });
                dialog5 = dialog3;
                final Dialog finalDialog1 = dialog5;
                appCompatImageView.setOnClickListener(new OnClickListener() {
                    public void onClick(View view) {
                        WaterIntakeAdapter.this.db.deleteWaterIntake(waterIntake.getWater_id(), WaterIntakeAdapter.this.mContext);
                        WaterIntakeAdapter.this.mWaterIntakeList.remove(i);
                        if (Homefragment.getkgstaus()) {
                            Homefragment.colorArcProgressBar.setMax(Homefragment.getMaxML());
                            Homefragment.colorArcProgressBar.setProgress(Homefragment.getCurrentML() > Homefragment.getMaxML() ? Homefragment.getMaxML() : Homefragment.getCurrentML());
                        } else {
                            Homefragment.colorArcProgressBar.setMax(Homefragment.returnfloz(Homefragment.getMaxML()));
                            Homefragment.colorArcProgressBar.setProgress(Homefragment.returnfloz(Homefragment.getCurrentML() > Homefragment.getMaxML() ? Homefragment.getMaxML() : Homefragment.getCurrentML()));
                        }
                        WaterIntakeAdapter.this.notifyItemRemoved(i);
                        WaterIntakeAdapter.this.notifyItemRangeChanged(i, WaterIntakeAdapter.this.mWaterIntakeList.size());
                        Homefragment.mRecyclerView.setAdapter(Homefragment.adapter);
                        Homefragment.textviewcountml.setRiseInterval((float) Integer.valueOf((String) Homefragment.textviewcountml.getText()).intValue(), (float) (Homefragment.getkgstaus() ? Homefragment.getCurrentML() : Homefragment.returnfloz(Homefragment.getCurrentML()))).setDuration(1000).runInt(true).start();
                        if (WaterIntakeAdapter.this.mWaterIntakeList != null && WaterIntakeAdapter.this.mWaterIntakeList.size() == 0) {
                            if (WaterIntakeAdapter.this.db.getHydrardeCount() > 0) {
                                TextView view0 = Homefragment.txtHydradeCount;
                                StringBuilder stringBuilder = new StringBuilder();
                                stringBuilder.append(WaterIntakeAdapter.this.db.getHydrardeCount());
                                stringBuilder.append(" DAY");
                                view0.setText(stringBuilder.toString());
                            } else {
                                Homefragment.txtHydradeCount.setText("START DRINKING FIRST CUP OF WATER!");
                            }
                        }
                        if (AppPref.getToggle(WaterIntakeAdapter.this.mContext)) {
                            WaterIntakeAdapter.this.notificationHelper.getnotification(WaterIntakeAdapter.this.mContext);
                        }
                        finalDialog1.dismiss();
                    }
                });
                try {
                    dialog3.show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }


    public int getItemCount() {
        return this.mWaterIntakeList.size();
    }
}
