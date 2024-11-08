function chargerDonnees() {
    return new Promise(resolve => {
        setTimeout(() => {
            addEventOnButton();

            resolve();

        }, 400); // délai simulé de 2 secondes
    });
}

// Afficher le contenu et masquer le loader une fois le chargement terminé
window.addEventListener('load', async () => {
    await chargerDonnees(); // Charger les données
    document.getElementById("loader").style.display = "none"; // Masquer le loader
    document.getElementById("header").style.display = "block"; // Afficher le contenu
});