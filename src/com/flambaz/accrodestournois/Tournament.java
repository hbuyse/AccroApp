package com.flambaz.accrodestournois;

/*
 * Tu pourras trouver de l'aide à cette adresse :
 * http://hmkcode.com/android-custom-listview-items-row/
 */

public class Tournament {
    private String place;
    private String detail;
    private String day;
    private String month;
    private String link;
    
    public Tournament() {
        // TODO Auto-generated constructor stub
        super();
        this.place = null;
        this.detail = null;
        this.day = null;
        this.month = null;
        this.link = null;
    }
    
    
    public Tournament(String place, String detail, String day, String month, String link, int nbday) {
        super();
        this.place = place;
        this.detail = detail + " / " + nbday + " ";
        if (nbday == 1) {
            this.detail += "day";
        }
        else {
            this.detail += "days";
        }
        this.day = day;
        this.month = month;
        this.link = link;
    }
    
    /* Getters
     */
    public String getDay() {
        return this.day;
    }
    
    public String getMonth() {
        return this.month;
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
}
