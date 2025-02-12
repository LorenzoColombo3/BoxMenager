package com.example.box_manager.model;

import java.util.ArrayList;

public class Company {
    private String uid;   
    private String nome;
    private String indirizzo;
    private String email;
    private String telefono;
    private ArrayList<String> clienti;
    private ArrayList<String> depositi;


    public Company() {}

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public ArrayList<String> getCliente() {
        return clienti;
    }

    public void setCliente(ArrayList<String> cliente) {
        this.clienti = cliente;
    }
    public void removeCliente(String id) {
        this.clienti.remove(id);
    }

    public void addCliente(String id) {
        this.clienti.add(id);
    }
    
    public ArrayList<String> getDeposito() {
        return depositi;
    }

    public void setDeposito(ArrayList<String> deposito) {
        this.depositi = deposito;
    }

	public String getIndirizzo() {
		return indirizzo;
	}

	public void setIndirizzo(String indirizzo) {
		this.indirizzo = indirizzo;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
}
