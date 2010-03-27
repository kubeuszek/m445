package br.compnatural.atividade_1;

public class Main {
	public static void main(String[] args) {
		if("1".equals(args[0])){
			Experimento_1 teste = new Experimento_1();
			teste.run();
		}else if("2".equals(args[0])){
			Experimento_2 teste = new Experimento_2();
			teste.run();
		}
	}
}
