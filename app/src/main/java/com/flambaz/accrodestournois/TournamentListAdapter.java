package com.flambaz.accrodestournois;

import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class TournamentListAdapter extends ArrayAdapter<Tournament> {

    private final Context context;

    /* Declaration of my ArrayList full of Tournaments
     */
    private final ArrayList<Tournament> tournamentArrayList;


    /* Here we must override the constructor for ArrayAdapter.
     * The only variable we care about now is ArrayList<Item> objects,
     * because it is the list of objects we want to display.
     */
    public TournamentListAdapter(Context context, int textViewResourceId, ArrayList<Tournament> tournamentArrayList) {

        super(context, textViewResourceId, tournamentArrayList);

        this.context = context;
        this.tournamentArrayList = tournamentArrayList;
    }


    /* We are overriding the getView method here - this is what defines how each
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
            rowView = inflater.inflate(R.layout.row, null);
        }

        /* Recall that the variable position is sent in as an argument to this method.
         * The variable simply refers to the position of the current object in the list.
         * (The ArrayAdapter iterates through the list we sent it)
         *
         * Therefore, i refers to the current Tournament object.
         */
        Tournament i = tournamentArrayList.get(position);


        if (i != null) {
            /* This is how you obtain a reference to the TextViews.
             * These TextViews are created in the XML files we defined.
             *
             * We delete all the views that are stored in hte dayMonth Linearlayout
             */
            TextView placeView    = (TextView)     rowView.findViewById(R.id.place);
            TextView detailsView  = (TextView)     rowView.findViewById(R.id.details);
            LinearLayout dayMonth = (LinearLayout) rowView.findViewById(R.id.day_month);
            dayMonth.removeAllViews();

            /* Check to see if each individual textview is null.
             * If not, assign some text!
             */
            if (placeView != null) {
                placeView.setText(i.getPlace());
            }

            if (detailsView != null) {
                detailsView.setText(i.getDetail());
            }

            /* Adding the different day and month
             */
            for (int j = 0; j < i.getNbDay(); j++){
                /* Creation of a LinearLayout that will stock the day and the month
                 */
                LinearLayout ll = new LinearLayout(this.context);
                ll.setOrientation(LinearLayout.VERTICAL);

                int width = (int) (getContext().getResources().getDisplayMetrics().density * 50f + 0.5f);
                Log.i("Width", " " + width);
                LinearLayout.LayoutParams ll_params = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT);

                int margin_right = (int) (this.context.getResources().getDisplayMetrics().density * 5f + 0.5f);
                Log.i("Margin Right", " " + margin_right);
                ll_params.setMargins(0, 0, margin_right, 0);


                /* Creation of a day and implementation
                 */
                TextView day = new TextView(this.context);
                ViewGroup.LayoutParams day_params = new ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                day.setText(i.getDay(j));
                day.setTextColor(rowView.getResources().getColor(R.color.font_day));
                day.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 16);
                day.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
                day.setBackgroundResource(R.drawable.day_shape);
                day.setGravity(Gravity.CENTER);


                /* Creation of a month and implementation
                 */
                TextView month = new TextView(this.context);
                int height = (int) (getContext().getResources().getDisplayMetrics().density * 20f + 0.5f);
                ViewGroup.LayoutParams month_params = new ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                month.setText(i.getMonth(j));
                month.setTextColor(rowView.getResources().getColor(R.color.font_month));
                month.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 16);
                month.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
                month.setBackgroundResource(R.drawable.month_shape);
                month.setGravity(Gravity.CENTER);

                /* Adding the two views in the linearlayout vertical that create the day-month cell
                 */
                ll.addView(month, 0, month_params);
                ll.addView(day,   1, day_params);

                /* Adding the linearlayout vertical to the linearlayout horizontal
                 */
                dayMonth.addView(ll);
            }
        }

        /* The view must be returned to our activity
         */
        return rowView;
    }
}
