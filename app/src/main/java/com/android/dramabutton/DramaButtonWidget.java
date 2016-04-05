package com.android.dramabutton;

import java.util.HashMap;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.util.Log;
import android.view.View;
import android.widget.RemoteViews;
import android.widget.Toast;
 
public class DramaButtonWidget extends AppWidgetProvider {
 
	public static String ACTION_WIDGET_RECEIVER = "ActionReceiverWidget";
	private MediaPlayer mMediaPlayer = null;
    public static int CURRENT_SOUND =  R.raw.dramabutton;
 
	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
 
		RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget);
 
		Intent active = new Intent(context, DramaButtonWidget.class);
		active.setAction(ACTION_WIDGET_RECEIVER);
		active.putExtra("msg", "Drama Daily Count: ");
		//when you will click button1 the message "Message for Button 1" will appear as a notification
		//you can do whatever you want anyway on the press of this button
		PendingIntent actionPendingIntent = PendingIntent.getBroadcast(context, 0, active, 2);
		remoteViews.setOnClickPendingIntent(R.id.dramaButton, actionPendingIntent);
		
 
		appWidgetManager.updateAppWidget(appWidgetIds, remoteViews);
		context.getSharedPreferences("widget", 0).edit().putInt("clicks", 0).commit();
	}
 
	@Override
	public void onReceive(final Context context, Intent intent) {
		//this takes care of managing the widget
		// v1.5 fix that doesn't call onDelete Action
		
		final String action = intent.getAction();
		if (AppWidgetManager.ACTION_APPWIDGET_DELETED.equals(action)) {
			final int appWidgetId = intent.getExtras().getInt(
					AppWidgetManager.EXTRA_APPWIDGET_ID,
					AppWidgetManager.INVALID_APPWIDGET_ID);
			if (appWidgetId != AppWidgetManager.INVALID_APPWIDGET_ID) {
				this.onDeleted(context, new int[] { appWidgetId });
			}
		} else {
			// check, if our Action was called
			if (intent.getAction().equals(ACTION_WIDGET_RECEIVER)) {
				String msg = "null";
				//Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
				try {
					msg = intent.getStringExtra("msg");
				} catch (NullPointerException e) {
					Log.e("Error", "msg = null");
				}
				// check to see if media player is going. Stops it and releases if so. 
				if (mMediaPlayer != null) {
	        		//stops running sound
	                mMediaPlayer.stop();
	                //prepares media with out sound file
	                mMediaPlayer = MediaPlayer.create( context, CURRENT_SOUND);
		             //tell media players to start the sound.
		            mMediaPlayer.start();
	                }
	        	else {
		        	//prepares media with out sound file
		            mMediaPlayer = MediaPlayer.create( context, CURRENT_SOUND);
		             //tell media players to start the sound.
		            mMediaPlayer.start();
	        	}
				//Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
				//remoteViews.setInt(R.id.dramaButton, "setImageResource", R.drawable.pressed);
				int clickCount = context.getSharedPreferences("widget", Context.MODE_PRIVATE).getInt("clicks", 0);
			    context.getSharedPreferences("widget", Context.MODE_PRIVATE).edit().putInt("clicks", ++clickCount).commit();
				
		         
				PendingIntent contentIntent = PendingIntent.getActivity(context, 0, intent, 0);
				NotificationManager notificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
				Notification noty = new Notification(R.drawable.ic_launcher, "Drama's Daily", System.currentTimeMillis());
 
				noty.setLatestEventInfo(context, "Drama Daily", msg + clickCount, contentIntent);
				notificationManager.notify(1, noty);
 
			}
			else {
				// do nothing
			}
 
			super.onReceive(context, intent);
		}
	}
}