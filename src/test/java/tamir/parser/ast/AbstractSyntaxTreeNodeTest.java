package tamir.parser.ast;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tamir.calculator.CalculatorContext;
import tamir.parser.ast.operator.binary.AdditionAstNode;
import tamir.parser.ast.operator.binary.MultiplicationAstNode;
import tamir.parser.ast.operator.unary.PreDecrementAstNode;
import tamir.parser.ast.operator.unary.PreIncrementAstNode;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AbstractSyntaxTreeNodeTest {

	private IntegerAstNode four;
	private IntegerAstNode six;
	private VariableAstNode variableX;
	private VariableAstNode variableY;
	private CalculatorContext context;

	@BeforeEach
	void setup() {
		four = new IntegerAstNode(4);
		six = new IntegerAstNode(6);
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
		assertEquals(96, new MultiplicationAstNode(four, new MultiplicationAstNode(four, six)).interpret(context));
	}

	@Test
	void whenAstContainsPreIncrementAndDecrementOnSameVariable_thenVariableValueStaysTheSame() {
		int xValueBeforeChange = variableX.interpret(context);
		new AdditionAstNode(new PreIncrementAstNode("x"), new PreDecrementAstNode("x"));
		assertEquals(xValueBeforeChange, variableX.interpret(context));
	}
}