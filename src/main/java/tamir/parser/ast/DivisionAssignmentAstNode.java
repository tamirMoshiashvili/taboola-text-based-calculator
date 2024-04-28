package tamir.parser.ast;

public class DivisionAssignmentAstNode extends AssignmentAstNode {

	public DivisionAssignmentAstNode(String assignedVariableName, AbstractSyntaxTreeNode valueExpression) {
		super(assignedVariableName, valueExpression);
	}
}
