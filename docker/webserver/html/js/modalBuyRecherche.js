var cells;
let isSubmitting;

function buyAction(symbole, buyQuantity, buyingPrice) {


    const buy = {
        nom: symbole, // Assurez-vous que cette propriété correspond à votre classe Java
        symbole: symbole,
        nbAction: buyQuantity,
        prixCourant: buyingPrice,
        compte: sessionStorage.getItem('compteId'),
    };
    console.log(buy)
    console.log(JSON.stringify(buy));
    fetch('http://localhost:8888/api/buyAction', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(buy),

    })
        .then(response => {
            if (!response.ok) {
                return response.text().then(text => {
                    throw new Error('Erreur lors de l\'envoi du message: ' + text);
                });
            }
            return response.json(); // Utilisez directement response.json()
        })
        .then(data => {
            console.log('Message envoyé avec succès:', data);
        })
        .catch(error => {
            console.error('Erreur:', error);
        });
}

function addEventOnButton() {
    const button = document.getElementById("buy");
    console.log(button);
    button.addEventListener('click', function() {
        if (isSubmitting) return;
        isSubmitting = true;

        const row = this.closest('tr');

        const modal = 'buyModal';
        let prefix = "buy-"


        document.getElementById(prefix + 'symbol').value = document.getElementById('Symbol').textContent;
        document.getElementById(prefix + 'name').value =  document.getElementById('Name').textContent;
        document.getElementById(prefix + 'purchasePrice').value = document.getElementById('Price').textContent;


        new bootstrap.Modal(document.getElementById(modal)).show();
        setTimeout(() => {
            isSubmitting = false; // Réinitialiser après un certain temps
        }, 100);
    });

    document.getElementById('confirmBuy').addEventListener('click', function () {
        if (isSubmitting) return;
        isSubmitting = true;
        let prefix = "buy-"
        const buyQuantity = document.getElementById('buy-moveQuantity').value;
        buyAction(document.getElementById(prefix + 'symbol').value, buyQuantity, document.getElementById(prefix + 'purchasePrice').value);

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



