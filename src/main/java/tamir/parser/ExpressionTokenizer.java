package tamir.parser;

import java.util.Arrays;
import java.util.List;

import static java.lang.Character.isDigit;
import static java.util.stream.Collectors.toList;
import static tamir.parser.operator.UnaryOperator.*;

class ExpressionTokenizer {

	private static final String TOKEN_SEPARATOR = " ";

	static List<String> splitIntoTokens(String expression) {
		return Arrays.stream(expression.split(TOKEN_SEPARATOR))
				.filter(token -> !token.isBlank())
				.collect(toList());
	}

	static boolean isVariable(String token) {
		return isValidLetter(token.charAt(0))
				&& token.chars().allMatch(ExpressionTokenizer::isValidLetterOrDigit);
	}

	static boolean isInteger(String token) {
		return (isNegativeInteger(token) || isDigit(token.charAt(0)))
				&& token.chars().skip(1).allMatch(Character::isDigit);
	}

	static boolean isPreIncrement(String token) {
		return token.startsWith(INCREMENT.getToken()) && isVariable(getVariableNameOfPreUnaryOperatorToken(token));
	}

	static boolean isPostIncrement(String token) {
		return token.endsWith(INCREMENT.getToken()) && isVariable(getVariableNameOfPostUnaryOperatorToken(token));
	}

	static boolean isPreDecrement(String token) {
		return token.startsWith(DECREMENT.getToken()) && isVariable(getVariableNameOfPreUnaryOperatorToken(token));
	}

	static boolean isPostDecrement(String token) {
		return token.endsWith(DECREMENT.getToken()) && isVariable(getVariableNameOfPostUnaryOperatorToken(token));
	}

	static String getVariableNameOfPreUnaryOperatorToken(String token) {
		return token.substring(getUnaryOperatorLength());
	}

	static String getVariableNameOfPostUnaryOperatorToken(String token) {
		return token.substring(0, token.length() - getUnaryOperatorLength());
	}

	private static boolean isValidLetter(char c) {
		return ('a' <= c && c <= 'z')
				|| ('A' <= c && c <= 'Z');
	}

	private static boolean isValidLetterOrDigit(int c) {
		return isValidLetter((char) c) || isDigit(c);
	}

	private static boolean isNegativeInteger(String token) {
		return token.charAt(0) == '-' && token.length() > 1;
	}
}
