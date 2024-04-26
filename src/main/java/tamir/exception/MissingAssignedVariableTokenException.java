package tamir.exception;

public class MissingAssignedVariableTokenException extends RuntimeException {

	public MissingAssignedVariableTokenException(String invalidToken) {
		super("Assignment expression must contain assigned variable as first token, but received: " + invalidToken);
	}
}
