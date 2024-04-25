package tamir.exception;

public class InvalidTokenException extends RuntimeException {

	public InvalidTokenException(String unknownToken) {
		super("Invalid token: " + unknownToken);
	}
}
