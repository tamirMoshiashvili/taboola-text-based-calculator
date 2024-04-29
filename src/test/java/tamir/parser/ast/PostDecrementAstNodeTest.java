package tamir.parser.ast;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tamir.calculator.CalculatorContext;
import tamir.exception.UnknownVariableException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class PostDecrementAstNodeTest {

	private VariableAstNode variableX;
	private VariableAstNode variableY;
	private CalculatorContext context;

	@BeforeEach
	void setup() {
		variableX = new VariableAstNode("x");
		variableY = new VariableAstNode("y");
		context = new CalculatorContext();
		context.put("x", 0);
		context.put("y", 10);
	}

	@Test
	void whenPostDecrementUnknownVariable_thenInterpretThrowsUnknownVariableException() {
		assertThrows(UnknownVariableException.class, () -> new PostDecrementAstNode("unknown").interpret(context));
	}

	@Test
	void whenPostDecrementOfVariable_thenInterpretReturnsValueBeforeDecrement() {
		assertEquals(0, new PostDecrementAstNode("x").interpret(context));
	}

	@Test
	void whenPostDecrementOfVariable_thenInterpretUpdatesVariableInContextByMinusOne() {
		int xValueBeforePostDecrement = variableX.interpret(context);
		new PostDecrementAstNode("x").interpret(context);
		assertEquals(xValueBeforePostDecrement - 1, variableX.interpret(context));
	}

	@Test
	void whenPostDecrementVariable_thenInterpretDoesNotAffectOtherVariablesInTheContext() {
		int yValueBeforePostDecrement = variableY.interpret(context);
		new PostDecrementAstNode("x").interpret(context);
		assertEquals(yValueBeforePostDecrement, variableY.interpret(context));
	}
}