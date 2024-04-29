package tamir.parser.ast;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tamir.calculator.CalculatorContext;
import tamir.exception.UnknownVariableException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class PreIncrementAstNodeTest {

	private VariableAstNode variableX;
	private VariableAstNode variableY;
	private CalculatorContext context;

	@BeforeEach
	void setup() {
		variableX = new VariableAstNode("x");
		variableY = new VariableAstNode("y");
		context = new CalculatorContext();
		context.put("x", 44);
		context.put("y", 2);
	}

	@Test
	void whenPreIncrementUnknownVariable_thenInterpretThrowsUnknownVariableException() {
		assertThrows(UnknownVariableException.class, () -> new PreIncrementAstNode("unknown").interpret(context));
	}

	@Test
	void whenPreIncrementOfVariable_thenInterpretReturnsValueAfterIncrement() {
		assertEquals(45, new PreIncrementAstNode("x").interpret(context));
	}

	@Test
	void whenPreIncrementOfVariable_thenInterpretUpdatesVariableInContextByPlusOne() {
		int xValueBeforePreIncrement = variableX.interpret(context);
		new PreIncrementAstNode("x").interpret(context);
		assertEquals(xValueBeforePreIncrement + 1, variableX.interpret(context));
	}

	@Test
	void whenPreIncrementVariable_thenInterpretDoesNotAffectOtherVariablesInTheContext() {
		int yValueBeforePreIncrement = variableY.interpret(context);
		new PreIncrementAstNode("x").interpret(context);
		assertEquals(yValueBeforePreIncrement, variableY.interpret(context));
	}

	@Test
	void whenAstContainsMultipleInstancesOfVariableWithPreIncrement_thenValueBeingCalculatedByPreCalculatedValue() {
		PreIncrementAstNode preIncrementY = new PreIncrementAstNode("y");
		assertEquals(7, new AdditionAstNode(preIncrementY, preIncrementY).interpret(context));
	}
}