package drinkwater.health.waterdrinkalarm.watertracker.drinkwaterreminder.Activity;

import android.annotation.SuppressLint;
import android.content.Context;
import androidx.recyclerview.widget.RecyclerView.Adapter;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;

import drinkwater.health.waterdrinkalarm.watertracker.drinkwaterreminder.views.OnItemClickListener;
import drinkwater.health.waterdrinkalarm.watertracker.drinkwaterreminder.R;

public class MyAdapter extends Adapter<MyAdapter.MyHolder> {
    static int selectedPosition = -1;
    Context c;
    ArrayList<cupmaster> cupmasters;
    /* access modifiers changed from: private */
    public OnItemClickListener onItemClickListener;

    public class MyHolder extends ViewHolder {
        ImageView imageView;
        TextView nameTxt;
        FrameLayout rdtml;

        public MyHolder(View view) {
            super(view);
            this.imageView = (ImageView) view.findViewById(R.id.imagecu1);
            this.nameTxt = (TextView) view.findViewById(R.id.nameTxt);
            this.rdtml = (FrameLayout) view.findViewById(R.id.simpleRadioButton);
            view.setOnClickListener(new OnClickListener() {
                @SuppressLint({"NewApi"})
                public void onClick(View view) {
                    MyAdapter.selectedPosition = ((cupmaster) MyAdapter.this.cupmasters.get(MyHolder.this.getAdapterPosition())).getCup_id();
                    MyAdapter.this.notifyDataSetChanged();
                    MyAdapter.this.onItemClickListener.onItemClick(Integer.valueOf(MyHolder.this.getAdapterPosition()), MyAdapter.this.cupmasters.size());
                }
            });
        }
    }

    public MyAdapter(Context context, ArrayList<cupmaster> arrayList) {
        this.c = context;
        this.cupmasters = arrayList;
    }

    public MyHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new MyHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.model, viewGroup, false));
    }

    public void onBindViewHolder(MyHolder myHolder, int i1) {
        myHolder.imageView.setImageResource(((cupmaster) this.cupmasters.get(i1)).getImage_id());
        TextView textView;
        StringBuilder stringBuilder;
        if (Homefragment.getkgstaus()) {
            textView = myHolder.nameTxt;
            stringBuilder = new StringBuilder();
            stringBuilder.append(((cupmaster) this.cupmasters.get(i1)).getCup_size());
            stringBuilder.append(" ml");
            textView.setText(stringBuilder.toString());
        } else {
            textView = myHolder.nameTxt;
            stringBuilder = new StringBuilder();
            stringBuilder.append(Homefragment.returnfloz(((cupmaster) this.cupmasters.get(i1)).getCup_size()));
            stringBuilder.append(" fl oz");
            textView.setText(stringBuilder.toString());
        }
        if (((cupmaster) this.cupmasters.get(i1)).getCup_id() == selectedPosition) {
            myHolder.rdtml.setBackgroundResource((R.drawable.btn_bgsel));
        } else {
            myHolder.rdtml.setBackgroundResource((R.drawable.backgotit));
        }
    }

    public int getItemCount() {
        return this.cupmasters.size();
    }

    public OnItemClickListener getOnItemClickListener() {
        return this.onItemClickListener;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}
