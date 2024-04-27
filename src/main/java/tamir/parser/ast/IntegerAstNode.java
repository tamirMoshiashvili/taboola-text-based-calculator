package tamir.parser.ast;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class IntegerAstNode implements AbstractSyntaxTreeNode {

	private final int value;
}
