package com.godlife.goaldomain.exception;

import java.util.NoSuchElementException;

public class NoSuchGoalException extends NoSuchElementException {
	public NoSuchGoalException() {
		super("Not found Goal");
	}
}
