package br.com.mathcunha.recibo;

public class Desconto {
	
	private Salario salario;
	
	private String descricao;
	
	public Salario getSalario() {
		return salario;
	}

	public void setSalario(Salario salario) {
		this.salario = salario;
	}

	public Desconto(double valorPercentual, String descricao, Salario salario){
		setValorPercentual(valorPercentual);
		this.salario = salario;
		this.descricao = descricao;
	}
	
	public double getValorPercentual() {
		return valorPercentual;
	}

	public void setValorPercentual(double valorPercentual) {
		this.valorPercentual = valorPercentual;
	}
	
	public Double getValorDesconto(){
		return salario.getValorBase()*getValorPercentual();
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}

	private double valorPercentual;
	
}
