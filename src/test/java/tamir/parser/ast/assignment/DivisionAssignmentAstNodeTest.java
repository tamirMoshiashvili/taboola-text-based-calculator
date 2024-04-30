package tamir.parser.ast.assignment;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tamir.calculator.CalculatorContext;
import tamir.exception.UnknownVariableException;
import tamir.parser.ast.IntegerAstNode;
import tamir.parser.ast.VariableAstNode;

import static org.junit.jupiter.api.Assertions.*;

class DivisionAssignmentAstNodeTest {

	private IntegerAstNode two;
	private VariableAstNode variableX;
	private CalculatorContext context;

	@BeforeEach
	void setup() {
		two = new IntegerAstNode(2);
		variableX = new VariableAstNode("x");
		context = new CalculatorContext();
		context.put("x", 4);
	}

	@Test
	void whenDivisionAssigningExistingVariable_thenVariableValueIsBeingUpdatedInTheContext() {
		new DivisionAssignmentAstNode("x", two).interpret(context);
		assertEquals(2, variableX.interpret(context));
	}

	@Test
	void whenDivisionAssigningOfUnknownVariable_thenInterpretThrowsUnknownVariableException() {
		assertThrows(UnknownVariableException.class,
				() -> new DivisionAssignmentAstNode("unknown", two).interpret(context));
	}

	@Test
	void whenDivisionAssigningByOne_thenVariableValueDoesNotChange() {
		int xValueBeforeChange = variableX.interpret(context);
		new DivisionAssignmentAstNode("x", new IntegerAstNode(1)).interpret(context);
		assertEquals(xValueBeforeChange, variableX.interpret(context));
	}

	@Test
	void whenDivisionAssigningByZero_thenThrowsArithmeticException() {
		assertThrows(ArithmeticException.class,
				() -> new DivisionAssignmentAstNode("x", new IntegerAstNode(0)).interpret(context));
	}

	@Test
	void whenDivisionAssigningPositiveVariableByNegativeInteger_thenVariableValueIsNegative() {
		assertTrue(variableX.interpret(context) > 0);
		IntegerAstNode negativeInteger = new IntegerAstNode(-1);
		new DivisionAssignmentAstNode("x", negativeInteger).interpret(context);
		assertTrue(variableX.interpret(context) < 0);
	}

	@Test
	void whenDivisionAssigningNegativeVariableByNegativeInteger_thenVariableValueIsPositive() {
		context.put("y", -1);
		IntegerAstNode negativeInteger = new IntegerAstNode(-1);
		new DivisionAssignmentAstNode("y", negativeInteger).interpret(context);
		assertTrue(variableX.interpret(context) > 0);
	}
}