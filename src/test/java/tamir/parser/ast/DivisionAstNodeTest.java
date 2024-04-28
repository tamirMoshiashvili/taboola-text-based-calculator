package tamir.parser.ast;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tamir.calculator.CalculatorContext;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class DivisionAstNodeTest {

	private IntegerAstNode integer1;
	private IntegerAstNode integer2;
	private VariableAstNode variableX;
	private VariableAstNode variableY;
	private CalculatorContext context;

	@BeforeEach
	void setup() {
		integer1 = new IntegerAstNode(1);
		integer2 = new IntegerAstNode(12);
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
		assertEquals(12, new DivisionAstNode(integer2, integer1).interpret(context));
	}

	@Test
	void whenDividingVariableByInteger_thenInterpretReturnsDivisionProduct() {
		assertEquals(6, new DivisionAstNode(variableY, integer1).interpret(context));
	}

	@Test
	void whenDividingIntegerByVariable_thenInterpretReturnsDivisionProduct() {
		assertEquals(2, new DivisionAstNode(integer2, variableY).interpret(context));
	}

	@Test
	void whenDividingByZero_thenInterpretThrowsArithmeticException() {
		assertThrows(ArithmeticException.class, () -> new DivisionAstNode(integer2, new IntegerAstNode(0)).interpret(context));
	}

	@Test
	void whenDividingTwoIntegersWithRemainder_thenInterpretReturnIntegerWithoutRemainder() {
		assertEquals(0, new DivisionAstNode(variableY, integer2).interpret(context));
	}
}