// Funzione per gestire il logout
function logout() {
    fetch('/logout', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        }
    })
    .then(response => {
        if (response.ok) {
            window.location.href = '/login'; 
        } else {
            alert('Errore durante il logout. Riprova.');
        }
    })
    .catch(error => {
        console.error('Errore nella richiesta di logout:', error);
        alert('Errore di rete. Riprova.');
    });
}

document.querySelector('.logout-btn').addEventListener('click', logout);
