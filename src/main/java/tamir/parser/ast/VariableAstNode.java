package tamir.parser.ast;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import tamir.calculator.CalculatorContext;

@AllArgsConstructor
@EqualsAndHashCode
public class VariableAstNode implements AbstractSyntaxTreeNode {

	private final String variableName;

	@Override
	public int interpret(CalculatorContext context) {
		return context.get(variableName);
	}
}
