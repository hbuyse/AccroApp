/*
AccroDesTournois : list all the volleyball tournaments of the french
website Accro Des Tournois (http://www.accro-des-tournois.com)

Copyright (C) 2014  Henri Buyse

AccroDesTournois is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

AccroDesTournois is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with AccroDesTournois.  If not, see <http://www.gnu.org/licenses/>.
*/

package fr.hbuyse.accrodestournois;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class TournamentActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /* Get the message from the intent
         */
        Intent intent = getIntent();
        String place_tournament = intent.getExtras().getString("place_tournament");
        setTitle(place_tournament);


        setContentView(R.layout.activity_tournament);


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
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        /* Handle action bar item clicks here. The action bar will
         * automatically handle clicks on the Home/Up button, so long
         * as you specify a parent activity in AndroidManifest.xml.
         */
        switch (item.getItemId()) {
            case R.id.action_settings:
                Toast.makeText(this, "To implement", Toast.LENGTH_SHORT);
                return true;

            case R.id.refresh:
                recreate();
                return true;

            case R.id.contactMenu:
                Intent i = new Intent(this, Contact.class);
                startActivity(i);
                return true;

            default:
                return super.onOptionsItemSelected(item);

        }
    }
}
