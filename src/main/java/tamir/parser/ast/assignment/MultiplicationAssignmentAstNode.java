package tamir.parser.ast.assignment;

import lombok.EqualsAndHashCode;
import tamir.calculator.CalculatorContext;
import tamir.parser.ast.AbstractSyntaxTreeNode;

@EqualsAndHashCode(callSuper = true)
public class MultiplicationAssignmentAstNode extends AssignmentAstNode {

	public MultiplicationAssignmentAstNode(String assignedVariableName, AbstractSyntaxTreeNode<Integer> valueExpression) {
		super(assignedVariableName, valueExpression);
	}

	@Override
	public Void interpret(CalculatorContext context) {
		context.put(assignedVariableName, context.get(assignedVariableName) * valueExpression.interpret(context));
		return null;
	}
}
