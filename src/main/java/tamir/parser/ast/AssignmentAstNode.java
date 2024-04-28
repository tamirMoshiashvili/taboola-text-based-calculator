package tamir.parser.ast;

import lombok.AllArgsConstructor;
import tamir.calculator.CalculatorContext;

@AllArgsConstructor
public class AssignmentAstNode {

	protected final String assignedVariableName;
	protected final AbstractSyntaxTreeNode valueExpression;

	public void execute(CalculatorContext context) {
		context.put(assignedVariableName, valueExpression.interpret(context));
	}
}
