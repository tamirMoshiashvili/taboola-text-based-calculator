package tamir.parser.ast.assignment;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tamir.calculator.CalculatorContext;
import tamir.exception.UnknownVariableException;
import tamir.parser.ast.IntegerAstNode;
import tamir.parser.ast.VariableAstNode;

import static org.junit.jupiter.api.Assertions.*;

class SubtractionAssignmentAstNodeTest {

	private IntegerAstNode one;
	private VariableAstNode variableX;
	private CalculatorContext context;

	@BeforeEach
	void setup() {
		one = new IntegerAstNode(1);
		variableX = new VariableAstNode("x");
		context = new CalculatorContext();
		context.put("x", 3);
	}

	@Test
	void whenSubtractionAssigningExistingVariable_thenVariableIsBeingUpdatedInTheContext() {
		new SubtractionAssignmentAstNode("x", one).interpret(context);
		assertEquals(2, variableX.interpret(context));
	}

	@Test
	void whenSubtractionAssigningOfUnknownVariable_thenInterpretThrowsUnknownVariableException() {
		assertThrows(UnknownVariableException.class,
				() -> new SubtractionAssignmentAstNode("unknown", one).interpret(context));
	}

	@Test
	void whenSubtractionAssigningZero_thenVariableValueDoesNotChange() {
		int xValueBeforeChange = variableX.interpret(context);
		new SubtractionAssignmentAstNode("x", new IntegerAstNode(0)).interpret(context);
		assertEquals(xValueBeforeChange, variableX.interpret(context));
	}

	@Test
	void whenSubtractionAssigningByVariablesValue_thenVariableEqualsZero() {
		new SubtractionAssignmentAstNode("x", variableX).interpret(context);
		assertEquals(0, variableX.interpret(context));
	}

	@Test
	void whenSubtractionAssigningByNegativeInteger_thenVariableValueHasBeenIncreased() {
		int xValueBeforeChange = variableX.interpret(context);
		IntegerAstNode negativeInteger = new IntegerAstNode(-1);
		new SubtractionAssignmentAstNode("x", negativeInteger).interpret(context);
		assertTrue(variableX.interpret(context) > xValueBeforeChange);
	}
}