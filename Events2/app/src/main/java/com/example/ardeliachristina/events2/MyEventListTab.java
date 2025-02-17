package com.example.ardeliachristina.events2;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class MyEventListTab extends Fragment{
    DatabaseHelper databaseHelper;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.myevent, container, false);

        ListView myEventList = rootView.findViewById(R.id.listMyEvent);

        ArrayList<Event> myEvent= new ArrayList<>();
        myEvent.clear();
        databaseHelper = new DatabaseHelper(getContext());


        for(int i=0; i<databaseHelper.getTransactionList().size();i++){
            if(LoginActivity.currentUserID.equals(databaseHelper.getTransactionList().get(i).userID)){
                for (int j=0;j<databaseHelper.getEventList().size();j++) {
                    if (databaseHelper.getTransactionList().get(i).eventID.equals(databaseHelper.getEventList().get(j).eventID)) {
                        myEvent.add(databaseHelper.getEventList().get(j));
                    }
                }
            }
        }

        AnotherAdapter adapt = new AnotherAdapter(getContext(), myEvent);
        myEventList.setAdapter(adapt);

        return rootView;
    }


    class AnotherAdapter extends BaseAdapter {
        Context context;
        ArrayList <Event> evList;
        public AnotherAdapter(Context context, ArrayList<Event>eve){
            this.context= context;
            this.evList=eve;
        }


        @Override
        public int getCount() {
            return evList.size();
        }

        @Override
        public Object getItem(int i) {
            return evList.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {

            if (view == null) {
                view = LayoutInflater.from(context).
                        inflate(R.layout.myeventlist, viewGroup, false);
            }

            Event currentEvent= (Event) getItem(i);
            TextView txtEventID = view.findViewById(R.id.listEventID);
            TextView txtEventName = view.findViewById(R.id.listEventName);
            TextView txtEventStart = view.findViewById(R.id.listEventStart);
            TextView txtEventEnd = view.findViewById(R.id.listEventEnd);

            txtEventID.setText(currentEvent.eventID);
            txtEventName.setText(currentEvent.eventName);
            txtEventStart.setText("Start Date : "+currentEvent.startDate);
            txtEventEnd.setText("End Date : "+currentEvent.endDate);

            return view;
        }
    }
    
}
