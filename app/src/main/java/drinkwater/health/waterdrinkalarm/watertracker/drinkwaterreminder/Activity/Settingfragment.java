package drinkwater.health.waterdrinkalarm.watertracker.drinkwaterreminder.Activity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.u.securekeys.SecureEnvironment;

import java.util.Calendar;

import drinkwater.health.waterdrinkalarm.watertracker.drinkwaterreminder.Helper.AppPref;
import drinkwater.health.waterdrinkalarm.watertracker.drinkwaterreminder.Helper.NotificationHelper;
import drinkwater.health.waterdrinkalarm.watertracker.drinkwaterreminder.R;
import drinkwater.health.waterdrinkalarm.watertracker.drinkwaterreminder.ReceiverAndService.AlarmUtill;

public class Settingfragment extends Fragment {
    public static CheckBox checknotification;
    public static TextView textreminderauto;
    int cHour;
    int cMinute;
    Calendar calendarawake;
    Calendar calendarsleep;
    Context context;
    int currentHour;
    int currentMinute;
    DatabaseHelper db;
    NotificationHelper notificationHelper;
    RelativeLayout relativeLayout2;
    RelativeLayout relawaketime;
    RelativeLayout relgender;
    RelativeLayout relinterval;
    RelativeLayout relnotification;
    RelativeLayout relreminderauto;
    RelativeLayout relsleeptime;
    RelativeLayout relunit;
    RelativeLayout relweight;
    TextView textnotification_status;
    TimePickerDialog timePickerDialogawake;
    TimePickerDialog timePickerDialogsleep;
    String title = "If you like Drink Water, show us some love with a 5-Stars Rating: it really helps.";
    TextView txtgender;
    TextView txtkgml;
    TextView txtremindertime;
    TextView txtsleeptime;
    TextView txtwaketime;
    TextView txtweight;
    TextView txtweightkglb;

    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.fragment_settingfragment, viewGroup, false);
        FrameLayout adMobView = (FrameLayout) inflate.findViewById(R.id.adMobView);
        this.txtgender = (TextView) inflate.findViewById(R.id.txtgender2);
        this.txtweight = (TextView) inflate.findViewById(R.id.textweight2);
        this.txtweightkglb = (TextView) inflate.findViewById(R.id.textweightkglb);
        this.txtwaketime = (TextView) inflate.findViewById(R.id.textwaketime2);
        this.txtsleeptime = (TextView) inflate.findViewById(R.id.textsleeptime2);
        this.txtremindertime = (TextView) inflate.findViewById(R.id.txtreminder2);
        textreminderauto = (TextView) inflate.findViewById(R.id.textreminderauto2);
        this.txtkgml = (TextView) inflate.findViewById(R.id.txtkgml);
        this.textnotification_status = (TextView) inflate.findViewById(R.id.textnotification_status);
        checknotification = (CheckBox) inflate.findViewById(R.id.is_notification);
        this.relgender = (RelativeLayout) inflate.findViewById(R.id.relpersonaldetail);
        this.relweight = (RelativeLayout) inflate.findViewById(R.id.relweight);
        this.relawaketime = (RelativeLayout) inflate.findViewById(R.id.relwaketime);
        this.relsleeptime = (RelativeLayout) inflate.findViewById(R.id.relsleeptime);
        this.relinterval = (RelativeLayout) inflate.findViewById(R.id.relinterval);
        this.relreminderauto = (RelativeLayout) inflate.findViewById(R.id.relreminderauto);
        this.relnotification = (RelativeLayout) inflate.findViewById(R.id.relnotification);
        this.relnotification.setOnClickListener(new OnClickListener() {
            @SuppressLint({"NewApi"})
            public void onClick(View view) {
                final Dialog dialog = new Dialog(Settingfragment.this.context);
                dialog.setContentView(R.layout.show_notification_dialog);
                Button button = (Button) dialog.findViewById(R.id.buttonoknotification);
                Button button2 = (Button) dialog.findViewById(R.id.buttoncancelnotification);
                RadioGroup radioGroup = (RadioGroup) dialog.findViewById(R.id.radioGroupnotification);
                final RadioButton radioButton = (RadioButton) dialog.findViewById(R.id.radsimplenotification);
                RadioButton radioButton2 = (RadioButton) dialog.findViewById(R.id.radadvancenotification);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
                radioButton.setChecked(AppPref.getNotificationType(Settingfragment.this.getContext()));
                radioButton2.setChecked(AppPref.getNotificationType(Settingfragment.this.getContext()));
                button.setOnClickListener(new OnClickListener() {
                    public void onClick(View view) {
                        if (radioButton.isChecked()) {
                            AppPref.setNotificationType(Settingfragment.this.getActivity(), true);
                        } else {
                            AppPref.setNotificationType(Settingfragment.this.getActivity(), false);
                        }
                        Settingfragment.this.textnotification_status.setText(AppPref.getNotificationType(Settingfragment.this.getContext()) ? "Simple notification" : "Full Screen notification");
                        dialog.dismiss();
                    }
                });
                button2.setOnClickListener(new OnClickListener() {
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
                try {
                    dialog.show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        this.relunit = (RelativeLayout) inflate.findViewById(R.id.relunit);
        this.db = new DatabaseHelper(this.context);
        this.notificationHelper = new NotificationHelper(this.context);
        this.txtgender.setText(AppPref.isMale(getContext()) ? "Male" : "Female");
        Boolean valueOf = Boolean.valueOf(AppPref.isKglb(getContext()));
        int weight = AppPref.getWeight(getContext());
        StringBuilder stringBuilder;
        if (valueOf.booleanValue()) {
            TextView textView = this.txtweightkglb;
            stringBuilder = new StringBuilder();
            stringBuilder.append(weight);
            stringBuilder.append(" Kg");
            textView.setText(stringBuilder.toString());
        } else {
            int round = (int) Math.round(((double) weight) * 2.20462d);
            TextView textView2 = this.txtweightkglb;
            stringBuilder = new StringBuilder();
            stringBuilder.append(round);
            stringBuilder.append(" lbs");
            textView2.setText(stringBuilder.toString());
        }
        if (AppPref.getToggle(getContext())) {
            checknotification.setChecked(true);
        } else {
            checknotification.setChecked(false);
        }
        TextView textView3 = this.txtwaketime;
        StringBuilder stringBuilder2 = new StringBuilder();
        stringBuilder2.append(AppPref.getAwaketime(getContext()));
        stringBuilder2.append("");
        textView3.setText(stringBuilder2.toString());
        textView3 = this.txtsleeptime;
        stringBuilder2 = new StringBuilder();
        stringBuilder2.append(AppPref.getSleeptime(getContext()));
        stringBuilder2.append("");
        textView3.setText(stringBuilder2.toString());
        this.txtremindertime.setText(getReminderText());
        this.txtkgml.setText(AppPref.isKglb(getContext()) ? "Kg,ml" : "lbs,fl oz");
        this.textnotification_status.setText(AppPref.getNotificationType(getContext()) ? "Simple notification" : "Full Screen notification");
        textreminderauto.setText(getRemindersound());
        final LayoutInflater layoutInflater2 = layoutInflater;
        this.relweight.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                final Dialog dialog = new Dialog(Settingfragment.this.context);
                dialog.setContentView(R.layout.kgdialog);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
                final EditText editText = (EditText) dialog.findViewById(R.id.editkg);
                TextView textView = (TextView) dialog.findViewById(R.id.txtkg);
                final Boolean valueOf = Boolean.valueOf(AppPref.isKglb(Settingfragment.this.getContext()));
                if (valueOf.booleanValue()) {
                    textView.setText("Kg");
                } else {
                    textView.setText("lbs");
                }
                Button button = (Button) dialog.findViewById(R.id.buttoncancelweight);
                Button button2 = (Button) dialog.findViewById(R.id.buttonokweight);
                button2.setOnClickListener(new OnClickListener() {
                    public void onClick(View view) {
                        try {
                            if (editText.getText().length() <= 0 || Integer.parseInt(editText.getText().toString()) > 0) {
                                int parseInt = Integer.parseInt(editText.getText().toString());
                                if (!valueOf.booleanValue()) {
                                    parseInt = (int) Math.round(((double) parseInt) / 2.20462d);
                                }
                                AppPref.setWeight(Settingfragment.this.getContext(), parseInt);
                                int i = AppPref.getWeight(Settingfragment.this.context);
                                int round = (int) Math.round(((double) i) * 2.20462d);
                                StringBuilder stringBuilder;
                                if (valueOf.booleanValue()) {
                                    TextView textView = Settingfragment.this.txtweightkglb;
                                    stringBuilder = new StringBuilder();
                                    stringBuilder.append(String.valueOf(i));
                                    stringBuilder.append(" Kg");
                                    textView.setText(stringBuilder.toString());
                                } else {
                                    TextView textView2 = Settingfragment.this.txtweightkglb;
                                    stringBuilder = new StringBuilder();
                                    stringBuilder.append(String.valueOf(round));
                                    stringBuilder.append(" lbs");
                                    textView2.setText(stringBuilder.toString());
                                }
                                Settingfragment.this.db.updateweight(Settingfragment.this.context, parseInt);
                                String str = "colorArcProgressBar";
                                StringBuilder stringBuilder2 = new StringBuilder();
                                stringBuilder2.append("");
                                stringBuilder2.append(Homefragment.colorArcProgressBar.getMax());
                                stringBuilder2.append(" ");
                                stringBuilder2.append(Homefragment.colorArcProgressBar.getProgress());
                                stringBuilder2.append(" ");
                                stringBuilder2.append(Homefragment.getMaxML());
                                stringBuilder2.append(" ");
                                stringBuilder2.append(Homefragment.getCurrentML());
                                stringBuilder2.append(" ");
                                stringBuilder2.append(Homefragment.getCurrentML() > Homefragment.getMaxML() ? Homefragment.getMaxML() : Homefragment.getCurrentML());
                                Log.d(str, stringBuilder2.toString());
                                if (Homefragment.getkgstaus()) {
                                    Homefragment.colorArcProgressBar.setMax(Homefragment.getMaxML());
                                    Homefragment.colorArcProgressBar.setProgress(Homefragment.getCurrentML() > Homefragment.getMaxML() ? Homefragment.getMaxML() : Homefragment.getCurrentML());
                                } else {
                                    Homefragment.colorArcProgressBar.setMax(Homefragment.returnfloz(Homefragment.getMaxML()));
                                    Homefragment.colorArcProgressBar.setProgress(Homefragment.returnfloz(Homefragment.getCurrentML() > Homefragment.getMaxML() ? Homefragment.getMaxML() : Homefragment.getCurrentML()));
                                }
                                TextView textView3 = Homefragment.txtml;
                                stringBuilder2 = new StringBuilder();
                                stringBuilder2.append(Settingfragment.this.db.getMl(Settingfragment.this.getContext()));
                                stringBuilder2.append(Settingfragment.this.context.getResources().getString(R.string.ml));
                                textView3.setText(String.valueOf(stringBuilder2.toString()));
                                if (Settingfragment.checknotification.isChecked()) {
                                    Settingfragment.this.notificationHelper.getnotification(Settingfragment.this.context);
                                }
                                dialog.dismiss();
                                return;
                            }
                            Toast.makeText(Settingfragment.this.context, "Enter your weight(Weight should not less than 10)", Toast.LENGTH_SHORT).show();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                });
                button.setOnClickListener(new OnClickListener() {
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
                try {
                    dialog.show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        this.relawaketime.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                Settingfragment.this.calendarawake = Calendar.getInstance();
                String[] split = AppPref.getAwaketime(Settingfragment.this.context).split(":");
                int parseInt = Integer.parseInt(split[0]);
                int parseInt2 = Integer.parseInt(split[1]);
                Settingfragment.this.cHour = parseInt;
                Settingfragment.this.cMinute = parseInt2;
                Settingfragment.this.timePickerDialogawake = new TimePickerDialog(Settingfragment.this.getContext(), new OnTimeSetListener() {
                    public void onTimeSet(TimePicker timePicker, int i, int i2) {
                        Settingfragment.this.txtwaketime.setText(String.format("%02d:%02d", new Object[]{Integer.valueOf(i), Integer.valueOf(i2)}));
                        Settingfragment.this.cHour = i;
                        Settingfragment.this.cMinute = i2;
                        AppPref.setAwaketime(Settingfragment.this.getContext(), Settingfragment.this.txtwaketime.getText().toString());
                    }
                }, Settingfragment.this.cHour, Settingfragment.this.cMinute, true);
                Settingfragment.this.timePickerDialogawake.show();
            }
        });
        this.relsleeptime.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                Settingfragment.this.calendarsleep = Calendar.getInstance();
                String[] split = AppPref.getSleeptime(Settingfragment.this.context).split(":");
                int parseInt = Integer.parseInt(split[0]);
                int parseInt2 = Integer.parseInt(split[1]);
                Settingfragment.this.currentHour = parseInt;
                Settingfragment.this.currentMinute = parseInt2;
                Settingfragment.this.timePickerDialogsleep = new TimePickerDialog(Settingfragment.this.getContext(), new OnTimeSetListener() {
                    public void onTimeSet(TimePicker timePicker, int i, int i2) {
                        Settingfragment.this.currentHour = i;
                        Settingfragment.this.currentMinute = i2;
                        Calendar instance = Calendar.getInstance();
                        instance.set(11, i);
                        instance.set(12, i2);
                        instance.set(13, 0);
                        if (AlarmUtill.getWakeupTime(Settingfragment.this.getContext()) > instance.getTimeInMillis()) {
                            Toast.makeText(Settingfragment.this.context, "Sleeptime must be greater than wakeuptime", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        Settingfragment.this.txtsleeptime.setText(String.format("%02d:%02d", new Object[]{Integer.valueOf(i), Integer.valueOf(i2)}));
                        AppPref.setSleeptime(Settingfragment.this.getContext(), Settingfragment.this.txtsleeptime.getText().toString());
                    }
                }, Settingfragment.this.currentHour, Settingfragment.this.currentMinute, true);
                Settingfragment.this.timePickerDialogsleep.show();
            }
        });
        this.relunit.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                final Dialog dialog = new Dialog(Settingfragment.this.context);
                dialog.setContentView(R.layout.unitdialog);
                Button button = (Button) dialog.findViewById(R.id.buttonokunit);
                Button button2 = (Button) dialog.findViewById(R.id.buttoncancelunit);
                RadioGroup radioGroup = (RadioGroup) dialog.findViewById(R.id.radioGroupGender);
                final RadioButton radioButton = (RadioButton) dialog.findViewById(R.id.radkg);
                RadioButton radioButton2 = (RadioButton) dialog.findViewById(R.id.radlb);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
                radioButton.setChecked(AppPref.isKglb(Settingfragment.this.getContext()));
                radioButton2.setChecked(AppPref.isKglb(Settingfragment.this.getContext()));
                button.setOnClickListener(new OnClickListener() {
                    public void onClick(View view) {
                        int weight;
                        TextView textView;
                        StringBuilder stringBuilder;
                        if (radioButton.isChecked()) {
                            AppPref.setKglb(Settingfragment.this.getActivity(), true);
                            weight = AppPref.getWeight(Settingfragment.this.context);
                            textView = Settingfragment.this.txtweightkglb;
                            stringBuilder = new StringBuilder();
                            stringBuilder.append(String.valueOf(weight));
                            stringBuilder.append(" Kg");
                            textView.setText(stringBuilder.toString());
                        } else {
                            AppPref.setKglb(Settingfragment.this.getActivity(), false);
                            weight = (int) Math.round(((double) AppPref.getWeight(Settingfragment.this.context)) * 2.20462d);
                            textView = Settingfragment.this.txtweightkglb;
                            stringBuilder = new StringBuilder();
                            stringBuilder.append(String.valueOf(weight));
                            stringBuilder.append(" lbs");
                            textView.setText(stringBuilder.toString());
                        }
                        Homefragment.setkgstatus(Settingfragment.this.getActivity());
                        Settingfragment.this.txtkgml.setText(AppPref.isKglb(Settingfragment.this.getContext()) ? "Kg,ml" : "lbs,fl oz");
                        if (AppPref.getToggle(Settingfragment.this.getContext())) {
                            Settingfragment.this.notificationHelper.getnotification(Settingfragment.this.context);
                        }
                        dialog.dismiss();
                    }
                });
                button2.setOnClickListener(new OnClickListener() {
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
                try {
                    dialog.show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        this.relgender.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                final Dialog dialog = new Dialog(Settingfragment.this.context);
                dialog.setContentView(R.layout.genderdialog);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
                Button button = (Button) dialog.findViewById(R.id.buttonokgender);
                Button button2 = (Button) dialog.findViewById(R.id.buttoncancelgender);
                RadioGroup radioGroup = (RadioGroup) dialog.findViewById(R.id.radioGroupGender);
                final RadioButton radioButton = (RadioButton) dialog.findViewById(R.id.radMale);
                RadioButton radioButton2 = (RadioButton) dialog.findViewById(R.id.radFemale);
                radioButton.setChecked(AppPref.isMale(Settingfragment.this.getContext()));
                radioButton2.setChecked(AppPref.isMale(Settingfragment.this.getContext()));
                button.setOnClickListener(new OnClickListener() {
                    public void onClick(View view) {
                        if (radioButton.isChecked()) {
                            AppPref.setMale(Settingfragment.this.getActivity(), true);
                        } else {
                            AppPref.setMale(Settingfragment.this.getActivity(), false);
                        }
                        Settingfragment.this.txtgender.setText(AppPref.isMale(Settingfragment.this.getContext()) ? "Male" : "Female");
                        dialog.dismiss();
                    }
                });
                button2.setOnClickListener(new OnClickListener() {
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
                try {
                    dialog.show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        checknotification.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                if (Settingfragment.checknotification.isChecked()) {
                    Settingfragment.checknotification.setChecked(true);
                    AppPref.setToggle(Settingfragment.this.context, true);
                    Settingfragment.this.notificationHelper.getnotification(Settingfragment.this.context);
                    return;
                }
                Settingfragment.checknotification.setChecked(false);
                AppPref.setToggle(Settingfragment.this.context, false);
                Settingfragment.this.notificationHelper.cancelNotification();
            }
        });
        this.relinterval.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                final Dialog dialog = new Dialog(Settingfragment.this.context);
                dialog.setContentView(R.layout.reminderintervaldialog);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
                Button button = (Button) dialog.findViewById(R.id.buttonokinterval);
                Button button2 = (Button) dialog.findViewById(R.id.buttoncancelinterval);
                RadioGroup radioGroup = (RadioGroup) dialog.findViewById(R.id.radioGroupreminder);
                final RadioButton radioButton = (RadioButton) dialog.findViewById(R.id.radthirty);
                final RadioButton radioButton2 = (RadioButton) dialog.findViewById(R.id.radfourty);
                final RadioButton radioButton3 = (RadioButton) dialog.findViewById(R.id.radsixty);
                final RadioButton radioButton4 = (RadioButton) dialog.findViewById(R.id.radninty);
                switch (AppPref.getReminder(Settingfragment.this.getActivity())) {
                    case 1:
                        radioButton.setChecked(true);
                        break;
                    case 2:
                        radioButton2.setChecked(true);
                        break;
                    case 3:
                        radioButton3.setChecked(true);
                        break;
                    case 4:
                        radioButton4.setChecked(true);
                        break;
                    default:
                        radioButton.setChecked(true);
                        break;
                }
                final Dialog dialog2 = dialog;
                button.setOnClickListener(new OnClickListener() {
                    public void onClick(View view) {
                        if (radioButton.isChecked()) {
                            Settingfragment.this.setalarm(30);
                        } else if (radioButton2.isChecked()) {
                            Settingfragment.this.setalarm(45);
                        } else if (radioButton3.isChecked()) {
                            Settingfragment.this.setalarm(60);
                        } else if (radioButton4.isChecked()) {
                            Settingfragment.this.setalarm(90);
                        }
                        AlarmUtill.alarmList(Settingfragment.this.context, false);
                        Settingfragment.this.txtremindertime.setText(Settingfragment.this.getReminderText());
                        AppPref.setReceive(Settingfragment.this.getActivity(), false);
                        dialog2.dismiss();
                    }
                });
                button2.setOnClickListener(new OnClickListener() {
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
                try {
                    dialog.show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        this.relreminderauto.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                final Dialog dialog = new Dialog(Settingfragment.this.context);
                dialog.setContentView(R.layout.reminderautodialog);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
                Button button = (Button) dialog.findViewById(R.id.buttonokauto);
                Button button2 = (Button) dialog.findViewById(R.id.buttoncancelauto);
                final RadioButton radioButton = (RadioButton) dialog.findViewById(R.id.turnoff);
                final RadioButton radioButton2 = (RadioButton) dialog.findViewById(R.id.turnmute);
                final RadioButton radioButton3 = (RadioButton) dialog.findViewById(R.id.auto);
                ((RadioGroup) dialog.findViewById(R.id.redauto)).setOnCheckedChangeListener(new OnCheckedChangeListener() {
                    public void onCheckedChanged(RadioGroup radioGroup, int i) {
                        if (radioButton3.isChecked()) {
                            radioButton3.setTextColor(Settingfragment.this.getResources().getColor(R.color.blue));
                            radioButton2.setTextColor(Settingfragment.this.getResources().getColor(R.color.noti));
                            radioButton.setTextColor(Settingfragment.this.getResources().getColor(R.color.noti));
                        }
                        if (radioButton2.isChecked()) {
                            radioButton3.setTextColor(Settingfragment.this.getResources().getColor(R.color.noti));
                            radioButton2.setTextColor(Settingfragment.this.getResources().getColor(R.color.blue));
                            radioButton.setTextColor(Settingfragment.this.getResources().getColor(R.color.noti));
                        }
                        if (radioButton.isChecked()) {
                            radioButton3.setTextColor(Settingfragment.this.getResources().getColor(R.color.noti));
                            radioButton2.setTextColor(Settingfragment.this.getResources().getColor(R.color.noti));
                            radioButton.setTextColor(Settingfragment.this.getResources().getColor(R.color.blue));
                        }
                    }
                });
                switch (AppPref.getAuto(Settingfragment.this.getActivity())) {
                    case 0:
                        radioButton3.setChecked(true);
                        break;
                    case 1:
                        radioButton2.setChecked(true);
                        break;
                    case 2:
                        radioButton.setChecked(true);
                        break;
                    default:
                        radioButton3.setChecked(true);
                        break;
                }
                final Dialog dialog2 = dialog;
                button.setOnClickListener(new OnClickListener() {
                    public void onClick(View view) {
                        if (radioButton3.isChecked()) {
                            AppPref.setAuto(Settingfragment.this.getActivity(), 0);
                            Settingfragment.this.getRemindersound();
                        } else if (radioButton2.isChecked()) {
                            AppPref.setAuto(Settingfragment.this.getActivity(), 1);
                            Settingfragment.this.getRemindersound();
                        } else if (radioButton.isChecked()) {
                            AppPref.setAuto(Settingfragment.this.getActivity(), 2);
                            Settingfragment.this.getRemindersound();
                        }
                        Settingfragment.textreminderauto.setText(Settingfragment.this.getRemindersound());
                        dialog2.dismiss();
                    }
                });
                button2.setOnClickListener(new OnClickListener() {
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
                try {
                    dialog.show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        return inflate;
    }


    public void setalarm(int i) {
        if (i == 30) {
            AppPref.setReminder(getActivity(), 1);
        } else if (i == 45) {
            AppPref.setReminder(getActivity(), 2);
        } else if (i == 60) {
            AppPref.setReminder(getActivity(), 3);
        } else if (i == 90) {
            AppPref.setReminder(getActivity(), 4);
        }
    }

    public void onStart() {
        super.onStart();
    }

    public void onStop() {
        super.onStop();
    }

    public void onDestroy() {
        super.onDestroy();
    }

    /* access modifiers changed from: private */
    public String getReminderText() {
        switch (AppPref.getReminder(getActivity())) {
            case 1:
                return "30 minutes";
            case 2:
                return "45 minutes";
            case 3:
                return "60 minutes";
            case 4:
                return "90 minutes";
            default:
                return "30 minutes";
        }
    }

    /* access modifiers changed from: private */
    public String getRemindersound() {
        switch (AppPref.getAuto(getActivity())) {
            case 0:
                return "Auto";
            case 1:
                return "Mute";
            case 2:
                return "Turn Off";
            default:
                return "Auto";
        }
    }
}
