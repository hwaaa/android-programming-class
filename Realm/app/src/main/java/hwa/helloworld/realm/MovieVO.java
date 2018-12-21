package hwa.helloworld.realm;

import io.realm.RealmObject;

/**
 * Created by student on 2018-12-21.
 */

public class MovieVO extends RealmObject {
    private int number;
    private String title;

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
