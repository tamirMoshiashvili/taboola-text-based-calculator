package tamir.token;

import tamir.exception.*;
import tamir.token.assignment.*;
import tamir.token.expression.*;

import java.util.Arrays;
import java.util.List;

import static java.lang.Character.isDigit;
import static java.lang.Integer.parseInt;
import static java.util.stream.Collectors.toList;
import static tamir.token.Operator.*;

public class AssignmentExpressionTokenizer {

	private static final String TOKEN_SEPARATOR = " ";
	private static final long VARIABLE_AND_ASSIGNMENT_TOKENS_SIZE = 2;
	private static final int MIN_NUM_TOKENS_OF_ASSIGNMENT_EXPRESSION = 3;

	public static ParsedAssignmentExpression parseAssignmentExpressionIntoTokens(String assignmentExpression) {
		validateExpressionIsNotBlank(assignmentExpression);
		List<String> tokens = splitIntoTokens(assignmentExpression);
		validateNumberOfTokens(tokens);
		VariableToken variable = parseAssignedVariable(tokens.get(0));
		AssignmentToken assignment = parseAssignmentToken(tokens.get(1));
		List<Token> expressionTokens = parseExpressionTokens(tokens);

		return new ParsedAssignmentExpression(variable, assignment, expressionTokens);
	}

	private static void validateExpressionIsNotBlank(String expression) {
		if (expression == null || expression.isBlank())
			throw new BlankExpressionException();
	}

	private static List<String> splitIntoTokens(String assignmentExpression) {
		return Arrays.stream(assignmentExpression.split(TOKEN_SEPARATOR))
				.filter(token -> !token.isBlank())
				.collect(toList());
	}

	private static void validateNumberOfTokens(List<String> tokens) {
		if (tokens.size() < MIN_NUM_TOKENS_OF_ASSIGNMENT_EXPRESSION)
			throw new InvalidNumTokensInAssignmentExpressionException(tokens);
	}

	private static VariableToken parseAssignedVariable(String token) {
		if (isVariable(token))
			return new VariableToken(token);
		throw new MissingAssignedVariableTokenException(token);
	}

	private static AssignmentToken parseAssignmentToken(String token) {
		if (token.equals(ASSIGNMENT.getToken()))
			return new AssignmentToken();
		if (token.equals(ADDITION_ASSIGNMENT.getToken()))
			return new AdditionAssignmentToken();
		if (token.equals(SUBTRACTION_ASSIGNMENT.getToken()))
			return new SubtractionAssignmentToken();
		if (token.equals(MULTIPLICATION_ASSIGNMENT.getToken()))
			return new MultiplicationAssignmentToken();
		if (token.equals(DIVISION_ASSIGNMENT.getToken()))
			return new DivisionAssignmentToken();
		throw new MissingAssignmentTokenException(token);
	}

	private static List<Token> parseExpressionTokens(List<String> tokens) {
		return tokens.stream().skip(VARIABLE_AND_ASSIGNMENT_TOKENS_SIZE)
				.map(AssignmentExpressionTokenizer::parseToken)
				.collect(toList());
	}

	static Token parseToken(String token) {
		if (isInteger(token))
			return new IntegerToken(parseInt(token));
		if (isVariable(token))
			return new VariableToken(token);
		if (token.equals(ADDITION.getToken()))
			return new AdditionToken();
		if (token.equals(SUBTRACTION.getToken()))
			return new SubtractionToken();
		if (token.equals(MULTIPLICATION.getToken()))
			return new MultiplicationToken();
		if (token.equals(DIVISION.getToken()))
			return new DivisionToken();
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

	private static boolean isInteger(String token) {
		return (isNegativeInteger(token) || Character.isDigit(token.charAt(0)))
				&& token.chars().skip(1).allMatch(Character::isDigit);
	}

	private static boolean isNegativeInteger(String token) {
		return token.charAt(0) == '-' && token.length() > 1;
	}

	private static boolean isVariable(String token) {
		return isValidLetter(token.charAt(0))
				&& token.chars().allMatch(AssignmentExpressionTokenizer::isValidLetterOrDigit);
	}

	private static boolean isValidLetterOrDigit(int c) {
		return isValidLetter((char) c) || isDigit(c);
	}

	private static boolean isValidLetter(char c) {
		return ('a' <= c && c <= 'z')
				|| ('A' <= c && c <= 'Z');
	}

	private static boolean isPreIncrement(String token) {
		return token.startsWith(INCREMENT.getToken()) && isVariable(getVariableNameOfPreUnaryOperatorToken(token));
	}

	private static boolean isPostIncrement(String token) {
		return token.endsWith(INCREMENT.getToken()) && isVariable(getVariableNameOfPostUnaryOperatorToken(token));
	}

	private static boolean isPreDecrement(String token) {
		return token.startsWith(DECREMENT.getToken()) && isVariable(getVariableNameOfPreUnaryOperatorToken(token));
	}

	private static boolean isPostDecrement(String token) {
		return token.endsWith(DECREMENT.getToken()) && isVariable(getVariableNameOfPostUnaryOperatorToken(token));
	}

	private static String getVariableNameOfPreUnaryOperatorToken(String token) {
		return token.substring(getUnaryOperatorLength());
	}

	private static String getVariableNameOfPostUnaryOperatorToken(String token) {
		return token.substring(0, token.length() - getUnaryOperatorLength());
	}
}
