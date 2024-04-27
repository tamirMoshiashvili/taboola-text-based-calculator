package tamir.parser;

import lombok.AllArgsConstructor;
import tamir.token.expression.Token;

@AllArgsConstructor
public class IntegerAstNode implements AbstractSyntaxTreeNode {

	private final Token value;
}
