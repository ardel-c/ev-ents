package com.example.ardeliachristina.events2;

public class Event {
    String eventID;
    String eventName;
    String description;
    String location;
    String startDate, endDate;
    int rating;
    double latitude, longitude;

    public Event(String eventID, String eventName, String description, String location,
                 String startDate, String endDate, int rating, double latitude, double longitude){
        this.eventID=eventID;
        this.eventName=eventName;
        this.description=description;
        this.location=location;
        this.startDate=startDate;
        this.endDate=endDate;
        this.rating=rating;
        this.latitude=latitude;
        this.longitude=longitude;
    }

}