package no.jpg.bcvekst;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.widget.Button;
import android.widget.ImageView;

public class CreateUser extends Activity {

    private static int RESULT_LOAD_IMAGE = 1;
    private static final int PICK_FROM_GALLERY = 2;
    Bitmap thumbnail = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_user);

        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
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
            View rootView = inflater.inflate(R.layout.fragment_create_user, container, false);


            ImageView imageView = (ImageView) rootView.findViewById(R.id.imageView);
            imageView.setOnTouchListener(new View.OnTouchListener() {
                public boolean onTouch(View v, MotionEvent event) {
                    Log.i("imageView", "img_View11 _1");

                    int action = event.getAction();
                    switch (action) {

                        case MotionEvent.ACTION_DOWN:
                            Log.i("img_View11", "img_View11 _2");
                            break;

                        case MotionEvent.ACTION_MOVE:
                            Log.i("img_View11", "img_View11 _3");
                            break;

                        case MotionEvent.ACTION_UP:
                            Log.i("img_View11", "img_View11 _4");
                            // here you set what you want to do when user clicks your button,
                            // e.g. launch a new activity
                            Intent i = new Intent(
                                    Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                            startActivityForResult(i, RESULT_LOAD_IMAGE);
                            break;
                    }
                    return true;
                }
            });

            //open the main webview
            Button proceedButton = (Button) rootView.findViewById(R.id.proceedButton);
            proceedButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent webIntent = new Intent(CreateUser.this, WebviewActivity.class);
                    webIntent.putExtra("key", "value"); //Optional parameters
                    //custom animation
                    startActivity(webIntent);
                }
            });
            return rootView;
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data){

            Uri selectedImage = data.getData();
            String[] filePathColumn = { MediaStore.Images.Media.DATA };
            Cursor cursor = getContentResolver().query(selectedImage,filePathColumn, null, null, null);
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();
            thumbnail = (BitmapFactory.decodeFile(picturePath));
        }
    }



}
