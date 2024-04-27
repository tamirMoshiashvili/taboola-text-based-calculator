package tamir.parser;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class IntegerAstNode implements AbstractSyntaxTreeNode {

	private final int value;
}
