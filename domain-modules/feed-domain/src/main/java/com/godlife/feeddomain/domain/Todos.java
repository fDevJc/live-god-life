package com.godlife.feeddomain.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class Todos {
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "parent_todo_id")
	private final List<Todo> childTodos = new ArrayList<>();

	public Todos(List<Todo> childTodos) {
		this.childTodos.addAll(childTodos);
	}

	public List<Todo> get() {
		return Collections.unmodifiableList(childTodos);
	}

	public int getTotalTodoTaskCount() {
		int count = 0;
		for (Todo todo : childTodos) {
			if (todo instanceof TodoTask) {
				count++;
			} else {
				count += ((TodoFolder)todo).getChildTodos().get().size();
			}
		}
		return count;
	}
}
