package tamir.parser.operator;

import lombok.AllArgsConstructor;
import lombok.Getter;
import tamir.exception.InvalidBinaryOperatorException;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;

import static java.util.stream.Collectors.toMap;
import static java.util.stream.Collectors.toSet;

@AllArgsConstructor
@Getter
public enum BinaryOperator {

	ADDITION("+", 0),
	SUBTRACTION("-", 0),
	MULTIPLICATION("*", 1),
	DIVISION("/", 1),
	;

	public static boolean isBinaryOperator(String token) {
		return BINARY_OPERATORS.contains(token);
	}

	public static BinaryOperator fromToken(String token) {
		return Optional.ofNullable(TOKEN_TO_OPERATOR.get(token))
				.orElseThrow(() -> new InvalidBinaryOperatorException(token));
	}

	private final String token;
	private final int precedence;

	private static final Set<String> BINARY_OPERATORS = getAllBinaryOperatorTokens();
	private static final Map<String, BinaryOperator> TOKEN_TO_OPERATOR = createTokenToOperatorMap();

	private static Set<String> getAllBinaryOperatorTokens() {
		return Arrays.stream(BinaryOperator.values())
				.map(BinaryOperator::getToken)
				.collect(toSet());
	}

	private static Map<String, BinaryOperator> createTokenToOperatorMap() {
		return Arrays.stream(BinaryOperator.values())
				.collect(toMap(BinaryOperator::getToken, Function.identity()));
	}
}
