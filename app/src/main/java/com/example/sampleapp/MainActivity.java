package com.example.sampleapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private static final int MY_REQUEST_PERMISSION = 100;
    private int PICK_FILE_FROM_STORAGE_REQUEST = 1;
    Uri fileUri;
    String filepath = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, MY_REQUEST_PERMISSION);
        }
        Button uploadButton = (Button) findViewById(R.id.button);
        uploadButton.setOnClickListener(new View.OnClickListener( ) {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("files/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,"select file"),PICK_FILE_FROM_STORAGE_REQUEST);

            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_FILE_FROM_STORAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null){
            Uri uri = data.getData();
            UploadFile(uri);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case MY_REQUEST_PERMISSION:{
                if (grantResults.length >0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            }else {

                }return;
        }

    }

//   private void UploadFile(Uri fileUri) {
//           Retrofit.Builder builder = new Retrofit.Builder().baseUrl().addConverterFactory(GsonConverterFactory.create());
//            Retrofit retrofit = builder.build();
//            UserClient client = retrofit.create(UserClient.class);
//           Call<ResponseBody> call = null;
//           call.enqueue(new Callback<ResponseBody>( ) {
//               @Override
//               public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//
//               }
//
//               @Override
//               public void onFailure(Call<ResponseBody> call, Throwable t) {
//
//               }
//           });
//       }

    }

    private void UploadFile(Uri uri) {
        File file = new File(filepath);
        Retrofit retrofit = NetworkClient.getRetrofit();
        RequestBody requestBody = RequestBody.create(MediaType.parse("file/"),file);
        MultipartBody.Part parts = MultipartBody.Part.createFormData("newFile", file.getName(),requestBody);

        RequestBody somedata = RequestBody.create(MediaType.parse("text/plain"),"This is a new File");


        UserClient userClient = retrofit.create(UserClient.class);
        Call call = userClient.uploadImage(parts,somedata);
        call.enqueue(new Callback( ) {
            @Override
            public void onResponse(Call call, Response response) {

            }

            @Override
            public void onFailure(Call call, Throwable t) {

            }
        });

    }
}