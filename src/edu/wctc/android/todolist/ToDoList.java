package edu.wctc.android.todolist;

import android.app.Application;
import android.content.Context;

public class ToDoList extends Application {
	private static Context context;

	public void onCreate() {
		super.onCreate();
		ToDoList.context = getApplicationContext();
	}

	public static Context getAppContext() {
		return ToDoList.context;
	}
}