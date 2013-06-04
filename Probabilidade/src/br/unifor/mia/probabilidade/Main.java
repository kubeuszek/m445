package br.unifor.mia.probabilidade;

public class Main {
	public static void main(String[] args) {
		int i = 0;
		
		System.out.println("usage: --");
		System.out.println("java -jar probabilidade.jar n pseudoImune acidente nascimento");
		
		Execution execution = new Execution(new Integer(args[i++]), new Integer(args[i++]), new Integer(args[i++]), new Integer(args[i++]));
		
		execution.run();
	}
}
