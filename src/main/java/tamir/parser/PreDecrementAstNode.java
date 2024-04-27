package tamir.parser;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class PreDecrementAstNode implements AbstractSyntaxTreeNode {

	private final String variableName;
}
