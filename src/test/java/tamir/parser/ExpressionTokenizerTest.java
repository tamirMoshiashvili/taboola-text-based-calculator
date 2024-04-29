package tamir.parser;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static tamir.parser.ExpressionTokenizer.*;

class ExpressionTokenizerTest {

	@Test
	void whenExpressionIsBlank_thenSplitIntoTokensReturnEmptyList() {
		assertTrue(splitIntoTokens("").isEmpty());
		assertTrue(splitIntoTokens(" ").isEmpty());
		assertTrue(splitIntoTokens("    ").isEmpty());
		assertTrue(splitIntoTokens("\t").isEmpty());
	}

	@Test
	void whenExpressionIsSingleToken_thenSplitIntoTokensReturnSingletonList() {
		assertEquals(List.of("x"), splitIntoTokens("x"));
		assertEquals(List.of("1"), splitIntoTokens("1"));
		assertEquals(List.of("holla"), splitIntoTokens("holla"));
		assertEquals(List.of("+"), splitIntoTokens("+"));
		assertEquals(List.of("="), splitIntoTokens("="));
		assertEquals(List.of("i++"), splitIntoTokens("i++"));
	}

	@Test
	void whenExpressionContainsMultipleTokens_thenSplitIntoTokensReturnListOfTokensSeparatedBySpace() {
		assertEquals(List.of("x", "=", "1"), splitIntoTokens("x = 1"));
	}

	@Test
	void whenExpressionContainsRedundantSpaces_thenSpacesBeingFilteredOut() {
		assertEquals(List.of("x", "=", "1"), splitIntoTokens("  x     = \t 1"));
	}

	@Test
	void whenTokenContainsLettersDifferentFromAToZ_thenIsVariableReturnsFalse() {
		assertFalse(isVariable("a!"));
		assertFalse(isVariable("a#"));
		assertFalse(isVariable("a~~~"));
		assertFalse(isVariable("<x>"));
		assertFalse(isVariable("x/\\"));
		assertFalse(isVariable("t   "));
	}

	@Test
	void whenTokenStartsWithLetterAndContainsLettersAndOrDigits_thenIsVariableReturnTrue() {
		assertTrue(isVariable("x"));
		assertTrue(isVariable("x1"));
		assertTrue(isVariable("xyz"));
		assertTrue(isVariable("h12345"));
		assertTrue(isVariable("item"));
		assertTrue(isVariable("HAHAHA"));
		assertTrue(isVariable("Holla"));
	}

	@Test
	void whenTokenDoesNotStartWithLetter_thenIsVariableReturnFalse() {
		assertFalse(isVariable("1"));
		assertFalse(isVariable("1x"));
		assertFalse(isVariable("=y"));
		assertFalse(isVariable("~c"));
		assertFalse(isVariable("!c"));
		assertFalse(isVariable("++c"));
		assertFalse(isVariable("-C"));
	}

	@Test
	void whenTokenContainsDigitsOfPositiveNumber_thenIsIntegerReturnsTrue() {
		assertTrue(isInteger("1"));
		assertTrue(isInteger("100"));
		assertTrue(isInteger(String.valueOf(Integer.MAX_VALUE)));
	}

	@Test
	void whenTokenIsZero_thenIsIntegerReturnsTrue() {
		assertTrue(isInteger("0"));
	}

	@Test
	void whenTokenIsNegativeZero_thenIsIntegerReturnsTrue() {
		assertTrue(isInteger("-0"));
	}

	@Test
	void whenTokenStartsWithNegativeSignAndContainsDigits_thenIsIntegerReturnTrue() {
		assertTrue(isInteger("-1"));
		assertTrue(isInteger("-5555"));
		assertTrue(isInteger(String.valueOf(Integer.MIN_VALUE)));
	}

	@Test
	void whenTokenContainsFloatingPoint_thenIsIntegerReturnsFalse() {
		assertFalse(isInteger("0."));
		assertFalse(isInteger("0.0"));
		assertFalse(isInteger("1.1"));
		assertFalse(isInteger("1.234"));
		assertFalse(isInteger("127.0.0.1"));
		assertFalse(isInteger("."));
	}

	@Test
	void whenTokenStartsWithDoublePlusAndContainsValidVariableName_thenIsPreIncrementReturnsTrue() {
		assertTrue(isPreIncrement("++x"));
		assertTrue(isPreIncrement("++a"));
		assertTrue(isPreIncrement("++item"));
		assertTrue(isPreIncrement("++www123"));
	}

	@Test
	void whenTokenStartsWithDoublePlusButDoesNotContainValidVariableName_thenIsPreIncrementReturnsFalse() {
		assertFalse(isPreIncrement("++"));
		assertFalse(isPreIncrement("+++"));
		assertFalse(isPreIncrement("++1"));
		assertFalse(isPreIncrement("++1000"));
		assertFalse(isPreIncrement("++5x"));
		assertFalse(isPreIncrement("++5x5"));
	}

	@Test
	void whenTokenContainsSinglePlus_thenIsPreIncrementReturnsFalse() {
		assertFalse(isPreIncrement("+"));
	}

	@Test
	void whenTokenEndsWithDoublePlusAndContainsValidVariableName_thenIsPostIncrementReturnsTrue() {
		assertTrue(isPostIncrement("x++"));
		assertTrue(isPostIncrement("a++"));
		assertTrue(isPostIncrement("item++"));
		assertTrue(isPostIncrement("www123++"));
	}

	@Test
	void whenTokenEndsWithDoublePlusButDoesNotContainValidVariableName_thenIsPostIncrementReturnsFalse() {
		assertFalse(isPostIncrement("++"));
		assertFalse(isPostIncrement("+++"));
		assertFalse(isPostIncrement("1++"));
		assertFalse(isPostIncrement("1000++"));
		assertFalse(isPostIncrement("5x++"));
		assertFalse(isPostIncrement("5x5++"));
	}

	@Test
	void whenTokenContainsSinglePlus_thenIsPostIncrementReturnsFalse() {
		assertFalse(isPostIncrement("+"));
	}

	@Test
	void whenTokenStartsWithDoubleMinusAndContainsValidVariableName_thenIsPreDecrementReturnsTrue() {
		assertTrue(isPreDecrement("--x"));
		assertTrue(isPreDecrement("--a"));
		assertTrue(isPreDecrement("--item"));
		assertTrue(isPreDecrement("--www123"));
	}

	@Test
	void whenTokenStartsWithDoubleMinusButDoesNotContainValidVariableName_thenIsPreDecrementReturnsFalse() {
		assertFalse(isPreDecrement("--"));
		assertFalse(isPreDecrement("---"));
		assertFalse(isPreDecrement("--1"));
		assertFalse(isPreDecrement("--1000"));
		assertFalse(isPreDecrement("--5x"));
		assertFalse(isPreDecrement("--5x5"));
	}

	@Test
	void whenTokenContainsSingleMinus_thenIsPreDecrementReturnsFalse() {
		assertFalse(isPreDecrement("-"));
	}

	@Test
	void whenTokenEndsWithDoubleMinusAndContainsValidVariableName_thenIsPostDecrementReturnsTrue() {
		assertTrue(isPostDecrement("x--"));
		assertTrue(isPostDecrement("a--"));
		assertTrue(isPostDecrement("item--"));
		assertTrue(isPostDecrement("www123--"));
	}

	@Test
	void whenTokenEndsWithDoubleMinusButDoesNotContainValidVariableName_thenIsPostDecrementReturnsFalse() {
		assertFalse(isPostDecrement("--"));
		assertFalse(isPostDecrement("---"));
		assertFalse(isPostDecrement("1--"));
		assertFalse(isPostDecrement("1000--"));
		assertFalse(isPostDecrement("5x--"));
		assertFalse(isPostDecrement("5x5--"));
	}

	@Test
	void whenTokenContainsSingleMinus_thenIsPostDecrementReturnsFalse() {
		assertFalse(isPostDecrement("-"));
	}

	@Test
	void whenPreUnaryOperatorContainsValidVariableName_thenGetVariableNameOfPreUnaryOperatorTokenReturnsVariableName() {
		assertEquals("x", getVariableNameOfPreUnaryOperatorToken("++x"));
		assertEquals("x", getVariableNameOfPreUnaryOperatorToken("--x"));
		assertEquals("abc", getVariableNameOfPreUnaryOperatorToken("--abc"));
		assertEquals("item", getVariableNameOfPreUnaryOperatorToken("++item"));
		assertEquals("a1", getVariableNameOfPreUnaryOperatorToken("++a1"));
	}

	@Test
	void whenPostUnaryOperatorContainsValidVariableName_thenGetVariableNameOfPostUnaryOperatorTokenReturnsVariableName() {
		assertEquals("x", getVariableNameOfPostUnaryOperatorToken("x++"));
		assertEquals("x", getVariableNameOfPostUnaryOperatorToken("x--"));
		assertEquals("abc", getVariableNameOfPostUnaryOperatorToken("abc--"));
		assertEquals("item", getVariableNameOfPostUnaryOperatorToken("item++"));
		assertEquals("a1", getVariableNameOfPostUnaryOperatorToken("a1++"));
	}
}