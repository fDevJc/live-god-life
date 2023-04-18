package com.godlife.goaldomain.exception;

import java.util.NoSuchElementException;

public class NoSuchTodoException extends NoSuchElementException {
	public NoSuchTodoException() {
		super("Not found Todos");
	}
}
