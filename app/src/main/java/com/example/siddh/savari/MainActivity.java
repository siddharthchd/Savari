package com.example.siddh.savari;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Switch;

import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseAnonymousUtils;
import com.parse.ParseException;
import com.parse.ParseUser;

public class MainActivity extends AppCompatActivity {

    public void redirectActivity() {

        if (ParseUser.getCurrentUser().get("rideOrDrive") == "rider") {

            Intent intent = new Intent(getApplicationContext(), RiderActivity.class);
            startActivity(intent);

        }

    }

    public void getStarted(View view) {

        Switch userSwitch = (Switch) findViewById(R.id.userSwitch);
        Log.i("Switch user", String.valueOf(userSwitch.isChecked()));

        String userType = "rider";
        if (userSwitch.isChecked()) {

            userType = "driver";

        }
        ParseUser.getCurrentUser().put("rideOrDrive", userType);

        Log.i("Info", "Redirecting as " + userType);
        redirectActivity();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Parse.initialize(new Parse.Configuration.Builder(getApplicationContext())
                .applicationId("53e0d5c4bdbe64d4318735ff5908c1b94d5a4bdf")
                .clientKey("506049fa877a24d3b3fd8431bfaec5affbb9a95f")
                .server("http://ec2-52-14-8-255.us-east-2.compute.amazonaws.com:80/parse/")
                .build()
        );

        if (ParseUser.getCurrentUser() == null) {

            ParseAnonymousUtils.logIn(new LogInCallback() {
                @Override
                public void done(ParseUser user, ParseException e) {

                    if (e == null) {

                        Log.i("Info", "Anonymous log in successful");

                    } else {

                        Log.i("Info", "Anonymous log in failed");

                    }

                }
            });

        }

        //ParseUser.enableAutomaticUser();
        ParseACL defaultACL = new ParseACL();
        defaultACL.setPublicReadAccess(true);
        defaultACL.setPublicWriteAccess(true);
        ParseACL.setDefaultACL(defaultACL, true);
    }
}
