package no.jpg.bcvekst;

import android.app.Activity;
import android.app.Fragment;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import org.json.JSONException;


public class LoginActivity extends Activity implements ServiceConnection{

    private AppSynchronizer mService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_login);

        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }

        Intent bindIntent = new Intent(this, AppSynchronizer.class);
        bindService(bindIntent, this, BIND_AUTO_CREATE);
    }



    /**
     * A placeholder fragment containing a simple view.
     */
    public class PlaceholderFragment extends Fragment {


        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_login, container, false);


            //open the main webview
            Button userButton = (Button) rootView.findViewById(R.id.userButton);
            userButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent createUser = new Intent(LoginActivity.this, CreateUser.class);
                    createUser.putExtra("key", "value"); //Optional parameters
                    //custom animation
                    startActivity(createUser);
                }
            });

            return  rootView;
        }
    }

    @Override
    public void onServiceConnected(ComponentName componentName,
                                   IBinder iBinder) {
        mService = ((AppSynchronizer.LocalBinder) iBinder).getService();
        try {
            mService.getFileList();
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
    @Override
    public void onServiceDisconnected(ComponentName componentName) {
        mService = null;
    }


}
