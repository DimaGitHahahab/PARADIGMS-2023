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

function UnaryOperation(value, operation) {
    this.value = value;
    this.operation = operation;
}

UnaryOperation.prototype = {
    evaluate: function (x, y, z) {
        return this.operation(this.value.evaluate(x, y, z));
    },
    toString: function () {
        return `${this.value.toString()} negate`;
    },
}

function Negate(value) {
    UnaryOperation.call(this, value, a => -a)
}

Negate.prototype = UnaryOperation.prototype;

function parse(expression) {
    const stack = [];
    const tokens = expression.split(' ').filter(token => token.trim() !== '');

    const operations = {
        '+': Add,
        '-': Subtract,
        '*': Multiply,
        '/': Divide,
        'negate': Negate
    };
    for (const token of tokens) {
        if (token === 'x' || token === 'y' || token === 'z') {
            stack.push(new Variable(token));
        } else if (token in operations) {
            const arity = operations[token].length;
            const args = stack.splice(-arity);
            stack.push(new operations[token](...args));
        } else {
            stack.push(new Const(parseFloat(token)));
        }
    }
    return stack[0];
}