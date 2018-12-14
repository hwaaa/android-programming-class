package hwa.helloworld.listview_movie.form;

/**
 * Created by student on 2018-12-14.
 */

public class InformationItem {
    String info_title;
    String info_date;
    int info_img_id;

    public InformationItem(String info_title, String info_date, int info_img_id) {
        this.info_title = info_title;
        this.info_date = info_date;
        this.info_img_id = info_img_id;
    }

    public String getInfo_title() {
        return info_title;
    }

    public void setInfo_title(String info_title) {
        this.info_title = info_title;
    }

    public String getInfo_date() {
        return info_date;
    }

    public void setInfo_date(String info_date) {
        this.info_date = info_date;
    }

    public int getInfo_img_id() {
        return info_img_id;
    }

    public void setInfo_img_id(int info_img_id) {
        this.info_img_id = info_img_id;
    }
}
