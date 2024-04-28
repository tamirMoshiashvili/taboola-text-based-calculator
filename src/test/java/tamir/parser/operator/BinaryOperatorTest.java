package tamir.parser.operator;

import org.junit.jupiter.api.Test;
import tamir.exception.InvalidBinaryOperatorException;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static tamir.parser.operator.BinaryOperator.*;

class BinaryOperatorTest {

	@Test
	void whenComparingPrecedence_thenAdditionEqualsToSubtraction() {
		assertEquals(ADDITION.getPrecedence(), SUBTRACTION.getPrecedence());
	}

	@Test
	void whenComparingPrecedence_thenMultiplicationEqualsToDivision() {
		assertEquals(MULTIPLICATION.getPrecedence(), DIVISION.getPrecedence());
	}

	@Test
	void whenComparingPrecedence_thenAdditionIsLowerThanMultiplicationAndDivision() {
		assertTrue(ADDITION.getPrecedence() < MULTIPLICATION.getPrecedence());
		assertTrue(ADDITION.getPrecedence() < DIVISION.getPrecedence());
	}

	@Test
	void whenComparingPrecedence_thenSubtractionIsLowerThanMultiplicationAndDivision() {
		assertTrue(SUBTRACTION.getPrecedence() < MULTIPLICATION.getPrecedence());
		assertTrue(SUBTRACTION.getPrecedence() < DIVISION.getPrecedence());
	}

	@Test
	void whenTokenIsNotBinaryOperator_thenGetFromTokenThrowsInvalidBinaryOperatorException() {
		assertThrows(InvalidBinaryOperatorException.class, () -> BinaryOperator.getFromToken("++"));
		assertThrows(InvalidBinaryOperatorException.class, () -> BinaryOperator.getFromToken("--"));
		assertThrows(InvalidBinaryOperatorException.class, () -> BinaryOperator.getFromToken("**"));
		assertThrows(InvalidBinaryOperatorException.class, () -> BinaryOperator.getFromToken("//"));
		assertThrows(InvalidBinaryOperatorException.class, () -> BinaryOperator.getFromToken("^"));
		assertThrows(InvalidBinaryOperatorException.class, () -> BinaryOperator.getFromToken("1"));
		assertThrows(InvalidBinaryOperatorException.class, () -> BinaryOperator.getFromToken("~"));
		assertThrows(InvalidBinaryOperatorException.class, () -> BinaryOperator.getFromToken("!"));
		assertThrows(InvalidBinaryOperatorException.class, () -> BinaryOperator.getFromToken("%"));
	}

	@Test
	void whenTokenIsBinaryOperator_thenGetFromTokenReturnBinaryOperator() {
		assertEquals(ADDITION, BinaryOperator.getFromToken("+"));
		assertEquals(SUBTRACTION, BinaryOperator.getFromToken("-"));
		assertEquals(MULTIPLICATION, BinaryOperator.getFromToken("*"));
		assertEquals(DIVISION, BinaryOperator.getFromToken("/"));
	}

	@Test
	void whenTokenIsBinaryOperator_thenGetFromTokenDoesNotThrowException() {
		Arrays.asList(BinaryOperator.values())
				.forEach(binaryOperator -> assertDoesNotThrow(() -> BinaryOperator.getFromToken(binaryOperator.getToken())));
	}
}