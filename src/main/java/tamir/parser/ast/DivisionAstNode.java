package tamir.parser.ast;

import tamir.calculator.CalculatorContext;

public class DivisionAstNode extends BinaryOperatorAstNode {

	public DivisionAstNode(AbstractSyntaxTreeNode left, AbstractSyntaxTreeNode right) {
		super(left, right);
	}

	@Override
	public int interpret(CalculatorContext context) {
		return left.interpret(context) / right.interpret(context);
	}
}
