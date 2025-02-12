package com.example.box_manager.model;

public class Unit {
	private String numero;
    private int altezza;
    private int larghezza;
    private int profondita;
    private boolean occupato;
    private int costo;
    private String tipo;
    private int cliente;
    private double superficie; 


    public Unit(String numero, int altezza, int larghezza, int profondita, boolean occupato, int costo, String tipo,
			int cliente) {
		super();
		this.numero = numero;
		this.altezza = altezza;
		this.larghezza = larghezza;
		this.profondita = profondita;
		this.occupato = occupato;
		this.costo = costo;
		this.tipo = tipo;
		this.cliente = cliente;
	}

	public Unit() {}
	
	public double getSuperficie() {
        return superficie;
    }

    public void setSuperficie(double superficie) {
        this.superficie = superficie;
    }

    public int getAltezza() {
        return altezza;
    }

    public void setAltezza(int altezza) {
        this.altezza = altezza;
    }

    public int getLarghezza() {
        return larghezza;
    }

    public void setLarghezza(int larghezza) {
        this.larghezza = larghezza;
    }

    public int getProfondita() {
        return profondita;
    }

    public void setProfondita(int profondita) {
        this.profondita = profondita;
    }

    public boolean isOccupato() {
        return occupato;
    }

    public void setOccupato(boolean occupato) {
        this.occupato = occupato;
    }

    public int getCosto() {
        return costo;
    }

    public void setCosto(int costo) {
        this.costo = costo;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public int getCliente() {
        return cliente;
    }

    public void setCliente(int cliente) {
        this.cliente = cliente;
    }

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}
	
	public void assegnaCliente(int clienteId) {
	    this.cliente = clienteId;
	    this.occupato = true;
	}

	public void liberaBox() {
	    this.cliente = -1;
	    this.occupato = false;
	}

}
