package com.example.box_manager.model;

public class CustomerUnit {
	private String numero;
    private int codice;
    private int dataInizio;
    private int dataFine;

    public CustomerUnit() {}

    public CustomerUnit(String numero, int codice, int dataInizio, int dataFine) {
    	this.numero = numero;
    	this.dataInizio = dataInizio;
    	this.dataFine = dataFine;
    	this.codice = codice;
    }
    
    public int getCodice() {
        return codice;
    }

    public void setCodice(int codice) {
        this.codice = codice;
    }

    public int getDataInizio() {
        return dataInizio;
    }

    public void setDataInizio(int dataInizio) {
        this.dataInizio = dataInizio;
    }

    public int getDataFine() {
        return dataFine;
    }

    public void setDataFine(int dataFine) {
        this.dataFine = dataFine;
    }

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}
}
