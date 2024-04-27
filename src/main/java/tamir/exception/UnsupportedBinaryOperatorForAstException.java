package tamir.exception;

import tamir.parser.Operator;

public class UnsupportedBinaryOperatorForAstException extends RuntimeException {

	public UnsupportedBinaryOperatorForAstException(Operator unsupportedOperator) {
		super("Binary operator is not supported for abstract-syntax-tree parsing: " + unsupportedOperator.getToken());
	}
}
