package it.polito.tdp.gosales.model;

public class Arco {
	private Products p1;
	private Products p2;
	private Integer peso;
	public Arco(Products p1, Products p2, Integer peso) {
		super();
		this.p1 = p1;
		this.p2 = p2;
		this.peso = peso;
	}
	public Products getP1() {
		return p1;
	}
	public void setP1(Products p1) {
		this.p1 = p1;
	}
	public Products getP2() {
		return p2;
	}
	public void setP2(Products p2) {
		this.p2 = p2;
	}
	public Integer getPeso() {
		return peso;
	}
	public void setPeso(Integer peso) {
		this.peso = peso;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((p1 == null) ? 0 : p1.hashCode());
		result = prime * result + ((p2 == null) ? 0 : p2.hashCode());
		result = prime * result + ((peso == null) ? 0 : peso.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Arco other = (Arco) obj;
		if (p1 == null) {
			if (other.p1 != null)
				return false;
		} else if (!p1.equals(other.p1))
			return false;
		if (p2 == null) {
			if (other.p2 != null)
				return false;
		} else if (!p2.equals(other.p2))
			return false;
		if (peso == null) {
			if (other.peso != null)
				return false;
		} else if (!peso.equals(other.peso))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "Arco da: " + p1.getNumber() + " a " + p2.getNumber() + " , peso: " + peso + "\n";
	}
	
}
