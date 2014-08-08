/*
The MIT License (MIT)

Copyright (c) 2014 Henri Buyse

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
*/

package fr.hbuyse.accrodestournois;

/* You can find help at the address below :
 * http://hmkcode.com/android-custom-listview-items-row/
 */

import android.content.Context;

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
