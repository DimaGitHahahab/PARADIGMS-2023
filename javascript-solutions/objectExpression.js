"use strict";

function Const(value) {
    this.value = value
}

Const.prototype = {
    evaluate: function () {
        return this.value
    }, toString: function () {
        return this.value.toString()
    },
    prefix: function () {
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
    },
    prefix: function () {
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
    },
    prefix: function () {
        return `(${this.sign} ${this.args.map(arg => arg.prefix()).join(' ')})`;
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


const operationsList = {
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
        if (token in operationsList) {
            const args = []
            for (let i = 0; i < operationsList[token].length; i++) {
                args.push(stack.pop())
            }
            stack.push(new Operation(operationsList[token], token, ...args.reverse()))
        } else if (token in {'x': 0, 'y': 1, 'z': 2}) {
            stack.push(new Variable(token))
        } else {
            stack.push(new Const(+token))
        }
    }
    return stack.pop();
}

function isEmpty(expression) {
    if (expression.replaceAll("(", "").replaceAll(")", "").trim() === "") {
        return true;
    }
    return false;
}

function parsePrefix(expression) {
    if (isEmpty(expression)) {
        throw new Error("Invalid expression");
    }
    let openParenthesesCount = 0;
    const stack = [];
    const tokens = expression.replaceAll("(", " ( ").replaceAll(")", " ) ").trim().split(" ").reverse();
    let expectOperation = false;

    for (let token of tokens) {
        if (token === '') {
            continue;
        }
        if (token === '(') {
            if (!expectOperation) {
                throw new Error("Invalid expression");
            }
            openParenthesesCount++;
        } else if (token === ')') {
            openParenthesesCount--;
            expectOperation = false;
        } else if (token in operationsList) {
            const args = []
            for (let i = 0; i < operationsList[token].length; i++) {
                if (stack.length === 0) {
                    throw new Error("Invalid expression");
                }
                args.push(stack.pop())
            }
            stack.push(new Operation(operationsList[token], token, ...args));
            expectOperation = true;
        } else if (token in {'x': 0, 'y': 1, 'z': 2}) {
            stack.push(new Variable(token));
            expectOperation = false;
        } else if (!isNaN(+token)) {
            stack.push(new Const(+token));
            expectOperation = false;
        } else {
            throw new Error(`Unexpected symbol: ${token}`);
        }
    }

    if (openParenthesesCount !== 0) {
        throw new Error("Missing closing parenthesis");
    }

    if (stack.length !== 1) {
        throw new Error("Invalid expression");
    }
    return stack.pop();
}
