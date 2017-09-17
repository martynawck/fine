package com.fine.thistimeitsgonnabefine;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.util.Pair;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.gson.JsonObject;

import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class GalleryActivity extends AppCompatActivity {

    ImageButton imageButton, imageButton2, imageButton3, imageButton4, imageButton5;
    /**
     * Whether or not the system UI should be auto-hidden after
     * {@link #AUTO_HIDE_DELAY_MILLIS} milliseconds.
     */


    private static final boolean AUTO_HIDE = true;

    /**
     * If {@link #AUTO_HIDE} is set, the number of milliseconds to wait after
     * user interaction before hiding the system UI.
     */
    private static final int AUTO_HIDE_DELAY_MILLIS = 3000;

    /**
     * Some older devices needs a small delay between UI widget updates
     * and a change of the status and navigation bar.
     */
    private static final int UI_ANIMATION_DELAY = 300;
    private final Handler mHideHandler = new Handler();
    private View mContentView;
    private final Runnable mHidePart2Runnable = new Runnable() {
        @SuppressLint("InlinedApi")
        @Override
        public void run() {
            // Delayed removal of status and navigation bar

            // Note that some of these constants are new as of API 16 (Jelly Bean)
            // and API 19 (KitKat). It is safe to use them, as they are inlined
            // at compile-time and do nothing on earlier devices.
            mContentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                    | View.SYSTEM_UI_FLAG_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        }
    };
    private View mControlsView;
    private final Runnable mShowPart2Runnable = new Runnable() {
        @Override
        public void run() {
            // Delayed display of UI elements
            ActionBar actionBar = getSupportActionBar();
            if (actionBar != null) {
                actionBar.show();
            }
            mControlsView.setVisibility(View.VISIBLE);
        }
    };
    private boolean mVisible;
    private final Runnable mHideRunnable = new Runnable() {
        @Override
        public void run() {
            hide();
        }
    };
    /**
     * Touch listener to use for in-layout UI controls to delay hiding the
     * system UI. This is to prevent the jarring behavior of controls going away
     * while interacting with activity UI.
     */
    private final View.OnTouchListener mDelayHideTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (AUTO_HIDE) {
                delayedHide(AUTO_HIDE_DELAY_MILLIS);
            }
            return false;
        }
    };
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_gallery);

        mVisible = true;
        mControlsView = findViewById(R.id.fullscreen_content_controls);
        mContentView = findViewById(R.id.fullscreen_content);

        imageButton = (ImageButton) findViewById(R.id.imageButton);
        imageButton2 = (ImageButton) findViewById(R.id.imageButton2);
        imageButton3 = (ImageButton) findViewById(R.id.imageButton3);
        imageButton4 = (ImageButton) findViewById(R.id.imageButton4);
        imageButton5 = (ImageButton) findViewById(R.id.imageButton5);

        // Set up the user interaction to manually show or hide the system UI.
        mContentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggle();
            }
        });

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                runButton("https://i.pinimg.com/236x/87/b6/3b/87b63b529b2d79234931d0f6d9f49fe7--womens-jeans-skinny-jeans.jpg");
            }
        });

        imageButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                runButton("https://i.pinimg.com/originals/7e/d0/a3/7ed0a3fc9269566cbcf0cd78dd07d81b.jpg");
                }
        });

        imageButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               runButton("https://i.pinimg.com/736x/9d/6a/d3/9d6ad31ef6b465eb815b536eeb41fee2--converse-outfits-leggings-and-white-converse-outfit.jpg");
            }
        });

        imageButton4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                runButton("https://i.pinimg.com/736x/55/ca/b9/55cab9a435655dff821ed908633f15cf--comfy-school-outfits-cute-casual-outfits.jpg");
            }
        });

        imageButton5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                runButton("https://i.pinimg.com/736x/26/2c/4a/262c4a4ac38d9013234f21c7324bdad8--summer-preppy-outfits-casual-preppy-summer-fashion.jpg");
            }
        });

        // Upon interacting with UI controls, delay any scheduled hide()
        // operations to prevent the jarring behavior of controls going away
        // while interacting with the UI.
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    public void runButton (String file) {
        Retrofit retrofit = new Retrofit.Builder().baseUrl("http://www.fashwell.com").addConverterFactory(GsonConverterFactory.create()).build();
        MyApi api = retrofit.create(MyApi.class);
        HashMap<String, String> params = new HashMap<>();
        params.put("Authorization", "Token c136d272e4f7a5bb5969437a104c063e649e75e0");
        params.put("Accept", "application/json");
        params.put("Content-Type", "application/x-www-form-urlencoded");
        params.put("X-CSRFToken", "vFMaHcNgZmFfhRe0Hv7iJxsWaNVOUOSZ");

        //  String strRequestBody = "image=@"+filePath;
        String strRequestBody = "url="+file;//https://i.pinimg.com/236x/87/b6/3b/87b63b529b2d79234931d0f6d9f49fe7--womens-jeans-skinny-jeans.jpg";
        RequestBody requestBody = RequestBody.create(MediaType.parse("text/plain"), strRequestBody);
        final MyResponse myResponse;
        api.call(requestBody, params).enqueue(new Callback<MyResponse>() {
            @Override
            public void onResponse(Call<MyResponse> call, Response<MyResponse> response) {
               // myResponse = response;
                HashMap<String, Pair<Integer, Double>> keymap = new HashMap<>();
                for (MyAttributes attr : response.body().attributes) {
                    JsonObject json = attr.attributes.aesthetic;
                    //Log.d("attr",at2.toString());

                    String[] listOfNames = new String[] {"street", "bohemian", "classic", "edgy","athletic", "minimalistic", "feminine", "heritage"};

                    for (String name : listOfNames){
                        keymap = addJsonToHashTable(name, json, keymap);
                    }
                }

                HashMap<String,Double> probabilities = new HashMap<String, Double>();
                for (String aes : keymap.keySet()){
                    Pair<Integer,Double> p = keymap.get(aes);
                    probabilities.put(aes, p.second/p.first);
                }

                Map.Entry<String,Double> maxEntry = null;

                for(Map.Entry<String,Double> entry : probabilities.entrySet()) {
                    if (maxEntry == null || entry.getValue() > maxEntry.getValue()) {
                        maxEntry = entry;
                    }
                }

                boolean is_emoji_good = false;
                if (maxEntry.getKey() == "minimalistic" || maxEntry.getKey() == "heritage" || maxEntry.getKey() == "edgy")
                    is_emoji_good = false;
                else
                    is_emoji_good = true;
               // return maxEntry.getKey();

                Log.d("max",maxEntry.getKey());
                Intent intent = new Intent(getApplicationContext(), FinalResultActivity.class);
                intent.putExtra(FinalResultActivity.KEY_EXTRA_1, maxEntry.getKey());
                intent.putExtra(FinalResultActivity.KEY_EXTRA_2, is_emoji_good);
                startActivity(intent);
            }

            @Override
            public void onFailure(Call<MyResponse> call, Throwable t) {
          //      Log.e("martyna", t.getMessage());
            }
        });
        //return maxEntry.getKey();
    }

    public HashMap addJsonToHashTable(String name, JsonObject jsonObject,  HashMap<String, Pair<Integer, Double>> keymap) {
        if (name != null && jsonObject != null && jsonObject.has(name)) {
            if (keymap.containsKey(name)) {
                Pair<Integer, Double> hm = keymap.get(name);
                int newFirst = hm.first + 1;
                double newSecond = hm.second + Double.parseDouble(jsonObject.get(name).toString());
                Pair<Integer, Double> p = new Pair(newFirst, newSecond);
                keymap.put(name, p);

            } else {
               // Log.d("json", jsonObject.get("street").toString());
                Pair<Integer, Double> hm = new Pair(1, Double.parseDouble(jsonObject.get(name).toString()));
                keymap.put(name, hm);
            }
        }

        return keymap;
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        // Trigger the initial hide() shortly after the activity has been
        // created, to briefly hint to the user that UI controls
        // are available.
        delayedHide(100);
    }

    private void toggle() {
        if (mVisible) {
            hide();
        } else {
            show();
        }
    }

    private void hide() {
        // Hide UI first
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        mControlsView.setVisibility(View.GONE);
        mVisible = false;

        // Schedule a runnable to remove the status and navigation bar after a delay
        mHideHandler.removeCallbacks(mShowPart2Runnable);
        mHideHandler.postDelayed(mHidePart2Runnable, UI_ANIMATION_DELAY);
    }

    @SuppressLint("InlinedApi")
    private void show() {
        // Show the system bar
        mContentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);
        mVisible = true;

        // Schedule a runnable to display UI elements after a delay
        mHideHandler.removeCallbacks(mHidePart2Runnable);
        mHideHandler.postDelayed(mShowPart2Runnable, UI_ANIMATION_DELAY);
    }

    /**
     * Schedules a call to hide() in [delay] milliseconds, canceling any
     * previously scheduled calls.
     */
    private void delayedHide(int delayMillis) {
        mHideHandler.removeCallbacks(mHideRunnable);
        mHideHandler.postDelayed(mHideRunnable, delayMillis);
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Gallery Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.fine.thistimeitsgonnabefine/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Gallery Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.fine.thistimeitsgonnabefine/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }
}
