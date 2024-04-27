package tamir.exception;

import tamir.parser.AbstractSyntaxTreeNode;
import tamir.token.expression.Token;

import java.util.Stack;

public class InvalidAbstractSyntaxTreeStructureException extends RuntimeException {

	public InvalidAbstractSyntaxTreeStructureException(Stack<Token> operators,
													   Stack<AbstractSyntaxTreeNode> expressions,
													   Token invalidToken) {
		super(String.format("Token: %s creates invalid structure of abstract-syntax-tree for operators: %s and expressions: %s",
				invalidToken, operators, expressions));
	}

	public InvalidAbstractSyntaxTreeStructureException(Stack<Token> operators, Stack<AbstractSyntaxTreeNode> expressions) {
		super(String.format("Invalid structure of abstract-syntax-tree for operators: %s and expressions: %s",
				operators, expressions));
	}
}
