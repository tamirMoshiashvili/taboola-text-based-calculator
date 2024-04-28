package tamir.parser.ast;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tamir.calculator.CalculatorContext;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SubtractionAstNodeTest {

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
	void whenSubtractingTwoVariables_thenInterpretToDifferenceOfValues() {
		assertEquals(-2, new SubtractionAstNode(variableX, variableY).interpret(context));
	}

	@Test
	void whenSubtractingTwoIntegers_thenInterpretToDifferenceOfValues() {
		assertEquals(-1, new SubtractionAstNode(integer1, integer2).interpret(context));
	}

	@Test
	void whenSubtractingIntegerAndVariable_thenInterpretToDifferenceOfValues() {
		assertEquals(-2, new SubtractionAstNode(integer1, variableX).interpret(context));
	}

	@Test
	void whenSubtractingVariableAndInteger_thenInterpretToDifferenceOfValues() {
		assertEquals(3, new SubtractionAstNode(variableY, integer2).interpret(context));
	}
}