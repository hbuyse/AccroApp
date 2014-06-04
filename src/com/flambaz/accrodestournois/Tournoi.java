package com.flambaz.accrodestournois;

/*
 * Tu pourras trouver de l'aide à cette adresse :
 * http://hmkcode.com/android-custom-listview-items-row/
 */

public class Tournoi {
	private String lieu;
    private String details;
    private int    jour;
    private int    mois;
 
    public Tournoi(String lieu, String details, int jour, int mois) {
        super();
        this.lieu = lieu;
        this.details = details;
        this.jour = jour;
        this.mois = mois;
    }
    // getters and setters...
    
    public int getJour() {
    	return this.jour;
    }
    
    public int getMois() {
    	return this.mois;
    }
    
    public String getLieu() {
    	return this.lieu;
    }
    
    
    public String getDetails() {
    	return this.details;
    }
}
