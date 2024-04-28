package tamir.parser.assignment;

import lombok.EqualsAndHashCode;
import tamir.calculator.CalculatorContext;
import tamir.parser.ast.AbstractSyntaxTreeNode;

@EqualsAndHashCode(callSuper = true)
public class DivisionAssignmentRootNode extends AssignmentRootNode {

	public DivisionAssignmentRootNode(String assignedVariableName, AbstractSyntaxTreeNode valueExpression) {
		super(assignedVariableName, valueExpression);
	}

	@Override
	public void execute(CalculatorContext context) {
		context.put(assignedVariableName, context.get(assignedVariableName) / valueExpression.interpret(context));
	}
}
