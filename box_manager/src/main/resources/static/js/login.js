
import { initializeApp } from 'https://www.gstatic.com/firebasejs/9.0.0/firebase-app.js';
import { getAuth, signInWithEmailAndPassword } from 'https://www.gstatic.com/firebasejs/9.0.0/firebase-auth.js';

// Configurazione Firebase
const firebaseConfig = {
  apiKey: "AIzaSyDl65jOQIey5nE_b5jD6XncGkaeQ8YeYI0",
  authDomain: "box-manager-dcac8.firebaseapp.com",
  databaseURL: "https://box-manager-dcac8-default-rtdb.europe-west1.firebasedatabase.app",
  projectId: "box-manager-dcac8",
  storageBucket: "box-manager-dcac8.firebasestorage.app",
  messagingSenderId: "856274197305",
  appId: "1:856274197305:web:f752101df4347531f426e8"
};

// Inizializzazione dell'app
const app = initializeApp(firebaseConfig);
const auth = getAuth(app);

// Login con Firebase
document.getElementById('loginForm').addEventListener('submit', function (event) {
    event.preventDefault();

    const username = document.getElementById('username').value;
    const password = document.getElementById('password').value;

    if (username && password) {
        signInWithEmailAndPassword(auth, username, password)
            .then(userCredential => {
                userCredential.user.getIdToken()
                    .then(idToken => {
                        console.log(idToken);
                        fetch('/login', {
                            method: 'POST',
                            headers: {
                                'Content-Type': 'application/json',
                            },
                            body: JSON.stringify({ token: idToken })
                        })
                        .then(response => response.json())
                        .then(data => {
                            if (data.success) {
                                window.location.href = '/homepage';
                            } else {
                                alert("Login fallito.");
                            }
                        })
                        .catch(error => {
                            console.error("Errore nel comunicare con il backend:", error);
                            alert("Errore nel login.");
                        });
                    });
            })
            .catch(error => {
                alert("Email o password errati");
            });
    } else {
        alert("Inserisci username e password.");
    }
});
