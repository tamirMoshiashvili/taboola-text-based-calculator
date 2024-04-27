package tamir.token.expression;

import lombok.EqualsAndHashCode;

import static tamir.token.expression.Precedence.MULTIPLICATION_AND_DIVISION;

@EqualsAndHashCode
public class DivisionToken implements BinaryOperatorToken {

	@Override
	public int getPrecedence() {
		return MULTIPLICATION_AND_DIVISION.getPrecedence();
	}
}
