package tamir.parser.ast;

import lombok.AllArgsConstructor;
import tamir.calculator.CalculatorContext;

@AllArgsConstructor
public class VariableAstNode implements AbstractSyntaxTreeNode {

	private final String variableName;

	@Override
	public int interpret(CalculatorContext context) {
		return context.get(variableName);
	}
}
