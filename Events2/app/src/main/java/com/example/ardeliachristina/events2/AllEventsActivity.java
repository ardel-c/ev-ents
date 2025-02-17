package com.example.ardeliachristina.events2;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class AllEventsActivity extends AppCompatActivity {

    DatabaseHelper databaseHelper;
    RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_events);
        ListView listEvents;
        listEvents = findViewById(R.id.listEvents);
        Button btnFetch;
        btnFetch = findViewById(R.id.btnFetch);
        queue = Volley.newRequestQueue(this);

        databaseHelper = new DatabaseHelper(this);
        final CustomAdapter adapt = new CustomAdapter(this, databaseHelper.getEventList());
        listEvents.setAdapter(adapt);

        btnFetch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //fetch JSON
                loadJSON();
                recreate();
            }
        });


    }


    private void loadJSON() {
        String url = "https://api.myjson.com/bins/erh5x";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, (String) null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray data = response.getJSONArray("data");

                    //looping

                    for(int i = 0 ; i < data.length() ; i++)
                    {
                        JSONObject event = data.getJSONObject(i);

                        String id = event.getString("EventId");
                        String name = event.getString("EventName");
                        String desc = event.getString("Description");
                        String loc = event.getString("Location");
                        String StartDate = event.getString("StartDate");
                        String EndDate = event.getString("EndDate");
                        int rating = event.getInt("Rating");
                        double lat = event.getDouble("Latitude");
                        double longi = event.getDouble("Longitude");

                        for(int j = 0; j < databaseHelper.getEventList().size(); j++){
                            if(id.equals(databaseHelper.getEventList().get(j).eventID)){
                                break;
                            }else {
                                databaseHelper.insertEvent(id,name,desc,loc,StartDate,EndDate,rating,lat,longi);
                                break;
                            }

                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        queue.add(request);

    }


    class CustomAdapter extends BaseAdapter {
        Context context;
        ArrayList <Event> events;
        public CustomAdapter(Context context, ArrayList<Event>events){
            this.context= context;
            this.events=events;
        }


            @Override
            public int getCount() {
                return events.size();
            }

            @Override
            public Object getItem(int i) {
                return events.get(i);
            }

            @Override
            public long getItemId(int i) {
                return i;
            }

            @Override
            public View getView(int i, View view, ViewGroup viewGroup) {

                if (view == null) {
                    view = LayoutInflater.from(context).
                            inflate(R.layout.customlist, viewGroup, false);
                }

                Event currentEvent= (Event) getItem(i);
                TextView txtEventID = view.findViewById(R.id.listID);
                TextView txtEventName = view.findViewById(R.id.listName);
                TextView txtEventStart = view.findViewById(R.id.listStartDate);
                TextView txtEventEnd = view.findViewById(R.id.listEndDate);
                TextView txtRating = view.findViewById(R.id.listRating);

                txtEventID.setText(currentEvent.eventID);
                txtEventName.setText(currentEvent.eventName);
                txtEventStart.setText("Start Date : "+currentEvent.startDate);
                txtEventEnd.setText("End Date : "+currentEvent.endDate);
                txtRating.setText("Rating : "+currentEvent.rating);

                return view;
            }
        }


}
