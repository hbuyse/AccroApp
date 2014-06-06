package com.flambaz.accrodestournois;

/*
 * Tu pourras trouver de l'aide à cette adresse :
 * http://hmkcode.com/android-custom-listview-items-row/
 */

public class Tournoi {
	private String lieu;
    private String details;
    private String jour;
    private String mois;
    
    public Tournoi() {
		// TODO Auto-generated constructor stub
    	super();
        this.lieu = null;
        this.details = null;
        this.jour = null;
        this.mois = null;
	}
    
    
    public Tournoi(String lieu, String details, String jour, String mois) {
        super();
        this.lieu = lieu;
        this.details = details;
        this.jour = jour;
        this.mois = mois;
    }
    // getters and setters...
    
    public String getJour() {
    	return this.jour;
    }
    
    public String getMois() {
    	return this.mois;
    }
    
    public String getLieu() {
    	return this.lieu;
    }
    
    
    public String getDetails() {
    	return this.details;
    }
}
