package halo.com.alarmclockservice_thread.bean;

/**
 * Created by HoVanLy on 6/30/2016.
 */
public class Alarm {
    private int id;
    private String time;
    private String title;
    private String listDay;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getListDay() {
        return listDay;
    }

    public void setListDay(String listDay) {
        this.listDay = listDay;
    }
}
