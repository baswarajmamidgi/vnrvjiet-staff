package com.baswarajmamidgi.vnrstaff;

import android.app.Dialog;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.weiwangcn.betterspinner.library.BetterSpinner;


public class Timetable extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private String selectedbranch;
    private String selectedyear;
    private String selectedsection;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_timetable);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Time Table");
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
             ConnectivityManager manager = (ConnectivityManager) getApplicationContext().getSystemService(CONNECTIVITY_SERVICE);
            NetworkInfo info = manager.getActiveNetworkInfo();
            final boolean isconnected = info != null && info.isConnectedOrConnecting();
            if(!isconnected){
                Toast.makeText(this, "No Internet", Toast.LENGTH_SHORT).show();
                return;
            }
        final String[] branches=new String[]{"AE","CE","CSE","ECE","EEE","EIE","IT","ME"};
        final  String[] sections=new String[]{"1","2","3","4"};
        final String[] year=new String[]{"1","2","3","4"};

        Button submit= (Button) findViewById(R.id.submit);
        final EditText rollno= (EditText)findViewById(R.id.rollno);
        final BetterSpinner branchspinner= (BetterSpinner) findViewById(R.id.branch);
        final BetterSpinner sectionsspinner= (BetterSpinner) findViewById(R.id.section);
        final BetterSpinner yearspinner= (BetterSpinner) findViewById(R.id.year);



        final ArrayAdapter<String> branchadapter=new ArrayAdapter<String>(this,android.R.layout.simple_dropdown_item_1line,branches);
        branchspinner.setAdapter(branchadapter);


        final ArrayAdapter<String> yearadapter=new ArrayAdapter<String>(this,android.R.layout.simple_dropdown_item_1line,year);
        yearspinner.setAdapter(yearadapter);

        final ArrayAdapter<String> sectionsadapter=new ArrayAdapter<String>(this,android.R.layout.simple_dropdown_item_1line,sections);
        sectionsspinner.setAdapter(sectionsadapter);



        branchspinner.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedbranch= branches[position];

            }
        });


        yearspinner.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedyear= year[position];


            }
        });

        sectionsspinner.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedsection =sections[position];

            }
        });



        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (selectedyear == null) {
                    Toast.makeText(Timetable.this, "select year", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (selectedbranch == null) {
                    Toast.makeText(Timetable.this, "select branch", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (selectedsection == null) {
                    Toast.makeText(Timetable.this, "select section", Toast.LENGTH_SHORT).show();
                    return;
                }

                loadimage(selectedbranch, selectedyear, selectedsection);
            }


            private void loadimage(String branch, String year, String section) {
                final Dialog dialog = new Dialog(Timetable.this, R.style.Theme_Dialog);
                dialog.setContentView(R.layout.imageview);
                final ImageView imageView = (ImageView) dialog.findViewById(R.id.imageView);
                ImageView cancel = (ImageView) dialog.findViewById(R.id.cancel);
                dialog.show();

                final DatabaseReference databaseReference;
                final FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();

                databaseReference = firebaseDatabase.getReference().child("Timetable").child(branch).child(year).child(section);
                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            String url = dataSnapshot.getValue(String.class);

                            Glide.with(Timetable.this).load(url)
                                    .placeholder(R.drawable.loading)
                                    .error(R.drawable.noimage)
                                    .into(imageView);

                        } else {
                            imageView.setImageResource(R.drawable.noimage);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });

                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.overflowmenu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.help){
            startActivity(new Intent(Timetable.this, feedback.class));
        }
        return super.onOptionsItemSelected(item);

    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.home:{
                Intent i = new Intent(Timetable.this, MainActivity.class);
                startActivity(i);
                break;
            }
            case R.id.clubs: {
                Intent i = new Intent(Timetable.this, ClubsandStudentchapters.class);
                i.putExtra("activity", "clubs");
                startActivity(i);
                break;
            }
            case R.id.chapter: {
                Intent i = new Intent(Timetable.this, ClubsandStudentchapters.class);
                i.putExtra("activity", "chapters");
                startActivity(i);
                break;
            }

            case R.id.contacts: {
                Intent i = new Intent(Timetable.this, MiscContacts.class);
                startActivity(i);
                break;
            }

            case R.id.syallabus: {

                startActivity(new Intent(Timetable.this,Syllabus.class));
                break;

            }

            case R.id.documents:{
                Toast.makeText(this, "Coming Soon", Toast.LENGTH_SHORT).show();
                break;
            }

            case R.id.website: {
                Intent i=new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.vnrvjiet.ac.in/"));
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
                Intent intent = new Intent (Timetable.this,feedback.class);
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
