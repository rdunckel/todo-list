package edu.wctc.android.todolist;

import java.util.List;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.os.Bundle;
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
import edu.wctc.android.todolist.service.ToDoDatabaseService;
import edu.wctc.android.todolist.service.ToDoService;

public class MainActivity extends ListActivity {

	private ToDoService toDoService;
	private ArrayAdapter<ToDo> toDosAdapter;
	private List<ToDo> toDos;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// toDoService = new ToDoListService();
		toDoService = new ToDoDatabaseService();
		toDos = toDoService.getToDos();

		toDosAdapter = new ArrayAdapter<ToDo>(this, R.layout.activity_main,
				toDos);
		
		setListAdapter(toDosAdapter);

		ListView listView = getListView();
		listView.setTextFilterEnabled(true);

		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				showDetailDialog(view, id);
			}
		});

		listView.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long id) {
				deleteItem(view, id);
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
			showAddItemDialog();
			break;
		case 2:
			clearItems();
			break;
		default:
			break;

		}

		return super.onOptionsItemSelected(item);
	}

	private void showDetailDialog(View view, long id) {
		ToDo toDo = toDoService.getToDo(((TextView) view).getText().toString());

		LayoutInflater inflater = LayoutInflater.from(this);
		final View detailView = inflater.inflate(R.layout.detail, null);

		((TextView) detailView.findViewById(R.id.itemId)).setText(String
				.valueOf(toDo.getId()));
		((TextView) detailView.findViewById(R.id.itemDescription)).setText(toDo
				.getDescription());
		((TextView) detailView.findViewById(R.id.itemOwner)).setText(toDo
				.getOwner());

		new AlertDialog.Builder(this).setTitle("Item Details")
				.setView(detailView).setPositiveButton("Ok", null).show();
	}

	private void showAddItemDialog() {
		LayoutInflater inflater = LayoutInflater.from(this);
		final View addView = inflater.inflate(R.layout.add, null);

		new AlertDialog.Builder(this)
				.setTitle("Add Item")
				.setView(addView)
				.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

					public void onClick(DialogInterface dialog, int whichButton) {
						String description = ((EditText) addView
								.findViewById(R.id.itemDescription)).getText()
								.toString();
						String owner = ((EditText) addView
								.findViewById(R.id.itemOwner)).getText()
								.toString();
						// addItem(description);
						addItem(new ToDo.Builder(description).owner(owner)
								.build());
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

	@SuppressWarnings("unused")
	private void addItem(String description) {
		toDoService.addToDo(description);
		toDos = toDoService.getToDos();
		toDosAdapter.notifyDataSetChanged();

		Toast.makeText(getApplicationContext(), "Added item",
				Toast.LENGTH_SHORT).show();
	}

	private void addItem(ToDo toDo) {
		toDoService.addToDo(toDo);
		toDos = toDoService.getToDos();
		toDosAdapter.notifyDataSetChanged();

		Toast.makeText(getApplicationContext(), "Added item",
				Toast.LENGTH_SHORT).show();
	}

	private void deleteItem(View view, long id) {
		String description = ((TextView) view).getText().toString();
		toDoService.removeToDo(description);
		toDos = toDoService.getToDos();
		toDosAdapter.notifyDataSetChanged();
		Toast.makeText(getApplicationContext(), "Deleted item",
				Toast.LENGTH_SHORT).show();

	}

	private void clearItems() {
		toDoService.clearToDos();
		toDos = toDoService.getToDos();
		toDosAdapter.notifyDataSetChanged();
		Toast.makeText(getApplicationContext(), "Cleared items",
				Toast.LENGTH_SHORT).show();

	}

}
