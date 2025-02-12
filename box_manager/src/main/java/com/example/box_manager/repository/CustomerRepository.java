package com.example.box_manager.repository;

import java.util.ArrayList; 
import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.springframework.stereotype.Repository;

import com.example.box_manager.model.Customer;
import com.example.box_manager.model.CustomerUnit;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


@Repository
public class CustomerRepository {
	private final DatabaseReference databaseReference;

    public CustomerRepository() {
        this.databaseReference = FirebaseDatabase.getInstance().getReference("Azienda");
    }
    public CompletableFuture<Customer> getCustomersDataByUserUid(String uid, String cid) {
        CompletableFuture<Customer> future = new CompletableFuture<>();

        databaseReference.child(uid).child("Cliente").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    Customer foundCustomer =  new Customer();;

                    for (DataSnapshot customerSnapshot : snapshot.getChildren()) {
                        if (customerSnapshot != null && customerSnapshot.getKey().equals(cid)) {
                        	foundCustomer.setId(customerSnapshot.getKey());
                            foundCustomer.setCitta(getCustomerCitta(customerSnapshot));
                            foundCustomer.setEmail(getCustomerEmail(customerSnapshot));
                            foundCustomer.setNome(getCustomerNome(customerSnapshot));
                            foundCustomer.setCognome(getCustomerCognome(customerSnapshot));
                            System.out.println(foundCustomer.getEmail());
                            getCustomerUnits(uid, cid).thenAccept(units -> {
                                foundCustomer.setUnita(units);
                                future.complete(foundCustomer);
                            }).exceptionally(ex -> {
                                future.completeExceptionally(ex);
                                return null;
                            });

                            return;
                        }
                    }
                } else {
                    future.completeExceptionally(new RuntimeException("Nessun cliente trovato per l'UID: " + uid));
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                future.completeExceptionally(error.toException());
            }
        });

        return future;
    }

    private CompletableFuture<List<CustomerUnit>> getCustomerUnits(String uid, String cid) {
        CompletableFuture<List<CustomerUnit>> future = new CompletableFuture<>();

        databaseReference.child(uid).child("Cliente").child(cid).child("Unità").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                List<CustomerUnit> units = new ArrayList<>();
                if (snapshot.exists()) {

                	for (DataSnapshot unitSnapshot : snapshot.getChildren()) {
                	    if (unitSnapshot != null) {
                	        System.out.println("Esaminando unitSnapshot: " + unitSnapshot.getKey());
                	        System.out.println("Contenuto unitSnapshot: " + unitSnapshot.getValue());

                	        try {
                	            CustomerUnit unit = new CustomerUnit();
                	            System.out.println("1 - Inizio parsing unità");

                	            if (unitSnapshot.hasChild("numero")) {
                	                System.out.println("Campo numero presente");
                	                String numero = unitSnapshot.child("numero").getValue().toString();
                	                unit.setNumero(numero);
                	                System.out.println("numero: " + numero);
                	            } else {
                	                System.out.println("Campo numero mancante");
                	            }

                	            if (unitSnapshot.hasChild("codice")) {
                	                Integer codice = unitSnapshot.child("codice").getValue(Integer.class);
                	                unit.setCodice(codice);
                	                System.out.println("codice: " + codice);
                	            } else {
                	                System.out.println("Campo codice mancante");
                	            }

                	            if (unitSnapshot.hasChild("dataInizio")) {
                	                Integer dataInizio = unitSnapshot.child("dataInizio").getValue(Integer.class);
                	                unit.setDataInizio(dataInizio);
                	                System.out.println("dataInizio: " + dataInizio);
                	            } else {
                	                System.out.println("Campo dataInizio mancante");
                	            }

                	            if (unitSnapshot.hasChild("dataFine")) {
                	                Integer dataFine = unitSnapshot.child("dataFine").getValue(Integer.class);
                	                unit.setDataFine(dataFine);
                	                System.out.println("dataFine: " + dataFine);
                	            } else {
                	                System.out.println("Campo dataFine mancante");
                	            }

                	            units.add(unit);
                	        } catch (Exception e) {
                	            System.err.println("Errore durante il parsing di unitSnapshot: " + e.getMessage());
                	            e.printStackTrace();
                	        }
                	    } else {
                	        System.out.println("unitSnapshot nullo, saltando");
                	    }
                	}

                    future.complete(units);
                } else {
                    future.complete(null);
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                future.completeExceptionally(error.toException());
            }
        });

        return future;
    }
    
    public CompletableFuture<Void> addNewCustomer(String uid, Customer customer) {
        CompletableFuture<Void> future = new CompletableFuture<>();

        DatabaseReference counterRef = databaseReference.child(uid).child("ClienteCounter");

        counterRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                long newCustomerId = snapshot.exists() ? snapshot.getValue(Long.class) + 1 : 1;
                counterRef.setValue(newCustomerId, (error, ref) -> {
                    if (error != null) {
                        future.completeExceptionally(error.toException()); 
                    } else {
                        DatabaseReference newCustomerRef = databaseReference.child(uid).child("Cliente").child(String.valueOf(newCustomerId));

                        newCustomerRef.setValue(customer, (error2, ref2) -> {
                            if (error2 != null) {
                                future.completeExceptionally(error2.toException()); 
                            } else {
                                future.complete(null); 
                            }
                        });
                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError error) {
                future.completeExceptionally(error.toException()); 
            }
        });

        return future;
    }

    public CompletableFuture<Void> removeCustomer(String uid, String cid) {
        CompletableFuture<Void> future = new CompletableFuture<>();

        DatabaseReference customerRef = databaseReference.child(uid).child("Cliente").child(cid);
        System.out.println("riferimento creato");
        customerRef.removeValue((error, ref) -> {
        	System.out.println("sta rimuovendo");
            if (error != null) {
                future.completeExceptionally(error.toException()); 
            } else {
                future.complete(null); 
                System.out.println("rimosso");
            }
        });

        return future;
    }

    public CompletableFuture<Void> addCustomerUnit(String uid, String cid, CustomerUnit unit) {
        CompletableFuture<Void> future = new CompletableFuture<>();
        DatabaseReference unitsRef = databaseReference.child(uid).child("Cliente").child(cid).child("Unità");

        DatabaseReference newUnitRef = unitsRef.child(unit.getNumero());
        newUnitRef.setValue(unit, (error, ref) -> {
            if (error != null) {
                future.completeExceptionally(error.toException()); 
            } else {
                future.complete(null); 
            }
        });

        return future;
    }
    
    public CompletableFuture<Void> removeCustomerUnit(String uid, String cid, String unitNumber) {
        CompletableFuture<Void> future = new CompletableFuture<>();
        DatabaseReference unitsRef = databaseReference.child(uid).child("Cliente").child(cid).child("Unità");
        DatabaseReference unitRef = unitsRef.child(unitNumber);
        unitRef.removeValue((error, ref) -> {
            if (error != null) {
                future.completeExceptionally(error.toException()); 
            } else {
                future.complete(null); 
            }
        });

        return future;
    }
    
    public String getCustomerNome(DataSnapshot customerData) {
        return customerData.child("nome").getValue(String.class);
    }
    public String getCustomerCognome(DataSnapshot customerData) {
        return customerData.child("cognome").getValue(String.class);
    }
    public String getCustomerCitta(DataSnapshot customerData) {
        return customerData.child("citta").getValue(String.class);
    }
    public String getCustomerEmail(DataSnapshot customerData) {
        return customerData.child("email").getValue(String.class);
    }
}
