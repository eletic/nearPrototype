package com.example.android.nearprototype;

import android.location.Location;

import java.util.UUID;
/**
 * Created by home on 3/2/17.
 */
public class userStory
{
    private static userStory ourInstance = new userStory();
    
    public static userStory getInstance()
    {
        return ourInstance;
    }
    
    private userStory()
    {
        userID = UUID.randomUUID().toString();
        userLatitude = null;
        userLongitude = null;
    }

    public String getUserID()
    {
        return userID;
    }

    public String getUserLatitude()
    {
        return userLatitude;
    }

    public String getUserLongitude()
    {
        return userLongitude;
    }

    public String getUserTitle()
    {
        return userTitle;
    }

    public String getUserDescription()
    {
        return userDescription;
    }

    public void setUserTitle(String userTitle)
    {
        this.userTitle = userTitle;
    }

    public void setUserDescription(String userDescription)
    {
        this.userDescription = userDescription;
    }

    public void setUserMediaFile(String userMediaFile)
    {
        this.userMediaFile = userMediaFile;
    }

    public String getUserMediaFile()
    {
        return userMediaFile;
    }

    public void setLocation(Location userLoc)
    {
        userLatitude = String.valueOf(userLoc.getLatitude());
        userLongitude = String.valueOf(userLoc.getLongitude());
    }

    private String userID;
    private String userLatitude;
    private String userLongitude;
    private String userTitle;
    private String userDescription;
    private String userMediaFile;
}
