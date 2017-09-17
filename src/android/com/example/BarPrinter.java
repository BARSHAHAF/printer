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


import android.content.Context;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.os.ParcelFileDescriptor;
import android.os.StrictMode;
import android.print.PageRange;
import android.print.PrintAttributes;
import android.print.PrintDocumentAdapter;
import android.print.PrintDocumentInfo;
import android.print.PrintManager;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

import java.io.IOException;






import android.util.Log;

import java.util.Date;

public class BarPrinter extends CordovaPlugin {
  private static final String TAG = "BarPrinter";

  public void initialize(CordovaInterface cordova, CordovaWebView webView) {
    super.initialize(cordova, webView);

    
    Log.d(TAG, "Initializing BarPrinter");
  }

      private InputStream convertContentToInputStream(final String content, final String type) throws FileNotFoundException {
        InputStream input = null;
        if (type!= null && type.compareToIgnoreCase(FILE_DOC_TYPE) == 0){
            CordovaResourceApi resourceApi = webView.getResourceApi();
            Uri fileURL = resourceApi.remapUri(Uri.parse(content));
            File file = new File(fileURL.getPath());
            if (!file.exists()) {
                handlePrintError(new Exception("File does not exist"));
            } else {
                input = new FileInputStream(file);
            }
        } else {
            byte[] pdfAsBytes = Base64.decode(content, 0);
            input = new ByteArrayInputStream(pdfAsBytes);
        }
        return input;
    }
  
     private void writeInputStreamToOutput (InputStream input, FileOutputStream output) throws IOException {
        byte[] buf = new byte[1024];
        int bytesRead;

        while ((bytesRead = input.read(buf)) > 0) {
            output.write(buf, 0, bytesRead);
        }
        output.close();
    }
  
  public String PrintPdf2(String file)
  {
  
    cordova.getThreadPool().execute(new Runnable() {
            @Override
            public void run() {

                final String[] print = {"http://5.100.254.203/~promo/test.pdf"};

                PrintDocumentAdapter pda = new PrintDocumentAdapter(){



                         @Override
                    public void onWrite(PageRange[] pages, ParcelFileDescriptor destination, CancellationSignal cancellationSignal, WriteResultCallback callback) {

                        

                        InputStream input = null;
                        try {
                              input = new URL("http://5.100.254.203/~promo/test.pdf").openStream();
                        } catch (FileNotFoundException e) {
                         //   handlePrintError(e);
                        }
                        FileOutputStream output = new FileOutputStream(destination.getFileDescriptor());

                        try {
                            writeInputStreamToOutput(input, output);
                        } catch (IOException e) {
                            //handlePrintError(e);
                        }

                        callback.onWriteFinished(new PageRange[]{PageRange.ALL_PAGES});
                    }

                    @Override
                    public void onLayout(PrintAttributes oldAttributes, PrintAttributes newAttributes, CancellationSignal cancellationSignal, LayoutResultCallback callback, Bundle extras){

                        if (cancellationSignal.isCanceled()) {
                            callback.onLayoutCancelled();
                            return;
                        }


                        PrintDocumentInfo pdi = new PrintDocumentInfo.Builder("Name of file").setContentType(PrintDocumentInfo.CONTENT_TYPE_DOCUMENT).build();

                        callback.onLayoutFinished(pdi, true);
                    }
                };





             PrintManager printManager = (PrintManager) cordova.getActivity().getSystemService(Context.PRINT_SERVICE);

               //  PrintManager printManager = (PrintManager) mContext.getSystemService(Context.PRINT_SERVICE);
                String jobName = " Document";
                printManager.print(jobName, pda, null);
    
    
    
    
    
    
    


            }
        });
    
    return file+" . got It out";
}
  
  
  
  
  
  
  
  
  
  
  
  public String PrintPdf(String file)
  {
  
    
     StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        StrictMode.setThreadPolicy(policy);
    
    final String[] print = {"http://5.100.254.203/~promo/test.pdf"};

                PrintDocumentAdapter pda = new PrintDocumentAdapter(){



                    @Override
                    public void onWrite(PageRange[] pages, ParcelFileDescriptor destination, CancellationSignal cancellationSignal, WriteResultCallback callback){
                        InputStream input = null;
                        OutputStream output = null;

                        try {

                            //  input = new FileInputStream("http://5.100.254.203/~promo/test.pdf");
                            input = new URL(print[0]).openStream();
                            output = new FileOutputStream(destination.getFileDescriptor());

                            byte[] buf = new byte[1024];
                            int bytesRead;

                            while ((bytesRead = input.read(buf)) > 0) {
                                output.write(buf, 0, bytesRead);
                            }

                            callback.onWriteFinished(new PageRange[]{PageRange.ALL_PAGES});

                        } catch (FileNotFoundException ee){
                            //Catch exception
                            Log.e("bar",ee.getMessage());
                        } catch (Exception e) {
                            Log.e("bar",e.toString());
                            //Catch exception
                        } finally {

                  /*  try {
                       // input.close();
                       // output.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    */
                        }
                    }

                    @Override
                    public void onLayout(PrintAttributes oldAttributes, PrintAttributes newAttributes, CancellationSignal cancellationSignal, LayoutResultCallback callback, Bundle extras){

                        if (cancellationSignal.isCanceled()) {
                            callback.onLayoutCancelled();
                            return;
                        }


                        PrintDocumentInfo pdi = new PrintDocumentInfo.Builder("Name of file").setContentType(PrintDocumentInfo.CONTENT_TYPE_DOCUMENT).build();

                        callback.onLayoutFinished(pdi, true);
                    }
                };





             PrintManager printManager = (PrintManager) cordova.getActivity().getSystemService(Context.PRINT_SERVICE);

               //  PrintManager printManager = (PrintManager) mContext.getSystemService(Context.PRINT_SERVICE);
                String jobName = " Document";
                printManager.print(jobName, pda, null);
    
    
    
    
    
    
    
  return file+" . got It";
  
  }
  
  public boolean execute(String action, JSONArray args, final CallbackContext callbackContext) throws JSONException {
    if(action.equals("PrintPdf")) {
      String phrase = args.getString(0);
     // callbackContext.success("bar shahaf");
     
      String got =   PrintPdf2(phrase );
      
      
      final PluginResult result = new PluginResult(PluginResult.Status.OK, (got));
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
