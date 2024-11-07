var cip;
var prenom;
var nom;
let isSubmitting;



function buyAction() {
    document.getElementById('buyAction').addEventListener('click', function() {
        event.preventDefault();
        if (isSubmitting) return;
        isSubmitting = true;



        const symbole = document.getElementById('Symbol').textContent;
        const nomAction = document.getElementById('Symbol').textContent;
        const nbAction = "7";
        const prixCourant = document.getElementById('Price').textContent;
        const compte = "1";

        const buy = {
            nom: nomAction, // Assurez-vous que cette propriété correspond à votre classe Java
            symbole: symbole,
            nbAction: nbAction,
            prixCourant: prixCourant,
            compte: compte,
        };


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
    });

    setTimeout(() => {
        isSubmitting = false; // Réinitialiser après un certain temps
    }, 1500);
}

function sellAction() {
    document.getElementById('sellAction').addEventListener('click', function() {
        event.preventDefault();
        if (isSubmitting) return;
        isSubmitting = true;



        const symbole = document.getElementById('Symbol').textContent;
        const prixVente = document.getElementById('price').textContent;

        const infoSell = {
            symbole: symbole,
            compte: "4",
            cip: cip,
            nbAVendre: "2",
            prixVente: prixVente,
        };

        console.log(JSON.stringify(infoSell));
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

    setTimeout(() => {
        isSubmitting = false; // Réinitialiser après un certain temps
    }, 1500);
}

function getListActionCompte() {
    //const cip = document.getElementById('cip').textContent;
    const cippp = "roya1414";
    const url = `http://localhost:8888/api/listActionCompte?cip=${encodeURIComponent(cippp)}`;
    console.log("10");
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
            console.log(data);

            afficherDonnees(data);
        })
        .catch(error => {
            console.error('Erreur:', error);
        });
}

function afficherDonnees(data) {
    const container = document.getElementById('resultat'); // Cibler le tbody
    container.innerHTML = ''; // Vider le conteneur avant d'ajouter les nouvelles données

    data.forEach(compte => {
        // Créer une nouvelle ligne
        const row = document.createElement('tr');

        // Ajouter des cellules avec les données appropriées
        row.innerHTML = `
            <td id="${compte.diminutif}">${compte.diminutif}</td> <!-- Symbole -->
            <td>${compte.nom}</td> <!-- Nom -->
            <td>${compte.quantie_action}</td> <!-- Quantité -->
            <td>${compte.prix_acquisition}</td> <!-- Prix d'acquisition -->
            <td>${compte.montant}</td> <!-- Prix actuel -->
            <td>${calculateProfit(compte.prix_acquisition, compte.montant, compte.quantie_action)}</td> <!-- Profit -->
        `;

        // Ajouter la ligne au tbody
        container.appendChild(row);
    });
}

function calculateProfit(prixAcquisition, montant, quantity) {
    const acquisition = parseFloat(prixAcquisition) || 0;
    const currentPrice = parseFloat(montant) || 0;
    return ((currentPrice - acquisition) * quantity).toFixed(2); // Retourne le profit formaté à 2 décimales
}

    var keycloak;
    function initKeycloak() {
        keycloak = new Keycloak({
            "realm": "usager",
            "auth-server-url": "http://localhost:8180/",
            "ssl-required": "external",
            "clientId": "frontend",
            "public-client": true,
            "confidential-port": 0
        });
        keycloak.init({onLoad: 'login-required'}).then(function (authenticated) {
            //alert(authenticated ? 'authenticated' : 'not authenticated');

        }).catch(function () {
            alert('failed to initialize');
        });
    }
    initKeycloak();

function requestStudent() {
    fetch("http://localhost:8888/api/student", {
        method: 'GET',
        headers: {
            'Authorization': 'Bearer ' + keycloak.token
        }
    })
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            return response.json();
        })
        .then(data => {
            cip = data.cip;
            prenom = data.first_name;
            nom = data.last_name;

            console.log("Response: ", data);
            // Une fois la réponse réussie, arrêter l'interrogation
            clearInterval(requestInterval);
        })
        .catch(error => {
            console.log('refreshing');
            keycloak.updateToken(5)
                .then(() => {
                    console.log('Token refreshed');
                })
                .catch(() => {
                    console.log('Failed to refresh token');
                });
        });
}

// Exécuter requestStudent toutes les 200ms jusqu'à ce que cela fonctionne
let requestInterval;

document.addEventListener('DOMContentLoaded', function() {
    console.log("ccc");
    requestInterval = setInterval(requestStudent, 200);
    console.log("8");
    getListActionCompte();
    console.log("9");

});






