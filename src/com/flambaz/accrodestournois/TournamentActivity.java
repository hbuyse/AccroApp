package com.flambaz.accrodestournois;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;

public class TournamentActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /* Get the message from the intent
         */
        Intent intent = getIntent();
        String place_tournament = intent.getExtras().getString("place_tournament");
        setTitle(place_tournament);


        setContentView(R.layout.activity_tournoi);


        /* Create the list fragment and add it as our sole content.
         */
        if (getFragmentManager().findFragmentById(R.id.containerTournament) == null) {
            TournamentFragment tournoi = new TournamentFragment();
            getFragmentManager().beginTransaction().add(R.id.containerTournament, tournoi).commit();
        }
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        /* Inflate the menu; this adds items to the action bar if it is present.
         */
        getMenuInflater().inflate(R.menu.tournoi, menu);
        return true;
    }




    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        /* Handle action bar item clicks here. The action bar will
         * automatically handle clicks on the Home/Up button, so long
         * as you specify a parent activity in AndroidManifest.xml.
         */
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
