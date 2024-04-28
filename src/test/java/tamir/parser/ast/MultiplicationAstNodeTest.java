package tamir.parser.ast;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tamir.calculator.CalculatorContext;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MultiplicationAstNodeTest {
	private IntegerAstNode integer1;
	private IntegerAstNode integer2;
	private VariableAstNode variableX;
	private VariableAstNode variableY;
	private CalculatorContext context;

	@BeforeEach
	void setup() {
		integer1 = new IntegerAstNode(2);
		integer2 = new IntegerAstNode(3);
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
		assertEquals(6, new MultiplicationAstNode(integer1, integer2).interpret(context));
	}

	@Test
	void whenMultiplyingVariableByInteger_thenInterpretReturnsMultiplicationProduct() {
		assertEquals(8, new MultiplicationAstNode(variableY, integer1).interpret(context));
	}

	@Test
	void whenMultiplyingIntegerByVariable_thenInterpretReturnsMultiplicationProduct() {
		assertEquals(12, new MultiplicationAstNode(integer2, variableY).interpret(context));
	}

	@Test
	void testMultiplicationIsCommutative() {
		int integer1ByInteger2 = new MultiplicationAstNode(integer1, integer2).interpret(context);
		int integer2ByInteger1 = new MultiplicationAstNode(integer2, integer1).interpret(context);
		assertEquals(integer1ByInteger2, integer2ByInteger1);
	}

	@Test
	void whenMultiplyingByOne_thenInterpretReturnsSecondValue() {
		assertEquals(integer1.interpret(context), new MultiplicationAstNode(new IntegerAstNode(1), integer1).interpret(context));
		assertEquals(integer2.interpret(context), new MultiplicationAstNode(new IntegerAstNode(1), integer2).interpret(context));
		assertEquals(variableY.interpret(context), new MultiplicationAstNode(new IntegerAstNode(1), variableY).interpret(context));
	}

	@Test
	void whenMultiplyingByZero_thenReturnZero() {
		assertEquals(0, new MultiplicationAstNode(new IntegerAstNode(0), integer1).interpret(context));
		assertEquals(0, new MultiplicationAstNode(new IntegerAstNode(0), integer2).interpret(context));
		assertEquals(0, new MultiplicationAstNode(new IntegerAstNode(0), variableY).interpret(context));
	}
}