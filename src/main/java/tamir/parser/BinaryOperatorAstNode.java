package tamir.parser;

import lombok.AllArgsConstructor;
import tamir.token.expression.Token;

@AllArgsConstructor
public class BinaryOperatorAstNode implements AbstractSyntaxTreeNode {

	private final AbstractSyntaxTreeNode left;
	private final Token operator;
	private final AbstractSyntaxTreeNode right;
}
