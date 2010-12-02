package br.com.mathcunha.recibo;

import java.util.List;

public class Salario {
	private double valorBase;
	
	private  List<Desconto> descontos;

	public double getValorBase() {
		return valorBase;
	}

	public void setValorBase(double valorBase) {
		this.valorBase = valorBase;
	}

	public List<Desconto> getDescontos() {
		return descontos;
	}

	public void setDescontos(List<Desconto> descontos) {
		this.descontos = descontos;
	}
	
	
}
