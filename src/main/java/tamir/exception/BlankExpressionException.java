package tamir.exception;

public class BlankExpressionException extends RuntimeException {

	public BlankExpressionException() {
		super("Expression must not be blank");
	}
}
