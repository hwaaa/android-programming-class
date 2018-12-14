package hwa.helloworld.datetimepicker_movie;

import java.util.GregorianCalendar;

/**
 * Created by student on 2018-12-14.
 */

public class BookData {
    GregorianCalendar movie_start_time;
    String moive_title;
    double running_time;
    int all_seats;
    int booked_seats;

    public BookData(GregorianCalendar movie_start_time, String moive_title, double running_time, int all_seats, int booked_seats) {
        this.movie_start_time = movie_start_time;
        this.moive_title = moive_title;
        this.running_time = running_time;
        this.all_seats = all_seats;
        this.booked_seats = booked_seats;
    }

    public GregorianCalendar getMovie_start_time() {
        return movie_start_time;
    }

    public void setMovie_start_time(GregorianCalendar movie_start_time) {
        this.movie_start_time = movie_start_time;
    }

    public String getMoive_title() {
        return moive_title;
    }

    public void setMoive_title(String moive_title) {
        this.moive_title = moive_title;
    }

    public double getRunning_time() {
        return running_time;
    }

    public void setRunning_time(int running_time) {
        this.running_time = running_time;
    }

    public int getAll_seats() {
        return all_seats;
    }

    public void setAll_seats(int all_seats) {
        this.all_seats = all_seats;
    }

    public int getBooked_seats() {
        return booked_seats;
    }

    public void setBooked_seats(int booked_seats) {
        this.booked_seats = booked_seats;
    }
}
