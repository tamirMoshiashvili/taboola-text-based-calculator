package tamir.exception;

import tamir.parser.operator.AssignmentOperator;

public class UnsupportedAssignmentOperatorForAstException extends RuntimeException {

	public UnsupportedAssignmentOperatorForAstException(AssignmentOperator unsupportedAssignmentOperator) {
		super("Assignment operator is not supported for abstract-syntax-tree parsing:" + unsupportedAssignmentOperator.getToken());
	}
}
