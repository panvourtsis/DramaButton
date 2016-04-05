package com.android.dramabutton;

import android.content.Context;


public class Counter {
	
	public void Counter (final Context context) {
	
	int clickCount = context.getSharedPreferences("widget", Context.MODE_PRIVATE).getInt("clicks", 0);
    context.getSharedPreferences("widget", Context.MODE_PRIVATE).edit().putInt("clicks", ++clickCount).commit();
	}
}
