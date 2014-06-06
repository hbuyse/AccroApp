package com.flambaz.accrodestournois;

/*
 * Tu pourras trouver de l'aide à cette adresse :
 * http://hmkcode.com/android-custom-listview-items-row/
 */

public class Tournoi {
	private String lieu;
    private String detail;
    private String jour;
    private String mois;
    private String lien;
    
    public Tournoi() {
		// TODO Auto-generated constructor stub
    	super();
        this.lieu = null;
        this.detail = null;
        this.jour = null;
        this.mois = null;
        this.lien = null;
	}
    
    
    public Tournoi(String lieu, String detail, String jour, String mois, String lien) {
        super();
        this.lieu = lieu;
        this.detail = detail;
        this.jour = jour;
        this.mois = mois;
        this.lien = lien;
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
    
    public String getDetail() {
    	return this.detail;
    }
    
    public String getLien() {
    	return this.lien;
    }
}
