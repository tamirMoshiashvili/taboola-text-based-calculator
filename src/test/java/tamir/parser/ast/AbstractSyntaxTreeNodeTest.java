package tamir.parser.ast;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tamir.calculator.CalculatorContext;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AbstractSyntaxTreeNodeTest {

	private VariableAstNode variableX;
	private VariableAstNode variableY;
	private CalculatorContext context;

	@BeforeEach
	void setup() {
		variableX = new VariableAstNode("x");
		variableY = new VariableAstNode("y");
		context = new CalculatorContext();
		context.put("x", 2);
		context.put("y", 6);
	}

	@Test
	void whenAstHasCompositeOperationsOnVariables_thenReturnValue() {
		assertEquals(10, new AdditionAstNode(variableX, new AdditionAstNode(variableX, variableY)).interpret(context));
	}

	@Test
	void whenAstHasCompositeOperationsOnIntegers_thenReturnValue() {
		assertEquals(24, new MultiplicationAstNode(variableX, new MultiplicationAstNode(variableX, variableY)).interpret(context));
	}

	@Test
	void whenAstContainsPreIncrementAndDecrementOnSameVariable_thenVariableValueStaysTheSame() {
		int xValue = variableX.interpret(context);
		new AdditionAstNode(new PreIncrementAstNode("x"), new PreDecrementAstNode("x"));
		assertEquals(xValue, variableX.interpret(context));
	}
}