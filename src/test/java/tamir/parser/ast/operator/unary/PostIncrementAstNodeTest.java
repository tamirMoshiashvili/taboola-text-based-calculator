package tamir.parser.ast.operator.unary;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tamir.calculator.CalculatorContext;
import tamir.exception.UnknownVariableException;
import tamir.parser.ast.VariableAstNode;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
	void whenPostIncrementUnknownVariable_thenInterpretThrowsUnknownVariableException() {
		assertThrows(UnknownVariableException.class, () -> new PostIncrementAstNode("unknown").interpret(context));
	}

	@Test
	void whenPostIncrementOfVariable_thenInterpretReturnsValueBeforeIncrement() {
		assertEquals(2, new PostIncrementAstNode("x").interpret(context));
	}

	@Test
	void whenPostIncrementOfVariable_thenInterpretUpdatesVariableInContextByPlusOne() {
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