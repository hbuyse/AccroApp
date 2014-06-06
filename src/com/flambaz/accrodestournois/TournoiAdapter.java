package com.flambaz.accrodestournois;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class TournoiAdapter extends ArrayAdapter<Tournoi> {
	 
        @SuppressWarnings("unused")
		private final Context context;
        
        // Déclaration de mon ArrayList de Tournoi
        private final ArrayList<Tournoi> tournoiArrayList;
	 
        /* here we must override the constructor for ArrayAdapter
    	 * the only variable we care about now is ArrayList<Item> objects,
    	 * because it is the list of objects we want to display.
    	 */
        public TournoiAdapter(Context context, int textViewResourceId, ArrayList<Tournoi> tournoiArrayList) {
 
            super(context, textViewResourceId, tournoiArrayList);
 
            this.context = context;
            this.tournoiArrayList = tournoiArrayList;
        }
	 
    	/*
    	 * we are overriding the getView method here - this is what defines how each
    	 * list item will look.
    	 */
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
 
        	/* Assign the view we are converting to a local variable
        	 */
    		View rowView = convertView;

    		/* First, check to see if the view is null. 
    		 * If so, we have to inflate it.
    		 * To "inflate it" basically means to render, or show, the view.
    		 */ 
    		if (rowView == null) {
    			LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    			rowView = inflater.inflate(R.layout.adapter, null);
    		}
    		
    		/* Recall that the variable position is sent in as an argument to this method.
    		 * The variable simply refers to the position of the current object in the list.
    		 * (The ArrayAdapter iterates through the list we sent it)
    		 * 
    		 * Therefore, i refers to the current Tournoi object.
    		 */
    		Tournoi i = tournoiArrayList.get(position);
    		
    		
    		if (i != null) {
    			/* This is how you obtain a reference to the TextViews.
    			 * These TextViews are created in the XML files we defined.
    			 */
	            TextView lieuView    = (TextView) rowView.findViewById(R.id.firstLine);
	            TextView detailsView = (TextView) rowView.findViewById(R.id.secondLine);
	            TextView jourView    = (TextView) rowView.findViewById(R.id.date);
	            TextView moisView    = (TextView) rowView.findViewById(R.id.mois);
 
	            /* check to see if each individual textview is null.
				 * if not, assign some text!
				 */
				if (lieuView != null){
					lieuView.setText(i.getLieu());
				}
				if (detailsView != null){
					detailsView.setText(i.getDetail());
				}
				if (detailsView != null){
					moisView.setText(i.getMois());
				}
				if (jourView != null){
					jourView.setText(i.getJour());
				}
    		}

    		// the view must be returned to our activity
            return rowView;
        }
}
