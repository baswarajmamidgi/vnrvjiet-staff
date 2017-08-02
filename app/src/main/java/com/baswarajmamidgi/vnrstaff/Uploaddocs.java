package com.baswarajmamidgi.vnrstaff;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.weiwangcn.betterspinner.library.BetterSpinner;

import java.io.File;
import java.io.IOException;

public class Uploaddocs extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private StorageReference mStorageRef;
    private static final int gallery=12;
    private Uri uploadfileuri;
    ImageView imageView;
    private DatabaseReference databaseReference;
    private FirebaseDatabase firebaseDatabase;
    private String uploadfilename;
    private String selectedbranch;
    private String selectedyear;
    private String selectedsection;
    private TextView filename;
    private String category;
    private static int count=1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_uploaddocs);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        filename= (TextView) findViewById(R.id.filename);
        Button selectfile= (Button) findViewById(R.id.select);
        imageView= (ImageView) findViewById(R.id.uploadimage);
        Button submit= (Button) findViewById(R.id.submit);
        final EditText rollno= (EditText)findViewById(R.id.rollno);
        final BetterSpinner branchspinner= (BetterSpinner) findViewById(R.id.branch);
        final BetterSpinner sectionsspinner= (BetterSpinner) findViewById(R.id.section);
        final BetterSpinner yearspinner= (BetterSpinner) findViewById(R.id.year);
        final String type;

        Intent i=getIntent();
        category=i.getStringExtra("category");
        Log.i("log",category);
        if(category.equals("Timetable")) {
            toolbar.setTitle("Upload Timetable");
            type="*/*";
        }else{
            toolbar.setTitle("Update documents");
            type="*/*";


        }
        setSupportActionBar(toolbar);


        mStorageRef = FirebaseStorage.getInstance(com.google.firebase.FirebaseApp.initializeApp(Uploaddocs.this)).getReference();
        firebaseDatabase=FirebaseDatabase.getInstance();

        selectfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(Intent.ACTION_GET_CONTENT);
                i.setType(type);
                startActivityForResult(Intent.createChooser(i,"select file") ,gallery);
            }
        });


        final String[] branches=new String[]{"AE","CE","CSE","ECE","EEE","EIE","IT","ME"};
        final  String[] sections=new String[]{"1","2","3","4"};
        final String[] year=new String[]{"1","2","3","4"};

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



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);



    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == gallery && resultCode == RESULT_OK && data != null) {
            uploadfileuri = data.getData();
            File file = new File(uploadfileuri.getPath());
            String type=getfileext(uploadfileuri);
            if (type.equals("jpg") || file.toString().contains("png")) {
                try {

                    Bitmap bmp = MediaStore.Images.Media.getBitmap(getContentResolver(), uploadfileuri);
                    imageView.setImageBitmap(bmp);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            Uri returnUri = data.getData();
            Cursor returnCursor =
                    getContentResolver().query(returnUri, null, null, null, null);

            int nameIndex = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
            int sizeIndex = returnCursor.getColumnIndex(OpenableColumns.SIZE);
            returnCursor.moveToFirst();
            uploadfilename=returnCursor.getString(nameIndex);
            filename.setText(uploadfilename);


        }
    }
    public String getfileext(Uri uri)
    {
        ContentResolver contentResolver=getContentResolver();
        MimeTypeMap mimeTypeMap=MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));

    }

    @SuppressWarnings("VisibleForTests")
    public void buttonupload(View v)
    {


        ConnectivityManager manager= (ConnectivityManager) getApplicationContext().getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo info = manager.getActiveNetworkInfo();
        final boolean isconnected = info != null && info.isConnectedOrConnecting();
        if(!isconnected)
        {
            Toast.makeText(this, "No Internet", Toast.LENGTH_SHORT).show();
            return;
        }
        if (selectedyear == null) {
            Toast.makeText(Uploaddocs.this, "select year", Toast.LENGTH_SHORT).show();
            return;
        }
        if (selectedbranch == null) {
            Toast.makeText(Uploaddocs.this, "select branch", Toast.LENGTH_SHORT).show();
            return;
        }

        if (selectedsection == null) {
            Toast.makeText(Uploaddocs.this, "select section", Toast.LENGTH_SHORT).show();
            return;
        }

        if(uploadfileuri!=null)
        {
            final ProgressDialog progressDialog=new ProgressDialog(Uploaddocs.this);
            progressDialog.setTitle("uploading file");
            progressDialog.show();
            StorageReference storageReference=mStorageRef.child("documents/"+uploadfilename);
            storageReference.putFile(uploadfileuri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    progressDialog.dismiss();
                    Toast.makeText(Uploaddocs.this, "doc uploaded", Toast.LENGTH_SHORT).show();
                    Fileupload fileupload=new Fileupload(uploadfilename,taskSnapshot.getDownloadUrl().toString());
                    if(category.equals("Timetable") ){
                        databaseReference = firebaseDatabase.getReference().child(category).child(selectedbranch).child(selectedyear).child(selectedsection);
                        databaseReference.setValue(taskSnapshot.getDownloadUrl().toString());
                        Log.i("log","timetable");

                    }else {
                        databaseReference = firebaseDatabase.getReference().child(category).child(selectedbranch).child(selectedyear).child(selectedsection).child(String.valueOf(count));
                        databaseReference.setValue(fileupload);
                        count++;

                    }
                    finish();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(Uploaddocs.this, "doc uploading failed", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                    Log.i("log",e.getLocalizedMessage());

                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @SuppressWarnings("VisibleForTests")
                @Override
                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                double progress=(100*taskSnapshot.getBytesTransferred())/taskSnapshot.getTotalByteCount();
                    progressDialog.setMessage("uploaded   "+(int)progress+"");
                }
            });
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.documents_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.timetable){
            Intent i=new Intent(Uploaddocs.this, Uploaddocs.class);
            i.putExtra("category","Timetable");
            startActivity(i);

        }

        if(item.getItemId()==R.id.help){
            startActivity(new Intent(Uploaddocs.this, feedback.class));
        }
        return super.onOptionsItemSelected(item);

    }


    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.home:{
                Intent i = new Intent(Uploaddocs.this, MainActivity.class);
                startActivity(i);
                break;
            }
            case R.id.clubs: {
                Intent i = new Intent(Uploaddocs.this, ClubsandStudentchapters.class);
                i.putExtra("activity", "clubs");
                startActivity(i);
                break;
            }
            case R.id.chapter: {
                Intent i = new Intent(Uploaddocs.this, ClubsandStudentchapters.class);
                i.putExtra("activity", "chapters");
                startActivity(i);
                break;
            }

            case R.id.contacts: {
                Intent i = new Intent(Uploaddocs.this, MiscContacts.class);
                startActivity(i);
                break;
            }

            case R.id.syallabus: {

                startActivity(new Intent(Uploaddocs.this,Syllabus.class));
                break;

            }
            case R.id.timetable: {

                startActivity(new Intent(Uploaddocs.this,Timetable.class));
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
                Intent intent = new Intent (Uploaddocs.this,feedback.class);
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
