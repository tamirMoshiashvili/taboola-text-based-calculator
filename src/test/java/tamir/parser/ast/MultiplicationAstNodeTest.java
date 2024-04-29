package tamir.parser.ast;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tamir.calculator.CalculatorContext;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MultiplicationAstNodeTest {
	private IntegerAstNode two;
	private IntegerAstNode three;
	private VariableAstNode variableX;
	private VariableAstNode variableY;
	private CalculatorContext context;

	@BeforeEach
	void setup() {
		two = new IntegerAstNode(2);
		three = new IntegerAstNode(3);
		variableX = new VariableAstNode("x");
		variableY = new VariableAstNode("y");
		context = new CalculatorContext();
		context.put("x", 2);
		context.put("y", 4);
	}

	@Test
	void whenMultiplyingTwoVariables_thenInterpretReturnsMultiplicationProduct() {
		assertEquals(8, new MultiplicationAstNode(variableX, variableY).interpret(context));
	}

	@Test
	void whenMultiplyingTwoIntegers_thenInterpretReturnsMultiplicationProduct() {
		assertEquals(6, new MultiplicationAstNode(two, three).interpret(context));
	}

	@Test
	void whenMultiplyingVariableByInteger_thenInterpretReturnsMultiplicationProduct() {
		assertEquals(8, new MultiplicationAstNode(variableY, two).interpret(context));
	}

	@Test
	void whenMultiplyingIntegerByVariable_thenInterpretReturnsMultiplicationProduct() {
		assertEquals(12, new MultiplicationAstNode(three, variableY).interpret(context));
	}

	@Test
	void testMultiplicationIsCommutative() {
		int twoByThree = new MultiplicationAstNode(two, three).interpret(context);
		int threeByTwo = new MultiplicationAstNode(three, two).interpret(context);
		assertEquals(twoByThree, threeByTwo);
	}

	@Test
	void whenMultiplyingByOne_thenInterpretReturnsOtherOperandValue() {
		IntegerAstNode one = new IntegerAstNode(1);
		assertEquals(two.interpret(context), new MultiplicationAstNode(one, two).interpret(context));
		assertEquals(three.interpret(context), new MultiplicationAstNode(one, three).interpret(context));
		assertEquals(variableY.interpret(context), new MultiplicationAstNode(one, variableY).interpret(context));
	}

	@Test
	void whenMultiplyingByZero_thenReturnZero() {
		IntegerAstNode zero = new IntegerAstNode(0);
		assertEquals(0, new MultiplicationAstNode(zero, two).interpret(context));
		assertEquals(0, new MultiplicationAstNode(zero, three).interpret(context));
		assertEquals(0, new MultiplicationAstNode(zero, variableY).interpret(context));
	}

	@Test
	void whenMultiplyingPositiveNumberByPositiveNumber_thenInterpretReturnsPositiveNumber() {
		assertTrue(0 < new MultiplicationAstNode(two, three).interpret(context));
	}

	@Test
	void whenMultiplyingPositiveNumberByNegativeNumber_thenInterpretReturnsNegativeNumber() {
		IntegerAstNode minusOne = new IntegerAstNode(-1);
		assertTrue(0 > new MultiplicationAstNode(two, minusOne).interpret(context));
	}

	@Test
	void whenMultiplyingNegativeNumberByNegativeNumber_thenInterpretReturnsPositiveNumber() {
		IntegerAstNode minusTen = new IntegerAstNode(-10);
		IntegerAstNode minusOne = new IntegerAstNode(-1);
		assertTrue(0 < new MultiplicationAstNode(minusTen, minusOne).interpret(context));
	}
}