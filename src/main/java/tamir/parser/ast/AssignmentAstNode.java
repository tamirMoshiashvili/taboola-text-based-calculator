package tamir.parser.ast;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class AssignmentAstNode implements AbstractSyntaxTreeNode {

	private final String assignedVariableName;
	private final AbstractSyntaxTreeNode valueExpression;
}
