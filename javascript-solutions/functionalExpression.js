let variable = name => (x, y, z) => {
    switch (name) {
        case 'x':
            return x;
        case 'y':
            return y;
        case 'z':
            return z;
    }
}
let binaryOperation = operation => (left, right) => (x, y, z) => operation(left(x, y, z), right(x, y, z));
let add = (left, right) => binaryOperation((a, b) => a + b)(left, right);
let subtract = (left, right) => binaryOperation((a, b) => a - b)(left, right);
let multiply = (left, right) => binaryOperation((a, b) => a * b)(left, right);
let divide = (left, right) => binaryOperation((a, b) => a / b)(left, right);
let cnst = value => () => value;

const one = cnst(1);
const two = cnst(2);

let unaryOperation = operation => exp => (x, y, z) => operation(exp(x, y, z));
let sin = unaryOperation(Math.sin);
let cos = unaryOperation(Math.cos);
let negate = unaryOperation(x => -x);
