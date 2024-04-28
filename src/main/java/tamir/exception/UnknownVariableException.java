package tamir.exception;

public class UnknownVariableException extends RuntimeException {

	public UnknownVariableException(String invalidVariable) {
		super("Unknown variable: " + invalidVariable);
	}
}
