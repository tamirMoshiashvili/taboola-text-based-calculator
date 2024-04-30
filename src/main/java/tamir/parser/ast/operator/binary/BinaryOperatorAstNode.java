package tamir.parser.ast.operator.binary;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import tamir.parser.ast.AbstractSyntaxTreeNode;

@AllArgsConstructor
@EqualsAndHashCode
abstract class BinaryOperatorAstNode implements AbstractSyntaxTreeNode<Integer> {

	protected final AbstractSyntaxTreeNode<Integer> left;
	protected final AbstractSyntaxTreeNode<Integer> right;
}
