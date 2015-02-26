package com.terry.verticalbardemo;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;


public class MainActivity extends ActionBarActivity {

    private VolumePopupWindow volumePopupWindow;
    private ImageButton volume;
    private float downY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init(){
        volumePopupWindow = new VolumePopupWindow(this);
        volume = (ImageButton) findViewById(R.id.volume);
//        volume.setOnClickListener(click);
        volume.setOnTouchListener(touch);
    }

    private View.OnTouchListener touch = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    downY = event.getY();
                    showVolume();
                    break;
                case MotionEvent.ACTION_MOVE:
                    if(event.getY() > downY) {
                        volumePopupWindow.seekDown();
                    } else if(event.getY() < downY){
                        volumePopupWindow.seekUp();
                    }
                    downY = event.getY();
                    break;
                case MotionEvent.ACTION_UP:
                    volumePopupWindow.dismiss();
                    break;
            }
            return false;
        }
    };


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void showVolume() {
        volumePopupWindow.showAtLocation(volume, Gravity.CENTER, 0 , -10);
    }
}
