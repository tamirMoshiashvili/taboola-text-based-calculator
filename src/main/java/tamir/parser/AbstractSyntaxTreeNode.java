package tamir.parser;

public interface AbstractSyntaxTreeNode {

	default int interpret() {
		return 0;
	}
}
