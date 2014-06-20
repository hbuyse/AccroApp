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
        new ParseWebSite().execute();
    }
    
    

    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {  
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        return rootView;
    }

    
    
    
    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        
        /* Envoi de l'URL du tournoi sur lequel nous avons cliqué
         */
        Intent intent = new Intent(getActivity(), TournoiActivity.class);
        intent.putExtra("url_tournoi" , tournoiArrayList.get(position).getLien());
        intent.putExtra("lieu_tournoi", tournoiArrayList.get(position).getLieu());
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
            mProgressDialog.setTitle(R.string.main_progress);
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

            return null;
        }
        
        
        
        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            
            /* Extinction de la boite de dialogue
             */
            if (mProgressDialog.isShowing()) {
                mProgressDialog.dismiss();
            }
            
            
            /* Instantiate our ItemAdapter class
             * 
             * Il faut laisser le setter de la ListAdapter dans PostExecute sinon
             * il se peut que l'activité se termine et s'affiche sans que le thread ne soit terminé.
             */
            TournoiListAdapter m_adapter = new TournoiListAdapter(getActivity(), R.layout.row, tournoiArrayList);
            setListAdapter(m_adapter);
        }
    }
}
