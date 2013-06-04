package br.unifor.mia.probabilidade;

import java.util.Random;

public class Individuo {
	
	private Integer imunidade;
	private Integer infectado;
	private Integer id;
	private Integer idade;
	
	private static Random random = new Random();
			
	
	public Individuo (Integer imunidade, Integer infectado){
		this.imunidade = imunidade;
		this.infectado = infectado;
		this.idade = 0;
	}
	
	@Override
	public String toString(){
		if(this.imunidade == 100){
			return "*";
		}else if(this.imunidade > 0){
			return "@";
		}else if(this.infectado > 0){
			return "O"+id;
		}else{
			return "";
		}
		
	}
	
	public Boolean infectar(){
		if("@".equals(this.toString())){
			int valor = random.nextInt(100);
			if(this.imunidade < valor){
				this.infectado = 100;
				return true;
			}
		}else if("".equals(this.toString())){
			this.infectado = 100;
			return true;
		}
		
		return false;
	}
	
	public void aniversario(){
		idade++;
	}
	
	public void zerarIdade(){
		idade = 0;
	}
	
	public Integer getIdade(){
		return idade;
	}
	
	public boolean estaInfectado(){
		return infectado == 100;
	}
	
	public void setId(Integer id){
		this.id = id;
	}
	
}
