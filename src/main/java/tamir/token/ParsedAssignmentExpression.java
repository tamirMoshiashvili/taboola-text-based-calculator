package tamir.token;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import tamir.token.assignment.AssignmentToken;
import tamir.token.expression.Token;
import tamir.token.expression.VariableToken;

import java.util.List;

@AllArgsConstructor
@Getter
@EqualsAndHashCode
public class ParsedAssignmentExpression {

	private final VariableToken variable;
	private final AssignmentToken assignment;
	private final List<Token> expressionTokens;
}
