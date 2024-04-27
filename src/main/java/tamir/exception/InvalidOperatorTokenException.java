package tamir.exception;

public class InvalidOperatorTokenException extends RuntimeException {

	public InvalidOperatorTokenException(String invalidToken) {
		super("The following token is not an operator: " + invalidToken);
	}
}
