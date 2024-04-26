package tamir.token;

import org.junit.jupiter.api.Test;
import tamir.exception.*;
import tamir.token.assignment.*;
import tamir.token.expression.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static tamir.token.AssignmentExpressionTokenizer.parseAssignmentExpressionIntoTokens;
import static tamir.token.AssignmentExpressionTokenizer.parseToken;

class AssignmentExpressionTokenizerTest {

	@Test
	void whenExpressionIsNull_thenThrowsBlankExpressionException() {
		assertThrows(BlankExpressionException.class, () -> parseAssignmentExpressionIntoTokens(null));
	}

	@Test
	void whenExpressionIsEmpty_thenThrowsBlankExpressionException() {
		assertThrows(BlankExpressionException.class, () -> parseAssignmentExpressionIntoTokens(""));
	}

	@Test
	void whenExpressionIsSingleSpace_thenThrowsBlankExpressionException() {
		assertThrows(BlankExpressionException.class, () -> parseAssignmentExpressionIntoTokens(" "));
	}

	@Test
	void whenExpressionIsMultipleSpaces_thenThrowsBlankExpressionException() {
		assertThrows(BlankExpressionException.class, () -> parseAssignmentExpressionIntoTokens("  "));
	}

	@Test
	void whenExpressionIsTab_thenThrowsBlankExpressionException() {
		assertThrows(BlankExpressionException.class, () -> parseAssignmentExpressionIntoTokens("\t"));
	}

	@Test
	void whenTokenIsInteger_thenReturnIntegerToken() {
		assertEquals(new IntegerToken(1), parseToken("1"));
		assertEquals(new IntegerToken(0), parseToken("0"));
		assertEquals(new IntegerToken(-1), parseToken("-1"));
		assertEquals(new IntegerToken(1405), parseToken("1405"));
		assertEquals(new IntegerToken(Integer.MIN_VALUE), parseToken(String.valueOf(Integer.MIN_VALUE)));
		assertEquals(new IntegerToken(Integer.MAX_VALUE), parseToken(String.valueOf(Integer.MAX_VALUE)));
	}

	@Test
	void whenTokenIsVariable_thenReturnVariableToken() {
		assertEquals(new VariableToken("a"), parseToken("a"));
		assertEquals(new VariableToken("x"), parseToken("x"));
		assertEquals(new VariableToken("abc"), parseToken("abc"));
		assertEquals(new VariableToken("x1"), parseToken("x1"));
		assertEquals(new VariableToken("a1b2c3"), parseToken("a1b2c3"));
	}

	@Test
	void whenTokenStartsWithDigitAndContainsLetters_thenThrowInvalidTokenException() {
		assertThrows(InvalidTokenException.class, () -> parseToken("1a"));
		assertThrows(InvalidTokenException.class, () -> parseToken("0aax"));
		assertThrows(InvalidTokenException.class, () -> parseToken("0x1c2"));
	}

	@Test
	void whenExpressionIsAssignment_thenReturnAssignmentExpression() {
		ParsedAssignmentExpression expectedParsedExpression = new ParsedAssignmentExpression(
				new VariableToken("x"), new AssignmentToken(), List.of(new IntegerToken(1)));
		assertEquals(expectedParsedExpression, parseAssignmentExpressionIntoTokens("x = 1"));
	}

	@Test
	void whenExpressionIsAssignmentWithAddition_thenReturnAdditionAssignmentExpression() {
		ParsedAssignmentExpression expectedParsedExpression = new ParsedAssignmentExpression(
				new VariableToken("item"), new AdditionAssignmentToken(), List.of(new IntegerToken(2)));
		assertEquals(expectedParsedExpression, parseAssignmentExpressionIntoTokens("item += 2"));
	}

	@Test
	void whenTokenIsAssignmentWithSubtraction_thenReturnSubtractionAssignmentToken() {
		ParsedAssignmentExpression expectedParsedExpression = new ParsedAssignmentExpression(
				new VariableToken("a"), new SubtractionAssignmentToken(), List.of(new VariableToken("b")));
		assertEquals(expectedParsedExpression, parseAssignmentExpressionIntoTokens("a -= b"));
	}

	@Test
	void whenTokenIsAssignmentWithMultiplication_thenReturnMultiplicationAssignmentToken() {
		ParsedAssignmentExpression expectedParsedExpression = new ParsedAssignmentExpression(
				new VariableToken("x"), new MultiplicationAssignmentToken(), List.of(new IntegerToken(3)));
		assertEquals(expectedParsedExpression, parseAssignmentExpressionIntoTokens("x *= 3"));
	}

	@Test
	void whenTokenIsAssignmentWithDivision_thenReturnDivisionAssignmentToken() {
		ParsedAssignmentExpression expectedParsedExpression = new ParsedAssignmentExpression(
				new VariableToken("i"), new DivisionAssignmentToken(), List.of(new IntegerToken(2)));
		assertEquals(expectedParsedExpression, parseAssignmentExpressionIntoTokens("i /= 2"));
	}

	@Test
	void whenTokenIsAddition_thenReturnAdditionToken() {
		assertEquals(new AdditionToken(), parseToken("+"));
	}

	@Test
	void whenTokenIsSubtraction_thenReturnSubtractionToken() {
		assertEquals(new SubtractionToken(), parseToken("-"));
	}

	@Test
	void whenTokenIsMultiplication_thenReturnMultiplicationToken() {
		assertEquals(new MultiplicationToken(), parseToken("*"));
	}

	@Test
	void whenTokenIsDivision_thenReturnDivisionToken() {
		assertEquals(new DivisionToken(), parseToken("/"));
	}

	@Test
	void whenTokenIsPreIncrementWithValidVariableName_thenReturnPreIncrementToken() {
		assertEquals(new PreIncrementToken("x"), parseToken("++x"));
	}

	@Test
	void whenTokenIsPreIncrementWithInvalidVariableName_thenThrowsInvalidTokenException() {
		assertThrows(InvalidTokenException.class, () -> parseToken("++1"));
		assertThrows(InvalidTokenException.class, () -> parseToken("++2ax"));
	}

	@Test
	void whenTokenIsPostIncrementWithValidVariableName_thenReturnPostIncrementToken() {
		assertEquals(new PostIncrementToken("x"), parseToken("x++"));
	}

	@Test
	void whenTokenIsPostIncrementWithInvalidVariableName_thenThrowsInvalidTokenException() {
		assertThrows(InvalidTokenException.class, () -> parseToken("1++"));
		assertThrows(InvalidTokenException.class, () -> parseToken("2x++"));
	}

	@Test
	void whenTokenIsPreDecrementWithValidVariableName_thenReturnPreDecrementToken() {
		assertEquals(new PreDecrementToken("y"), parseToken("--y"));
	}

	@Test
	void whenTokenIsPreDecrementWithInvalidVariableName_thenThrowsInvalidTokenException() {
		assertThrows(InvalidTokenException.class, () -> parseToken("--10"));
		assertThrows(InvalidTokenException.class, () -> parseToken("--0t"));
	}

	@Test
	void whenTokenIsPostDecrementWithValidVariableName_thenReturnPostDecrementToken() {
		assertEquals(new PostDecrementToken("y"), parseToken("y--"));
	}

	@Test
	void whenTokenIsPostDecrementWithInvalidVariableName_thenThrowsInvalidTokenException() {
		assertThrows(InvalidTokenException.class, () -> parseToken("5555--"));
		assertThrows(InvalidTokenException.class, () -> parseToken("4e--"));
	}

	@Test
	void whenTokenIsInvalidOperator_thenThrowsInvalidTokenException() {
		assertThrows(InvalidTokenException.class, () -> parseToken("+++"));
		assertThrows(InvalidTokenException.class, () -> parseToken("---"));
		assertThrows(InvalidTokenException.class, () -> parseToken("**"));
		assertThrows(InvalidTokenException.class, () -> parseToken("//"));
		assertThrows(InvalidTokenException.class, () -> parseToken("=="));
		assertThrows(InvalidTokenException.class, () -> parseToken("+=+"));
		assertThrows(InvalidTokenException.class, () -> parseToken("+=-"));
		assertThrows(InvalidTokenException.class, () -> parseToken("-=-"));
		assertThrows(InvalidTokenException.class, () -> parseToken("*=*"));
		assertThrows(InvalidTokenException.class, () -> parseToken("/=/"));
		assertThrows(InvalidTokenException.class, () -> parseToken("x="));
		assertThrows(InvalidTokenException.class, () -> parseToken("1+2"));
	}

	@Test
	void whenParsingValidExpressionToTokens_thenTokensAreOrderedByOriginalExpressionOrder() {
		List<Token> expectedTokens = List.of(new IntegerToken(1), new AdditionToken(),
				new IntegerToken(2), new MultiplicationToken(),
				new IntegerToken(3));
		assertEquals(expectedTokens, parseAssignmentExpressionIntoTokens("x = 1 + 2 * 3").getExpressionTokens());
	}

	@Test
	void whenParsingValidExpressionWithMultipleSpaces_thenNoException() {
		ParsedAssignmentExpression parsedExpression = parseAssignmentExpressionIntoTokens("x =   2");
		ParsedAssignmentExpression expectedParsedExpression = new ParsedAssignmentExpression(
				new VariableToken("x"), new AssignmentToken(), List.of(new IntegerToken(2)));
		assertEquals(expectedParsedExpression, parsedExpression);
	}

	@Test
	void whenParsingExpressionWithLessThanThreeTokens_thenThrowsInvalidNumTokensInAssignmentExpressionException() {
		assertThrows(InvalidNumTokensInAssignmentExpressionException.class, () -> parseAssignmentExpressionIntoTokens("x"));
		assertThrows(InvalidNumTokensInAssignmentExpressionException.class, () -> parseAssignmentExpressionIntoTokens("="));
		assertThrows(InvalidNumTokensInAssignmentExpressionException.class, () -> parseAssignmentExpressionIntoTokens("1"));
		assertThrows(InvalidNumTokensInAssignmentExpressionException.class, () -> parseAssignmentExpressionIntoTokens("i++"));
		assertThrows(InvalidNumTokensInAssignmentExpressionException.class, () -> parseAssignmentExpressionIntoTokens("--x"));
		assertThrows(InvalidNumTokensInAssignmentExpressionException.class, () -> parseAssignmentExpressionIntoTokens("1 a"));
		assertThrows(InvalidNumTokensInAssignmentExpressionException.class, () -> parseAssignmentExpressionIntoTokens("x = "));
	}

	@Test
	void whenParsingExpressionWithoutVariableAsFirstToken_thenThrowsMissingAssignedVariableTokenException() {
		assertThrows(MissingAssignedVariableTokenException.class, () -> parseAssignmentExpressionIntoTokens("1 + 4"));
		assertThrows(MissingAssignedVariableTokenException.class, () -> parseAssignmentExpressionIntoTokens("= 1 - 2"));
		assertThrows(MissingAssignedVariableTokenException.class, () -> parseAssignmentExpressionIntoTokens("0x = - 2"));
	}

	@Test
	void whenParsingExpressionWithoutAssignmentToken_thenThrowsMissingAssignmentTokenException() {
		assertThrows(MissingAssignmentTokenException.class, () -> parseAssignmentExpressionIntoTokens("x + 1 + 4"));
		assertThrows(MissingAssignmentTokenException.class, () -> parseAssignmentExpressionIntoTokens("count - 2"));
	}

	@Test
	void whenParsingExpressionWithMultipleAssignmentTokens_thenThrowsInvalidTokenException() {
		assertThrows(InvalidTokenException.class, () -> parseAssignmentExpressionIntoTokens("x = 1 = 2"));
		assertThrows(InvalidTokenException.class, () -> parseAssignmentExpressionIntoTokens("a = b = c = 10000"));
	}
}