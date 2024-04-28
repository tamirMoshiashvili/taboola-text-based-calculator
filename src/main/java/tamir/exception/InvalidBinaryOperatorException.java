package tamir.exception;

import tamir.parser.operator.BinaryOperator;

import java.util.Arrays;

public class InvalidBinaryOperatorException extends RuntimeException {

	public InvalidBinaryOperatorException(String invalidToken) {
		super(String.format("Supported binary operator tokens are: %s, but the following is invalid: %s",
				Arrays.toString(BinaryOperator.values()), invalidToken));
	}
}
