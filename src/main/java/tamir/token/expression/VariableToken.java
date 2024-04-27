package tamir.token.expression;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import static tamir.token.expression.Precedence.NO_PRECEDENCE;

@AllArgsConstructor
@Getter
@EqualsAndHashCode
public class VariableToken implements Token {

	private final String variableName;

	@Override
	public boolean isVariable() {
		return true;
	}

	@Override
	public boolean isOperand() {
		return false;
	}

	@Override
	public boolean isUnaryOperator() {
		return false;
	}

	@Override
	public boolean isBinaryOperator() {
		return false;
	}

	@Override
	public int getPrecedence() {
		return NO_PRECEDENCE.getPrecedence();
	}
}
