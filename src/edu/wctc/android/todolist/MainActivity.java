package edu.wctc.android.todolist;

import java.util.List;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import edu.wctc.android.todolist.model.ToDo;
import edu.wctc.android.todolist.service.ToDoService;

public class MainActivity extends ListActivity {

	private ToDoService toDoService;
	private ArrayAdapter<ToDo> toDosAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		toDoService = new ToDoService();
		List<ToDo> toDos = toDoService.getToDos();

		toDosAdapter = new ArrayAdapter<ToDo>(this, R.layout.activity_main,
				toDos);

		setListAdapter(toDosAdapter);

		ListView listView = getListView();
		listView.setTextFilterEnabled(true);

		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// When clicked, show a toast with the TextView text
				Toast.makeText(getApplicationContext(),
						((TextView) view).getText(), Toast.LENGTH_SHORT).show();
			}
		});

		listView.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long id) {
				// When long-clicked, show a sample toast
				Log.d("DELETE", "position=" + position + ", id=" + id);
				deleteItem(id);
				Toast.makeText(getApplicationContext(),
						"Item has been deleted!", Toast.LENGTH_SHORT).show();
				return false;
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);

		menu.add(Menu.NONE, 1, Menu.NONE, "Add item");
		menu.add(Menu.NONE, 2, Menu.NONE, "Clear items");

		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {

		case 1:
			addItemDialog();
			break;
		case 2:
			clearItems();
			break;
		default:
			break;

		}

		return super.onOptionsItemSelected(item);
	}

	private void addItemDialog() {
		LayoutInflater inflater = LayoutInflater.from(this);
		final View addView = inflater.inflate(R.layout.add, null);

		new AlertDialog.Builder(this)
				.setTitle("Add Item")
				.setView(addView)
				.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
						// Retrieve what the user entered.
						EditText inputBox = (EditText) addView
								.findViewById(R.id.item);
						String description = (inputBox).getText().toString();
						addItem(description);
					}

				})
				.setNegativeButton("Cancel",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int whichButton) {
								// Cancel - do nothing.
							}
						}).show();
	}

	private void addItem(String description) {
		toDoService.addToDo(description);
		toDosAdapter.notifyDataSetChanged();

		Toast.makeText(getApplicationContext(), "Added item",
				Toast.LENGTH_SHORT).show();
	}

	private void deleteItem(long id) {
		toDoService.removeToDo(id);
		toDosAdapter.notifyDataSetChanged();
		Toast.makeText(getApplicationContext(), "Deleted item",
				Toast.LENGTH_SHORT).show();

	}

	private void clearItems() {
		toDoService.clearToDos();
		toDosAdapter.notifyDataSetChanged();
		Toast.makeText(getApplicationContext(), "Cleared items",
				Toast.LENGTH_SHORT).show();

	}

}
