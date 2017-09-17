package com.fine.thistimeitsgonnabefine;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


import android.content.CursorLoader;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private Button btnChoose;

    public static String BASE_URL = "http://www.fashwell.com/api/hackzurich/v1/attributes/";
    static final int PICK_IMAGE_REQUEST = 1;
    String filePath;

    private GoogleApiClient client;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnChoose = (Button) findViewById(R.id.button_choose);

        btnChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageBrowse();
            }
        });

       /* btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (filePath != null) {
                    Log.d("log", filePath);
                    Retrofit retrofit = new Retrofit.Builder().baseUrl("http://www.fashwell.com").addConverterFactory(GsonConverterFactory.create()).build();
                    MyApi api = retrofit.create(MyApi.class);
                    HashMap<String, String> params = new HashMap<>();
                    params.put("Authorization", "Token c136d272e4f7a5bb5969437a104c063e649e75e0");
                    params.put("Accept", "application/json");
                    params.put("Content-Type", "application/x-www-form-urlencoded");
                    params.put("X-CSRFToken", "vFMaHcNgZmFfhRe0Hv7iJxsWaNVOUOSZ");

                    //  String strRequestBody = "image=@"+filePath;
                    String strRequestBody = "url=http://images.eu.christianlouboutin.com/media/catalog/product/cache/2/small_image/496x/9df78eab33525d08d6e5fb8d27136e95/3/1/7/0/christianlouboutin-twistissima-3170205_BK01_2_1200x1200_1493288276.jpg";
                    RequestBody requestBody = RequestBody.create(MediaType.parse("text/plain"),strRequestBody);
                    api.call(requestBody, params).enqueue(new Callback<MyResponse>() {
                        @Override
                        public void onResponse(Call<MyResponse> call, retrofit2.Response<MyResponse> response) {
                            Log.e("marti",response.message());
                            //  Log.e("marti",response.body().toString());
                            // Log.e("martyna", response.body().attributes.get(0).caption);
                        }

                        @Override
                        public void onFailure(Call<MyResponse> call, Throwable t) {
                            Log.e("martyna", t.getMessage());
                        }
                    });
                    //   imageUpload("http://images.eu.christianlouboutin.com/media/catalog/product/cache/2/small_image/496x/9df78eab33525d08d6e5fb8d27136e95/3/1/7/0/christianlouboutin-twistissima-3170205_BK01_2_1200x1200_1493288276.jpg");
                } else {
                    Toast.makeText(getApplicationContext(), "Image not selected!", Toast.LENGTH_LONG).show();
                }

            }
        });*/
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    private void imageBrowse() {
        Intent myIntent = new Intent(MainActivity.this, GalleryActivity.class);
        MainActivity.this.startActivity(myIntent);
      //  Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        //startActivityForResult(galleryIntent, PICK_IMAGE_REQUEST);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {

            if (requestCode == PICK_IMAGE_REQUEST) {
                Uri picUri = data.getData();

                Log.d("picUri", picUri.toString());
                //   Log.d("filePath", filePath);
                filePath = getRealPathFromURI(picUri);
                Log.d("log", filePath);
                //fath = data..toString();

              //  imageView.setImageURI(picUri);

            }

        }

    }

    public String getRealPathFromURI(Uri uri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        @SuppressWarnings("deprecation")
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        int column_index = cursor
                .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }
/*
    public void requestWithSomeHttpHeaders() {
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest postRequest = new StringRequest(Request.Method.GET, BASE_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // response
                        Log.d("Response", response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO Auto-generated method stub
                        Log.d("ERROR", "error => " + error.toString());
                    }
                }
        ) {

            @Override
            public byte[] getBody() throws AuthFailureError {
                return "url=http://images.eu.christianlouboutin.com/media/catalog/product/cache/2/small_image/496x/9df78eab33525d08d6e5fb8d27136e95/3/1/7/0/christianlouboutin-twistissima-3170205_BK01_2_1200x1200_1493288276.jpg".getBytes();
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Authorization", "Token c136d272e4f7a5bb5969437a104c063e649e75e0");
                params.put("Accept", "application/json");
                params.put("Content-Type", "application/x-www-form-urlencoded");
                params.put("X-CSRFToken", "vFMaHcNgZmFfhRe0Hv7iJxsWaNVOUOSZ");

                return params;
            }

        };
        queue.add(postRequest);

    }
*/
    /*
    private void imageUpload(final String imagePath) {

        SimpleMultiPartRequest smr = new SimpleMultiPartRequest(Request.Method.POST, BASE_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("Response", response);
                        try {
                            JSONObject jObj = new JSONObject(response);

                            String message = jObj.getString("message");

                            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();

                        } catch (JSONException e) {
                            // JSON error
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(), "Json error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            public byte[] getBody() throws AuthFailureError {
                return "url=http://images.eu.christianlouboutin.com/media/catalog/product/cache/2/small_image/496x/9df78eab33525d08d6e5fb8d27136e95/3/1/7/0/christianlouboutin-twistissima-3170205_BK01_2_1200x1200_1493288276.jpg".getBytes();
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Authorization", "Token c136d272e4f7a5bb5969437a104c063e649e75e0");
                params.put("Accept", "application/json");
                params.put("Content-Type", "application/x-www-form-urlencoded");
                params.put("X-CSRFToken", "vFMaHcNgZmFfhRe0Hv7iJxsWaNVOUOSZ");

                return params;
            }
        };

        smr.addFile("image", imagePath);


    }
*/
    // Get Path of selected image
    private String getPath(Uri contentUri) {
        String[] proj = {MediaStore.Images.Media.DATA};
        CursorLoader loader = new CursorLoader(getApplicationContext(), contentUri, proj, null, null, null);
        Cursor cursor = loader.loadInBackground();
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String result = cursor.getString(column_index);
        cursor.close();
        return result;
    }

    @Override
    public void onStart() {
        super.onStart();
/*
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.fine.fineappclient/http/host/path")
        );
//        AppIndex.AppIndexApi.start(client, viewAction);*/
    }

    @Override
    public void onStop() {
        super.onStop();
/*
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.fine.fineappclient/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();*/
    }
}