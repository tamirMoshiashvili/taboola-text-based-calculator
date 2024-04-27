package tamir.parser.ast;

public interface AbstractSyntaxTreeNode {

	default int interpret() {
		return 0;
	}
}
