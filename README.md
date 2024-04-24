# Text Based Calculator
Parse assignment expressions as strings, where the syntax is a subset of Java numeric expressions and operators.

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