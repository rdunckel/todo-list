package edu.wctc.android.todolist.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import edu.wctc.android.todolist.model.ToDo;

public class ToDoListService implements ToDoService {

	private List<ToDo> toDos;
	private long nextAvailableId;

	public ToDoListService() {
		nextAvailableId = 1;
		toDos = new ArrayList<ToDo>();
		setupTestData();
	}

	@Override
	public List<ToDo> getToDos() {
		return toDos;
	}

	@Override
	public void clearToDos() {
		toDos.clear();

	}

	@Override
	public ToDo getToDo(long id) {
		return getToDos().get(Long.valueOf(id).intValue());
	}

	@Override
	public ToDo getToDo(String description) {
		ToDo toDoFound = null;
		for (ToDo toDo : toDos) {
			if (description.equals(toDo.getDescription())) {
				toDoFound = toDo;
			}
		}
		return toDoFound;
	}

	@Override
	public void addToDo(String description) {
		ToDo toDo = new ToDo(nextAvailableId++, description);
		toDos.add(toDo);
	}

	@Override
	public void addToDo(ToDo toDo) {
		if (toDo.getId() < nextAvailableId) {
			toDo.setId(nextAvailableId++);
		}
		toDos.add(toDo);
	}

	@Override
	public void removeToDo(long id) {
		if (id < toDos.size()) {
			toDos.remove(Long.valueOf(id).intValue());
		}
	}

	private void setupTestData() {
		ToDo[] toDoArray = new ToDo[] {
				new ToDo.Builder("Do the laundry").build(),
				new ToDo.Builder("Wash the dishes").build(),
				new ToDo.Builder("Mow the lawn").build() };

		List<ToDo> toDos = Arrays.asList(toDoArray);

		for (ToDo toDo : toDos) {
			addToDo(toDo);
		}

	}

	@Override
	public void removeToDo(String description) {
		// TODO Auto-generated method stub

	}
}
