package tamir.parser.assignment;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tamir.calculator.CalculatorContext;
import tamir.exception.UnknownVariableException;
import tamir.parser.ast.IntegerAstNode;
import tamir.parser.ast.VariableAstNode;

import static org.junit.jupiter.api.Assertions.*;

class SubtractionAssignmentRootNodeTest {

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
		new SubtractionAssignmentRootNode("x", one).execute(context);
		assertEquals(2, variableX.interpret(context));
	}

	@Test
	void whenSubtractionAssigningOfUnknownVariable_thenInterpretThrowsUnknownVariableException() {
		assertThrows(UnknownVariableException.class,
				() -> new SubtractionAssignmentRootNode("unknown", one).execute(context));
	}

	@Test
	void whenSubtractionAssigningZero_thenVariableValueDoesNotChange() {
		int xValueBeforeChange = variableX.interpret(context);
		new SubtractionAssignmentRootNode("x", new IntegerAstNode(0)).execute(context);
		assertEquals(xValueBeforeChange, variableX.interpret(context));
	}

	@Test
	void whenSubtractionAssigningByVariablesValue_thenVariableEqualsZero() {
		new SubtractionAssignmentRootNode("x", variableX).execute(context);
		assertEquals(0, variableX.interpret(context));
	}

	@Test
	void whenSubtractionAssigningByNegativeInteger_thenVariableValueHasBeenIncreased() {
		int xValueBeforeChange = variableX.interpret(context);
		IntegerAstNode negativeInteger = new IntegerAstNode(-1);
		new SubtractionAssignmentRootNode("x", negativeInteger).execute(context);
		assertTrue(variableX.interpret(context) > xValueBeforeChange);
	}
}