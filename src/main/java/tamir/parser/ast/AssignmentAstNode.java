package tamir.parser.ast;

public class AssignmentAstNode extends BinaryOperatorAstNode {

	public AssignmentAstNode(AbstractSyntaxTreeNode left, AbstractSyntaxTreeNode right) {
		super(left, right);
	}
}
