package tamir.parser.ast;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tamir.calculator.CalculatorContext;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AdditionAstNodeTest {

	private IntegerAstNode integer1;
	private IntegerAstNode integer2;
	private VariableAstNode variableX;
	private VariableAstNode variableY;
	private CalculatorContext context;

	@BeforeEach
	void setup() {
		integer1 = new IntegerAstNode(1);
		integer2 = new IntegerAstNode(2);
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
		assertEquals(3, new AdditionAstNode(integer1, integer2).interpret(context));
	}

	@Test
	void whenAddingIntegerAndVariable_thenInterpretToSumOfValues() {
		assertEquals(4, new AdditionAstNode(integer1, variableX).interpret(context));
	}

	@Test
	void whenAddingVariableAndInteger_thenInterpretToSumOfValues() {
		assertEquals(7, new AdditionAstNode(variableY, integer2).interpret(context));
	}

	@Test
	void testAdditionIsCommutative() {
		int variableWithInteger = new AdditionAstNode(variableY, integer2).interpret(context);
		int integerWithVariable = new AdditionAstNode(integer2, variableY).interpret(context);
		assertEquals(variableWithInteger, integerWithVariable);
	}
}