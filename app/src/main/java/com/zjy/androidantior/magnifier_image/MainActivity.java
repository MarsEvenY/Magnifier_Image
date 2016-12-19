package com.zjy.androidantior.magnifier_image;

import android.app.Activity;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.widget.FrameLayout;

public class MainActivity extends Activity {


    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.activity_main);

        FrameLayout frameLayout = (FrameLayout) findViewById(R.id.framelayout1);
        frameLayout.addView(new MyView(this));

    }


}
