package tamir.parser.ast;

import tamir.calculator.CalculatorContext;

public class DivisionAssignmentAstNode extends AssignmentAstNode {

	public DivisionAssignmentAstNode(String assignedVariableName, AbstractSyntaxTreeNode valueExpression) {
		super(assignedVariableName, valueExpression);
	}

	@Override
	public void execute(CalculatorContext context) {
		context.put(assignedVariableName, context.get(assignedVariableName) / valueExpression.interpret(context));
	}
}
