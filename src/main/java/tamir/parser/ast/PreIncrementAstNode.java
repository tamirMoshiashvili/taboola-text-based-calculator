package tamir.parser.ast;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class PreIncrementAstNode implements AbstractSyntaxTreeNode {

	private final String variableName;
}
