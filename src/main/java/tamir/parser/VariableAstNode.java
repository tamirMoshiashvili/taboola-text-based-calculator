package tamir.parser;

import lombok.AllArgsConstructor;
import tamir.token.expression.Token;

@AllArgsConstructor
public class VariableAstNode implements AbstractSyntaxTreeNode {

	private final Token variableName;
}
