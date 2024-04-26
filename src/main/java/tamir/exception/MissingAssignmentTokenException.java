package tamir.exception;

public class MissingAssignmentTokenException extends RuntimeException {

	public MissingAssignmentTokenException(String invalidToken) {
		super("Assignment expression must contain assignment token, but received: " + invalidToken);
	}
}
