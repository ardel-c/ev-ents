package com.example.ardeliachristina.events2;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class FavoriteEventTab extends Fragment{

    DatabaseHelper databaseHelper;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.favorite, container, false);
        ListView listFav;
        listFav = rootView.findViewById(R.id.listFav);

        ArrayList<Event> eventSorted= new ArrayList<Event>();
        eventSorted.clear();
        databaseHelper = new DatabaseHelper(getContext());
        eventSorted.addAll(databaseHelper.getEventList());
        Collections.sort(eventSorted, new Comparator<Event>(){
            public int compare(Event ev1, Event ev2){
                if(ev1.rating == ev2.rating)
                    return 0;
                return ev1.rating > ev2.rating ? -1 : 1;
            }
        });

        CusAdapter adapt = new CusAdapter(getContext(), eventSorted);
        listFav.setAdapter(adapt);
        return rootView;
    }

    class CusAdapter extends BaseAdapter {
        Context context;
        ArrayList <Event> evList;
        public CusAdapter(Context context, ArrayList<Event>eve){
            this.context= context;
            this.evList=eve;
        }


        @Override
        public int getCount() {
            return 3;
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
