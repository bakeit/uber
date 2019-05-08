/*
 * Copyright (c) 2015-present, Parse, LLC.
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree. An additional grant
 * of patent rights can be found in the PATENTS file in the same directory.
 */
package com.parse.starter;


import android.app.Application;
import android.util.Log;

import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;


public class StarterApplication extends Application {

  @Override
  public void onCreate() {
    super.onCreate();

    Parse.enableLocalDatastore(this);

    Parse.initialize(new Parse.Configuration.Builder(getApplicationContext())
                    .applicationId("c043eb5cb6060094357a0e9bbc0b7b5adbd05954")
                    .clientKey("b8967d640bbadab48c28bfff1d3a7f957b4be8c0")
                    .server("http://18.191.159.24:80/parse/")
                    .build()
    );


    ParseUser.enableAutomaticUser();
    ParseACL defaultACL = new ParseACL();

    ParseACL.setDefaultACL(defaultACL, true);
  }
}
