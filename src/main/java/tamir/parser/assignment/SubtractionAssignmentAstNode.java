package tamir.parser.assignment;

import tamir.calculator.CalculatorContext;
import tamir.parser.ast.AbstractSyntaxTreeNode;

public class SubtractionAssignmentAstNode extends AssignmentAstNode {

	public SubtractionAssignmentAstNode(String assignedVariableName, AbstractSyntaxTreeNode valueExpression) {
		super(assignedVariableName, valueExpression);
	}

	@Override
	public void execute(CalculatorContext context) {
		context.put(assignedVariableName, context.get(assignedVariableName) - valueExpression.interpret(context));
	}
}
