package com.example.box_manager.config;

import com.google.auth.oauth2.GoogleCredentials;  
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;

import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;

@Configuration
public class FirebaseConfig {

    @PostConstruct
    public void initializeFirebase() {
        try {
            // Carica il file delle credenziali 
            InputStream serviceAccount = getClass().getClassLoader().getResourceAsStream("box-manager-dcac8-firebase-adminsdk-fpouc-2d3084e2ea.json");

            if (serviceAccount == null) {
                throw new IOException("File delle credenziali non trovato nel classpath.");
            }

            @SuppressWarnings("deprecation")
			FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .setDatabaseUrl("https://box-manager-dcac8-default-rtdb.europe-west1.firebasedatabase.app")
                    .build();

            // Inizializza Firebase
            FirebaseApp.initializeApp(options);
            System.out.println("Connessione avvenuta con Firebase Database");

            // Inizializza Firebase Authentication
            FirebaseAuth.getInstance();
            System.out.println("Firebase Authentication inizializzato");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
