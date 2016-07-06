package halo.com.alarmclockservice_thread.recyclerview;

import android.view.View;

/**
 * Created by HoVanLy on 6/14/2016.
 */
 public interface IClickItem {
    void onClick(View view, int position);
    void onLongClick(View view, int position);
}
