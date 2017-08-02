package com.baswarajmamidgi.vnrstaff;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
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

import java.util.ArrayList;
import java.util.List;

/**
 * Created by baswarajmamidgi on 16/03/17.
 */

public class MiscContacts extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    private RecyclerView recyclerView;
    private List<Carddetails> list;
    private ContactsAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_misccontacts);
        Toolbar toolbar= (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        toolbar.setTitle("Important Contacts");
        setSupportActionBar(toolbar);

        list=new ArrayList<>();
        recyclerView= (RecyclerView) findViewById(R.id.recycler_view);
        adapter=new ContactsAdapter(MiscContacts.this,list);
        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        prepareCategories();

        recyclerView.setAdapter(adapter);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);




    }
    private void prepareCategories() {

            Carddetails a = new Carddetails("Girls Hostel", "Shailaja","Hostel incharge","9989478011","shailaha@yahoo.in");
            list.add(a);
            a = new Carddetails("Boys Hostel", "Naredra","Academic vice dean","9989478011","shailaha@yahoo.in");
            list.add(a);

        }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.overflowmenu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.help){
            startActivity(new Intent(MiscContacts.this, feedback.class));
        }
        return super.onOptionsItemSelected(item);

    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {

            case R.id.home:{
                Intent i = new Intent(MiscContacts.this, MainActivity.class);
                startActivity(i);
                break;
            }
            case R.id.clubs: {
                Intent i = new Intent(MiscContacts.this, ClubsandStudentchapters.class);
                i.putExtra("activity", "clubs");
                startActivity(i);
                break;
            }
            case R.id.chapter: {
                Intent i = new Intent(MiscContacts.this, ClubsandStudentchapters.class);
                i.putExtra("activity", "chapters");
                startActivity(i);
                break;
            }

            case R.id.contacts: {
                Intent i = new Intent(MiscContacts.this, MiscContacts.class);
                startActivity(i);
                break;
            }

            case R.id.syallabus: {

                startActivity(new Intent(MiscContacts.this,Syllabus.class));
                break;

            }
            case R.id.timetable: {
                startActivity(new Intent(MiscContacts.this,Timetable.class));
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
                Intent intent = new Intent (MiscContacts.this,feedback.class);
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
