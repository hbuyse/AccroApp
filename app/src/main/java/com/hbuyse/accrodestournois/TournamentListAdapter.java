package com.hbuyse.accrodestournois;

import android.content.Context;
import android.graphics.Typeface;
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
            rowView = inflater.inflate(R.layout.row, parent, false);
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
            TextView surfaceView  = (TextView)     rowView.findViewById(R.id.surface);
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

            if (surfaceView != null) {
                switch (i.getType()) {
                    case 1:
                        surfaceView.setText(getContext().getString(R.string.beach));
                        break;
                    case 2:
                        surfaceView.setText(getContext().getString(R.string.beach) + " - " + getContext().getString(R.string.news));
                        break;
                    case 3:
                        surfaceView.setText(getContext().getString(R.string.beach) + " - " + getContext().getString(R.string.full));
                        break;
                    case 4:
                        surfaceView.setText(getContext().getString(R.string.grass));
                        break;
                    case 5:
                        surfaceView.setText(getContext().getString(R.string.grass) + " - " + getContext().getString(R.string.news));
                        break;
                    case 6:
                        surfaceView.setText(getContext().getString(R.string.grass) + " - " + getContext().getString(R.string.full));
                        break;
                    case 7:
                        surfaceView.setText(getContext().getString(R.string.indoor));
                        break;
                    case 8:
                        surfaceView.setText(getContext().getString(R.string.indoor) + " - " + getContext().getString(R.string.news));
                        break;
                    case 9:
                        surfaceView.setText(getContext().getString(R.string.indoor) + " - " + getContext().getString(R.string.full));
                        break;
                    default:
                        surfaceView.setVisibility(View.GONE);
                        break;
                }
            }

            /* Adding the different day and month
             */
            for (int j = 0; j < i.getNbDay(); j++){
                /* Creation of a LinearLayout that will stock the day and the month
                 */
                LinearLayout ll = new LinearLayout(this.context);
                ll.setOrientation(LinearLayout.VERTICAL);

                int width_ll = (int) (getContext().getResources().getDisplayMetrics().density * 40f + 0.5f);
                LinearLayout.LayoutParams ll_params = new LinearLayout.LayoutParams(
                        width_ll, LinearLayout.LayoutParams.MATCH_PARENT);

                int margin_right = (int) (this.context.getResources().getDisplayMetrics().density * 4f + 0.5f);
                ll_params.setMargins(0, 0, margin_right, 0);


                /* Creation of a day and implementation
                 */
                TextView day = new TextView(this.context);
                LinearLayout.LayoutParams day_params = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1f);
                day.setText(i.getDay(j));
                day.setTextColor(rowView.getResources().getColor(R.color.font_day));
                day.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 16);
                day.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
                day.setBackgroundResource(R.drawable.day_shape);
                day.setGravity(Gravity.CENTER);


                /* Creation of a month and implementation
                 */
                TextView month = new TextView(this.context);
                LinearLayout.LayoutParams month_params = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1f);
                month.setText(i.getMonth(j));
                month.setTextColor(rowView.getResources().getColor(R.color.font_month));
                month.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 16);
                month.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
                month.setBackgroundResource(R.drawable.month_shape);
                month.setGravity(Gravity.CENTER);


                /* Adding the two views in the linearlayout vertical that create the day-month cell
                 */
                ll.addView(month, month_params);
                ll.addView(day  , day_params);

                /* Adding the linearlayout vertical to the linearlayout horizontal
                 */
                dayMonth.addView(ll, ll_params);
            }
        }

        /* The view must be returned to our activity
         */
        return rowView;
    }
}
