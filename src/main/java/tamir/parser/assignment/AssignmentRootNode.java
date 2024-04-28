package tamir.parser.assignment;

import lombok.AllArgsConstructor;
import tamir.calculator.CalculatorContext;
import tamir.parser.ast.AbstractSyntaxTreeNode;

@AllArgsConstructor
public class AssignmentRootNode {

	protected final String assignedVariableName;
	protected final AbstractSyntaxTreeNode valueExpression;

	public void execute(CalculatorContext context) {
		context.put(assignedVariableName, valueExpression.interpret(context));
	}
}
