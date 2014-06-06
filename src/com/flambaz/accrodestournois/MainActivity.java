package com.flambaz.accrodestournois;

import java.util.ArrayList;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;


public class MainActivity extends ListActivity {
    /* URL Address
     */
    String url = "http://www.accro-des-tournois.com";
    ProgressBar mProgressDialog;
	
	/* declare class variables
	 */
	private ArrayList<Tournoi> tournoiArrayList = new ArrayList<Tournoi>();
	private Runnable viewParts;
	private TournoiAdapter m_adapter;
	
	/* Called when the activity is first created.
	 */
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_main);

        // instantiate our ItemAdapter class
        m_adapter = new TournoiAdapter(this, R.layout.adapter, tournoiArrayList);
        setListAdapter(m_adapter);

        /* here we are defining our runnable thread.
         */
        viewParts = new Runnable(){
        	public void run(){
        		handler.sendEmptyMessage(0);
        	}
        };
        
        // here we call the thread we just defined - it is sent to the handler below.
        Thread thread =  new Thread(null, viewParts, "MagentoBackground");
        thread.start();
    }
    
    
    private Handler handler = new Handler() {
		public void handleMessage(Message msg)
		{
			// create some objects
			// here is where you could also request data from a server
			// and then create objects from that data.
			tournoiArrayList.add(new Tournoi("MyItemName", "This is item #1", "14", "Sep"));
			tournoiArrayList.add(new Tournoi("MyItemName #2", "This is item #2", "14", "Sep"));
/*			tournoiArrayList.add(new Tournoi("MyItemName", "This is item #1", "14", "Sep"));
			tournoiArrayList.add(new Tournoi("MyItemName #2", "This is item #2", "14", "Sep"));
			tournoiArrayList.add(new Tournoi("MyItemName", "This is item #1", "14", "Sep"));
			tournoiArrayList.add(new Tournoi("MyItemName #2", "This is item #2", "14", "Sep"));
			tournoiArrayList.add(new Tournoi("MyItemName", "This is item #1", "14", "Sep"));
			tournoiArrayList.add(new Tournoi("MyItemName #2", "This is item #2", "14", "Sep"));
			tournoiArrayList.add(new Tournoi("MyItemName", "This is item #1", "14", "Sep"));
			tournoiArrayList.add(new Tournoi("MyItemName #2", "This is item #2", "14", "Sep"));
			tournoiArrayList.add(new Tournoi("MyItemName", "This is item #1", "14", "Sep"));
			tournoiArrayList.add(new Tournoi("MyItemName #2", "This is item #2", "14", "Sep"));
			tournoiArrayList.add(new Tournoi("MyItemName", "This is item #1", "14", "Sep"));
			tournoiArrayList.add(new Tournoi("MyItemName #2", "This is item #2", "14", "Sep"));
			tournoiArrayList.add(new Tournoi("MyItemName", "This is item #1", "14", "Sep"));
			tournoiArrayList.add(new Tournoi("MyItemName #2", "This is item #2", "14", "Sep"));
*/

			m_adapter = new TournoiAdapter(MainActivity.this, R.layout.adapter, tournoiArrayList);

			// display the list.
	        setListAdapter(m_adapter);
		}
	};
	
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		Tournoi item = (Tournoi) getListAdapter().getItem(position);
		Toast.makeText(this, item.getDetails() + " selected", Toast.LENGTH_LONG).show();
	}
}
