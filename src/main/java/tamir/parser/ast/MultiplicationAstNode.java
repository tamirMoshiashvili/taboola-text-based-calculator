package tamir.parser.ast;

import tamir.calculator.CalculatorContext;

public class MultiplicationAstNode extends BinaryOperatorAstNode {

	public MultiplicationAstNode(AbstractSyntaxTreeNode left, AbstractSyntaxTreeNode right) {
		super(left, right);
	}

	@Override
	public int interpret(CalculatorContext context) {
		return left.interpret(context) * right.interpret(context);
	}
}
