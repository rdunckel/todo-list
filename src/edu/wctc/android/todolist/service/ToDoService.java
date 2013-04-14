package edu.wctc.android.todolist.service;

import java.util.List;

import edu.wctc.android.todolist.model.ToDo;

public interface ToDoService {

	public abstract List<ToDo> getToDos();

	public abstract void clearToDos();

	public abstract ToDo getToDo(long id);

	public abstract ToDo getToDo(String description);

	public abstract void addToDo(String description);

	public abstract void addToDo(ToDo toDo);

	public abstract void removeToDo(long id);

	public abstract void removeToDo(String description);

}