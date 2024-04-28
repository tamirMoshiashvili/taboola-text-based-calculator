package tamir.parser.ast;

import tamir.calculator.CalculatorContext;

public interface AbstractSyntaxTreeNode {

	int interpret(CalculatorContext context);
}
