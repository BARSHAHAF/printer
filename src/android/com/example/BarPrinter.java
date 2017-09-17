/**
 */
package com.example;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaInterface;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CordovaWebView;
import org.apache.cordova.PluginResult;
import org.apache.cordova.PluginResult.Status;
import org.json.JSONObject;
import org.json.JSONArray;
import org.json.JSONException;

import android.util.Log;

import java.util.Date;

public class BarPrinter extends CordovaPlugin {
  private static final String TAG = "BarPrinter";

  public void initialize(CordovaInterface cordova, CordovaWebView webView) {
    super.initialize(cordova, webView);

    Log.d(TAG, "Initializing BarPrinter");
  }

  public boolean execute(String action, JSONArray args, final CallbackContext callbackContext) throws JSONException {
    if(action.equals("PrintPdf")) {
      String phrase = args.getString(0);
     // callbackContext.success("bar shahaf");
     
      final PluginResult result = new PluginResult(PluginResult.Status.OK, ("PrintPdf"+phrase));
      callbackContext.sendPluginResult(result);
      
      
      // Echo back the first argument
      Log.d(TAG, phrase);
    } else if(action.equals("echo")) {
      String phrase = args.getString(0);
     // callbackContext.success("bar shahaf");
     
      final PluginResult result = new PluginResult(PluginResult.Status.OK, ("lolll"));
      callbackContext.sendPluginResult(result);
      
      
      // Echo back the first argument
      Log.d(TAG, phrase);
    } else if(action.equals("getDate")) {
      // An example of returning data back to the web layer
     // return new PluginResult("ok", "result");
      
      //callbackContext.success("bar shahaf");
    }
    return true;
  }

}
