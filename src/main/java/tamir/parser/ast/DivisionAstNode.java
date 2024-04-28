package tamir.parser.ast;

import lombok.EqualsAndHashCode;
import tamir.calculator.CalculatorContext;

@EqualsAndHashCode(callSuper = true)
public class DivisionAstNode extends BinaryOperatorAstNode {

	public DivisionAstNode(AbstractSyntaxTreeNode left, AbstractSyntaxTreeNode right) {
		super(left, right);
	}

	@Override
	public int interpret(CalculatorContext context) {
		return left.interpret(context) / right.interpret(context);
	}
}
