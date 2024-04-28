package tamir.parser.ast;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;

@AllArgsConstructor
@EqualsAndHashCode
public abstract class BinaryOperatorAstNode implements AbstractSyntaxTreeNode {

	protected final AbstractSyntaxTreeNode left;
	protected final AbstractSyntaxTreeNode right;
}
