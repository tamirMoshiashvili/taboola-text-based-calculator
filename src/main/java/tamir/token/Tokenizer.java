package tamir.token;

import tamir.exception.BlankExpressionException;
import tamir.exception.InvalidTokenException;

import java.util.Arrays;
import java.util.List;

import static java.lang.Character.isDigit;
import static java.lang.Integer.parseInt;
import static java.util.stream.Collectors.toList;

public class Tokenizer {

	private static final String TOKEN_SEPARATOR = " ";

	public static List<Token> parseTokens(String expression) {
		validateExpressionIsNotBlank(expression);
		return Arrays.stream(expression.split(TOKEN_SEPARATOR))
				.map(Tokenizer::parseToken)
				.collect(toList());
	}

	static Token parseToken(String token) {
		if (isInteger(token))
			return new IntegerToken(parseInt(token));
		if (isVariable(token))
			return new VariableToken(token);
		if (token.equals("+"))
			return new AdditionToken();
		if (token.equals("-"))
			return new SubtractionToken();
		if (token.equals("*"))
			return new MultiplicationToken();
		if (token.equals("/"))
			return new DivisionToken();
		if (token.equals("="))
			return new AssignmentToken();
		if (token.equals("+="))
			return new AdditionAssignmentToken();
		if (token.equals("-="))
			return new SubtractionAssignmentToken();
		if (token.equals("*="))
			return new MultiplicationAssignmentToken();
		if (token.equals("/="))
			return new DivisionAssignmentToken();
		if (isPreIncrement(token))
			return new PreIncrementToken(getVariableNameOfPreUnaryOperatorToken(token));
		if (isPostIncrement(token))
			return new PostIncrementToken(getVariableNameOfPostUnaryOperatorToken(token));
		if (isPreDecrement(token))
			return new PreDecrementToken(getVariableNameOfPreUnaryOperatorToken(token));
		if (isPostDecrement(token))
			return new PostDecrementToken(getVariableNameOfPostUnaryOperatorToken(token));
		throw new InvalidTokenException(token);
	}

	private static void validateExpressionIsNotBlank(String expression) {
		if (expression == null || expression.isBlank())
			throw new BlankExpressionException();
	}

	private static boolean isInteger(String token) {
		return ((token.charAt(0) == '-' && token.length() > 1) || Character.isDigit(token.charAt(0)))
				&& token.chars().skip(1).allMatch(Character::isDigit);
	}

	private static boolean isVariable(String token) {
		return isValidLetter(token.charAt(0))
				&& token.chars().allMatch(Tokenizer::isValidLetterOrDigit);
	}

	private static boolean isValidLetterOrDigit(int c) {
		return isValidLetter((char) c) || isDigit(c);
	}

	private static boolean isValidLetter(char c) {
		return ('a' <= c && c <= 'z')
				|| ('A' <= c && c <= 'Z');
	}

	private static boolean isPreIncrement(String token) {
		return token.startsWith("++") && isVariable(getVariableNameOfPreUnaryOperatorToken(token));
	}

	private static boolean isPostIncrement(String token) {
		return token.endsWith("++") && isVariable(getVariableNameOfPostUnaryOperatorToken(token));
	}

	private static boolean isPreDecrement(String token) {
		return token.startsWith("--") && isVariable(getVariableNameOfPreUnaryOperatorToken(token));
	}

	private static boolean isPostDecrement(String token) {
		return token.endsWith("--") && isVariable(getVariableNameOfPostUnaryOperatorToken(token));
	}

	private static String getVariableNameOfPreUnaryOperatorToken(String token) {
		return token.substring(2);
	}

	private static String getVariableNameOfPostUnaryOperatorToken(String token) {
		return token.substring(0, token.length() - 2);
	}
}
