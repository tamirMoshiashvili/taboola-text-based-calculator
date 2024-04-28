package tamir.parser.ast;

import org.junit.jupiter.api.Test;
import tamir.calculator.CalculatorContext;

import static org.junit.jupiter.api.Assertions.assertEquals;

class IntegerAstNodeTest {

	private CalculatorContext context = new CalculatorContext();

	@Test
	void testInterpretOfIntegerReturnsOriginalValue() {
		assertEquals(0, new IntegerAstNode(0).interpret(context));
		assertEquals(1, new IntegerAstNode(1).interpret(context));
		assertEquals(-1, new IntegerAstNode(-1).interpret(context));
		assertEquals(Integer.MAX_VALUE, new IntegerAstNode(Integer.MAX_VALUE).interpret(context));
		assertEquals(Integer.MIN_VALUE, new IntegerAstNode(Integer.MIN_VALUE).interpret(context));
	}
}