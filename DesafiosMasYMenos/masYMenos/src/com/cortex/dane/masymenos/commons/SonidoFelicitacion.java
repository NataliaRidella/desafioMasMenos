package com.cortex.dane.masymenos.commons;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Handler;

import com.cortex.dane.masymenos.R;

public class SonidoFelicitacion extends MediaPlayer{
	
	
	public static void Felicitame(Context activity)
	{
		final MediaPlayer success = MediaPlayer.create(activity, R.raw.success);
		success.start();
		
		success.setOnCompletionListener(new OnCompletionListener() {
		    public void onCompletion(MediaPlayer mp) {
		        mp.release();

		    };
		});
	}
	
	public static void felicitameConDelay(Context pActivity, int delay)
	{
		final Context activity = pActivity;
		(new Handler()).postDelayed(new Runnable() {
			public void run() {
				SonidoFelicitacion.Felicitame(activity);
	            }
	        }, delay);
	}

}
