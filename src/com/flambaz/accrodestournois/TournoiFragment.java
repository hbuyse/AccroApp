package com.flambaz.accrodestournois;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class TournoiFragment extends Fragment {
	private String url_tournoi;
	private ProgressDialog mProgressDialog;
	private Document webPage = null;

	
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		url_tournoi = getActivity().getIntent().getExtras().getString("url_tournoi");
		
		/* Execute Title AsyncTask
		 * create some objects
		 * here is where you could also request data from a server
		 * and then create objects from that data.
		 */
	    try {
			new ParseWebSite().execute().get();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		/* Récupération de la View
		 */
		View rootView = inflater.inflate(R.layout.fragment_tournoi, container, false);
		
		/* Récupération de toutes les informations contenues dans le HTML
		 */
		String _libelleTournoi = webPage.select("div[id=textualbig] div[id=libelletournoi]").text();
		String _centreDivers   = webPage.select("div[id=textualbig] ul[class=center] strong").text();
		String _publicateur      = webPage.select("div[id=textualbig] ul[class=center] em").text();
		
		
		
		
        /* Récupération du titre du tournoi (pas le lieu)
         */
        TextView libelle_tournoi = (TextView) rootView.findViewById(R.id.libelleTournoi);
        TextView centre_divers   = (TextView) rootView.findViewById(R.id.centreDivers);
        TextView publicateur     = (TextView) rootView.findViewById(R.id.publicateur);
        
        /* Si il n'y a pas de titre, on libère la place.
         */
		if (_libelleTournoi.equals("")){
			libelle_tournoi.setVisibility(View.GONE);
		}
        libelle_tournoi.setText(_libelleTournoi);

        centre_divers.setText(_centreDivers);
        
        publicateur.setText(_publicateur);
        
		
		return rootView;
	}
	
	
	
	
	/* Tache asynchrone permettant de parser le site web accro-des-tournois
	 */
    private class ParseWebSite extends AsyncTask<Void, Void, Void> {
        
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressDialog = new ProgressDialog(getActivity());
            mProgressDialog.setTitle(R.string.tournoi_progress);
            mProgressDialog.setMessage("Chargement...");
            mProgressDialog.setIndeterminate(false);
            mProgressDialog.show();
        }
 
        
        
        
        @Override
        protected Void doInBackground(Void... parameters) {
            /* Connection au site web défini par 'url'
			 */
            try {
				webPage = Jsoup.connect(url_tournoi).get();
			} catch (IOException e) {
				// TODO Auto-generated catch block
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
        }
    }
}
