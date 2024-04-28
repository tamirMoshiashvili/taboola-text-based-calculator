package tamir.parser.ast;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public abstract class BinaryOperatorAstNode implements AbstractSyntaxTreeNode {

	protected final AbstractSyntaxTreeNode left;
	protected final AbstractSyntaxTreeNode right;
}
