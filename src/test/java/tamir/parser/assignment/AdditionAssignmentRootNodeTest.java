package tamir.parser.assignment;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tamir.calculator.CalculatorContext;
import tamir.exception.UnknownVariableException;
import tamir.parser.ast.IntegerAstNode;
import tamir.parser.ast.VariableAstNode;

import static org.junit.jupiter.api.Assertions.*;

class AdditionAssignmentRootNodeTest {

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
	void whenAdditionAssigningExistingVariable_thenVariableValueIsBeingUpdatedInTheContext() {
		new AdditionAssignmentRootNode("x", one).interpret(context);
		assertEquals(4, variableX.interpret(context));
	}

	@Test
	void whenAdditionAssigningOfUnknownVariable_thenInterpretThrowsUnknownVariableException() {
		assertThrows(UnknownVariableException.class,
				() -> new AdditionAssignmentRootNode("unknown", one).interpret(context));
	}

	@Test
	void whenAdditionAssigningZeroToExistingVariable_thenVariableValueDoesNotChange() {
		int xValueBeforeChange = variableX.interpret(context);
		new AdditionAssignmentRootNode("x", new IntegerAstNode(0)).interpret(context);
		assertEquals(xValueBeforeChange, variableX.interpret(context));
	}

	@Test
	void whenAdditionAssigningNegativeInteger_thenVariableValueHasBeenDecreased() {
		int xValueBeforeChange = variableX.interpret(context);
		IntegerAstNode negativeInteger = new IntegerAstNode(-1);
		new AdditionAssignmentRootNode("x", negativeInteger).interpret(context);
		assertTrue(variableX.interpret(context) < xValueBeforeChange);
	}
}