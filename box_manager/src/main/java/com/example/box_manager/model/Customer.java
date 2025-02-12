package com.example.box_manager.model;

import java.util.List;

public class Customer {
	private String id;
    private String nome;
    private String cognome;
    private String citta;
    private String email;
    private List<CustomerUnit> unita;

    public Customer() {}

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public String getCitta() {
        return citta;
    }

    public void setCitta(String citta) {
        this.citta = citta;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<CustomerUnit> getUnita() {
        return unita;
    }

    public void setUnita(List<CustomerUnit> unita) {
        this.unita = unita;
    }

	public String getId() {
		return id;
	}

	public void setId(String string) {
		this.id = string;
	}
}
