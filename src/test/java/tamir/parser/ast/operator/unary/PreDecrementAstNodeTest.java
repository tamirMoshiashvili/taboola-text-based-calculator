package tamir.parser.ast.operator.unary;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tamir.calculator.CalculatorContext;
import tamir.exception.UnknownVariableException;
import tamir.parser.ast.VariableAstNode;
import tamir.parser.ast.operator.binary.AdditionAstNode;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
	void whenPreDecrementUnknownVariable_thenInterpretThrowsUnknownVariableException() {
		assertThrows(UnknownVariableException.class, () -> new PreDecrementAstNode("unknown").interpret(context));
	}

	@Test
	void whenPreDecrementOfVariable_thenInterpretReturnsValueAfterDecrement() {
		assertEquals(4, new PreDecrementAstNode("x").interpret(context));
	}

	@Test
	void whenPreDecrementOfVariable_thenInterpretUpdatesVariableInContextByMinusOne() {
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
		Assertions.assertEquals(3, new AdditionAstNode(preDecrementY, preDecrementY).interpret(context));
	}

	@Test
	void whenAstContainsMultipleInstancesOfVariableWithPreDecrement_thenVariableIsBeingDecrementedMultipleTimes() {
		PreDecrementAstNode preDecrementY = new PreDecrementAstNode("y");
		new AdditionAstNode(preDecrementY, preDecrementY).interpret(context);
		assertEquals(1, variableY.interpret(context));
	}
}