package fr.hbuyse.accrodestournois;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.widget.ImageView;
import android.widget.TextView;

import fr.hbuyse.accrodestournois.R;

public class Contact extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        View.OnClickListener click = new View.OnClickListener() {



            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.addCategory(Intent.CATEGORY_BROWSABLE);

                switch(view.getId()){
                    case R.id.facebook_adt:

                        break;
                    case R.id.twitter_adt:
                        break;
                    case R.id.googleplus_adt:
                        break;
                    case R.id.email_adt:
                        break;
                    case R.id.website_adt:
                        break;
                    case R.id.email_hb:
                        break;
                    case R.id.github_hb:
                        break;
                }

                startActivity(i);
            }
        }
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
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public boolean
}
