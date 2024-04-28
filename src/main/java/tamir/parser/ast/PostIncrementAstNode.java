package tamir.parser.ast;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class PostIncrementAstNode implements AbstractSyntaxTreeNode {

	private final String variableName;
}
