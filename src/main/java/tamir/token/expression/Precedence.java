package tamir.token.expression;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Precedence {

	NO_PRECEDENCE(0),
	ADDITION_AND_SUBTRACTION(1),
	MULTIPLICATION_AND_DIVISION(2);

	private final int precedence;

}
