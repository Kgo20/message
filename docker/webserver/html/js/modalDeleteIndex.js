var cells;

function addEventOnButton() {
    const buttons = document.querySelectorAll("button[id^='delete-'], button[id^='buy-']");
    console.log(buttons);
    buttons.forEach(button => {
        button.addEventListener('click', function() {
            if (isSubmitting) return;
            isSubmitting = true;

            const row = this.closest('tr');
            cells = row.querySelectorAll('td');

            const isSell = this.id.startsWith('delete-');
            const modal = isSell ? 'sellModal' : 'buyModal';
            let prefix = ""
            if(modal === "sellModal")
                prefix = "sell-"
            else if(modal === "buyModal")
                prefix = "buy-"


            document.getElementById(prefix + 'symbol').value = cells[0].textContent;
            document.getElementById(prefix + 'name').value = cells[1].textContent;
            document.getElementById(prefix + 'quantity').value = cells[2].textContent;
            document.getElementById(prefix + 'purchasePrice').value = cells[3].textContent;
            document.getElementById(prefix + 'currentPrice').value = cells[4].textContent;

            new bootstrap.Modal(document.getElementById(modal)).show();
            setTimeout(() => {
                isSubmitting = false; // Réinitialiser après un certain temps
            }, 100);
        });
    });

    document.getElementById('confirmSell').addEventListener('click', function() {
        if (isSubmitting) return;
        isSubmitting = true;

        const sellQuantity = document.getElementById('sell-moveQuantity').value;
        sellAction(cells[0].textContent, sellQuantity, cells[4].textContent);

        setTimeout(function() {
            getListActionCompte();
            setTimeout(function() {
                addEventOnButton();
            }, 200);
        }, 100);

        bootstrap.Modal.getInstance(document.getElementById('sellModal')).hide();
        document.getElementById('sellForm').reset();

        setTimeout(() => {
            isSubmitting = false; // Réinitialiser après un certain temps
        }, 100);
    });

    document.getElementById('confirmBuy').addEventListener('click', function() {
        if (isSubmitting) return;
        isSubmitting = true;
        const buyQuantity = document.getElementById('buy-moveQuantity').value;
        buyAction(cells[0].textContent, buyQuantity, cells[4].textContent);

        setTimeout(function() {
            getListActionCompte();
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
