package tamir.parser;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class VariableAstNode implements AbstractSyntaxTreeNode {

	private final String variableName;
}
