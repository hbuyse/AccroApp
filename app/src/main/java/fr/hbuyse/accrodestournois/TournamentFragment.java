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

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

import fr.hbuyse.accrodestournois.R;

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
       if(isOnline()) {
           new ParseWebSite(getActivity(), rootView).execute();
        }
        else {
            Toast.makeText(getActivity(),
                    getActivity().getResources().getString(R.string.no_connection),
                    Toast.LENGTH_LONG).show();
        }

        return rootView;
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


        /* Method that create the LinearLayout corresponding to the header
         */
        protected LinearLayout header(Document webPage) {
            LinearLayout ll = new LinearLayout(getActivity());

            // Orientation of the LinearLayout and background
            ll.setOrientation(LinearLayout.VERTICAL);
            ll.setBackgroundResource(R.drawable.header_tournament);


            // Width and height for the Views
            LinearLayout.LayoutParams viewsParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);


            /* ***********************
             * * SUBTITLE TOURNAMENT *
             * ***********************
             */
            String _subtitleTournament = webPage.select("div[id=libelletournoi]").text();

            // Create the textView
            TextView subtitleTournament = new TextView(getActivity());

            // Making the design
            subtitleTournament.setGravity(Gravity.CENTER);
            subtitleTournament.setTextColor(getResources().getColor(R.color.white));
            subtitleTournament.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
            subtitleTournament.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
            subtitleTournament.setText(_subtitleTournament.toUpperCase());

            // If there is no title, we free the space
            if (_subtitleTournament.equals("")) {
                subtitleTournament.setVisibility(View.GONE);
            }

            // Adding the view to the
            ll.addView(subtitleTournament, viewsParams);


            /* *********
             * * RULER *
             * *********
             */
            View ruler = new View(getActivity());
            int height_ruler = (int) (getActivity().getResources().getDisplayMetrics().density * 2f + 0.5f);
            ViewGroup.LayoutParams ruler_params = new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, height_ruler);
            ruler.setBackgroundColor(getResources().getColor(R.color.white));
            // If there is no title, we free the space
            if (_subtitleTournament.equals("")) {
                ruler.setVisibility(View.GONE);
            }

            // Adding the view to the
            ll.addView(ruler, ruler_params);


            /* ************************
             * * VARIOUS INFORMATIONS *
             * ************************
             */
            String _variousInfos = webPage.select("ul[class=center] strong").text().replace("|", "-");

            // Create the textView
            TextView variousInfos = new TextView(getActivity());

            // Making the design
            variousInfos.setGravity(Gravity.CENTER);
            variousInfos.setTextColor(getResources().getColor(R.color.white));
            variousInfos.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
            variousInfos.setTypeface(Typeface.DEFAULT, Typeface.NORMAL);
            variousInfos.setText(_variousInfos);

            /* Adding the view to the linearlayout
             * If there is a  title, then we put a margin to the various infos textview
             */
            if (!_subtitleTournament.equals("")) {
                int marginTop = (int) (getResources().getDisplayMetrics().density * 5f + 0.5f);
                viewsParams.setMargins(0, marginTop, 0, 0);
            }
            ll.addView(variousInfos, viewsParams);



            return ll;
        }


        /* Method that create the LinearLayout corresponding to the additional Informations
        */
        protected LinearLayout additionalInfos(Document webPage) {
            LinearLayout ll = new LinearLayout(getActivity());


            /* Set the visibility to GONE because we do not want the LinearLayout to show up if it
             * does not contain a thing
             */
            ll.setVisibility(View.GONE);


            // Orientation of the LinearLayout and background
            ll.setOrientation(LinearLayout.VERTICAL);
            ll.setBackgroundResource(R.drawable.item_tournament_shape);


            // Width and height for the Views
            LinearLayout.LayoutParams viewsParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);



            /* ***************************
             * * TITLE - ADDITIONAL INFO *
             * ***************************
             */
            TextView title = new TextView(getActivity());


            // Making the design of the title of the Additional Infos view
            title.setGravity(Gravity.START);
            title.setTextColor(getResources().getColor(R.color.font));
            title.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
            title.setTypeface(Typeface.DEFAULT, Typeface.ITALIC);
            title.setText(getResources().getString(R.string.additionalInfo));


            // Adding the view to the layout
            ll.addView(title, viewsParams);


            /* *********
             * * RULER *
             * *********
             */
            View ruler = new View(getActivity());
            int height_ruler = (int) (getActivity().getResources().getDisplayMetrics().density * 1f + 0.5f);
            ViewGroup.LayoutParams ruler_params = new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, height_ruler);
            ruler.setBackgroundColor(getResources().getColor(R.color.font));

            // Adding the view to the
            ll.addView(ruler, ruler_params);


            /* ************************
             * * VARIOUS INFORMATIONS *
             * ************************
             */
            String _additionalInfo = webPage.select("div[id=tdetails] p").text();
            Elements _tdetails = webPage.select("div[id=tdetails] p");

            // Create the textView
            TextView additionalInfo = new TextView(getActivity());

            // Making the design
            additionalInfo.setGravity(Gravity.START);
            additionalInfo.setTextColor(getResources().getColor(R.color.font));
            additionalInfo.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
            additionalInfo.setTypeface(Typeface.DEFAULT, Typeface.NORMAL);
            additionalInfo.setLineSpacing(5, 1);



            // Putting text into the textView
            if (!_tdetails.last().text().isEmpty()) {
                additionalInfo.setText(specialChar(_tdetails.last().html()));
                ll.setVisibility(View.VISIBLE);
            }


            // Adding the view to the
            int marginTop = (int) (getResources().getDisplayMetrics().density * 5f + 0.5f);
            viewsParams.setMargins(0, marginTop, 0, 0);
            ll.addView(additionalInfo, viewsParams);



            return ll;
        }


        /* Method that return a TextView containing the publisher
         */
        protected TextView publisher(Document webPage) {
            String _publisher = webPage.select("ul[id=tlist] div[class=align_right] a").text();

            TextView publisher = new TextView(getActivity());

            publisher.setGravity(Gravity.END);
            publisher.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
            publisher.setTextColor(getResources().getColor(R.color.font));
            publisher.setTypeface(Typeface.DEFAULT, Typeface.ITALIC);
            publisher.setText(getString(R.string.publisher) + " " + _publisher);

            return publisher;
        }


        /* Method that fill the linearlayout contact
         */
        protected LinearLayout contact(Document webpage) {
            LinearLayout ll = new LinearLayout(getActivity());


            // Orientation of the LinearLayout and background
            ll.setBackgroundResource(R.drawable.contact_tournament);


            // Width, height and weight for the Views
            LinearLayout.LayoutParams viewsParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT, 1f);


            // Part of webPage
            Elements _tdetails = webPage.select("div[id=tdetails] p");


            final TextView  contactName = new TextView(getActivity());
            final ImageView contactPhone = new ImageView(getActivity());
            final ImageView contactMail = new ImageView(getActivity());
            final ImageView contactWebsite = new ImageView(getActivity());
            final ImageView contactAddr = new ImageView(getActivity());

            contactName.setVisibility(View.GONE);
            contactPhone.setVisibility(View.GONE);
            contactWebsite.setVisibility(View.GONE);
            contactMail.setVisibility(View.GONE);
            contactAddr.setVisibility(View.GONE);


            // Making the design of the title of the Additional Infos view
            contactName.setGravity(Gravity.CENTER_VERTICAL);
            contactName.setTextColor(getResources().getColor(R.color.white));
            contactName.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
            contactName.setTypeface(Typeface.DEFAULT, Typeface.NORMAL);


            // Design of the contact phone image
            contactPhone.setImageResource(R.drawable.ic_action_call);
            contactPhone.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent callIntent = new Intent(Intent.ACTION_DIAL);
                    callIntent.setData(Uri.parse("tel:" + contactPhone.getContentDescription().toString().trim()));
                    startActivity(callIntent );
                }
            });


            // GETTING THE URL OF THE CLUB WEBPAGE
            for (Element i : _tdetails) {
                String addr = i.getElementsContainingText("Site du tournoi").attr("href");
                if (!addr.isEmpty()) {
                    contactWebsite.setContentDescription(addr);
                }
            }


            // GETTING THE URL OF THE WEBPAGE TO SEND A MESSAGE TO THE PROMOTER
            for (Element i : _tdetails) {
                String addr = i.getElementsContainingText("Envoyer un message").attr("href");
                if (!addr.isEmpty()) {
                    contactMail.setContentDescription(addr);
                }
            }


            // Design of the contact website image
            contactWebsite.setImageResource(R.drawable.ic_action_web_site);
            contactWebsite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.addCategory(Intent.CATEGORY_BROWSABLE);
                    i.setData(Uri.parse(contactWebsite.getContentDescription().toString().trim()));
                    startActivity(i);
                }
            });


            // Design of the contact mail image
            contactMail.setImageResource(R.drawable.ic_action_email);
            contactMail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(contactMail.getContentDescription().toString().trim()));
                    startActivity(i);
                }
            });


            // Design of the contact address
            contactAddr.setVisibility(View.VISIBLE);
            contactAddr.setContentDescription(_tdetails.select("a[href]").first().attr("href"));
            contactAddr.setImageResource(R.drawable.ic_action_place);
            contactAddr.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(contactAddr.getContentDescription().toString().trim()));
                    startActivity(i);
                }
            });


            for (Element i : _tdetails) {
                /* Is there a contact name?
                 */
                if (!i.select("span[class=usericon]").isEmpty()) {
                    contactName.setText(specialChar(i.text()));
                    contactName.setVisibility(View.VISIBLE);
                }

                /* Is there a phone number?
                 */
                if (!i.select("span[class=phoneicon]").isEmpty()) {
                    contactPhone.setContentDescription(i.text().replace(".", "").replace(" ", "").replace("-", ""));
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


            ll.addView(contactName, viewsParams);
            ll.addView(contactPhone, viewsParams);
            ll.addView(contactMail, viewsParams);
            ll.addView(contactWebsite, viewsParams);
            ll.addView(contactAddr, viewsParams);

            return ll;
        }


        private void parse() {
            /* Definition of the LinearLayout used for showing the tournament
             */
            LinearLayout ll = (LinearLayout) rootView.findViewById(R.id.layoutTournament);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            int marginTopViews = (int)
                    (getActivity().getResources().getDisplayMetrics().density * 15f + 0.5f);
            params.setMargins(0, marginTopViews, 0, 0);


            // ADDING THE HEADER
            LinearLayout.LayoutParams paramsHeader = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            ll.addView(header(webPage), paramsHeader);


            // ADDING THE CONTACT PART
            ll.addView(contact(webPage), params);



            // ADDING THE DYNAMIC ELEMENTS OF THE TOURNAMENT
            Elements _elementTournaments = webPage.select("ul[id=tlist] li[class=elementtournoi]");

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

                TextView title = new TextView(getActivity());
                title.setText(_title);
                title.setGravity(Gravity.START);
                title.setTextColor(getResources().getColor(R.color.font));
                title.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
                title.setTypeface(Typeface.DEFAULT, Typeface.ITALIC);

                TextView description = new TextView(getActivity());
                description.setText(specialChar(_description));
                description.setGravity(Gravity.START);
                description.setTextColor(getResources().getColor(R.color.font));
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


            // ADDING THE ADDITIONAL INFORMATIONS
            ll.addView(additionalInfos(webPage), params);


            // ADDING THE PUBLISHER OF THE TOURNAMENT
            ll.addView(publisher(webPage), params);

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

            /* Stopping the progress dialog
             */
            if (mProgressDialog.isShowing()) {
                this.parse();
                mProgressDialog.dismiss();
            }
            else {
                Toast.makeText(getActivity(),
                        R.string.internet_pb,
                        Toast.LENGTH_LONG).show();
            }

        }
    }
}