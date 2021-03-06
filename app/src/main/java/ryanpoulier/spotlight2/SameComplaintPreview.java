package ryanpoulier.spotlight2;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Date;

/**
 * Created by ASUS on 07/03/2016.
 */
public class SameComplaintPreview extends AppCompatActivity {

    TextView descriptionpreview, gpsCoordinatespreview, issuetypepreview, titlepreview,dateview;
    ImageView image1;
    VideoView video1;
    String title_issue_type, title_location;

    Context context= this;
    Button Submit_Complaint;
    DBhelper DBhelper;
    SQLiteDatabase sqLiteDatabase;
    Uri URI, vidURI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_same_complaint_preview);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.mipmap.home_icon);
        descriptionpreview = (TextView) findViewById(R.id.txtDesc_Preview);
        gpsCoordinatespreview= (TextView) findViewById(R.id.txtCoordinates);
        issuetypepreview= (TextView) findViewById(R.id.txtIssueType);
        titlepreview= (TextView) findViewById(R.id.txtTitle);
        Submit_Complaint = (Button) findViewById(R.id.btn_Complaint_Submit);
        dateview= (TextView) findViewById(R.id.txtDate);
        image1= (ImageView) findViewById(R.id.imgView_Preview_Image_1);
        video1 = (VideoView) findViewById(R.id.videoViewSame);
        //Integer idpreview = (TextView) findViewById(R.id.txtComplaintId);

        displayCurrentTime();
        displayDataDescription();
        displayDataLocation();
        displayDataIssueType();
        displayDataTitle();
        displayImages();
        displayVideo();
    }

    // code from https://www.youtube.com/watch?v=8byyh8Lb_xc&index=3&list=FLsCn-tnRZVHIyKOq7o6b36Q
    private void displayCurrentTime() {
        Date currentTime= new Date();
        String date_text= currentTime.toString();
        dateview.setText(date_text);
    }

    private void displayDataDescription() {
        SharedPreferences samepref = getSharedPreferences("desc", MODE_WORLD_READABLE);
        String desc= samepref.getString("description", "no data test");
        descriptionpreview.setText(desc);
    }

    private void displayDataLocation() {
        SharedPreferences prefs = getSharedPreferences("gpsCoordinates", MODE_WORLD_READABLE);
        String coordinates= prefs.getString("gpsCoordinates", "no data test");
        gpsCoordinatespreview.setText(coordinates);
        title_location= coordinates;
    }

    private void displayDataIssueType(){
        SharedPreferences prefs = getSharedPreferences("issuetype", MODE_WORLD_READABLE);
        String issuetype= prefs.getString("issuetype", "no data test");
        issuetypepreview.setText(issuetype);
        title_issue_type= issuetype;

    }
    private void displayDataTitle(){
        titlepreview.setText(title_issue_type + " @ " + title_location);
    }

    private void displayImages() {
        SharedPreferences samepref = getSharedPreferences("photo", MODE_WORLD_READABLE);
        String photolocation= samepref.getString("photo", "no data test");
        URI = Uri.parse(photolocation);

        InputStream inputStream;
        try {
            inputStream=getContentResolver().openInputStream(URI);
            Bitmap image = BitmapFactory.decodeStream(inputStream);
            image1.setImageBitmap(image);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void displayVideo() {
        SharedPreferences samepref = getSharedPreferences("video", MODE_WORLD_READABLE);
        String videolocation= samepref.getString("video", "no data test");

        vidURI = Uri.parse(videolocation);
        video1.setVideoURI(vidURI);
        video1.seekTo(1000);
    }

    // based on https://www.youtube.com/watch?v=T0ClYrJukPA
    public void SaveSameComplaint(View view) {
        SubmissionMessages();
    }

    public void SubmissionMessages () {
        AlertDialog.Builder builder = new AlertDialog.Builder(SameComplaintPreview.this);
        builder.setTitle("Alert");
        builder.setMessage("Are you sure you want to submit this updated complaint?");

        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                AfterSubmissionMessage();
            }
        });

        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

            }
        });

        AlertDialog alert = builder.create();
        alert.show();

    }

    public void AfterSubmissionMessage (){
        AlertDialog.Builder builder = new AlertDialog.Builder(SameComplaintPreview.this);

        builder.setTitle("Success!");
        builder.setMessage("Complaint was updated successfully. You will also receive a confirmation via SMS shortly");
        builder.setCancelable(false);
        builder.setNeutralButton("OK",new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Intent intent = new Intent(SameComplaintPreview.this, Home.class);
                startActivity(intent);
                dialog.cancel();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }

}
