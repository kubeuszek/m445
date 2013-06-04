package br.unifor.mia.probabilidade;

import java.util.Random;

public class Individuo {

	private Integer imunidade;

	private Integer id;
	private Integer idade;
	private Boolean recemInfectado;
	private String tipo;

	private static Random random = new Random();

	public Individuo(Integer imunidade, String tipo) {
		this.imunidade = imunidade;
		this.idade = 0;
		this.tipo = tipo;
	}

	@Override
	public String toString() {
		if (tipo.equals("O")) {
			return "O" + id;
		} else {
			return tipo;
		}
	}

	public Boolean infectar() {
		if ("@".equals(this.toString())) {
			int valor = random.nextInt(100);
			if (valor > this.imunidade) {
				this.tipo = "O";
				return true;
			}
		} else if ("".equals(this.toString())) {
			this.tipo = "O";
			return true;
		}

		return false;
	}

	public void aniversario() {
		idade++;
	}

	public Integer getIdade() {
		return idade;
	}

	public boolean estaInfectado() {
		return toString().indexOf("O") != -1;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Boolean getRecemInfectado() {
		return recemInfectado;
	}

	public void setRecemInfectado(Boolean recemInfectado) {
		this.recemInfectado = recemInfectado;
	}

}
