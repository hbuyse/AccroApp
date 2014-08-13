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

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import fr.hbuyse.accrodestournois.R;

public class SplashScreen extends Activity {

    /* Splash screen timer
     */
    private static int SPLASH_TIME_OUT = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);

        /* Remove the action bar from the activity
         */
        ActionBar actionBar = getActionBar();
        actionBar.hide();

        new Handler().postDelayed(new Runnable() {

            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */

            @Override
            public void run() {
                    /* This method will be executed once the timer is over
                     * Start your app main activity
                     */
                Intent i = new Intent(SplashScreen.this, MainActivity.class);
                startActivity(i);

                    /* Close this activity
                     */
                finish();
            }
        }, SPLASH_TIME_OUT);
    }

}