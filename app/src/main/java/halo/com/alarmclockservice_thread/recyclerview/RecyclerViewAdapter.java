package halo.com.alarmclockservice_thread.recyclerview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import halo.com.alarmclockservice_thread.R;
import halo.com.alarmclockservice_thread.bean.Alarm;


/**
 * Created by HoVanLy on 6/13/2016.
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {
    private List<Alarm> alarmList;
    private Context context;

    public RecyclerViewAdapter(List<Alarm> list, Context context) {
        this.alarmList = list;
        this.context = context;
    }

    // This method create row view when scrolling, return MyViewHolder.
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context)
                .inflate(R.layout.item_alarm_recyclerview, parent, false);
        return new MyViewHolder(itemView);
    }

    // set data for row view
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.tvTitle.setText(alarmList.get(position).getTitle());
        holder.tvTime.setText(alarmList.get(position).getTime());
        holder.tvListDay.setText(getStringListDay(alarmList.get(position).getListDay()));

    }

    @Override
    public int getItemCount() {
        if (alarmList != null) return alarmList.size();
        else return 0;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvTitle;
        public TextView tvTime;
        public TextView tvListDay;

        public MyViewHolder(View itemView) {
            super(itemView);
            tvTitle = (TextView) itemView.findViewById(R.id.tvTitle);
            tvTime = (TextView) itemView.findViewById(R.id.tvTime);
            tvListDay = (TextView) itemView.findViewById(R.id.tvListDay);
        }
    }


    public List<Alarm> getAlarmList() {
        return alarmList;
    }

    public void setAlarmList(List<Alarm> alarmList) {
        this.alarmList = alarmList;
    }

    public String getStringListDay(String temp) {
        if (!"".equals(temp)) {
            String stringList = "";
            String[] list = temp.split(";");
            for (int i = 0; i < list.length; i++) {
                stringList = stringList + list[i] + " ";
            }
            stringList = "Days: " + stringList;
            return stringList;
        }
        return "";
    }
}
