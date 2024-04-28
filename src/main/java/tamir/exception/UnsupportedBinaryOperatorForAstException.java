package tamir.exception;

import tamir.parser.operator.BinaryOperator;

public class UnsupportedBinaryOperatorForAstException extends RuntimeException {

	public UnsupportedBinaryOperatorForAstException(BinaryOperator unsupportedBinaryOperator) {
		super("Binary operator is not supported for abstract-syntax-tree parsing: " + unsupportedBinaryOperator.getToken());
	}
}
