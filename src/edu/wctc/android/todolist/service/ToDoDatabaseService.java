package edu.wctc.android.todolist.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;
import android.util.Log;
import edu.wctc.android.todolist.ToDoList;
import edu.wctc.android.todolist.model.ToDo;

public class ToDoDatabaseService implements ToDoService {

	private long nextAvailableId;
	private ToDoDatabaseHelper db;

	public ToDoDatabaseService() {
		nextAvailableId = 1;
		db = new ToDoDatabaseHelper(ToDoList.getAppContext());
		setupTestData();
	}

	public List<ToDo> getToDos() {
		Cursor toDoCursor = db.getReadableDatabase().query(
				ToDoDatabaseHelper.TODO_TABLE_NAME, null, null, null, null,
				null, null);

		List<ToDo> toDos = new ArrayList<ToDo>();

		while (toDoCursor.moveToNext()) {
			long id = toDoCursor.getLong(toDoCursor
					.getColumnIndex(ToDoDatabaseHelper.TODO_ID_KEY));

			String description = toDoCursor.getString(toDoCursor
					.getColumnIndex(ToDoDatabaseHelper.TODO_DESCRIPTION_KEY));

			String owner = toDoCursor.getString(toDoCursor
					.getColumnIndex(ToDoDatabaseHelper.TODO_OWNER_KEY));

			ToDo toDo = new ToDo.Builder(description).id(id).owner(owner)
					.build();
			toDos.add(toDo);
		}

		return toDos;
	}

	public void clearToDos() {
		db.getWritableDatabase().delete(ToDoDatabaseHelper.TODO_TABLE_NAME,
				null, null);
	}

	public ToDo getToDo(long id) {
		Cursor toDoCursor = db.getReadableDatabase().query(
				ToDoDatabaseHelper.TODO_TABLE_NAME, null,
				ToDoDatabaseHelper.TODO_ID_KEY + "=" + id, null, null, null,
				null);

		ToDo toDo = null;

		if (toDoCursor.moveToFirst()) {
			String description = toDoCursor.getString(toDoCursor
					.getColumnIndex(ToDoDatabaseHelper.TODO_DESCRIPTION_KEY));

			String owner = toDoCursor.getString(toDoCursor
					.getColumnIndex(ToDoDatabaseHelper.TODO_OWNER_KEY));

			toDo = new ToDo.Builder(description).id(id).owner(owner).build();
		}

		return toDo;
	}

	@Override
	public ToDo getToDo(String description) {
		Cursor toDoCursor = db.getReadableDatabase().query(
				ToDoDatabaseHelper.TODO_TABLE_NAME, null,
				ToDoDatabaseHelper.TODO_DESCRIPTION_KEY + "=?",
				new String[] { description }, null, null, null);

		ToDo toDo = null;

		if (toDoCursor.moveToFirst()) {
			long id = toDoCursor.getLong(toDoCursor
					.getColumnIndex(ToDoDatabaseHelper.TODO_ID_KEY));

			String owner = toDoCursor.getString(toDoCursor
					.getColumnIndex(ToDoDatabaseHelper.TODO_OWNER_KEY));

			toDo = new ToDo.Builder(description).id(id).owner(owner).build();
		}

		return toDo;
	}

	public void addToDo(String description) {
		ToDo toDo = new ToDo(nextAvailableId++, description);
		addToDo(toDo);
	}

	public void addToDo(ToDo toDo) {
		if (toDo.getId() < nextAvailableId) {
			toDo.setId(nextAvailableId++);
		}

		ContentValues content = new ContentValues();
		content.put(ToDoDatabaseHelper.TODO_ID_KEY, toDo.getId());
		content.put(ToDoDatabaseHelper.TODO_DESCRIPTION_KEY,
				toDo.getDescription());
		content.put(ToDoDatabaseHelper.TODO_OWNER_KEY, toDo.getOwner());

		db.getWritableDatabase().insert(ToDoDatabaseHelper.TODO_TABLE_NAME,
				null, content);
	}

	public void removeToDo(long id) {
		db.getWritableDatabase().delete(ToDoDatabaseHelper.TODO_TABLE_NAME,
				ToDoDatabaseHelper.TODO_ID_KEY + "=" + id, null);
		logRecords();
	}

	public void removeToDo(String description) {
		logRecords();
		db.getWritableDatabase().delete(ToDoDatabaseHelper.TODO_TABLE_NAME,
				ToDoDatabaseHelper.TODO_DESCRIPTION_KEY + "=?",
				new String[] { description });
		logRecords();
	}

	private void setupTestData() {
		clearToDos();
		ToDo[] toDoArray = new ToDo[] {
				new ToDo.Builder("Do the laundry").build(),
				new ToDo.Builder("Wash the dishes").build(),
				new ToDo.Builder("Mow the lawn").build() };

		List<ToDo> toDos = Arrays.asList(toDoArray);

		for (ToDo toDo : toDos) {
			addToDo(toDo);
		}

		logRecords();
	}

	private void logRecords() {
		Cursor todoCursor = db.getReadableDatabase().query(
				ToDoDatabaseHelper.TODO_TABLE_NAME, null, null, null, null,
				null, null);

		while (todoCursor.moveToNext()) {
			Log.d("DESCRIPTION", todoCursor.getString(todoCursor
					.getColumnIndex(ToDoDatabaseHelper.TODO_DESCRIPTION_KEY)));
			Log.d("OWNER", todoCursor.getString(todoCursor
					.getColumnIndex(ToDoDatabaseHelper.TODO_OWNER_KEY)));
		}
	}
}
