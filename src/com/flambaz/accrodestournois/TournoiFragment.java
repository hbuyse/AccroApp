package com.flambaz.accrodestournois;

import java.io.IOException;
import java.util.Iterator;
import java.util.concurrent.ExecutionException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.util.Log;
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
        String   _libelleTournoi = webPage.select("div[id=textualbig] div[id=libelletournoi]").text();
        String   _centreDivers   = webPage.select("div[id=textualbig] ul[class=center] strong").text().replace("|", "-");
        String   _publicateur    = webPage.select("div[id=textualbig] ul[class=center] em").text();

        String   _infosComp      = webPage.select("div[id=textualbig] div[id=tdetails] p").text();
        Elements _tdetails       = webPage.select("div[id=textualbig] div[id=tdetails] p");


        /* Récupération du titre du tournoi (pas le lieu)
         */
        TextView libelle_tournoi = (TextView) rootView.findViewById(R.id.libelleTournoi);
        TextView centre_divers   = (TextView) rootView.findViewById(R.id.centreDivers);
        TextView publicateur     = (TextView) rootView.findViewById(R.id.publicateur);

        /* Récupération de la partie : infos complémentaires.
         * Pour le moment, on désactive la vue au cas où elle n'existe pas.
         */
        TextView infosComp       = (TextView) rootView.findViewById(R.id.infosComp);
        infosComp.setVisibility(View.GONE);

        /* Récupération de la partie : contact
         * Pour le moment, on désactive toutes les vues au cas où elles n'existent pas.
         */
        TextView contactName     = (TextView) rootView.findViewById(R.id.contactName);
        contactName.setVisibility(View.GONE);
        TextView contactPhone    = (TextView) rootView.findViewById(R.id.contactPhone);
        contactPhone.setVisibility(View.GONE);
        TextView contactWebsite  = (TextView) rootView.findViewById(R.id.contactWebsite);
        contactWebsite.setVisibility(View.GONE);
        TextView contactMail     = (TextView) rootView.findViewById(R.id.contactMail);
        contactWebsite.setVisibility(View.GONE);


        /* Si il n'y a pas de titre, on libère la place.
         */
        if (_libelleTournoi.equals("")){
            libelle_tournoi.setVisibility(View.GONE);
        }
        
        /* On dépose les valeurs dans les champs correspondants
         */
        libelle_tournoi.setText(_libelleTournoi);
        centre_divers.setText(_centreDivers);
        publicateur.setText(_publicateur);
        infosComp.setText(_infosComp);


        for (Element i : _tdetails) {
            /* Vérification de la présence d'un nom
             */
            if ( ! i.select("span[class=usericon]").isEmpty() ) {
                contactName.setText(i.text());
                contactName.setVisibility(View.VISIBLE);
                Log.i("USER", i.text());
            }

            /* Vérification de la présence d'un numéro de téléphone
             */
            if ( ! i.select("span[class=phoneicon]").isEmpty() ) {
                contactPhone.setText(i.text());
                contactPhone.setVisibility(View.VISIBLE);
                Log.i("PHONE", i.text());
            }

            /* Vérification de la présence d'un nom
             */
            if ( ! i.select("span[class=mailicon]").isEmpty() ) {
                contactMail.setVisibility(View.VISIBLE);
                Log.i("MAIL", i.text());
            }

            /* Vérification de la présence d'un nom
             */
            if ( ! i.select("span[class=mouseicon]").isEmpty() ) {
                contactWebsite.setVisibility(View.VISIBLE);
                Log.i("WEBSITE", i.text());
            }
        }

        /* Vérification de la présence d'un nom
         */
        if (! _tdetails.last().text().isEmpty()) {
            infosComp.setText(_tdetails.last().html().replace("<br /><br />", "").replace("<br />", "\n").replace("&eacute;", "é"));
            infosComp.setVisibility(View.VISIBLE);
        }

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
