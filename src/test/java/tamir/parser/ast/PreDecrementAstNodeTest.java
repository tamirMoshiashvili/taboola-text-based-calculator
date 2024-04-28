package tamir.parser.ast;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tamir.calculator.CalculatorContext;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PreDecrementAstNodeTest {

	private VariableAstNode variableX;
	private VariableAstNode variableY;
	private CalculatorContext context;

	@BeforeEach
	void setup() {
		variableX = new VariableAstNode("x");
		variableY = new VariableAstNode("y");
		context = new CalculatorContext();
		context.put("x", 5);
		context.put("y", 3);
	}

	@Test
	void whenPreDecrementOfVariable_thenInterpretReturnsValueAfterDecrement() {
		assertEquals(4, new PreDecrementAstNode("x").interpret(context));
	}

	@Test
	void whenPreDecrementOfVariable_thenInterpretUpdatesVariableInContextToMinusOne() {
		int xValueBeforePreDecrement = variableX.interpret(context);
		new PreDecrementAstNode("x").interpret(context);
		assertEquals(xValueBeforePreDecrement - 1, variableX.interpret(context));
	}

	@Test
	void whenPreDecrementVariable_thenInterpretDoesNotAffectOtherVariablesInTheContext() {
		int yValueBeforePreDecrement = variableY.interpret(context);
		new PreDecrementAstNode("x").interpret(context);
		assertEquals(yValueBeforePreDecrement, variableY.interpret(context));
	}

	@Test
	void whenAstContainsMultipleInstancesOfVariableWithPreDecrement_thenValueBeingCalculatedByPreCalculatedValue() {
		PreDecrementAstNode preDecrementY = new PreDecrementAstNode("y");
		assertEquals(3, new AdditionAstNode(preDecrementY, preDecrementY).interpret(context));
	}

	@Test
	void whenAstContainsMultipleInstancesOfVariableWithPreDecrement_thenVariableIsBeingDecrementedMultipleTimes() {
		PreDecrementAstNode preDecrementY = new PreDecrementAstNode("y");
		new AdditionAstNode(preDecrementY, preDecrementY).interpret(context);
		assertEquals(1, variableY.interpret(context));
	}
}