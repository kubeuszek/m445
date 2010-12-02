package br.com.mathcunha.recibo;

public class Desconto {
	
	private Salario salario;
	
	public Salario getSalario() {
		return salario;
	}

	public void setSalario(Salario salario) {
		this.salario = salario;
	}

	public Desconto(double valorPercentual, Salario salario){
		setValorPercentual(valorPercentual);
		this.salario = salario;
	}
	
	public double getValorPercentual() {
		return valorPercentual;
	}

	public void setValorPercentual(double valorPercentual) {
		this.valorPercentual = valorPercentual;
	}
	
	public Double getValorDesconto(){
		return salario.getValorBase()*getValorPercentual()/(double)100;
	}

	private double valorPercentual;
	
}
