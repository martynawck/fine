package com.fine.thistimeitsgonnabefine;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class FinalResultActivity extends AppCompatActivity {


    public static String KEY_EXTRA_1 = "0";
    public static String KEY_EXTRA_2 = "1";

    boolean is_happy_face = false;
    TextView style;
    ImageView emoji;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final_result);

        style = (TextView) findViewById(R.id.editText2);
        emoji = (ImageView) findViewById(R.id.imageView);
        Bundle extras = getIntent().getExtras();
        String value = "";
        boolean value2 = false;
        if (extras != null) {
            value = extras.getString(KEY_EXTRA_1);
            value2 = extras.getBoolean(KEY_EXTRA_2);
        }

        style.setText(value);
        if (value2 == false){
            emoji.setImageResource(R.drawable.unamused);
        } else {
            emoji.setImageResource(R.drawable.heart_eyes);
        }
    }
}
