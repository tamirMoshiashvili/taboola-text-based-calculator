package tamir.calculator;

import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;
import static tamir.calculator.TextBasedCalculator.runTextBasedCalculator;

class TextBasedCalculatorTest {

	@Test
	void whenAssigningVariableWithInteger_thenTextCalculatorReturnsSingleVariableWithIntegerValue() {
		Map<String, Integer> variableToValue = runTextBasedCalculator(new Scanner("x = 1\n\n"));
		assertEquals(Map.of("x", 1), variableToValue);
	}

	@Test
	void testAssignmentDescriptionExample() {
		String input = String.join("\n",
				"i = 0",
				"j = ++i",
				"x = i++ + 5",
				"y = 5 + 3 * 10",
				"i += y",
				"\n");
		Map<String, Integer> variableToValue = runTextBasedCalculator(new Scanner(input));
		assertEquals(Map.of("i", 37, "j", 1, "x", 6, "y", 35), variableToValue);
	}

	@Test
	void whenEachExpressionSetsNewVariable_thenNumVariablesEqualsNumExpressions() {
		String input = String.join("\n",
				"a = 1",
				"b = 2",
				"c = 3",
				"\n");
		Map<String, Integer> variableToValue = runTextBasedCalculator(new Scanner(input));
		assertEquals(3, variableToValue.size());
	}

	@Test
	void whenExpressionIsInvalid_thenNoException() {
		assertDoesNotThrow(() -> runTextBasedCalculator(new Scanner("x = unknown\n\n")));
	}

	@Test
	void whenNoExpressionHasBeenEntered_thenVariablesAreEmpty() {
		Map<String, Integer> variableToValues = runTextBasedCalculator(new Scanner("\n"));
		assertTrue(variableToValues.isEmpty());
	}
}