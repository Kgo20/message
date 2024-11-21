var cip;
var prenom;
var nom;
var email;
var compteId;
var montant;
var montantDepart;
let isSubmitting;

function createUsager(cip, prenom, nom, email) {

    const cree = {
        cip: cip,
        prenom: prenom,
        nom: nom,
        courriel: email,
    };
    console.log(JSON.stringify(cree));
    fetch('http://localhost:8888/api/createUsager', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(cree),
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


function createCompte() {
    document.getElementById('createCompte').addEventListener('click', function() {
        event.preventDefault();
        if (isSubmitting) return;
        isSubmitting = true;

        //L'id du compte est une valeur donnée à la création, je ne sais pas comment aller la chercher
        const nom = "nom";
        const cip = "cip";
        const montant_depart = "3000";
        const montant = "3000";


        const cree = {
            idCompte: compte, // Assurez-vous que cette propriété correspond à votre classe Java
            nom: nom,
            cip: cip,
            montant_depart: montant_depart,
            montant: montant
        };

        console.log(JSON.stringify(cree));
        fetch('http://localhost:8888/api/createCompte', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(cree),

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

function addMontantDepart() {
    const compte = compteId;
    const montant =  document.getElementById("plusplus").value;
    console.log("motant", montant);
    const add = {
        idCompte: compte, // Assurez-vous que cette propriété correspond à votre classe Java
        montant: montant
    };

    console.log(JSON.stringify(add));
    fetch('http://localhost:8888/api/addMontantDepart', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(add),

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
            getListActionCompte2();
            console.log('Message envoyé avec succès:', data);
        })
        .catch(error => {
            console.error('Erreur:', error);
        });
}

function getCompteId() {
    const url = `http://localhost:8888/api/getCompteId?cip=${encodeURIComponent(cip)}`;
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
            console.log('GetCompteId:', data);
            compteId = data.compteId;
            montant = data.montant;
            montantDepart = data.montant_depart;
        })
        .catch(error => {
            console.error('Erreur:', error);
        });
}


function buyAction(symbole, buyQuantity, buyingPrice) {


    const buy = {
        nom: symbole, // Assurez-vous que cette propriété correspond à votre classe Java
        symbole: symbole,
        nbAction: buyQuantity,
        prixCourant: buyingPrice,
        compte: compteId,
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
}

function sellAction(symbole, sellQuantity, sellingPrice) {

    const infoSell = {
        symbole: symbole,
        compte: compteId,
        cip: cip,
        nbAVendre: sellQuantity,
        prixVente: sellingPrice,
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
}

function getActualPrice(symbol, idElement) {
    const apiKey = "cror3bhr01qo7n2ihk7gcror3bhr01qo7n2ihk80"; // API key compte zach pour finnhub
    fetch(`https://finnhub.io/api/v1/quote?symbol=${symbol}&token=${apiKey}`)
        .then(response => response.json())
        .then(data => {
            console.log(data.c + " $");
            document.getElementById(idElement).innerHTML = data.c
        })
        .catch(error => console.error('Error:', error)
        );
}


function getListActionCompte() {
    //const cip = document.getElementById('cip').textContent;
    const url = `http://localhost:8888/api/listActionCompte?cip=${encodeURIComponent(cip)}`;
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
            console.log("qwerty: ", data);

            afficherDonnees(data);
        })
        .catch(error => {
            console.error('Erreur:', error);
        });
}

function afficherDonnees(data) {

    if(data.length != 0) {
        document.getElementById("solde").textContent = "Solde: " + data[0].montant + "$";
        document.getElementById("startingAmount").textContent = "Montant Départ: " + data[0].montant_depart + "$";
        let total = 0;
        const container = document.getElementById('resultat'); // Cibler le tbody
        container.innerHTML = ''; // Vider le conteneur avant d'ajouter les nouvelles données
        data.forEach(compte => {
            // Créer une nouvelle ligne
            const row = document.createElement('tr');
            // Ajouter des cellules avec les données appropriées
            row.innerHTML = `
                <td class="align-content-center" id="${compte.diminutif}">${compte.diminutif}</td> <!-- Symbole -->
                <td class="align-content-center">${compte.nom}</td> <!-- Nom -->
                <td class="align-content-center text-end">${compte.quantie_action}</td> <!-- Quantité -->
                <td class="align-content-center text-end">${compte.prix_acquisition}</td> <!-- Prix d'acquisition -->
                <td class="align-content-center text-end" id="actualPrice-${compte.diminutif}"></td> <!-- Prix actuel -->
                <td class="align-content-center text-end">${calculateProfit(compte.prix_acquisition, compte.montant, compte.quantie_action)}</td> <!-- Profit -->
                <td class="align-content-center text-center">
                    <button class="btn btn-primary" id="buy-${compte.diminutif}">Acheter</button>
                    <button class="btn btn-primary" id="delete-${compte.diminutif}">Vendre</button>
                </td>
            `;
            getActualPrice(compte.diminutif, `actualPrice-${compte.diminutif}`);

            // Ajouter la ligne au tbody
            container.appendChild(row);
            setTimeout(() => {
                const prixActuelText = document.getElementById(`actualPrice-${compte.diminutif}`).textContent.trim();
                const prixActuel = parseFloat(prixActuelText) || 0; // Convertir en nombre ou 0 si invalide

                total += compte.quantie_action * prixActuel; // Calculer le total
                console.log(`Total après ${compte.diminutif}:`, total);

                // Mettre à jour le total après chaque itération
                document.getElementById("totaltotal").textContent = "Total du compte: " + total.toFixed(2) + "$";
            }, 700); // Attendre 100ms pour que `getActualPrice` remplisse le champ
        });
        total += parseFloat(data[0].montant) || 0;
        document.getElementById("totaltotal").textContent = "Total du compte: " + total.toFixed(2) + "$";

    } else {
        document.getElementById("solde").textContent = "Solde: " + montant + "$";
        document.getElementById("startingAmount").textContent = "Montant Départ: " + montantDepart + "$";
    }
}


function getListActionCompte2() {
    //const cip = document.getElementById('cip').textContent;
    const url = `http://localhost:8888/api/listActionCompte?cip=${encodeURIComponent(cip)}`;
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
            console.log("qwerty: ", data);
            afficherDonnees2(data);
        })
        .catch(error => {
            console.error('Erreur:', error);
        });
}

function afficherDonnees2(data) {
    getCompteId();
    console.log("qwertyyyiiib", montant);
    setTimeout(() => {
        if (data.length != 0) {
            document.getElementById("solde").textContent = "Solde: " + data[0].montant + "$";
            document.getElementById("startingAmount").textContent = "Montant Départ: " + data[0].montant_depart + "$";
        } else {
            console.log("gewv");
            document.getElementById("solde").textContent = "Solde: " + montant + "$";
            document.getElementById("startingAmount").textContent = "Montant Départ: " + montantDepart + "$";
        }
    }, 200); // Délai en millisecondes

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

function logout() {
    let logoutURL = "http://localhost:8180/realms/usager/protocol/openid-connect/logout";
    window.open(logoutURL, '_blank');  // '_blank' ouvre dans un nouvel onglet
}

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
            prenom = data;
            nom = data.last_name;
            email = data.email;
            createUsager(data.cip, data.first_name, data.last_name, data.email);
            getCompteId(cip);
            getListActionCompte();


            console.log("Response: ", data);
            document.getElementById("username").textContent = cip;

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
    requestInterval = setInterval(requestStudent, 200);
});






