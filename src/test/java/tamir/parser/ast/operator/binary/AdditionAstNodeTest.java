package tamir.parser.ast.operator.binary;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tamir.calculator.CalculatorContext;
import tamir.parser.ast.IntegerAstNode;
import tamir.parser.ast.VariableAstNode;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AdditionAstNodeTest {

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
	void whenAddingTwoVariables_thenInterpretToSumOfValues() {
		assertEquals(8, new AdditionAstNode(variableX, variableY).interpret(context));
	}

	@Test
	void whenAddingTwoIntegers_thenInterpretToSumOfValues() {
		assertEquals(3, new AdditionAstNode(one, two).interpret(context));
	}

	@Test
	void whenAddingIntegerAndVariable_thenInterpretToSumOfValues() {
		assertEquals(4, new AdditionAstNode(one, variableX).interpret(context));
	}

	@Test
	void whenAddingVariableAndInteger_thenInterpretToSumOfValues() {
		assertEquals(7, new AdditionAstNode(variableY, two).interpret(context));
	}

	@Test
	void testAdditionIsCommutative() {
		int variableWithInteger = new AdditionAstNode(variableY, two).interpret(context);
		int integerWithVariable = new AdditionAstNode(two, variableY).interpret(context);
		assertEquals(variableWithInteger, integerWithVariable);
	}

	@Test
	void whenAddingZeroToVariable_thenSumEqualsToVariableValue() {
		IntegerAstNode zero = new IntegerAstNode(0);
		assertEquals(variableX.interpret(context), new AdditionAstNode(zero, variableX).interpret(context));
	}

	@Test
	void whenAddingTwoPositiveNumber_thenInterpretReturnsPositiveNumber() {
		assertTrue(new AdditionAstNode(one, two).interpret(context) > 0);
	}

	@Test
	void whenAddingTwoNegativeNumbers_thenInterpretReturnsNegativeNumber() {
		IntegerAstNode minusOne = new IntegerAstNode(-1);
		IntegerAstNode minusTwo = new IntegerAstNode(-2);
		assertTrue(new AdditionAstNode(minusOne, minusTwo).interpret(context) < 0);
	}
}