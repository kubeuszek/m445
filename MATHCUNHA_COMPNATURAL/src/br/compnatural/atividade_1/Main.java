package br.compnatural.atividade_1;

public class Main {
	public static void main(String[] args) {
		if("1".equals(args[0])){
			Experimento_1 teste = new Experimento_1();
			teste.run();
		}else if("2".equals(args[0])){
			Experimento_2 teste = new Experimento_2();
			teste.run();
		}else if("3".equals(args[0])){
			Experimento_3 teste = new Experimento_3();
			teste.run();
		}else if("4".equals(args[0])){
			Experimento_4 teste = new Experimento_4();
			teste.run();
		}else if("5".equals(args[0])){
			Experimento_5 teste = new Experimento_5();
			teste.run();
		}
	}
}
