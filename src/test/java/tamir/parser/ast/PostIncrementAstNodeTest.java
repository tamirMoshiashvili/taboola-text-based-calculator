package tamir.parser.ast;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tamir.calculator.CalculatorContext;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PostIncrementAstNodeTest {

	private VariableAstNode variableX;
	private VariableAstNode variableY;
	private CalculatorContext context;

	@BeforeEach
	void setup() {
		variableX = new VariableAstNode("x");
		variableY = new VariableAstNode("y");
		context = new CalculatorContext();
		context.put("x", 2);
		context.put("y", 4);
	}

	@Test
	void whenPostIncrementOfVariable_thenInterpretReturnsValueBeforeIncrement() {
		assertEquals(2, new PostIncrementAstNode("x").interpret(context));
	}

	@Test
	void whenPostIncrementOfVariable_thenInterpretUpdatesVariableInContextToPlusOne() {
		int xValueBeforePostIncrement = variableX.interpret(context);
		new PostIncrementAstNode("x").interpret(context);
		assertEquals(xValueBeforePostIncrement + 1, variableX.interpret(context));
	}

	@Test
	void whenPostIncrementVariable_thenInterpretDoesNotAffectOtherVariablesInTheContext() {
		int yValueBeforePostIncrement = variableY.interpret(context);
		new PostIncrementAstNode("x").interpret(context);
		assertEquals(yValueBeforePostIncrement, variableY.interpret(context));
	}
}