package drinkwater.health.waterdrinkalarm.watertracker.drinkwaterreminder.Activity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;

import drinkwater.health.waterdrinkalarm.watertracker.drinkwaterreminder.Helper.Constants;
import drinkwater.health.waterdrinkalarm.watertracker.drinkwaterreminder.views.OnItemClickListener;
import drinkwater.health.waterdrinkalarm.watertracker.drinkwaterreminder.R;
import drinkwater.health.waterdrinkalarm.watertracker.drinkwaterreminder.views.onDialogClick;

public class TVShowFragment extends DialogFragment {
    MyAdapter adapter;
    Context context;
    int cupSize = 100;
    ArrayList<cupmaster> cup_size;
    DatabaseHelper db;
    LayoutInflater inflater;
    /* access modifiers changed from: private */
    public drinkwater.health.waterdrinkalarm.watertracker.drinkwaterreminder.views.onDialogClick onDialogClick;
    RecyclerView rv;
    TVShowFragment tv;

    @SuppressLint({"ValidFragment"})
    public TVShowFragment(onDialogClick ondialogclick) {
        this.onDialogClick = ondialogclick;
    }

    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Nullable
    public View onCreateView(final LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(0));
        View inflate = layoutInflater.inflate(R.layout.fraglayout, viewGroup);
        Button button = (Button) inflate.findViewById(R.id.btnok);
        Button button2 = (Button) inflate.findViewById(R.id.btncancel);
        ImageView button3 = (ImageView) inflate.findViewById(R.id.btnadd);
        this.db = new DatabaseHelper(this.context);
        this.cup_size = new ArrayList();
        this.cup_size.addAll(this.db.getData());
        MyAdapter.selectedPosition = this.db.getcurrentcupid();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("");
        stringBuilder.append(MyAdapter.selectedPosition);
        Log.d("SelectPosition", stringBuilder.toString());
        this.rv = (RecyclerView) inflate.findViewById(R.id.mRecyerID);
        this.rv.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        this.adapter = new MyAdapter(getActivity(), this.cup_size);
        this.rv.setAdapter(this.adapter);
        getDialog().setTitle("Switch cup");
        button.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.putExtra("listdata", "list");
                TVShowFragment.this.db.updateIsCurrent();
                TVShowFragment.this.db.setKEY_iscurrent(MyAdapter.selectedPosition);
                TVShowFragment.this.getTargetFragment().onActivityResult(TVShowFragment.this.getTargetRequestCode(), -1, intent);
                TVShowFragment.this.getDialog().dismiss();
            }
        });
        button3.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                TVShowFragment.this.getDialog().dismiss();

                final Dialog dialog = new Dialog(TVShowFragment.this.context);
                dialog.setContentView(R.layout.popup);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
                final EditText editText = (EditText) dialog.findViewById(R.id.edit1);
                TextView textView = (TextView) dialog.findViewById(R.id.txtcustomeml);
                if (Homefragment.getkgstaus()) {
                    textView.setText(Constants.ML);
                } else {
                    textView.setText(Constants.FL);
                }

                Button button = (Button) dialog.findViewById(R.id.buttoncancelcus);
                Button button2 = (Button) dialog.findViewById(R.id.buttonokcus);
                button2.setOnClickListener(new OnClickListener() {
                    public void onClick(View view) {
                        try {
                            if (!(editText.getText().toString().isEmpty() || editText == null)) {
                                int i = Integer.parseInt(editText.getText().toString());
                                if (i < 0) {
                                    Toast.makeText(TVShowFragment.this.context, "Please enter a number greater than 0", 1).show();
                                    return;
                                }
                                if (!Homefragment.getkgstaus()) {
                                    i = (int) Math.round(((double) i) / Homefragment.floz);
                                }
                                if (TVShowFragment.this.db.insertData(i, R.drawable.ic_custom, 0)) {
                                    TVShowFragment.this.onDialogClick.onDisimiss();
                                   dialog.dismiss();
                                    return;
                                }
                                return;
                            }
                        } catch (NumberFormatException e) {
                            e.printStackTrace();
                        }
                        if (0 > 0) {
                        }
                    }
                });
                button.setOnClickListener(new OnClickListener() {
                    public void onClick(View view) {
                        TVShowFragment.this.onDialogClick.onDisimiss();
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
        button2.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                TVShowFragment.this.dismiss();
            }
        });
        this.adapter.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(Integer num, int i) {
            }
        });
        return inflate;
    }

    public int getValue() {
        return this.cupSize;
    }
}
