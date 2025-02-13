package com.example.box_manager.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.springframework.stereotype.Repository;

import com.example.box_manager.model.Unit;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

@Repository
public class UnitRepository {
    private final DatabaseReference databaseReference;

    public UnitRepository() {
        this.databaseReference = FirebaseDatabase.getInstance().getReference("Azienda");
    }
    
    public CompletableFuture<List<Unit>> getDepositoUnits(String uid) {
        CompletableFuture<List<Unit>> future = new CompletableFuture<>();

        DatabaseReference depositoRef = databaseReference.child(uid).child("Deposito");

        depositoRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                List<Unit> units = new ArrayList<>();

                if (snapshot.exists()) {
                    
                    for (DataSnapshot depositoSnapshot : snapshot.getChildren()) {
                        try {
                            Unit unit = new Unit();

                            if (depositoSnapshot.hasChild("numero")) {
                                unit.setNumero(depositoSnapshot.child("numero").getValue(String.class));
                            }
                            if (depositoSnapshot.hasChild("altezza")) {
                                unit.setAltezza(depositoSnapshot.child("altezza").getValue(Integer.class));
                            }
                            if (depositoSnapshot.hasChild("larghezza")) {
                                unit.setLarghezza(depositoSnapshot.child("larghezza").getValue(Integer.class));
                            }
                            if (depositoSnapshot.hasChild("profondita")) {
                                unit.setProfondita(depositoSnapshot.child("profondita").getValue(Integer.class));
                            }
                            if (depositoSnapshot.hasChild("occupato")) {
                                unit.setOccupato(depositoSnapshot.child("occupato").getValue(Boolean.class));
                            }
                            if (depositoSnapshot.hasChild("costo")) {
                                unit.setCosto(depositoSnapshot.child("costo").getValue(Integer.class));
                            }
                            if (depositoSnapshot.hasChild("tipo")) {
                                unit.setTipo(depositoSnapshot.child("tipo").getValue(String.class));
                            }
                            if (depositoSnapshot.hasChild("cliente")) {
                                unit.setCliente(depositoSnapshot.child("cliente").getValue(Integer.class));
                            }

                            units.add(unit);
                        } catch (Exception e) {
                            System.err.println("Errore durante il parsing di depositoSnapshot: " + e.getMessage());
                            e.printStackTrace();
                        }
                    }

                    future.complete(units);
                } else {
                    future.complete(units);
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                future.completeExceptionally(error.toException());
            }
        });

        return future;
    }
    
    public CompletableFuture<Unit> getDepositoUnitById(String uid, String unitId) {
        CompletableFuture<Unit> future = new CompletableFuture<>();

        DatabaseReference unitRef = databaseReference.child(uid).child("Deposito").child(unitId);

        unitRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    try {
                        Unit unit = new Unit();

                        if (snapshot.hasChild("numero")) {
                            unit.setNumero(snapshot.child("numero").getValue(String.class));
                        }
                        if (snapshot.hasChild("altezza")) {
                            unit.setAltezza(snapshot.child("altezza").getValue(Integer.class));
                        }
                        if (snapshot.hasChild("larghezza")) {
                            unit.setLarghezza(snapshot.child("larghezza").getValue(Integer.class));
                        }
                        if (snapshot.hasChild("profondita")) {
                            unit.setProfondita(snapshot.child("profondita").getValue(Integer.class));
                        }
                        if (snapshot.hasChild("occupato")) {
                            unit.setOccupato(snapshot.child("occupato").getValue(Boolean.class));
                        }
                        if (snapshot.hasChild("costo")) {
                            unit.setCosto(snapshot.child("costo").getValue(Integer.class));
                        }
                        if (snapshot.hasChild("tipo")) {
                            unit.setTipo(snapshot.child("tipo").getValue(String.class));
                        }
                        if (snapshot.hasChild("cliente")) {
                            unit.setCliente(snapshot.child("cliente").getValue(Integer.class));
                        }

                        future.complete(unit);
                    } catch (Exception e) {
                        System.err.println("Errore durante il parsing dell'unità: " + e.getMessage());
                        future.completeExceptionally(e);
                    }
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

    
    public CompletableFuture<Void> addNewBox(String uid, Unit newBox) {
        CompletableFuture<Void> future = new CompletableFuture<>();

        DatabaseReference depositoRef = databaseReference.child(uid).child("Deposito");

        depositoRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                try {
                    long maxKey = 0;
                    for (DataSnapshot child : snapshot.getChildren()) {
                        try {
                            long key = Long.parseLong(child.getKey());
                            maxKey = Math.max(maxKey, key);
                        } catch (NumberFormatException e) {
                            System.err.println("Chiave non numerica: " + child.getKey());
                        }
                    }

                    long newKey = maxKey + 1;
                    depositoRef.child(String.valueOf(newKey)).setValue(newBox, (error, ref) -> {
                        if (error != null) {
                            System.err.println("Errore durante l'aggiunta del nuovo box: " + error.getMessage());
                            future.completeExceptionally(error.toException());
                        } else {
                            System.out.println("Nuovo box aggiunto con chiave: " + newKey);
                            future.complete(null);
                        }
                    });

                } catch (Exception e) {
                    System.err.println("Errore durante il calcolo della nuova chiave: " + e.getMessage());
                    future.completeExceptionally(e);
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                System.err.println("Errore durante il recupero delle chiavi esistenti: " + error.getMessage());
                future.completeExceptionally(error.toException());
            }
        });

        return future;
    }
    
    public CompletableFuture<Void> assegnaCliente(String uid, String boxNumero, int clienteId) {
        CompletableFuture<Void> future = new CompletableFuture<>();

        DatabaseReference boxRef = databaseReference.child(uid).child("Deposito").child(boxNumero);

        boxRef.child("cliente").setValue(clienteId, (error, ref) -> {
            if (error != null) {
                System.err.println("Errore durante l'assegnazione del cliente: " + error.getMessage());
                future.completeExceptionally(error.toException());
            } else {
                boxRef.child("occupato").setValue(true, (err, r) -> {
                    if (err != null) {
                        System.err.println("Errore durante l'aggiornamento dello stato occupato: " + err.getMessage());
                        future.completeExceptionally(err.toException());
                    } else {
                        System.out.println("Cliente assegnato e box segnato come occupato.");
                        future.complete(null);
                    }
                });
            }
        });

        return future;
    }

    public CompletableFuture<Void> liberaBox(String uid, String boxNumero) {
        CompletableFuture<Void> future = new CompletableFuture<>();

        DatabaseReference boxRef = databaseReference.child(uid).child("Deposito").child(boxNumero);

        boxRef.child("cliente").setValue(-1, (error, ref) -> { 
            if (error != null) {
                System.err.println("Errore durante la rimozione del cliente: " + error.getMessage());
                future.completeExceptionally(error.toException());
            } else {
                boxRef.child("occupato").setValue(false, (err, r) -> {
                    if (err != null) {
                        System.err.println("Errore durante l'aggiornamento dello stato libero: " + err.getMessage());
                        future.completeExceptionally(err.toException());
                    } else {
                        System.out.println("Box liberato con successo.");
                        future.complete(null);
                    }
                });
            }
        });

        return future;
    }


}

