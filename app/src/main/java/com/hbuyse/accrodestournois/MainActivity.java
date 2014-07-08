package com.hbuyse.accrodestournois;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;


public class MainActivity extends Activity {
    /* Called when the activity is first created.
     */
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /* Create the list fragment and add it as our sole content.
         */
        if (getFragmentManager().findFragmentById(R.id.containerMain) == null) {
            MainFragment list = new MainFragment();
            getFragmentManager().beginTransaction().add(R.id.containerMain, list).commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        /* Inflate the menu; this adds items to the action bar if it is present.
         */
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        /* Handle action bar item clicks here. The action bar will
         * automatically handle clicks on the Home/Up button, so long
         * as you specify a parent activity in AndroidManifest.xml.
         */
        switch (item.getItemId()) {
            case R.id.action_settings:
                Toast.makeText(this, "To implement", Toast.LENGTH_SHORT);
                return true;

            case R.id.refresh:
                recreate();
//                Fragment frag = getFragmentManager().findFragmentById(R.id.containerMain);
//                frag.getFragmentManager().beginTransaction().detach(frag).commit();
//                frag.getFragmentManager().beginTransaction().attach(frag).commit();
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }
}
