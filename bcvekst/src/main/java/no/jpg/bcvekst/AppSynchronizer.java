package no.jpg.bcvekst;

import android.app.Activity;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import com.loopj.android.http.BinaryHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.commons.io.FilenameUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.net.URI;

import no.jpg.bcvekst.controllers.BCRestClient;

public class AppSynchronizer extends Service {

    private LocalBinder mLocalBinder = new LocalBinder();
    private int result = Activity.RESULT_CANCELED;
    public static final String route = "update.json";
    public static final String RESULT = "result";
    public static final String NOTIFICATION = "com.vogella.android.service.receiver";


    public AppSynchronizer() {
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return Service.START_NOT_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return  mLocalBinder;
    }

    public void doLongRunningOperation() {
// TODO Start new thread for long running operation...
    }
    public class LocalBinder extends Binder {
        public AppSynchronizer getService() {
            return AppSynchronizer.this;
        }
    }

    // download files json async
    public void getFileList() throws JSONException {

        BCRestClient.get(route, null, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(JSONObject response) {
                JSONArray fileList = null;
                try {
                    fileList = response.getJSONArray("Files");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                assert fileList != null;
                // Do something with the response
                for (int i = 0; i < fileList.length(); i++)
                    try {
                        JSONObject afile = (JSONObject) fileList.get(i);
                        System.out.println(afile.toString());

                        downloadFile(afile.getString("file"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
            }
        });
    }

    public void downloadFile(String filePath)
    {
        String fileName = FilenameUtils.getName(filePath);

        String[] allowedContentTypes = new String[] { ".*"  };
        BCRestClient.get(filePath, new BinaryHttpResponseHandler(allowedContentTypes) {
            @Override
            public void onSuccess(byte[] fileData) {
                // Do something with the file
                URI uri = getRequestURI();
                System.out.println( "new file downloaded"+uri.getPath());
                File file = new File(uri.getPath());
                if(file.exists())
                {
                    file.delete();
                }
                else
                {

                }
            }
        });
    }
}
