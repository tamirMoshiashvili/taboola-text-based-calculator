package tamir.parser.ast.operator.binary;

import lombok.EqualsAndHashCode;
import tamir.calculator.CalculatorContext;
import tamir.parser.ast.AbstractSyntaxTreeNode;

@EqualsAndHashCode(callSuper = true)
public class DivisionAstNode extends BinaryOperatorAstNode {

	public DivisionAstNode(AbstractSyntaxTreeNode<Integer> left, AbstractSyntaxTreeNode<Integer> right) {
		super(left, right);
	}

	@Override
	public Integer interpret(CalculatorContext context) {
		return left.interpret(context) / right.interpret(context);
	}
}
