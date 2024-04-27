package tamir.token.expression;

import static tamir.token.expression.Precedence.NO_PRECEDENCE;

public interface UnaryOperatorToken extends Token {

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
		return true;
	}

	@Override
	default boolean isBinaryOperator() {
		return false;
	}

	@Override
	default int getPrecedence() {
		return NO_PRECEDENCE.getPrecedence();
	}
}
