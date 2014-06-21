package com.flambaz.accrodestournois;

import java.io.IOException;
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

public class TournamentFragment extends Fragment {
    private String url_tournament;
    private ProgressDialog mProgressDialog;
    private Document webPage = null;

    
    
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        url_tournament = getActivity().getIntent().getExtras().getString("url_tournament");
        
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
        String   _subtitleTournament = webPage.select("div[id=textualbig] div[id=libelletournoi]").text();
        String   _variousInfos       = webPage.select("div[id=textualbig] ul[class=center] strong").text().replace("|", "-");
        String   _publisher          = webPage.select("div[id=textualbig] ul[class=center] em").text();

        String   _additionalInfo     = webPage.select("div[id=textualbig] div[id=tdetails] p").text();
        Elements _tdetails           = webPage.select("div[id=textualbig] div[id=tdetails] p");


        /* Récupération du titre du tournoi (pas le lieu)
         */
        TextView subtitleTournament = (TextView) rootView.findViewById(R.id.subtitleTournament);
        TextView variousInfos       = (TextView) rootView.findViewById(R.id.variousInfos);
        TextView publisher          = (TextView) rootView.findViewById(R.id.publisher);

        /* Récupération de la partie : infos complémentaires.
         * Pour le moment, on désactive la vue au cas où elle n'existe pas.
         */
        TextView additionalInfo     = (TextView) rootView.findViewById(R.id.additionalInfo);
        additionalInfo.setVisibility(View.GONE);

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
        if (_subtitleTournament.equals("")){
        	subtitleTournament.setVisibility(View.GONE);
        }
        
        /* On dépose les valeurs dans les champs correspondants
         */
        subtitleTournament.setText(_subtitleTournament);
        variousInfos.setText(_variousInfos);
        publisher.setText(_publisher);
        additionalInfo.setText(_additionalInfo);


        for (Element i : _tdetails) {
            /* Is there a contact name?
             */
            if ( ! i.select("span[class=usericon]").isEmpty() ) {
                contactName.setText(i.text());
                contactName.setVisibility(View.VISIBLE);
                Log.i("USER", i.text());
            }

            /* Is there a phone number?
             */
            if ( ! i.select("span[class=phoneicon]").isEmpty() ) {
                contactPhone.setText(i.text());
                contactPhone.setVisibility(View.VISIBLE);
                Log.i("PHONE", i.text());
            }

            /* Is there a mail address?
             */
            if ( ! i.select("span[class=mailicon]").isEmpty() ) {
                contactMail.setVisibility(View.VISIBLE);
                Log.i("MAIL", i.text());
            }

            /* Is there a website?
             */
            if ( ! i.select("span[class=mouseicon]").isEmpty() ) {
                contactWebsite.setVisibility(View.VISIBLE);
                Log.i("WEBSITE", i.text());
            }
        }

        /* Is there additionals informations ?
         */
        if (! _tdetails.last().text().isEmpty()) {
            additionalInfo.setText(_tdetails.last().html().replace("<br /><br />", "").replace("<br />", "\n").replace("&eacute;", "é"));
            additionalInfo.setVisibility(View.VISIBLE);
        }

        return rootView;
    }
    
    
    
    
    /* Asynchronous task which download the source code of the website accro-des-tournois
     */
    private class ParseWebSite extends AsyncTask<Void, Void, Void> {
        
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressDialog = new ProgressDialog(getActivity());
            mProgressDialog.setTitle(R.string.tournamentProgress);
            mProgressDialog.setMessage(getString(R.string.loading));
            mProgressDialog.setIndeterminate(false);
            mProgressDialog.show();
        }
 
        
        
        
        @Override
        protected Void doInBackground(Void... parameters) {
            /* Connecting to the website using 'url_tournament'
             */
            try {
                webPage = Jsoup.connect(url_tournament).get();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            
            return null;
        }
        
        
        
        
        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            
            /* Stopping the progress dialog
             */
            if (mProgressDialog.isShowing()) {
                mProgressDialog.dismiss();
            }
        }
    }
}
