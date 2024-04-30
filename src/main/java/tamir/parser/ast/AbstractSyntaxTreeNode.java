package tamir.parser.ast;

import tamir.calculator.CalculatorContext;

public interface AbstractSyntaxTreeNode<T> {

	T interpret(CalculatorContext context);
}
