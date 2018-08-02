package br.edu.infnet.persistencesample;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

public class Question implements Serializable {

    private String text;
    private Date moment;

    public Question(String text) {
        this.text = text;
        this.moment = Calendar.getInstance().getTime();
    }

    public Question(String text, Date moment) {
        this.text = text;
        this.moment = moment;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getMoment() {
        return moment;
    }

    public void setMoment(Date moment) {
        this.moment = moment;
    }

    @Override
    public String toString() {
        return text + "\n" + moment + "\n";
    }
}
