"use strict";

function Const(value) {
    this.value = value
}

Const.prototype = {
    evaluate: function () {
        return this.value
    }, toString: function () {
        return this.value.toString()
    }
}

function Variable(name) {
    this.name = name
}

Variable.prototype = {
    evaluate: function (x, y, z) {
        return {x, y, z}[this.name]
    }, toString: function () {
        return this.name
    }
}

function Operation(operation, sign, ...args) {
    this.args = args;
    this.operation = operation;
    this.sign = sign;
}

Operation.prototype = {
    evaluate: function (...args) {
        return this.operation(...this.args.map(arg => arg.evaluate(...args)));
    }, toString: function () {
        return `${this.args.join(' ')} ${this.sign}`;
    }
}

function Add(...args) {
    Operation.call(this, (a, b) => a + b, '+', ...args);
}

Add.prototype = Operation.prototype;

function Subtract(...args) {
    Operation.call(this, (a, b) => a - b, '-', ...args);
}

Subtract.prototype = Operation.prototype;

function Multiply(...args) {
    Operation.call(this, (a, b) => a * b, '*', ...args);
}

Multiply.prototype = Operation.prototype;

function Divide(...args) {
    Operation.call(this, (a, b) => a / b, '/', ...args);
}

Divide.prototype = Operation.prototype;

function Negate(...args) {
    Operation.call(this, a => -a, 'negate', ...args);
}

Negate.prototype = Operation.prototype;

function Exp(...args) {
    Operation.call(this, a => Math.exp(a), 'exp', ...args);
}

Exp.prototype = Operation.prototype;

function Ln(...args) {
    Operation.call(this, a => Math.log(a), 'ln', ...args);
}

Ln.prototype = Operation.prototype;

const map = {
    '+': (a, b) => a + b,
    '-': (a, b) => a - b,
    '*': (a, b) => a * b,
    '/': (a, b) => a / b,
    'negate': a => -a,
    'exp': a => Math.exp(a),
    'ln': a => Math.log(a),

}

function parse(expression) {
    const stack = [];
    const tokens = expression.split(" ");
    for (let token of tokens) {
        if (token === '') {
            continue;
        }
        if (token in map) {
            const args = []
            for (let i = 0; i < map[token].length; i++) {
                args.push(stack.pop())
            }
            stack.push(new Operation(map[token], token, ...args.reverse()))
        } else if (token in {'x': 0, 'y': 1, 'z': 2}) {
            stack.push(new Variable(token))
        } else {
            stack.push(new Const(+token))
        }
    }
    return stack.pop();
}

