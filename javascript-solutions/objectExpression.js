"use strict";

function Const(value) {
    this.value = value
    this.evaluate = function (x, y, z) {
        return this.value
    }
    this.toString = function () {
        return this.value.toString()
    }
}

function Variable(name) {
    this.name = name
    this.evaluate = function (x, y, z) {
        return {x, y, z}[this.name]
    }
    this.toString = function () {
        return this.name
    }
}

function BinaryOperation(left, right, operation, sign) {
    this.left = left
    this.right = right
    this.operation = operation
    this.sign = sign
}

BinaryOperation.prototype = {
    evaluate: function (x, y, z) {
        return this.operation(this.left.evaluate(x, y, z), this.right.evaluate(x, y, z));
    },
    toString: function () {
        return `${this.left.toString()} ${this.right.toString()} ${this.sign}`;
    },
};

function Add(left, right) {
    BinaryOperation.call(this, left, right, (a, b) => a + b, '+');
}

Add.prototype = BinaryOperation.prototype;

function Subtract(left, right) {
    BinaryOperation.call(this, left, right, (a, b) => a - b, '-');
}

Subtract.prototype = BinaryOperation.prototype;

function Multiply(left, right) {
    BinaryOperation.call(this, left, right, (a, b) => a * b, '*');
}

Multiply.prototype = BinaryOperation.prototype;

function Divide(left, right) {
    BinaryOperation.call(this, left, right, (a, b) => a / b, '/');
}

Divide.prototype = BinaryOperation.prototype;

function UnaryOperation(value, operation, sign) {
    this.value = value;
    this.operation = operation;
    this.sign = sign;
}

UnaryOperation.prototype = {
    evaluate: function (x, y, z) {
        return this.operation(this.value.evaluate(x, y, z));
    },
    toString: function () {
        return `${this.value.toString()} ${this.sign}`;
    },
}

function Negate(value) {
    UnaryOperation.call(this, value, a => -a, 'negate')
}

Negate.prototype = UnaryOperation.prototype;

function Exp(value) {
    UnaryOperation.call(this, value, a => Math.exp(a), 'exp')
}

Exp.prototype = UnaryOperation.prototype;

function Ln(value) {
    UnaryOperation.call(this, value, a => Math.log(a), 'ln')
}

Ln.prototype = UnaryOperation.prototype;

function parse(expression) {
    const stack = [];
    const tokens = expression.split(' ').filter(token => token.trim() !== '');

    const operations = {
        '+': Add,
        '-': Subtract,
        '*': Multiply,
        '/': Divide,
        'negate': Negate,
        'exp': Exp,
        'ln': Ln,
    };
    for (const token of tokens) {
        if (token === 'x' || token === 'y' || token === 'z') {
            stack.push(new Variable(token));
        } else if (token in operations) {
            const amountOfArgsOfClass = operations[token].length;
            const args = stack.splice(-amountOfArgsOfClass);
            stack.push(new operations[token](...args));
        } else {
            stack.push(new Const(parseFloat(token)));
        }
    }
    return stack[0];
}