package tamir.exception;

import tamir.parser.ast.AbstractSyntaxTreeNode;
import tamir.parser.operator.BinaryOperator;

import java.util.Stack;

public class InvalidAbstractSyntaxTreeStructureException extends RuntimeException {

	public InvalidAbstractSyntaxTreeStructureException(Stack<BinaryOperator> binaryOperators,
													   Stack<AbstractSyntaxTreeNode<Integer>> expressions,
													   String invalidToken) {
		super(String.format("Token: %s creates invalid structure of abstract-syntax-tree for binaryOperators: %s and expressions: %s",
				invalidToken, binaryOperators, expressions));
	}

	public InvalidAbstractSyntaxTreeStructureException(Stack<BinaryOperator> binaryOperators,
													   Stack<AbstractSyntaxTreeNode<Integer>> expressions) {
		super(String.format("Invalid structure of abstract-syntax-tree for binaryOperators: %s and expressions: %s",
				binaryOperators, expressions));
	}
}
