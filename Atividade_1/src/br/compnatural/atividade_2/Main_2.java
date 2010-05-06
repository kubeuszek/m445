package br.compnatural.atividade_2;

public class Main_2 {
	public static void main(String[] args) {
		if("1".equals(args[0])){
			Experimento_2_1 teste = new Experimento_2_1();
			teste.run();
		}else if("2".equals(args[0])){
			Experimento_2_2 teste = new Experimento_2_2();
			teste.run();
		}else if("3".equals(args[0])){
			Experimento_2_2_GA teste = new Experimento_2_2_GA();
			teste.run();
		}
	}
}
