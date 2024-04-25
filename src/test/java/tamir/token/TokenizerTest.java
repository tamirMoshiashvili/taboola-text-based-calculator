package tamir.token;

import org.junit.jupiter.api.Test;
import tamir.exception.BlankExpressionException;
import tamir.exception.InvalidTokenException;

import static org.junit.jupiter.api.Assertions.*;
import static tamir.token.Tokenizer.parseToken;
import static tamir.token.Tokenizer.parseTokens;

class TokenizerTest {

	@Test
	void whenExpressionIsNull_thenThrowsBlankExpressionException() {
		assertThrows(BlankExpressionException.class, () -> parseTokens(null));
	}

	@Test
	void whenExpressionIsEmpty_thenThrowsBlankExpressionException() {
		assertThrows(BlankExpressionException.class, () -> parseTokens(""));
	}

	@Test
	void whenExpressionIsSingleSpace_thenThrowsBlankExpressionException() {
		assertThrows(BlankExpressionException.class, () -> parseTokens(" "));
	}

	@Test
	void whenExpressionIsMultipleSpaces_thenThrowsBlankExpressionException() {
		assertThrows(BlankExpressionException.class, () -> parseTokens("  "));
	}

	@Test
	void whenExpressionIsTab_thenThrowsBlankExpressionException() {
		assertThrows(BlankExpressionException.class, () -> parseTokens("\t"));
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
	void whenTokenIsVariableThenReturnVariableToken() {
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
	void whenTokenIsAssignment_thenReturnAssignmentToken() {
		assertEquals(new AssignmentToken(), parseToken("="));
	}

	@Test
	void whenTokenIsAssignmentWithAddition_thenReturnAdditionAssignmentToken() {
		assertEquals(new AdditionAssignmentToken(), parseToken("+="));
	}

	@Test
	void whenTokenIsAssignmentWithSubtraction_thenReturnSubtractionAssignmentToken() {
		assertEquals(new SubtractionAssignmentToken(), parseToken("-="));
	}

	@Test
	void whenTokenIsAssignmentWithMultiplication_thenReturnMultiplicationAssignmentToken() {
		assertEquals(new MultiplicationAssignmentToken(), parseToken("*="));
	}

	@Test
	void whenTokenIsAssignmentWithDivision_thenReturnDivisionAssignmentToken() {
		assertEquals(new DivisionAssignmentToken(), parseToken("/="));
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
}