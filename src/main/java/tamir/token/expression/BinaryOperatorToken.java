package tamir.token.expression;

public interface BinaryOperatorToken extends Token {

	@Override
	default boolean isVariable() {
		return false;
	}

	@Override
	default boolean isOperand() {
		return false;
	}

	@Override
	default boolean isUnaryOperator() {
		return false;
	}

	@Override
	default boolean isBinaryOperator() {
		return true;
	}
}
