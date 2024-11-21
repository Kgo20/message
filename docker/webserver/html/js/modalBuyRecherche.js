var cells;
var isSubmitting ;

function addEventOnButton() {
    const button = document.getElementById("buy");
    console.log(button);
    button.addEventListener('click', function() {
        if (isSubmitting) return;
        isSubmitting = true;

        const row = this.closest('tr');

        const modal = 'buyModal';
        let prefix = "buy-"


        // document.getElementById(prefix + 'symbol').value = document.getElementById('Symbol').value;
        // document.getElementById(prefix + 'name').value = "penis";
        // document.getElementById(prefix + 'name').value =  document.getElementById('Name').value;
        // document.getElementById(prefix + 'quantity').value = cells[2].textContent;
        // document.getElementById(prefix + 'purchasePrice').value = cells[3].textContent;


        new bootstrap.Modal(document.getElementById(modal)).show();
        setTimeout(() => {
            isSubmitting = false; // Réinitialiser après un certain temps
        }, 100);
    });

    document.getElementById('confirmBuy').addEventListener('click', function () {
        if (isSubmitting) return;
        isSubmitting = true;
        const buyQuantity = document.getElementById('buy-moveQuantity').value;
        buyAction(cells[0].textContent, buyQuantity, cells[4].textContent);

        setTimeout(function() {
            setTimeout(function() {
                addEventOnButton();
            }, 200);
        }, 100);

        bootstrap.Modal.getInstance(document.getElementById('buyModal')).hide();
        document.getElementById('buyForm').reset();

        setTimeout(() => {
            isSubmitting = false; // Réinitialiser après un certain temps
        }, 100);
    });
}
addEventOnButton();
