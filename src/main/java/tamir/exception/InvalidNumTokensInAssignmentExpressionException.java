package tamir.exception;

import java.util.List;

public class InvalidNumTokensInAssignmentExpressionException extends RuntimeException {

	public InvalidNumTokensInAssignmentExpressionException(List<String> tokens) {
		super("Assignment expression must contain at least 3 tokens, but received: " + tokens);
	}
}
