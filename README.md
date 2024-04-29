# Text Based Calculator

## Goal
Parse assignment expressions as strings, where the syntax is a subset of Java numeric expressions and binary operators.

As output, print each variable with its value.

Example:

Input:

```
i = 0
j = ++i
x = i++ + 5
y = 5 + 3 * 10
i += y
```

Output:

```
(i=37,j=1,x=6,y=35)
```

## Flow
1. Input is being entered line by line, until entering blank line
    1. `i = 0`
    2. `j = ++i`
2. Each token in a line is space-separated, for example:
`i = 0` will result with the tokens - [`i`, `=`, `0`]
3. Valid tokens are:
    1. integer numbers - `1`, `0`, `-3`, `1000`
    2. variables - `a`, `x`, `xyz`, `value1`
    3. operators
        1. binary operators - `+`, `-`, `*`, `/`
        2. unary operators - `++i`, `i++`
        3. assignments - `=`, `+=`, `-=`, `*=`, `/=`
4. Upon entering a blank line, print all the variables with their values
5. Each line is an assignment expression, for example: `x = 1`

## Parsing
Each line being split by space into tokens.

Expressions other than `<variableName> <assignmentOperator> <valueExpression>` will be declined,
and `valueExpression` must result with an integer.

After tokenization, an _abstract syntax tree_ being created from the tokens, 
using a simplified version of the _Shunting Yard_ algorithm. <br>
The AST (abstract syntax tree) represents assignment expression.

## Processing
Using the _Interpreter Design Pattern_, 
each node in the abstract syntax tree being processed and updates the context.
