package tamir.parser.ast.assignment;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import tamir.calculator.CalculatorContext;
import tamir.parser.ast.AbstractSyntaxTreeNode;

@AllArgsConstructor
@EqualsAndHashCode
public class AssignmentAstNode implements AbstractSyntaxTreeNode<Void> {

	protected final String assignedVariableName;
	protected final AbstractSyntaxTreeNode<Integer> valueExpression;

	public Void interpret(CalculatorContext context) {
		context.put(assignedVariableName, valueExpression.interpret(context));
		return null;
	}
}
