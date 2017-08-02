package com.baswarajmamidgi.vnrstaff;

import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Rect;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.NavigationView;
import android.support.v4.content.FileProvider;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static android.content.Intent.FLAG_GRANT_READ_URI_PERMISSION;
import static android.content.Intent.FLAG_GRANT_WRITE_URI_PERMISSION;

public class ClubsandStudentchapters extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private RecyclerView recyclerView;
    private List<Carddetails> list;
    private Cardadapter adapter;
    private String activity;
    private static final int  MEGABYTE = 1024 * 1024;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_clubsandstudentchapters);
        Toolbar toolbar= (Toolbar) findViewById(R.id.toolbar);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        setSupportActionBar(toolbar);


        activity=getIntent().getStringExtra("activity");
        getSupportActionBar().setTitle(activity);

        list=new ArrayList<>();
        recyclerView= (RecyclerView) findViewById(R.id.recycler_view);
        adapter=new Cardadapter(this,list);
        RecyclerView.LayoutManager layoutManager=new GridLayoutManager(this,2);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new ClubsandStudentchapters.GridSpacingItemDecoration(2, dpToPx(10), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
        prepareCategories();


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

    }
    private void prepareCategories() {
        if(activity.equals("clubs")) {
            int[] images = new int[]{R.drawable.arts, R.drawable.cres, R.drawable.drama, R.drawable.edcell, R.drawable.live,R.drawable.nss, R.drawable.scintilate, R.drawable.stento,R.drawable.robotics, R.drawable.teatro, R.drawable.vjsv, R.drawable.vnrsf,R.drawable.teamrandd};

            Carddetails a = new Carddetails("Creative arts", images[0]);
            list.add(a);
            a = new Carddetails("Crescendo", images[1]);
            list.add(a);
            a = new Carddetails("Dramatrix", images[2]);
            list.add(a);
            a = new Carddetails("ED cell", images[3]);
            list.add(a);
            a = new Carddetails("Live wire", images[4]);
            list.add(a);
            a = new Carddetails("NSS", images[5]);
            list.add(a);
            a = new Carddetails("Scintillate", images[6]);
            list.add(a);
            a = new Carddetails("Stentorian", images[7]);
            list.add(a);
            a = new Carddetails("Team R & D", images[12]);
            list.add(a);
            a = new Carddetails("Team Robotics", images[8]);
            list.add(a);
            a = new Carddetails("VJ Teatro", images[9]);
            list.add(a);
            a = new Carddetails("VJSV", images[10]);
            list.add(a);
            a = new Carddetails("VNR SF", images[11]);
            list.add(a);
        }
        else
        {
            int[] images = new int[]{R.drawable.asme,R.drawable.csi, R.drawable.ieee, R.drawable.iei, R.drawable.iste,R.drawable.tedx,R.drawable.isoi};

            Carddetails a = new Carddetails("ASME", images[0]);
            list.add(a);
            a = new Carddetails("CSI", images[1]);
            list.add(a);
            a = new Carddetails("IEEE", images[2]);
            list.add(a);
            a = new Carddetails("IEI", images[3]);
            list.add(a);
            a = new Carddetails("ISTE", images[4]);
            list.add(a);
            a = new Carddetails("TED x", images[5]);
            list.add(a);
            a = new Carddetails("ISOI", images[6]);
            list.add(a);


        }


        adapter.notifyDataSetChanged();
    }
    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position % spanCount; // item column

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }
    }

    /**
     * Converting dp to pixel
     */
    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.overflowmenu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.help){
            startActivity(new Intent(ClubsandStudentchapters.this, feedback.class));
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
                Intent i = new Intent(ClubsandStudentchapters.this, MainActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(i);
                finishAffinity();
                break;
            }
            case R.id.clubs: {
                Intent i = new Intent(ClubsandStudentchapters.this, ClubsandStudentchapters.class);
                i.putExtra("activity", "clubs");
                startActivity(i);
                break;
            }
            case R.id.chapter: {
                Intent i = new Intent(ClubsandStudentchapters.this, ClubsandStudentchapters.class);
                i.putExtra("activity", "chapters");
                startActivity(i);
                break;
            }

            case R.id.contacts: {
                Intent i = new Intent(ClubsandStudentchapters.this, MiscContacts.class);
                startActivity(i);
                break;
            }

            case R.id.syallabus: {

                startActivity(new Intent(ClubsandStudentchapters.this,Syllabus.class));
                break;

            }
            case R.id.timetable: {


                startActivity(new Intent(ClubsandStudentchapters.this,Timetable.class));
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
                Intent intent = new Intent (ClubsandStudentchapters.this,feedback.class);
                startActivity(intent);
                break;
            }
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

        return true;

    }



    public void download(String url,String filename,String filetype){

        ConnectivityManager manager = (ConnectivityManager) getApplicationContext().getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo info = manager.getActiveNetworkInfo();
        boolean isconnected = info != null && info.isConnectedOrConnecting();
        if (!isconnected) {
            Toast.makeText(this, "Preview not available.Connect to Internet to download the data.", Toast.LENGTH_LONG).show();
            return;
        }
        new ClubsandStudentchapters.Downloadpdf().execute(url,filename,filetype);

    }

    public void view(String filename,String type) {
        File file=new File(Environment.getExternalStorageDirectory()+"/vnrvjiet/"+filename);
        Uri path= FileProvider.getUriForFile(getApplicationContext(),BuildConfig.APPLICATION_ID + ".provider",file);

        Intent pdfintent=new Intent(Intent.ACTION_VIEW);
        if(type.equals("pdf")) {
            pdfintent.setDataAndType(path,"application/pdf");
        }
        else{
            pdfintent.setDataAndType(path,"image/*");
        }
        pdfintent.setFlags(FLAG_GRANT_READ_URI_PERMISSION | FLAG_GRANT_WRITE_URI_PERMISSION); //must for reading data from directory
        try {
            startActivity(pdfintent);
        }catch (ActivityNotFoundException e){
            Log.i("log",e.getLocalizedMessage());
        }


    }

    private class Downloadpdf extends AsyncTask<String, Integer, String[]> {
        ProgressDialog progressDialog;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog=new ProgressDialog(getApplicationContext());
            progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            progressDialog.setIndeterminate(false);
            progressDialog.setMax(100);
            progressDialog.setMessage("Downloading file...");
            progressDialog.show();

        }

        @Override
        protected  String[] doInBackground(String... strings) {

            String url = strings[0];
            String filename = strings[1];
            String extstoragedir = Environment.getExternalStorageDirectory().toString();
            File folder = new File(extstoragedir, "vnrvjiet");
            boolean bool = folder.mkdir();

            File file = new File(folder, filename);
            try {
                boolean state = file.createNewFile();
            } catch (IOException e) {
                Log.i("log", e.getLocalizedMessage());
                e.printStackTrace();
            }
            int count;
            try {
                Log.i("log","file downloader called");
                URL contenturl=new URL(url);
                HttpURLConnection httpURLConnection= (HttpURLConnection) contenturl.openConnection();
                httpURLConnection.connect();
                InputStream inputStream=httpURLConnection.getInputStream();
                FileOutputStream outputStream=new FileOutputStream(file);
                int totalsize=httpURLConnection.getContentLength();
                Log.i("log","length of file" +String.valueOf(totalsize));
                byte[] buffer= new byte[MEGABYTE];
                int bufferlength=0;
                while((count=inputStream.read(buffer))>0){
                    bufferlength+=count;
                    publishProgress((int) ( (bufferlength / (float)totalsize)*100));
                    outputStream.write(buffer,0,count);
                }
                inputStream.close();
                outputStream.flush();
                outputStream.close();


            } catch (MalformedURLException e) {
                Log.i("loge",e.getLocalizedMessage());
                e.printStackTrace();
            } catch (IOException e) {
                Log.i("loge",e.getLocalizedMessage());
                e.printStackTrace();
            }


            String[] data=new String[]{filename,strings[2]};
            return data;


        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);

            progressDialog.setProgress(values[0]);
        }

        @Override
        protected void onPostExecute(String file[]) {
            view(file[0], file[1]);
            progressDialog.dismiss();

        }
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
