package tamir.parser.ast;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tamir.calculator.CalculatorContext;

import static org.junit.jupiter.api.Assertions.*;

class DivisionAstNodeTest {

	private IntegerAstNode one;
	private IntegerAstNode twelve;
	private VariableAstNode variableX;
	private VariableAstNode variableY;
	private CalculatorContext context;

	@BeforeEach
	void setup() {
		one = new IntegerAstNode(1);
		twelve = new IntegerAstNode(12);
		variableX = new VariableAstNode("x");
		variableY = new VariableAstNode("y");
		context = new CalculatorContext();
		context.put("x", 3);
		context.put("y", 6);
	}

	@Test
	void whenDividingTwoVariables_thenInterpretReturnsDivisionProduct() {
		assertEquals(2, new DivisionAstNode(variableY, variableX).interpret(context));
	}

	@Test
	void whenDividingTwoIntegers_thenInterpretReturnsDivisionProduct() {
		assertEquals(12, new DivisionAstNode(twelve, one).interpret(context));
	}

	@Test
	void whenDividingVariableByInteger_thenInterpretReturnsDivisionProduct() {
		assertEquals(6, new DivisionAstNode(variableY, one).interpret(context));
	}

	@Test
	void whenDividingIntegerByVariable_thenInterpretReturnsDivisionProduct() {
		assertEquals(2, new DivisionAstNode(twelve, variableY).interpret(context));
	}

	@Test
	void whenDividingByZero_thenInterpretThrowsArithmeticException() {
		IntegerAstNode zero = new IntegerAstNode(0);
		assertThrows(ArithmeticException.class, () -> new DivisionAstNode(twelve, zero).interpret(context));
	}

	@Test
	void whenDividingTwoIntegersWithRemainder_thenInterpretReturnsIntegerWithoutRemainder() {
		assertEquals(0, new DivisionAstNode(variableY, twelve).interpret(context));
	}

	@Test
	void whenDividingNumberByItself_thenInterpretReturnsOne() {
		assertEquals(1, new DivisionAstNode(twelve, twelve).interpret(context));
	}

	@Test
	void whenDividingPositiveNumberByPositiveNumber_thenInterpretReturnsPositiveNumber() {
		assertTrue(0 < new DivisionAstNode(twelve, one).interpret(context));
	}

	@Test
	void whenDividingPositiveNumberByNegativeNumber_thenInterpretReturnsNegativeNumber() {
		IntegerAstNode minusOne = new IntegerAstNode(-1);
		assertTrue(0 > new DivisionAstNode(twelve, minusOne).interpret(context));
	}

	@Test
	void whenDividingNegativeNumberByNegativeNumber_thenInterpretReturnsPositiveNumber() {
		IntegerAstNode minusTen = new IntegerAstNode(-10);
		IntegerAstNode minusOne = new IntegerAstNode(-1);
		assertTrue(0 < new DivisionAstNode(minusTen, minusOne).interpret(context));
	}
}