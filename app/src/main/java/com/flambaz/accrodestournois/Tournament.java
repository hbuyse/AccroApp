package com.flambaz.accrodestournois;

/* You can find help at the address below :
 * http://hmkcode.com/android-custom-listview-items-row/
 */

import org.jsoup.select.Elements;

public class Tournament {
    private String place;
    private String detail;
    private Elements days;
    private Elements months;
    private String link;
    private int nbday;


    public Tournament() {
        super();
        this.place = null;
        this.detail = null;
        this.days = null;
        this.months = null;
        this.link = null;
    }


    /* Parametered constructor : allow to pass all the informations necessary that we show in the row of the ListView
     */
    public Tournament(String place, String detail, Elements days, Elements months, String link, int nbday) {
        super();
        this.place = place;
        this.detail = detail;
        this.days = days;
        this.months = months;
        this.link = link;
        this.nbday = nbday;
    }


    /* Getters
     */
    public String getDay(int index) {
        return this.days.get(index).text();
    }

    public String getMonth(int index) {
        return this.months.get(index).text();
    }

    public String getPlace() {
        return this.place;
    }

    public String getDetail() {
        return this.detail;
    }

    public String getLink() {
        return this.link;
    }

    public int getNbDay() {
        return this.nbday;
    }
}
