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

/* You can find help at the address below :
 * http://hmkcode.com/android-custom-listview-items-row/
 */

import android.content.Context;
import android.util.Log;

import org.jsoup.select.Elements;

public class Tournament {
    private Context context;
    private String place;
    private String detail;
    private Elements days;
    private Elements months;
    private String link;
    private int nbday;
    private String surface;


    public Tournament() {
        this.context = null;
        this.place = null;
        this.detail = null;
        this.days = null;
        this.months = null;
        this.link = null;
        this.surface = null;
    }


    /* Parametered constructor : allow to pass all the informations necessary that we show in the row of the ListView
     */
    public Tournament(Context context, String place, String detail, Elements days, Elements months, String link, int nbday, String surface) {
        this.context = context;
        this.place = place;
        this.detail = detail;
        this.days = days;
        this.months = months;
        this.link = link;
        this.nbday = nbday;
        this.surface = surface;
    }


    /* Getters
     */
    public String getDay(int index) {
        return this.days.get(index).text();
    }

    public String getMonth(int index) {
        return this.months.get(index).text();
    }

    public String getPlace() {
        return this.place;
    }

    public String getDetail() {
        return this.detail;
    }

    public String getLink() {
        return this.link;
    }

    public int getNbDay() {
        return this.nbday;
    }

    public boolean isFull() {
        Log.i("FULL", getPlace() + String.valueOf(surface.contains("complet")));
        return surface.contains("complet");
    }

    public String getSurfaceAndCarac() {
        String tmp = "";

        /* Type of surface
         */
        if (surface.contains("sand")) {
            tmp += this.context.getResources().getString(R.string.beach);
        }
        else if (surface.contains("indoor")) {
            tmp += this.context.getResources().getString(R.string.indoor);
        }
        else if (surface.contains("grass")) {
            tmp += this.context.getResources().getString(R.string.grass);
        }

        /* Nocturnal?
         */
        if (surface.contains("nuit")) {
            tmp += " - " + this.context.getResources().getString(R.string.night);
        }

        /* New tournament?
         */
        if (surface.contains("new")) {
            tmp += " - " + this.context.getResources().getString(R.string.news);
        }

        /* Is there still place?
         */
        if (surface.contains("complet")) {
            tmp += " - " + this.context.getResources().getString(R.string.full);
        }

        /* Return the temporary string
         */
        return tmp;
    }
}
