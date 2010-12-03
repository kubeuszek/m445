package br.com.mathcunha.recibo;

import java.util.List;

public class Salario {
	private double valorBase;
	private double valorLiquido;
	private double valorTransporte;
	private int mes;

	public double calcValorLiquido() {
		double descontos = 0;

		for (Desconto desconto : getDescontos()) {
			descontos += desconto.calcValorDesconto();
		}
		valorLiquido = getValorBase() - descontos;

		return getValorLiquido();
	}
	
	
	public String getValorExtenso(){
		return new Extenso(getValorLiquido()).toString();
	}
	
	public String getValorTransporteExtenso(){
		return new Extenso(getValorTransporte()).toString();
	}

	private List<Desconto> descontos;

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

	public double getValorLiquido() {
		return valorLiquido;
	}


	public void setValorTransporte(double valorTransporte) {
		this.valorTransporte = valorTransporte;
	}


	public double getValorTransporte() {
		return valorTransporte;
	}


	public void setMes(int mes) {
		this.mes = mes;
	}


	public int getMes() {
		return mes;
	}

}
