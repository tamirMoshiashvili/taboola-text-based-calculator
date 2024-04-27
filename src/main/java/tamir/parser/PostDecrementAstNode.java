package tamir.parser;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class PostDecrementAstNode implements AbstractSyntaxTreeNode {

	private final String variableName;
}
