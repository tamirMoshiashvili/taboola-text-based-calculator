package tamir.parser.ast;

public class SubtractionAssignmentAstNode extends AssignmentAstNode {

	public SubtractionAssignmentAstNode(String assignedVariableName, AbstractSyntaxTreeNode valueExpression) {
		super(assignedVariableName, valueExpression);
	}
}
