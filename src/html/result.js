window.onload = () => renderCalculationResult();

const urlParamsNames = {
    FIRST_OPERAND: 'firstOperand',
    SECOND_OPERAND: 'secondOperand',
    OPERATION: 'operation'
}

const operationCallbackMap = {
    "+": (first, second) => first + second,
    "-": (first, second) => first - second,
    "×": (first, second) => first * second,
    "÷": (first, second) => first / second,
};

const renderCalculationResult = () => {
    const tBody = document.getElementById('tbody-result');
    const urlParams = new URLSearchParams(window.location.search);
    const firstOperand = Number.parseFloat(urlParams.get(urlParamsNames.FIRST_OPERAND));
    const secondOperand = Number.parseFloat(urlParams.get(urlParamsNames.SECOND_OPERAND));
    const operation = urlParams.get(urlParamsNames.OPERATION);

    const resultRow = document.createElement("tr");
    resultRow.innerHTML =
        `<td>${firstOperand}</td><td>${operation}</td><td>${secondOperand}</td><td>${operationCallbackMap[operation](firstOperand, secondOperand)}</td>`
    tBody.append(resultRow);
};