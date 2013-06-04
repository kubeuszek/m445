package br.unifor.mia.probabilidade;

import java.util.Map;

public class Dados {
	public final Integer imunes;
	public final Integer pseudoImunes;
	public final Integer infectantes;
	public final Integer doentes;
	public final Integer acidentados;
	public final Integer sadios;
	public final Integer nascidos;

	public Dados(Map<String, Integer> map) {
		imunes = map.get("imunes");
		pseudoImunes = map.get("pseudoImunes");
		infectantes = map.get("infectantes");
		doentes = map.get("doentes");
		acidentados = map.get("acidentados");
		sadios = map.get("sadios");
		nascidos = map.get("nascidos");
	}
	
	public String toString(){
		return String.format("%d\t%d\t%d\t%d\t%d\t%d\t%d\t", imunes, pseudoImunes, infectantes, doentes, acidentados, sadios, nascidos);
	}
}
