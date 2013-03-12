package edu.wctc.android.todolist.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import edu.wctc.android.todolist.model.ToDo;

public class ToDoService {

	private List<ToDo> toDos;

	public ToDoService() {
		buildToDos();
	}

	public List<ToDo> getToDos() {
		return toDos;
	}

	public void setToDos(List<ToDo> toDos) {
		this.toDos = toDos;
	}

	public void addToDo(String description) {
		ToDo toDo = new ToDo(toDos.size() + 1, description);
		toDos.add(toDo);
	}

	public void addToDo(ToDo toDo) {
		toDos.add(toDo);
	}

	public void removeToDo(long id) {
		if (id < toDos.size()) {
			toDos.remove(new ToDo(id, ""));
		}
	}

	public void clearToDos() {
		toDos.clear();

	}

	private void buildToDos() {
		ToDo[] toDoArray = { new ToDo(1, "Do the laundry"),
				new ToDo(2, "Wash the dishes"), new ToDo(3, "Mow the lawn") };
		toDos = new ArrayList<ToDo>(Arrays.asList(toDoArray));
	}

}
