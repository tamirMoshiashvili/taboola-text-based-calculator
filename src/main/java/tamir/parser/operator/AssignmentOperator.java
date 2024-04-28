package tamir.parser.operator;

import lombok.AllArgsConstructor;
import lombok.Getter;
import tamir.exception.InvalidAssignmentOperatorException;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

import static java.util.stream.Collectors.toMap;

@AllArgsConstructor
@Getter
public enum AssignmentOperator {

	ASSIGNMENT("="),
	ADDITION_ASSIGNMENT("+="),
	SUBTRACTION_ASSIGNMENT("-="),
	MULTIPLICATION_ASSIGNMENT("*="),
	DIVISION_ASSIGNMENT("/="),
	;

	public static AssignmentOperator fromToken(String token) {
		return Optional.ofNullable(TOKEN_TO_ASSIGNMENT.get(token))
				.orElseThrow(() -> new InvalidAssignmentOperatorException(token));
	}

	private static final Map<String, AssignmentOperator> TOKEN_TO_ASSIGNMENT = createTokenToAssignmentOperatorMap();

	private static Map<String, AssignmentOperator> createTokenToAssignmentOperatorMap() {
		return Arrays.stream(AssignmentOperator.values())
				.collect(toMap(AssignmentOperator::getToken, Function.identity()));
	}

	private final String token;
}
