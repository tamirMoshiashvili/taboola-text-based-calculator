package tamir.parser;

import lombok.AllArgsConstructor;
import tamir.token.expression.Token;

@AllArgsConstructor
public class UnaryOperatorAstNode implements AbstractSyntaxTreeNode {

	private final Token token;
}
