package tamir.token;

import lombok.AllArgsConstructor;
import lombok.Getter;
import tamir.exception.InvalidOperatorTokenException;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toMap;
import static java.util.stream.Collectors.toSet;

@AllArgsConstructor
@Getter
public enum Operator {

	ASSIGNMENT("=", 0),
	ADDITION_ASSIGNMENT("+=", 0),
	SUBTRACTION_ASSIGNMENT("-=", 0),
	MULTIPLICATION_ASSIGNMENT("*=", 0),
	DIVISION_ASSIGNMENT("/=", 0),
	INCREMENT("++", 1),
	DECREMENT("--", 1),
	ADDITION("+", 2),
	SUBTRACTION("-", 2),
	MULTIPLICATION("*", 3),
	DIVISION("/", 3),
	;

	public static int getUnaryOperatorLength() {
		return INCREMENT.getToken().length();
	}

	public static boolean isBinaryOperator(String token) {
		return BINARY_OPERATORS.contains(token);
	}

	public static Operator getFromToken(String token) {
		return Optional.ofNullable(TOKEN_TO_OPERATOR.get(token))
				.orElseThrow(() -> new InvalidOperatorTokenException(token));
	}

	private final String token;
	private final int precedence;

	private static final Set<String> BINARY_OPERATORS = getAllBinaryOperatorTokens();
	private static final Map<String, Operator> TOKEN_TO_OPERATOR = createTokenToOperatorMap();

	private static Set<String> getAllBinaryOperatorTokens() {
		return Stream.of(ASSIGNMENT, ADDITION_ASSIGNMENT, SUBTRACTION_ASSIGNMENT, MULTIPLICATION_ASSIGNMENT, DIVISION_ASSIGNMENT,
				ADDITION, SUBTRACTION, MULTIPLICATION, DIVISION)
				.map(Operator::getToken)
				.collect(toSet());
	}

	private static Map<String, Operator> createTokenToOperatorMap() {
		return Arrays.stream(Operator.values())
				.collect(toMap(Operator::getToken, Function.identity()));
	}
}
