package tamir.parser.ast;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tamir.calculator.CalculatorContext;
import tamir.exception.UnknownVariableException;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class VariableAstNodeTest {

	private VariableAstNode variableX;
	private VariableAstNode variableY;
	private CalculatorContext context;

	@BeforeEach
	void setup() {
		variableX = new VariableAstNode("x");
		variableY = new VariableAstNode("y");
		context = new CalculatorContext();
		context.put("x", 1);
		context.put("y", 2);
	}

	@Test
	void testInterpretOfVariableReturnsValueOfVariableFromContext() {
		assertEquals(1, variableX.interpret(context));
		assertEquals(2, variableY.interpret(context));
	}

	@Test
	void whenInterpretVariable_thenContextDoesNotChange() {
		Map<String, Integer> contextBeforeInterpret = context.getVariableToValue();
		variableX.interpret(context);
		assertEquals(contextBeforeInterpret, context.getVariableToValue());
	}

	@Test
	void whenInterpretUnknownVariable_thenThrowsUnknownVariableException() {
		assertThrows(UnknownVariableException.class, () -> new VariableAstNode("unknown").interpret(context));
	}
}