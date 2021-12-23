window.onload = () => {
    onOperationChangeHandler();
    onSubmitHandler();
}

const onOperationChangeHandler = () => {
    const operationLabel = document.getElementById('operation-label');
    const operationRadios = document.getElementsByClassName("operation");
    Array.from(operationRadios).forEach(
        (operationRadio) => operationRadio.addEventListener('change', (event) =>
            operationLabel.textContent = event.target.value
        )
    )
}

const onSubmitHandler = () => {
    const calculationForm = document.getElementById('calculation-form');
    calculationForm.addEventListener('submit', (event) => {
            const operation = calculationForm.elements.operation.value;
            const secondOperand = calculationForm.elements.secondOperand.value;
            if (operation === "÷" && secondOperand == 0) {
                event.preventDefault();
                alert("ОШИБКА: деление на 0");
            }
        }
    )
}