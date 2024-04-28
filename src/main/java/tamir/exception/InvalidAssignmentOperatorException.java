package tamir.exception;

import tamir.parser.operator.AssignmentOperator;

import java.util.Arrays;

public class InvalidAssignmentOperatorException extends RuntimeException {

	public InvalidAssignmentOperatorException(String invalidToken) {
		super(String.format("Supported assignment tokens are: %s, but the following is invalid: %s",
				Arrays.toString(AssignmentOperator.values()), invalidToken));
	}
}
