package tamir.exception;

import tamir.parser.Operator;
import tamir.parser.ast.AbstractSyntaxTreeNode;

import java.util.Stack;

public class InvalidAbstractSyntaxTreeStructureException extends RuntimeException {

	public InvalidAbstractSyntaxTreeStructureException(Stack<Operator> operators,
													   Stack<AbstractSyntaxTreeNode> expressions,
													   String invalidToken) {
		super(String.format("Token: %s creates invalid structure of abstract-syntax-tree for operators: %s and expressions: %s",
				invalidToken, operators, expressions));
	}

	public InvalidAbstractSyntaxTreeStructureException(Stack<Operator> operators, Stack<AbstractSyntaxTreeNode> expressions) {
		super(String.format("Invalid structure of abstract-syntax-tree for operators: %s and expressions: %s",
				operators, expressions));
	}
}
