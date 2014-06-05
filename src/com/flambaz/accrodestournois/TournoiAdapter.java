package com.flambaz.accrodestournois;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class TournoiAdapter extends ArrayAdapter<Tournoi> {
	 
        private final Context context;
        private final ArrayList<Tournoi> tournoiArrayList;
	 
	        public TournoiAdapter(Context context, ArrayList<Tournoi> tournoiArrayList) {
	 
	            super(context, R.layout.adapter, tournoiArrayList);
	 
	            this.context = context;
	            this.tournoiArrayList = tournoiArrayList;
	        }
	 
	        @Override
	        public View getView(int position, View convertView, ViewGroup parent) {
	 
	            // 1. Create inflater 
	            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	 
	            // 2. Get rowView from inflater
	            View rowView = inflater.inflate(R.layout.adapter, parent, false);
	 
	            // 3. Get the two text view from the rowView
	            TextView lieuView    = (TextView) rowView.findViewById(R.id.firstLine);
	            TextView detailsView = (TextView) rowView.findViewById(R.id.secondLine);
	            TextView dateView    = (TextView) rowView.findViewById(R.id.date);
	            TextView moisView    = (TextView) rowView.findViewById(R.id.mois);
	 
	            // 4. Set the text for textView 
	            lieuView.setText(tournoiArrayList.get(position).getLieu());
	            detailsView.setText(tournoiArrayList.get(position).getDetails());
	            dateView.setText(tournoiArrayList.get(position).getJour());
	            moisView.setText(tournoiArrayList.get(position).getMois());
	            
	            // 5. return rowView
	            return rowView;
	        }
}
