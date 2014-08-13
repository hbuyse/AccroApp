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

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TableRow;

public class Contact extends Activity {

    TableRow em_hb;
    TableRow gh_hb;

    ImageView fb;
    ImageView tw;
    ImageView gp;
    ImageView em;
    ImageView wb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        getActionBar().setDisplayHomeAsUpEnabled(true);

        em_hb = (TableRow)  findViewById(R.id.email_hb);
        gh_hb = (TableRow)  findViewById(R.id.github_hb);

        fb    = (ImageView) findViewById(R.id.facebook_adt);
        tw    = (ImageView) findViewById(R.id.twitter_adt);
        gp    = (ImageView) findViewById(R.id.googleplus_adt);
        em    = (ImageView) findViewById(R.id.email_adt);
        wb    = (ImageView) findViewById(R.id.website_adt);

        em_hb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.addCategory(Intent.CATEGORY_BROWSABLE);
                i.setData(Uri.parse(getResources().getString(R.string.dev_mail_addr)));
                startActivity(i);
            }
        });

        gh_hb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.addCategory(Intent.CATEGORY_BROWSABLE);
                i.setData(Uri.parse(getResources().getString(R.string.dev_github_addr)));
                startActivity(i);
            }
        });

        fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.addCategory(Intent.CATEGORY_BROWSABLE);
                try {
                    i.setData(Uri.parse(getResources().getString(R.string.facebook_addr)));
                    startActivity(i);
                }
                catch (android.content.ActivityNotFoundException e) {
                    i.setData(Uri.parse(getResources().getString(R.string.facebook_addr2)));
                    startActivity(i);
                }

            }
        });

        tw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.addCategory(Intent.CATEGORY_BROWSABLE);
                i.setData(Uri.parse(getResources().getString(R.string.twitter_addr)));
                startActivity(i);
            }
        });

        gp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.addCategory(Intent.CATEGORY_BROWSABLE);
                i.setData(Uri.parse(getResources().getString(R.string.googleplus_addr)));
                startActivity(i);
            }
        });

        em.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.addCategory(Intent.CATEGORY_BROWSABLE);
                i.setData(Uri.parse(getResources().getString(R.string.email_addr)));
                startActivity(i);
            }
        });

        wb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.addCategory(Intent.CATEGORY_BROWSABLE);
                i.setData(Uri.parse(getResources().getString(R.string.website_addr)));
                startActivity(i);
            }
        });
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.contact, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        switch(item.getItemId()){
            case R.id.action_settings:
                return true;
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
