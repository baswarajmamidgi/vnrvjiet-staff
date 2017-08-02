package com.baswarajmamidgi.vnrstaff;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Workshopslist extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    WorkShopAdapter adapter;
    private DatabaseReference mDatabase;
    RecyclerView recyclerView;
    List<Workshopclass> list;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_workshop);
        Toolbar toolbar= (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Events & Workshops");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        setSupportActionBar(toolbar);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        mDatabase=database.getReference().child("workshops");
        ConnectivityManager manager = (ConnectivityManager) getApplicationContext().getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo info = manager.getActiveNetworkInfo();
        final boolean isconnected = info != null && info.isConnectedOrConnecting();
        final ProgressDialog progressDialog=new ProgressDialog(Workshopslist.this);
        progressDialog.setMessage("Loading data...");
        progressDialog.show();
        recyclerView= (RecyclerView) findViewById(R.id.recycler_view);
        list=new ArrayList<>();
        adapter=new WorkShopAdapter(this,list);
        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);


        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postsnapshot : dataSnapshot.getChildren()) {
                    Map<String, String> map = (Map)postsnapshot.getValue();
                    Workshopclass workshopclass = new Workshopclass(map.get("name"), map.get("duration"), map.get("contactno"), map.get("registrationfee"), map.get("imageurl"));
                    list.add(workshopclass);
                    adapter.notifyDataSetChanged();

                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        recyclerView.setAdapter(adapter);

        final Handler handler=new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                if(!list.isEmpty()){
                    progressDialog.dismiss();
                }
                handler.post(this);

            }
        });

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(adapter.getItemCount()==0){
                    if(!isconnected) {
                        Toast.makeText(Workshopslist.this, "No Internet", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                        startActivity(new Intent(Workshopslist.this,MainActivity.class));
                        finishAffinity();
                    }

                }
            }
        },3000);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);




    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.overflowmenu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.help){
            startActivity(new Intent(Workshopslist.this, feedback.class));
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
                Intent i = new Intent(Workshopslist.this, MainActivity.class);
                startActivity(i);
                break;
            }
            case R.id.clubs: {
                Intent i = new Intent(Workshopslist.this, ClubsandStudentchapters.class);
                i.putExtra("activity", "clubs");
                startActivity(i);
                break;
            }
            case R.id.chapter: {
                Intent i = new Intent(Workshopslist.this, ClubsandStudentchapters.class);
                i.putExtra("activity", "chapters");
                startActivity(i);
                break;
            }

            case R.id.contacts: {
                Intent i = new Intent(Workshopslist.this, MiscContacts.class);
                startActivity(i);
                break;
            }


            case R.id.timetable: {

                startActivity(new Intent(Workshopslist.this,Timetable.class));
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
                Intent intent = new Intent (Workshopslist.this,feedback.class);
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
