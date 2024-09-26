function buyAction() {
    document.getElementById('buyAction').addEventListener('click', function() {

        const symbole = document.getElementById('symbole').textContent;
        const nomCompagnie = document.getElementById('nomCompagnie').textContent;
        const nbAction = document.getElementById('symbole').value;
        const prixCourant = document.getElementById('prixCourant').textContent;
        //const compte = document.getElementById('compte').textContent;
        //const cip = document.getElementById('cip').textContent;

        const infoBuy = {
            symbole: symbole,
            nomCompagnie: nomCompagnie,
            nbAction: nbAction,
            prixCourant: prixCourant,
            //compte: compte,
            //cip: cip,
        };

        fetch('http://localhost:8888/api/buyAction', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(infoBuy),
        })
            .then(response => {
                if (!response.ok) {
                    throw new Error('Erreur lors de l\'envoi du message');
                }
                // Vérifier s'il y a un contenu à traiter
                return response.text().then(text => {
                    return text ? JSON.parse(text) : {}; // Si le texte est vide, retourner un objet vide
                });
            })
            .then(data => {
                console.log('Message envoyé avec succès:', data);
            })
            .catch(error => {
                console.error('Erreur:', error);
            });
    });
}

function sellAction() {
    document.getElementById('sellAction').addEventListener('click', function() {
        const symbole = document.getElementById('symbole').textContent;
        //const compte = document.getElementById('compte').textContent;
        //const cip = document.getElementById('cip').textContent;

        const infoSell = {
            symbole: symbole,
            //compte: compte,
            //cip: cip,
        };
        fetch('http://localhost:8888/api/sellAction', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(infoSell),
        })
            .then(response => {
                if (!response.ok) {
                    throw new Error('Erreur lors de l\'envoi du message');
                }
                // Vérifier s'il y a un contenu à traiter
                return response.text().then(text => {
                    return text ? JSON.parse(text) : {}; // Si le texte est vide, retourner un objet vide
                });
            })
            .then(data => {
                console.log('Message envoyé avec succès:', data);
            })
            .catch(error => {
                console.error('Erreur:', error);
            });
    });
}

function getListActionCompte() {
    //const cip = document.getElementById('cip').textContent;
    const cip = "roya1414";
    const url = `http://localhost:8888/api/listActionCompte?cip=${encodeURIComponent(cip)}`;

    fetch(url, {
        method: 'GET',
    })
        .then(response => {
            if (!response.ok) {
                throw new Error('Erreur lors de l\'envoi du message');
            }
            // Vérifier s'il y a un contenu à traiter
            return response.text().then(text => text ? JSON.parse(text) : {});
        })
        .then(data => {
            console.log('Données récupérées avec succès:', data);
            // Ici, tu peux manipuler les données récupérées et les afficher dans ton HTML
            afficherDonnees(data);
        })
        .catch(error => {
            console.error('Erreur:', error);
        });
}

function afficherDonnees(data) {
    const container = document.getElementById('resultat'); // Un conteneur HTML pour afficher les données
    container.innerHTML = ''; // Vider le conteneur avant d'ajouter les nouvelles données

    data.forEach(compte => {
        const div = document.createElement('div');
        div.textContent = `CIP: ${compte.cip}, Nom Usager: ${compte.nomUsager}, Montant: ${compte.montant}`;
        container.appendChild(div);
    });
}

document.addEventListener('DOMContentLoaded', function() {
    getListActionCompte();
});




