package tamir.calculator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tamir.exception.UnknownVariableException;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CalculatorContextTest {

	private CalculatorContext context;

	@BeforeEach
	void setup() {
		context = new CalculatorContext();
		context.put("count", 10);
	}

	@Test
	void whenPutNewVariableToContext_thenVariableWasAdded() {
		context.put("x", 1);
		assertEquals(1, context.get("x"));
	}

	@Test
	void whenPutValueToExistingVariable_thenVariableValueHasBeenUpdated() {
		assertEquals(10, context.get("count"));
		context.put("count", 4);
		assertEquals(4, context.get("count"));
	}

	@Test
	void whenGettingVariableValueOfUnknownVariable_thenThrowsUnknownVariableException() {
		assertThrows(UnknownVariableException.class, () -> context.get("unknown"));
	}

	@Test
	void whenAlteringVariableToValueMap_thenContextNotBeingChanged() {
		assertEquals(10, context.get("count"));
		Map<String, Integer> variableToValue = context.getVariableToValue();
		variableToValue.put("count", 1);
		assertEquals(10, context.get("count"));
	}
}