package com.hbuyse.accrodestournois;

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
    private String surface;


    public Tournament() {
        super();
        this.place = null;
        this.detail = null;
        this.days = null;
        this.months = null;
        this.link = null;
        this.surface = null;
    }


    /* Parametered constructor : allow to pass all the informations necessary that we show in the row of the ListView
     */
    public Tournament(String place, String detail, Elements days, Elements months, String link, int nbday, String surface) {
        super();
        this.place = place;
        this.detail = detail;
        this.days = days;
        this.months = months;
        this.link = link;
        this.nbday = nbday;
        this.surface = surface;
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

    public int getType() {
        if (surface.equals("sand")) {
            return 1;
        }
        else if (surface.equals("sand-new")) {
            return 2;
        }
        else if (surface.equals("sand-full")) {
            return 3;
        }
        else if (surface.equals("grass")) {
            return 4;
        }
        else if (surface.equals("grass-new")) {
            return 5;
        }
        else if (surface.equals("grass-full")) {
            return 6;
        }
        else if (surface.equals("indoor")) {
            return 7;
        }
        else if (surface.equals("indoor-new")) {
            return 8;
        }
        else if (surface.equals("indoor-full")) {
            return 9;
        }
        else {
            return 0;
        }
    }
}
