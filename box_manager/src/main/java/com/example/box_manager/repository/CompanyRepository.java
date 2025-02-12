package com.example.box_manager.repository;

import java.util.ArrayList;   
import java.util.concurrent.CompletableFuture;

import org.springframework.stereotype.Repository;

import com.example.box_manager.model.Company;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

@Repository
public class CompanyRepository {

    private final DatabaseReference databaseReference;

    public CompanyRepository() {
        this.databaseReference = FirebaseDatabase.getInstance().getReference("Azienda");
    }

    public CompletableFuture<Company> getCompanyDataByUserUid(String uid) {
        CompletableFuture<Company> future = new CompletableFuture<>();

        databaseReference.child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
        	@Override
        	public void onDataChange(DataSnapshot snapshot) {
        	    if (snapshot.exists()) {
        	        System.out.println("Dati aziendali ricevuti per UID: " + uid);
        	        Company company = new Company();
        	        company.setUid(uid);
        	        company.setNome(getAziendaNome(snapshot));
        	        company.setEmail(getAziendaEmail(snapshot));
        	        company.setIndirizzo(getAziendaIndirizzo(snapshot));
        	        company.setTelefono(getAziendaTelefono(snapshot));

        	        // Mappatura degli ID dei clienti
        	        DataSnapshot clientiSnapshot = snapshot.child("Cliente");
        	        ArrayList<String> clientiMap = new ArrayList<>();
        	        if (clientiSnapshot.exists()) {
        	            for (DataSnapshot clienteSnapshot : clientiSnapshot.getChildren()) {
        	                clientiMap.add(clienteSnapshot.getKey());
        	            }
        	        }
        	        company.setCliente(clientiMap);

        	        // Mappatura degli ID dei depositi
        	        DataSnapshot depositiSnapshot = snapshot.child("Deposito");
        	        ArrayList<String> depositiMap = new ArrayList<>();
        	        if (depositiSnapshot.exists()) {
        	            for (DataSnapshot depositoSnapshot : depositiSnapshot.getChildren()) {
        	                depositiMap.add(depositoSnapshot.getKey());
        	            }
        	        }
        	        company.setDeposito(depositiMap);

        	        System.out.println(company.getDeposito().toString());
        	        System.out.println(company.getCliente().toString());
        	        
        	        future.complete(company);
        	    } else {
        	        System.out.println("Nessun dato trovato per l'UID: " + uid);
        	        future.completeExceptionally(new RuntimeException("Azienda non trovata per l'utente: " + uid));
        	    }
        	}


            @Override
            public void onCancelled(DatabaseError error) {
                future.completeExceptionally(error.toException());
            }
        });

        return future;
    }

    public String getAziendaNome(DataSnapshot companyData) {
        return companyData.child("Nome").getValue(String.class);
    }
    
    public String getAziendaEmail(DataSnapshot companyData) {
    	return companyData.child("Email").getValue(String.class);
    }
    
    public String getAziendaIndirizzo(DataSnapshot companyData) {
    	return companyData.child("Indirizzo").getValue(String.class);
    }
    
    public String getAziendaTelefono(DataSnapshot companyData) {
    	return companyData.child("Telefono").getValue(String.class);
    }

}
