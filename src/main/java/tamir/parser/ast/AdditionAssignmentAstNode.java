package tamir.parser.ast;

public class AdditionAssignmentAstNode extends AssignmentAstNode {

	public AdditionAssignmentAstNode(String assignedVariableName, AbstractSyntaxTreeNode valueExpression) {
		super(assignedVariableName, valueExpression);
	}
}
