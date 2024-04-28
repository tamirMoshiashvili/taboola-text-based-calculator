package tamir.parser.operator;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum UnaryOperator {

	INCREMENT("++"),
	DECREMENT("--"),
	;

	public static int getUnaryOperatorLength() {
		return INCREMENT.getToken().length();
	}

	private final String token;
}
