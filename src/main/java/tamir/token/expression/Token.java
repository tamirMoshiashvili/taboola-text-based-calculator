package tamir.token.expression;

public interface Token {

	boolean isVariable();

	boolean isOperand();

	boolean isUnaryOperator();

	boolean isBinaryOperator();

	int getPrecedence();
}
