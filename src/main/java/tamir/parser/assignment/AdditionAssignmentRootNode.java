package tamir.parser.assignment;

import tamir.calculator.CalculatorContext;
import tamir.parser.ast.AbstractSyntaxTreeNode;

public class AdditionAssignmentRootNode extends AssignmentRootNode {

	public AdditionAssignmentRootNode(String assignedVariableName, AbstractSyntaxTreeNode valueExpression) {
		super(assignedVariableName, valueExpression);
	}

	@Override
	public void execute(CalculatorContext context) {
		context.put(assignedVariableName, context.get(assignedVariableName) + valueExpression.interpret(context));
	}
}
