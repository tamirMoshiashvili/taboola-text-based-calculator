package tamir.token.expression;

import lombok.EqualsAndHashCode;

import static tamir.token.expression.Precedence.ADDITION_AND_SUBTRACTION;

@EqualsAndHashCode
public class AdditionToken implements BinaryOperatorToken {

	@Override
	public int getPrecedence() {
		return ADDITION_AND_SUBTRACTION.getPrecedence();
	}
}
