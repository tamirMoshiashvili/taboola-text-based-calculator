package tamir.parser.ast;

public class MultiplicationAssignmentAstNode extends AssignmentAstNode {

	public MultiplicationAssignmentAstNode(String assignedVariableName, AbstractSyntaxTreeNode valueExpression) {
		super(assignedVariableName, valueExpression);
	}
}
