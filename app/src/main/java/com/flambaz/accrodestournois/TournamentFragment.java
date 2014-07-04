package com.flambaz.accrodestournois;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class TournamentFragment extends Fragment {
    private String url_tournament;
    private ProgressDialog mProgressDialog;
    private Document webPage = null;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /* Getting the URL from the activity before
         */
        url_tournament = getActivity().getIntent().getExtras().getString("url_tournament");
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        /* Getting the View
         */
        View rootView = inflater.inflate(R.layout.fragment_tournament, container, false);

        /* Execute Title AsyncTask
         * create some objects
         * here is where you could also request data from a server
         * and then create objects from that data.
         */
        new ParseWebSite(getActivity(), rootView).execute();

        return rootView;
    }


    /* Asynchronous task which download the source code of the website accro-des-tournois
     */
    private class ParseWebSite extends AsyncTask<Void, Void, Void> {
        private Context context;
        private View rootView;


        /* Using a constructor in order to pass the view and the context
         * With those things, I can now pass show the ProgressDialog.
         */
        public ParseWebSite(Context context, View rootView) {
            this.context = context;
            this.rootView = rootView;
        }


        protected String specialChar(String _string){
            String temp = _string;
            Log.i("String", _string);
            temp = temp.replace(" <br /> <br />", "")
                .replace("<br /> ", "")
                .replace("<br />", "\n")
                .replace("&quot;", "\"")
                .replace("&amp;", "&")
                .replace("&apos;", "\'")
                .replace("&raquo;", "\"")
                .replace("&laquo;", "\"");

            // Letter A
            temp = temp.replace("&agrave;", "à")
                .replace("&acirc;", "â");

            // Letter C
            temp = temp.replace("&ccedil;", "ç");

            // Letter E
            temp = temp.replace("&eacute;", "é")
                .replace("&ecirc;", "ê")
                .replace("&egrave;", "è")
                .replace("&euml;", "ë");

            // Letter I
            temp = temp.replace("&iuml;", "ï")
                .replace("&icirc;", "î");

            // Letter O
            temp = temp.replace("&ouml;", "ö")
                .replace("&ocirc;", "ô");

            // Letter U
            temp = temp.replace("&ucirc;", "û")
                .replace("&uuml;", "ü");

            // Letter Y
            temp = temp.replace("&uuml;", "ÿ");

            return temp.trim();
        }


        private void parse() {
            /* Creation of the ruler
             */
            View ruler = new View(getActivity());
            int height_ruler = (int) (getActivity().getResources().getDisplayMetrics().density * 1f + 0.5f);
            ViewGroup.LayoutParams ruler_params = new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, height_ruler);
            ruler.setBackgroundColor(getResources().getColor(R.color.font));
            

            /* Definition of the LinearLayout used for showing the tournament
             */
            LinearLayout ll = (LinearLayout) rootView.findViewById(R.id.textV);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(0, 0, 0, 20);


            /* Recover all the informations that are contained in the HTML webpage
             */
            String _subtitleTournament = webPage.select("div[id=libelletournoi]").text();
            String _variousInfos = webPage.select("ul[class=center] strong").text().replace("|", "-");
            String _publisher = webPage.select("ul[id=tlist] div[class=align_right] a").text();

            String _additionalInfo = webPage.select("div[id=tdetails] p").text();
            Elements _tdetails = webPage.select("div[id=tdetails] p");

            Elements _elementTournaments = webPage.select("ul[id=tlist] li[class=elementtournoi]");


            /* Recover the title of the tournament (not the place)
             */
            TextView subtitleTournament = (TextView) rootView.findViewById(R.id.subtitleTournament);
            TextView variousInfos = (TextView) rootView.findViewById(R.id.variousInfos);
            TextView publisher = (TextView) rootView.findViewById(R.id.publisher);


            /* Recover the part : additional informations
             * For now, we disable the view in order to not show it if it does not exist
             */
            TextView additionalInfo = (TextView) rootView.findViewById(R.id.additionalInfo);
            additionalInfo.setVisibility(View.GONE);


            /* Recover the part : contact
             * For now, we disable the views in order to not show them if they do not exist
             */
            TextView contactName = (TextView) rootView.findViewById(R.id.contactName);
            contactName.setVisibility(View.GONE);
            TextView contactPhone = (TextView) rootView.findViewById(R.id.contactPhone);
            contactPhone.setVisibility(View.GONE);
            TextView contactWebsite = (TextView) rootView.findViewById(R.id.contactWebsite);
            contactWebsite.setVisibility(View.GONE);
            TextView contactMail = (TextView) rootView.findViewById(R.id.contactMail);
            contactWebsite.setVisibility(View.GONE);


            /* If there is no title, we free the space
             */
            if (_subtitleTournament.equals("")) {
                subtitleTournament.setVisibility(View.GONE);
            }


            /* We put the values in the corresponding textviews
             */
            subtitleTournament.setText(_subtitleTournament.toUpperCase());
            variousInfos.setText(_variousInfos);
            publisher.setText(getString(R.string.publisher) + " " + _publisher);
            additionalInfo.setText(_additionalInfo);


            /* *******************
             * * TOURNAMENT PART *
             * *******************
             */
            for (Element i : _elementTournaments) {

                View ruler1 = new View(getActivity());
                int height_ruler1 = (int) (getActivity().getResources().getDisplayMetrics().density * 1f + 0.5f);
                ViewGroup.LayoutParams ruler_params1 = new ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT, height_ruler1);
                ruler1.setBackgroundColor(getResources().getColor(R.color.font));
                /* Definition of the Linear Layout
                 */
                LinearLayout linearLayout = new LinearLayout(getActivity());

                /* Parameters that will be used when we add a TextView
                 */
                LinearLayout.LayoutParams paramsTitle = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                LinearLayout.LayoutParams paramsDescr = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                paramsDescr.setMargins(0, 15, 0, 0);


                /* Definition of the LinearLayout parameters
                 */
                linearLayout.setOrientation(LinearLayout.VERTICAL);
                linearLayout.setBackgroundResource(R.drawable.item_tournament_shape);


                String _title = i.select("h3").text();
                String _description = i.select("div").html();
                Log.i("Description", _description);

                TextView title = new TextView(getActivity());
                title.setText(_title);
                title.setGravity(Gravity.START);
                title.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
                title.setTypeface(Typeface.DEFAULT, Typeface.ITALIC);

                TextView description = new TextView(getActivity());
                description.setText(specialChar(_description));
                description.setGravity(Gravity.START);
                description.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
                description.setLineSpacing(5, 1);


                /* Adding to the LinearLayout that will be added after that
                 */
                linearLayout.addView(title, paramsTitle);
                linearLayout.addView(ruler1, ruler_params1);
                linearLayout.addView(description, paramsDescr);


                /* Adding to the principal view
                 */
                ll.addView(linearLayout, params);
            }



            /* ****************
             * * CONTACT PART *
             * ****************
             */
            for (Element i : _tdetails) {
                /* Is there a contact name?
                 */
                if (!i.select("span[class=usericon]").isEmpty()) {
                    contactName.setText(i.text());
                    contactName.setVisibility(View.VISIBLE);
                }

                /* Is there a phone number?
                 */
                if (!i.select("span[class=phoneicon]").isEmpty()) {
                    contactPhone.setText(i.text());
                    contactPhone.setVisibility(View.VISIBLE);
                }

                /* Is there a mail address?
                 */
                if (!i.select("span[class=mailicon]").isEmpty()) {
                    contactMail.setVisibility(View.VISIBLE);
                }

                /* Is there a website?
                 */
                if (!i.select("span[class=mouseicon]").isEmpty()) {
                    contactWebsite.setVisibility(View.VISIBLE);
                }
            }

            /* ********************************
             * * ADDITIONAL INFORMATIONS PART *
             * ********************************
             */
            if (!_tdetails.last().text().isEmpty()) {
                additionalInfo.setText(specialChar(_tdetails.last().html()));
                additionalInfo.setVisibility(View.VISIBLE);
            }
        }


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
                e.printStackTrace();
            }

            return null;
        }


        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);

            this.parse();

            /* Stopping the progress dialog
             */
            if (mProgressDialog.isShowing()) {
                mProgressDialog.dismiss();
            }
        }
    }
}
