package br.compnatural.atividade_3;

public class Main3 {
	public static void main(String[] args) {
		for (String arg : args) {
			if("1".equals(arg)){
				Experimento_1 teste = new Experimento_1();
				teste.run();
			}else if("2".equals(arg)){
				Experimento_3 teste = new Experimento_3();
				teste.run();
			}else if("3".equals(arg)){
				Experimento_2GA teste = new Experimento_2GA();
				teste.run();
			}else if("4".equals(arg)){
				Experimento_2PSO teste = new Experimento_2PSO();
				teste.run();
			}
		}
	}
}
