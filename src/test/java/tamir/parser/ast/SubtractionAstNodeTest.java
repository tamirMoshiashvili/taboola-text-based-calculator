package tamir.parser.ast;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tamir.calculator.CalculatorContext;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SubtractionAstNodeTest {

	private IntegerAstNode one;
	private IntegerAstNode two;
	private VariableAstNode variableX;
	private VariableAstNode variableY;
	private CalculatorContext context;

	@BeforeEach
	void setup() {
		one = new IntegerAstNode(1);
		two = new IntegerAstNode(2);
		variableX = new VariableAstNode("x");
		variableY = new VariableAstNode("y");
		context = new CalculatorContext();
		context.put("x", 3);
		context.put("y", 5);
	}

	@Test
	void whenSubtractingTwoVariables_thenInterpretToDifferenceOfValues() {
		assertEquals(-2, new SubtractionAstNode(variableX, variableY).interpret(context));
	}

	@Test
	void whenSubtractingTwoIntegers_thenInterpretToDifferenceOfValues() {
		assertEquals(-1, new SubtractionAstNode(one, two).interpret(context));
	}

	@Test
	void whenSubtractingIntegerAndVariable_thenInterpretToDifferenceOfValues() {
		assertEquals(-2, new SubtractionAstNode(one, variableX).interpret(context));
	}

	@Test
	void whenSubtractingVariableAndInteger_thenInterpretToDifferenceOfValues() {
		assertEquals(3, new SubtractionAstNode(variableY, two).interpret(context));
	}

	@Test
	void whenSubtractingZeroFromValue_thenInterpretReturnValueWithoutChange() {
		IntegerAstNode zero = new IntegerAstNode(0);
		assertEquals(1, new SubtractionAstNode(one, zero).interpret(context));
	}

	@Test
	void whenSubtractingPositiveNumberFromValue_thenValueHasBeenDecreased() {
		int xValueBeforeChange = variableX.interpret(context);
		assertTrue(xValueBeforeChange > new SubtractionAstNode(variableX, one).interpret(context));
	}

	@Test
	void whenSubtractingNegativeNumberFromValue_thenValueHasBeenIncreased() {
		int xValueBeforeChange = variableX.interpret(context);
		IntegerAstNode minusOne = new IntegerAstNode(-1);
		assertTrue(xValueBeforeChange < new SubtractionAstNode(variableX, minusOne).interpret(context));
	}
}