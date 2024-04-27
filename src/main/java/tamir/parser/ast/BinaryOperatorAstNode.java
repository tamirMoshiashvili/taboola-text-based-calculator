package tamir.parser.ast;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public abstract class BinaryOperatorAstNode implements AbstractSyntaxTreeNode {

	private final AbstractSyntaxTreeNode left;
	private final AbstractSyntaxTreeNode right;
}
