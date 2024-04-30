package tamir.parser.ast;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;

@AllArgsConstructor
@EqualsAndHashCode
abstract class BinaryOperatorAstNode implements AbstractSyntaxTreeNode<Integer> {

	protected final AbstractSyntaxTreeNode<Integer> left;
	protected final AbstractSyntaxTreeNode<Integer> right;
}
