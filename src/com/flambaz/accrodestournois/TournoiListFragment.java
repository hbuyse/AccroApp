package com.flambaz.accrodestournois;

import java.io.IOException;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import android.app.ListFragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

public class TournoiListFragment extends ListFragment {
	/* URL Address
     */
    static String url = "http://www.accro-des-tournois.com";
    static ProgressDialog mProgressDialog;
    
    
	/* declare class variables
	 */
	private ArrayList<Tournoi> tournoiArrayList = new ArrayList<Tournoi>();
	
		
	public TournoiListFragment() {
		// TODO Auto-generated constructor stub
	}
	
	
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		  
		/* Execute Title AsyncTask
		 * create some objects
		 * here is where you could also request data from a server
		 * and then create objects from that data.
		 */
	    //new ParseWebSite().execute();
		
		tournoiArrayList.add(new Tournoi("Toto", "3x3","26", "Sep", "http://", 3));
		tournoiArrayList.add(new Tournoi("Toto", "3x3","26", "Sep", "http://", 3));
		tournoiArrayList.add(new Tournoi("Toto", "3x3","26", "Sep", "http://", 3));
		tournoiArrayList.add(new Tournoi("Toto", "3x3","26", "Sep", "http://", 3));
		
	    /* instantiate our ItemAdapter class
    	 */
	    TournoiListAdapter m_adapter = new TournoiListAdapter(getActivity(), R.layout.row, tournoiArrayList);
	    setListAdapter(m_adapter);
	}
	
	
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {  
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        return rootView;
	}

	
	
	
    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
		/* Envoi de l'URL du tournoi sur lequel nous avons cliqué
		 */
		Intent intent = new Intent(getActivity(), TournoiActivity.class);
		intent.putExtra("url_tournoi", tournoiArrayList.get(position).getLien());
		startActivity(intent);
    }
    
    
    
    
    /* Tache asynchrone permettant de parser le site web accro-des-tournois
	 */
    private class ParseWebSite extends AsyncTask<Void, Void, Void> {
    	private Elements    tournois;
        
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressDialog = new ProgressDialog(getActivity());
            mProgressDialog.setTitle(R.string.title_progress);
            mProgressDialog.setMessage("Chargement...");
            mProgressDialog.setIndeterminate(false);
            mProgressDialog.show();
        }
 
        
        @Override
        protected Void doInBackground(Void... params) {
            try {                
                /* Connection au site web défini par 'url'
                 */
                Document webPage = Jsoup.connect(url).get();
                
                /* Récupération des valeurs depuis le HTML
                 */
                tournois = webPage.select("li[class=elementtournoi]");
                
                for (int i = 0; i < tournois.size(); i++){
            		Element lieu     = tournois.get(i).select("div[class=annucontent] h3").first();
            		Element detail   = tournois.get(i).select("div[class=annucontent] div").first();
            		Element jour     = tournois.get(i).select("div[class=calendrierjour]").first();
            		Element mois     = tournois.get(i).select("div[class=calendriermois]").first();
            		String  lien     = tournois.get(i).select("a").first().attr("abs:href");
            		
            		int nbJour       = tournois.get(i).select("div[class=calendrierjour]").size();
            		
            		tournoiArrayList.add(new Tournoi(lieu.text(), detail.text(), jour.text(), mois.text(), lien, nbJour));
            	}
                
            }
            catch (IOException e) {
                e.printStackTrace();
            }
            
            mProgressDialog.dismiss();
            
            return null;
        }
    }
}
