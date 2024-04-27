package tamir.parser;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class PreIncrementAstNode implements AbstractSyntaxTreeNode {

	private final String variableName;
}
