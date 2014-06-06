package com.flambaz.accrodestournois;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends ListActivity {
    /* URL Address
     */
    String url = "http://www.accro-des-tournois.com";
    ProgressDialog mProgressDialog;
	
	/* declare class variables
	 */
	private ArrayList<Tournoi> tournoiArrayList = new ArrayList<Tournoi>();
	private Runnable viewParts;
	private TournoiAdapter m_adapter;
	
	
	
	/* Called when the activity is first created.
	 */
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_main);

        // instantiate our ItemAdapter class
        m_adapter = new TournoiAdapter(this, R.layout.adapter, tournoiArrayList);
        setListAdapter(m_adapter);

        /* Execute Title AsyncTask
         */
        new ParseWebSite().execute();
        
        
		// create some objects
		// here is where you could also request data from a server
        // and then create objects from that data.
//		tournoiArrayList.add(new Tournoi("MyItemName", "This is item #1", "14", "Sep"));
//		tournoiArrayList.add(new Tournoi("MyItemName #2", "This is item #2", "14", "Sep"));

//		m_adapter = new TournoiAdapter(MainActivity.this, R.layout.adapter, tournoiArrayList);
//
//		// display the list.
//        setListAdapter(m_adapter);
    }
        
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		Tournoi item = (Tournoi) getListAdapter().getItem(position);
		Toast.makeText(this, item.getDetails() + " selected", Toast.LENGTH_LONG).show();
	}
	
	
	// Tache asynchrone permettant de parser le site web accro-des-tournois
    private class ParseWebSite extends AsyncTask<Void, Void, Void> {
    	private Elements    tournois;
        private Elements    liens;
        private Elements    lieux;
        private Elements    details;
        private Elements    jours;
        private Elements    mois;
        
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressDialog = new ProgressDialog(MainActivity.this);
            mProgressDialog.setTitle(R.string.title_progress);
            mProgressDialog.setMessage("Chargement...");
            mProgressDialog.setIndeterminate(false);
            mProgressDialog.show();
        }
 
        @Override
        protected Void doInBackground(Void... params) {
            try {
                /* Lancement des logs 
                 */
                Log.d("MainActivity", "Download ");
                
                /* Connection au site web défini par 'url'
                 */
                Document webPage = Jsoup.connect(url).get();
                
                /* Récupération des valeurs depuis le HTML
                 */
                tournois = webPage.select("li[class=elementtournoi]");
                lieux   = webPage.select("li[class=elementtournoi] div[class=annucontent] h3");
                details = webPage.select("li[class=elementtournoi] div[class=annucontent] div");
                jours   = webPage.select("li[class=elementtournoi] div[class=calendrierjour]");
                mois    = webPage.select("li[class=elementtournoi] div[class=calendriermois]");

                
            }
            catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
 
        @Override
        protected void onPostExecute(Void result) {
        	
        	for (int i = 0; i < lieux.size(); i++){
        		tournoiArrayList.add(new Tournoi(lieux.get(i).text(), details.get(i).text(), jours.get(i).text(), mois.get(i).text()));
        	}

        	m_adapter = new TournoiAdapter(MainActivity.this, R.layout.adapter, tournoiArrayList);

    		// display the list.
            setListAdapter(m_adapter);
            
            mProgressDialog.dismiss();
        }
    }
}
