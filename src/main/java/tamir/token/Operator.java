package tamir.token;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Operator {

	ADDITION("+"),
	SUBTRACTION("-"),
	MULTIPLICATION("*"),
	DIVISION("/"),
	ASSIGNMENT("="),
	ADDITION_ASSIGNMENT("+="),
	SUBTRACTION_ASSIGNMENT("-="),
	MULTIPLICATION_ASSIGNMENT("*="),
	DIVISION_ASSIGNMENT("/="),
	INCREMENT("++"),
	DECREMENT("--"),
	;

	public static int getUnaryOperatorLength() {
		return INCREMENT.getToken().length();
	}

	private final String token;
}
