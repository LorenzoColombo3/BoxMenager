function showAddBoxForm() {
    document.getElementById("addBoxForm").style.display = "block";
    document.getElementById("overlay").style.display = "block";
    fetch('/available', {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json'
        }
    })
    .then(response => {
        if (response.ok) {
            return response.json();  
        }
        throw new Error('Errore nella richiesta');
    })
    .then(data => {
        const selectElement = document.getElementById("numero"); 
        selectElement.innerHTML = "<option value='' disabled selected>Seleziona un box libero</option>";
        data.forEach(box => {
            const option = document.createElement("option");
            option.value = box.numero;  
            option.textContent = `${box.numero} - ${box.superficie.toFixed(2)} m²`; 
            selectElement.appendChild(option);  
        });
    })
    .catch(error => {
        console.error('Errore nella richiesta:', error);
    });
}

function hideAddBoxForm() {
    document.getElementById("addBoxForm").style.display = "none";
    document.getElementById("overlay").style.display = "none";
}
