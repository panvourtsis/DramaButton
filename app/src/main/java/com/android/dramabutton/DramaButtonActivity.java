package com.android.dramabutton;

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ImageView;

public class DramaButtonActivity extends Activity implements OnTouchListener {
    /** Called when the activity is first created. */
	private MediaPlayer mMediaPlayer = null;
	public static int CURRENT_SOUND =  R.raw.dramabutton;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
                
        ImageView DramaButton = (ImageView) findViewById(R.id.dramaButton);
        DramaButton.setOnTouchListener(this);
    }
    
    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
    	ImageView DramaButton = (ImageView) findViewById(R.id.dramaButton);
		if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
        	DramaButton.setImageResource(R.drawable.pressed);
        		//checks if sound runing
	        	if (mMediaPlayer != null) {
	        		//stops running sound
	                mMediaPlayer.stop();
	                //prepares media with out sound file
	                mMediaPlayer = MediaPlayer.create( getBaseContext(), CURRENT_SOUND);
		             //tell media players to start the sound.
		            mMediaPlayer.start();
	                }
	        	else {
		        	//prepares media with out sound file
		            mMediaPlayer = MediaPlayer.create( getBaseContext(), CURRENT_SOUND);
		             //tell media players to start the sound.
		            mMediaPlayer.start();
	        	}
        	return true;
		} else if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
        	DramaButton.setImageResource(R.drawable.unpressed);
            return true;
		}
        return false;   
    }
}