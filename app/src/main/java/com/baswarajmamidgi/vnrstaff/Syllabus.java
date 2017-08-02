package com.baswarajmamidgi.vnrstaff;

import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import static android.content.Intent.FLAG_GRANT_READ_URI_PERMISSION;
import static android.content.Intent.FLAG_GRANT_WRITE_URI_PERMISSION;

public class Syllabus extends AppCompatActivity {
    private static final int  MEGABYTE = 1024 * 1024;
    String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_syllabus);
        SharedPreferences sharedPreferences=getSharedPreferences("info", Context.MODE_PRIVATE);


        final DatabaseReference databaseReference;
        FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
        String branch = sharedPreferences.getString("branch", null);
        String year = sharedPreferences.getString("year", null);
        String section = sharedPreferences.getString("section", null);
        if (branch == null) {
            Toast.makeText(this, "Select branch in your profile info", Toast.LENGTH_SHORT).show();
            finish();
            return;

        }
        if (year == null) {
            Toast.makeText(this, "Select year in your profile info", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }
        if (section == null) {
            Toast.makeText(this, "Select section in your profile info", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }
        final String filename = year+branch+ ".pdf";
        String path = Environment.getExternalStorageDirectory() + "/vnrvjiet/" +filename;
        File file = new File(path);
        if (file.exists()) {
            Log.i("log", "opening file");
            view(filename, "pdf");


        } else {
            Log.i("log",branch+year+section);

            ConnectivityManager manager = (ConnectivityManager) getApplicationContext().getSystemService(CONNECTIVITY_SERVICE);
            NetworkInfo info = manager.getActiveNetworkInfo();
            final boolean isconnected = info != null && info.isConnectedOrConnecting();
            if(!isconnected){
                Toast.makeText(this, "No Internet", Toast.LENGTH_SHORT).show();
            }

            databaseReference=firebaseDatabase.getReference().child("Syllabus").child(branch).child(year);
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if(dataSnapshot.exists()) {
                        url = dataSnapshot.getValue(String.class);
                        Log.i("log",url);
                    }else{
                        Toast.makeText(Syllabus.this, "File not available", Toast.LENGTH_SHORT).show();
                    }


                }
                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
            final Handler handler=new Handler();
            handler.post(new Runnable() {
                @Override
                public void run() {
                    if(url!=null) {
                        download(url,filename,"pdf");
                        Log.i("log", "downloading file");
                    }
                    else {
                        handler.post(this);
                    }
                }
            });
        }

    }
    public void download(String url,String filename,String filetype){

        ConnectivityManager manager = (ConnectivityManager) getApplicationContext().getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo info = manager.getActiveNetworkInfo();
        boolean isconnected = info != null && info.isConnectedOrConnecting();
        if (!isconnected) {
            Toast.makeText(this, "Preview not available.Connect to Internet to download the data.", Toast.LENGTH_LONG).show();
            return;
        }
        new Syllabus.Downloadpdf().execute(url,filename,filetype);

    }


    public void view(String filename,String type)
    {
        File file=new File(Environment.getExternalStorageDirectory()+"/vnrvjiet/"+filename);
        Uri path= FileProvider.getUriForFile(Syllabus.this,BuildConfig.APPLICATION_ID + ".provider",file);

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
            finish();
        }catch (ActivityNotFoundException e){
            Log.i("log",e.getLocalizedMessage());
        }


    }
    private class Downloadpdf extends AsyncTask<String, Integer, String[]> {
        ProgressDialog progressDialog;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog=new ProgressDialog(Syllabus.this);
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


}
