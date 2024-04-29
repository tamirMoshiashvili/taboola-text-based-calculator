package tamir.calculator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tamir.exception.UnknownVariableException;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class CalculatorContextTest {

	private CalculatorContext context;

	@BeforeEach
	void setup() {
		context = new CalculatorContext();
		context.put("count", 10);
	}

	@Test
	void whenPutNewVariableToContext_thenVariableWasAdded() {
		assertFalse(context.getVariableToValue().containsKey("x"));
		context.put("x", 1);
		assertEquals(1, context.get("x"));
	}

	@Test
	void whenPutValueToExistingVariable_thenVariableValueHasBeenUpdated() {
		int countValueBeforeChange = context.get("count");
		int updatedValue = countValueBeforeChange + 1;
		context.put("count", updatedValue);
		assertEquals(updatedValue, context.get("count"));
	}

	@Test
	void whenGettingVariableValueOfUnknownVariable_thenThrowsUnknownVariableException() {
		assertThrows(UnknownVariableException.class, () -> context.get("unknown"));
	}

	@Test
	void whenAlteringVariableToValueMap_thenContextNotBeingChanged() {
		int countValueBeforeChange = context.get("count");
		Map<String, Integer> variableToValue = context.getVariableToValue();
		variableToValue.put("count", countValueBeforeChange + 1);
		assertEquals(countValueBeforeChange, context.get("count"));
	}

	@Test
	void testVariableNameIsCaseSensitive() {
		assertEquals(1, context.getVariableToValue().size());
		String lowercaseVariableName = context.getVariableToValue().keySet().iterator().next();
		int lowercaseVariableValue = context.get(lowercaseVariableName);
		String uppercaseVariableName = lowercaseVariableName.toUpperCase();
		int uppercaseVariableValue = lowercaseVariableValue + 1;
		assertNotEquals(lowercaseVariableValue, uppercaseVariableValue);
		context.put(uppercaseVariableName, uppercaseVariableValue);

		Map<String, Integer> expectedVariableToValueAfterChange = Map.of(lowercaseVariableName, lowercaseVariableValue,
				uppercaseVariableName, uppercaseVariableValue);
		assertEquals(expectedVariableToValueAfterChange, context.getVariableToValue());
	}
}