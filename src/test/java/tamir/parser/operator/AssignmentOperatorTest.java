package tamir.parser.operator;

import org.junit.jupiter.api.Test;
import tamir.exception.InvalidAssignmentOperatorException;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static tamir.parser.operator.AssignmentOperator.*;

class AssignmentOperatorTest {

	@Test
	void whenTokenIsAssignmentOperator_thenGetFromTokenDoesNotThrowException() {
		Arrays.asList(AssignmentOperator.values())
				.forEach(assignmentOperator -> assertDoesNotThrow(() -> AssignmentOperator.fromToken(assignmentOperator.getToken())));
	}

	@Test
	void whenTokenIsAssignmentOperator_thenGetFromTokenReturnAssignmentOperator() {
		assertEquals(ASSIGNMENT, AssignmentOperator.fromToken("="));
		assertEquals(ADDITION_ASSIGNMENT, AssignmentOperator.fromToken("+="));
		assertEquals(SUBTRACTION_ASSIGNMENT, AssignmentOperator.fromToken("-="));
		assertEquals(MULTIPLICATION_ASSIGNMENT, AssignmentOperator.fromToken("*="));
		assertEquals(DIVISION_ASSIGNMENT, AssignmentOperator.fromToken("/="));
	}

	@Test
	void whenTokenIsNotAssignmentOperator_thenGetFromTokenThrowsInvalidAssignmentOperatorException() {
		assertThrows(InvalidAssignmentOperatorException.class, () -> AssignmentOperator.fromToken("=="));
		assertThrows(InvalidAssignmentOperatorException.class, () -> AssignmentOperator.fromToken("^="));
		assertThrows(InvalidAssignmentOperatorException.class, () -> AssignmentOperator.fromToken("%="));
		assertThrows(InvalidAssignmentOperatorException.class, () -> AssignmentOperator.fromToken("&="));
		assertThrows(InvalidAssignmentOperatorException.class, () -> AssignmentOperator.fromToken("0"));
	}
}