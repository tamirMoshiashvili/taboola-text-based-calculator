package tamir.parser.ast;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class PostDecrementAstNode implements AbstractSyntaxTreeNode {

	private final String variableName;
}
