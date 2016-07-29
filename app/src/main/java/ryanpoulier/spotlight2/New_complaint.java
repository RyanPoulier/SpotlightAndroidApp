package ryanpoulier.spotlight2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

public class New_complaint extends AppCompatActivity {
    ListView lst_Issue_Type;
    String issuetype;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_complaint);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.mipmap.home_icon);

        lst_Issue_Type= (ListView)findViewById(R.id.lv_issue_type);

        lst_Issue_Type.setOnItemClickListener(
                new AdapterView.OnItemClickListener(){
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        issuetype=String.valueOf(parent.getItemAtPosition(position));
                        storeIssueType();
                    }

                                    }
        );
    }

    public void OpenNewComplaintLocation (View view){
        Intent intent=new Intent (this,New_Complaint_Location.class);
        startActivity(intent);
    }

    public void storeIssueType () {

        SharedPreferences prefs = getSharedPreferences("issuetype", MODE_WORLD_READABLE);

        SharedPreferences.Editor editor=prefs.edit();
        editor.putString("issuetype", issuetype);
        editor.apply();

        //Toast.makeText (New_complaint.this, issuetype, Toast.LENGTH_LONG).show();

    }

    public void EmeOpenNewComplaintSuggest (View view){
        Intent intent=new Intent (this,New_Complaint_Suggest.class);
        startActivity(intent);
    }

}
