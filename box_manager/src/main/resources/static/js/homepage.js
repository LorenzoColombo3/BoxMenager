// Funzione che gestisce il click sui pulsanti e invia una richiesta GET al server
function goToPage(page) {
    let url = '';
    switch (page) {
        case 'sites':
            url = '/boxes';
            break;
        case 'customers':
            url = '/customers';
            break;
        case 'profile':
            url = '/profile';
            break;
        default:
            console.error('Endpoint sconosciuto');
            return;
    }

    fetch(url, {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json'
        }
    })
    .then(response => {
        if (response.ok) {
            return response.text(); 
        }
        throw new Error('Errore nella richiesta');
    })
    .then(data => {
        console.log(data);
        window.location.href = url; 
    })
    .catch(error => {
        console.error('Errore nella richiesta:', error);
    });
}
