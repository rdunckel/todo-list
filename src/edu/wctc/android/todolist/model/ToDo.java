package edu.wctc.android.todolist.model;

public class ToDo {

	private long id;
	private String description;
	private String owner = "";

	public static class Builder {
		private String description;

		private long id = 0;
		private String owner = "";

		public Builder(String description) {
			this.description = description;
		}

		public Builder id(long val) {
			id = val;
			return this;
		}

		public Builder owner(String val) {
			owner = val;
			return this;
		}

		public ToDo build() {
			return new ToDo(this);
		}

	}

	private ToDo(Builder builder) {
		description = builder.description;
		id = builder.id;
		owner = builder.owner;
	}

	public ToDo(long id, String description) {
		this.id = id;
		this.description = description;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	@Override
	public String toString() {
		return description;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ToDo other = (ToDo) obj;
		if (id != other.id)
			return false;
		return true;
	}

}
