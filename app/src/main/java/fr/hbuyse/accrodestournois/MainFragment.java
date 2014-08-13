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

import android.app.ListFragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

import fr.hbuyse.accrodestournois.R;

public class MainFragment extends ListFragment {
    /* URL Address
     */
    static String url = "http://www.accro-des-tournois.com";
    static ProgressDialog mProgressDialog;


    /* Declare class variables
     */
    private ArrayList<Tournament> tournamentArrayList = new ArrayList<Tournament>();


    public MainFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /* Execute Title AsyncTask
         * Create some objects
         * Here is where you could also request data from a server
         * And then create objects from that data.
         */
        if(isOnline()) {
            new ParseWebSite().execute();
        }
        else {
            Toast.makeText(getActivity(),
                    getActivity().getResources().getString(R.string.no_connection),
                    Toast.LENGTH_LONG).show();
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        return rootView;
    }


    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        /* When we click on a Tournament, we pass through the Intent the
         * URL of the Tournament and the place
         */
        Intent intent = new Intent(getActivity(), TournamentActivity.class);
        intent.putExtra("url_tournament", tournamentArrayList.get(position).getLink());
        intent.putExtra("place_tournament", tournamentArrayList.get(position).getPlace());
        startActivity(intent);
    }


    public boolean isOnline() {
        ConnectivityManager cm;
        cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        }
        return false;
    }

    /* Asynchronous task allowing to parse the website accro-des-tournois
     */
    private class ParseWebSite extends AsyncTask<Void, Void, Void> {
        private Elements tournaments;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressDialog = new ProgressDialog(getActivity());
            mProgressDialog.setTitle(R.string.mainProgress);
            mProgressDialog.setMessage(getString(R.string.loading));
            mProgressDialog.setIndeterminate(false);
            mProgressDialog.show();
        }


        @Override
        protected Void doInBackground(Void... params) {
            try {
                /* Connection to the website defined bu 'url'
                 */
                Document webPage = Jsoup.connect(url).timeout(10*1000).get();

                /* Recovering the datas from the HTML
                 */
                tournaments = webPage.select("li[class=elementtournoi]");
                for ( Element i : tournaments ) {
                    Element place = i.select("div[class=annucontent] h3").first();
                    Element detail = i.select("div[class=annucontent] div").first();
                    Elements days = i.select("div[class=calendrierjour]");
                    Elements months = i.select("div[class=calendriermois]");
                    String link = i.select("a").first().attr("href");
                    String surface = i.select("a").first().attr("class");

                    int nbJour = i.select("div[class=calendrierjour]").size();

                    tournamentArrayList.add(new Tournament(getActivity(), place.text().toUpperCase(),
                            detail.text(), days, months, link, nbJour, surface));
                }
            }
            catch (java.net.SocketTimeoutException e){
                e.printStackTrace();

                /* Shutting down the progress dialog
                 */
                if (mProgressDialog.isShowing()) {
                    mProgressDialog.dismiss();
                }
            }
            catch (java.net.SocketException e) {
                e.printStackTrace();

                /* Shutting down the progress dialog
                 */
                if (mProgressDialog.isShowing()) {
                    mProgressDialog.dismiss();
                }
            }
            catch (IOException e) {
                e.printStackTrace();
            }



            return null;
        }


        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);

            /* Shutting down the progress dialog
             */
            if (mProgressDialog.isShowing()) {
                mProgressDialog.dismiss();
            }
            else {
                Toast.makeText(getActivity(),
                        R.string.internet_pb,
                        Toast.LENGTH_LONG).show();
            }


            /* Instantiate our ItemAdapter class
             *
             * We have to let the setter of the ListAdapter in PostExecute otherwise
             * the fragment may show itself but without the data.
             * It will exhibit itself before the thread ends.
             */
            TournamentListAdapter m_adapter = new TournamentListAdapter(getActivity(), R.layout.row,
                    tournamentArrayList);
            setListAdapter(m_adapter);
        }
    }
}
