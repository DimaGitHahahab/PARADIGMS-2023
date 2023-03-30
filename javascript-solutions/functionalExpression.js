'use strict'
const variable = name => (x, y, z) => {
    switch (name) {
        case 'x':
            return x;
        case 'y':
            return y;
        case 'z':
            return z;
    }
}
const Operation = operation => (...operands) => (...args) => operation(...operands.map(operand => operand(...args)));
const add = (...args) => Operation((a, b) => a + b)(...args);
const subtract = (...args) => Operation((a, b) => a - b)(...args);
const multiply = (...args) => Operation((a, b) => a * b)(...args);
const divide = (...args) => Operation((a, b) => a / b)(...args);
const cnst = value => () => value;
const negate = value => Operation(a => -a)(value)

const one = cnst(1);
const two = cnst(2);

const sin = value => Operation(Math.sin)(value)
const cos = value => Operation(Math.cos)(value)