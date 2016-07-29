package ryanpoulier.spotlight2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.VideoView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class New_Complaint_Video extends AppCompatActivity {

    public static final int VIDEO_GALLERY_REQUEST = 20;

    String pictureDirectoryPath;
    VideoView view_Video_1;
    Uri videoUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new__complaint__video);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.mipmap.home_icon);

        view_Video_1 = (VideoView) findViewById(R.id.vidview_Video_1);
    }

    //code from https://www.youtube.com/watch?v=wBuWqqBWziU&list=FLsCn-tnRZVHIyKOq7o6b36Q&index=1
    public void  OpenVideoGallery (View v) {
        //using an implicit intent
        Intent videoPickerIntent= new Intent (Intent.ACTION_PICK);

        File pictureDirectory= Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        pictureDirectoryPath= pictureDirectory.getPath();

        Uri data= Uri.parse (pictureDirectoryPath);

        videoPickerIntent.setType("video/*");

        startActivityForResult(videoPickerIntent, VIDEO_GALLERY_REQUEST); //refactored
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode==RESULT_OK) {
            if (requestCode== VIDEO_GALLERY_REQUEST) {
                videoUri = data.getData();
                view_Video_1.setVideoURI(videoUri);
                view_Video_1.start();

            }
        }
    }

     public void OpenNewComplaintDescription (View view){
        storeVideoURI ();
        Intent intent=new Intent (this,New_Complaint_Description.class);
        startActivity(intent);
    }

    public void storeVideoURI () {

        SharedPreferences prefs = getSharedPreferences("video", MODE_WORLD_READABLE);

        SharedPreferences.Editor editor=prefs.edit();
        editor.putString("video", videoUri.toString());
        editor.apply();

        //Toast.makeText (this, "Video URI Saved", Toast.LENGTH_LONG).show();

    }
}

