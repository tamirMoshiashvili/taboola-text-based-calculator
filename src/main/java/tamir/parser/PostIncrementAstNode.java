package tamir.parser;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class PostIncrementAstNode implements AbstractSyntaxTreeNode {

	private final String variableName;
}
