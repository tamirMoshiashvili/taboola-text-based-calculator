# Text Based Calculator

## Goal
Parse assignment expressions as strings, where the syntax is a subset of Java numeric expressions and binaryOperators.

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

## Assumptions
1. Input is being entered line by line, until entering blank line
    1. `i = 0`
    2. `j = ++i`
2. Each token in a line is space-separated, for example:
    1. input - `i = 0`
    2. tokens - [`i`, `=`, `0`]
3. Valid tokens are:
    1. constant numbers - `1`, `0`, `-3`, `1000`
    2. variables - `a`, `x`, `xyz`
    3. binaryOperators
        1. binary binaryOperators - `+`, `-`, `*`, `/`
        2. unary binaryOperators - `++i`, `i++`
        3. assignments - `=`, `+=`, `-=`, `*=`, `/=`
4. Upon entering a blank line, print all the variables with their values
