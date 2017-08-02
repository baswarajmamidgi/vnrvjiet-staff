package com.baswarajmamidgi.vnrstaff;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Viewclubdetails extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    TextView clubinfo;
    ImageView clublogo;
    FloatingActionButton facebook;
    FloatingActionButton youtube;
    Bundle bundle;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_viewclubdetails);
        Toolbar toolbar= (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        setSupportActionBar(toolbar);
        Intent i=getIntent();
        bundle=i.getExtras();
        getSupportActionBar().setTitle(bundle.getString("name"));
        clubinfo= (TextView) findViewById(R.id.clubinfo);
        clublogo= (ImageView) findViewById(R.id.clublogo);
        facebook= (FloatingActionButton) findViewById(R.id.fb);
        youtube= (FloatingActionButton) findViewById(R.id.youtube);
        String youtubeurl=bundle.getString("youtube");
        if (youtubeurl == null ) {
            youtube.setVisibility(View.INVISIBLE);
        }
        clublogo.setImageResource(bundle.getInt("image"));
       if(java.util.Objects.equals(bundle.getString("clubinfo"), null))
        {
            ImageView imageView= (ImageView) findViewById(R.id.vjsv);
            imageView.setImageResource(R.drawable.vjsvdetails);
            imageView.setVisibility(View.VISIBLE);
            clubinfo.setVisibility(View.INVISIBLE);

        }

        clubinfo.setText(bundle.getString("clubinfo"));

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);




    }
    public void openFb(View v)
    {
        Intent i=new Intent(Intent.ACTION_VIEW,Uri.parse(bundle.getString("fblink")));
        startActivity(i);
    }
    public void openYoutube(View v)
    {
        Intent i=new Intent(Intent.ACTION_VIEW,Uri.parse(bundle.getString("youtube")));
        startActivity(i);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.overflowmenu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.help){
            startActivity(new Intent(Viewclubdetails.this, feedback.class));
        }
        return super.onOptionsItemSelected(item);

    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        final DatabaseReference databaseReference;
        FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
        SharedPreferences sharedPreferences=getSharedPreferences("info", Context.MODE_PRIVATE);
        final String[] url = new String[1];
        switch (id) {

            case R.id.home:{
                Intent i = new Intent(Viewclubdetails.this, MainActivity.class);
                startActivity(i);
                break;
            }
            case R.id.clubs: {
                Intent i = new Intent(Viewclubdetails.this, ClubsandStudentchapters.class);
                i.putExtra("activity", "clubs");
                startActivity(i);
                break;
            }
            case R.id.chapter: {
                Intent i = new Intent(Viewclubdetails.this, ClubsandStudentchapters.class);
                i.putExtra("activity", "chapters");
                startActivity(i);
                break;
            }

            case R.id.contacts: {
                Intent i = new Intent(Viewclubdetails.this, MiscContacts.class);
                startActivity(i);
                break;
            }

            case R.id.syallabus: {

                startActivity(new Intent(Viewclubdetails.this,Syllabus.class));
                break;

            }
            case R.id.timetable: {
                startActivity(new Intent(Viewclubdetails.this,Timetable.class));
                break;
            }

            case R.id.documents:{
                Toast.makeText(this, "Coming Soon", Toast.LENGTH_SHORT).show();
                break;
            }

            case R.id.website: {
                Intent i=new Intent(Intent.ACTION_VIEW,Uri.parse("http://www.vnrvjiet.ac.in/"));
                startActivity(i);
                break;


            }
            case R.id.youtube:{
                Intent i=new Intent(Intent.ACTION_VIEW,Uri.parse("https://www.youtube.com/channel/UC_-pUnKSmSBCDnI1TVBuBzA"));
                startActivity(i);
                break;
            }
            case R.id.Feedback:
            {
                Intent intent = new Intent (Viewclubdetails.this,feedback.class);
                startActivity(intent);
                break;
            }
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

        return true;

    }




    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}

