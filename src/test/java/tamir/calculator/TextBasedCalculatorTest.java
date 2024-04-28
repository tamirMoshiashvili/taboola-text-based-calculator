package tamir.calculator;

import org.junit.jupiter.api.Test;
import tamir.exception.UnknownVariableException;

import java.util.Map;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
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
	void whenUsingUnknownVariable_thenThrowsUnknownVariableException() {
		assertThrows(UnknownVariableException.class, () -> runTextBasedCalculator(new Scanner("x = unknown\n\n")));
	}
}