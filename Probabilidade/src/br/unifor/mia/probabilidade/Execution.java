package br.unifor.mia.probabilidade;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class Execution implements Runnable {

	private Integer n;
	private Integer pseudoImune;
	private Integer acidente;
	private Integer nascimento;
	private List<Dados> dados = new ArrayList<Dados>();
	private Random random = new Random();
	private Integer totalInfectados = 0;

	public Execution(Integer n, Integer pseudoImune, Integer acidente,
			Integer nascimento) {
		this.n = n;
		this.pseudoImune = pseudoImune;
		this.acidente = acidente;
		this.nascimento = nascimento;
	}

	public void printMatriz(Object[][] matriz) {
		for (int i = 0; i < matriz.length; i++) {
			System.out.println("");
			for (int j = 0; j < matriz[i].length; j++) {
				System.out.print(matriz[i][j] + ",");
			}
		}
		System.out.println("");
	}

	private Individuo nascimento(Boolean doente) {
		int indice = 3;
		if (doente) {
			indice = 4;
		}

		int valor = random.nextInt(indice);

		switch (valor) {
		case 0:
			// Imune
			return new Individuo(100, 0);
		case 1:
			// pseudo Imune
			return new Individuo(pseudoImune, 0);
		case 2:
			// Sadio
			return new Individuo(0, 0);
		case 3:
			// Infectado
			return new Individuo(0, 100);
		}
		throw new NullPointerException("nenhum individuo nasceu");
	}

	private Integer infectarVizinhos(Individuo[][] matriz, Integer i, Integer j) {

		Integer iMenosUm = i - 1;
		Integer jMenosUm = j - 1;
		Integer iMaisUm = i + 1;
		Integer jMaisUm = j + 1;
		Integer infectantes = 0;

		//System.out.println("i=" + i + " j=" + j);

		if (iMenosUm >= 0) {
			if (jMenosUm >= 0) {
				Individuo atual = matriz[iMenosUm][jMenosUm];
				if (atual.infectar()) {
					atual.setId(++totalInfectados);
					atual.zerarIdade();
					infectantes++;
				}
			}
			if (jMaisUm < matriz[i].length) {
				Individuo atual = matriz[iMenosUm][jMaisUm];
				if (atual.infectar()) {
					atual.setId(++totalInfectados);
					atual.zerarIdade();
					infectantes++;
				}
			}
		}

		if (iMaisUm < matriz.length) {
			if (jMenosUm >= 0) {
				Individuo atual = matriz[iMaisUm][jMenosUm];
				if (atual.infectar()) {
					atual.setId(++totalInfectados);
					atual.zerarIdade();
					infectantes++;
				}
			}
			if (jMaisUm < matriz[i].length) {
				Individuo atual = matriz[iMaisUm][jMaisUm];
				if (atual.infectar()) {
					atual.setId(++totalInfectados);
					atual.zerarIdade();
					infectantes++;
				}
			}
		}

		return infectantes;
	}

	@Override
	public void run() {
		Boolean execute = true;
		Individuo[][] matriz = new Individuo[n][n];
		int atualizacao = 0;

		// Primeira geracao
		for (int i = 0; i < matriz.length; i++) {
			for (int j = 0; j < matriz[i].length; j++) {
				matriz[i][j] = nascimento(Boolean.FALSE);
			}
		}
		// Infectado

		Individuo doente = new Individuo(0, 100);
		doente.setId(++totalInfectados);
		matriz[random.nextInt(matriz.length)][random.nextInt(matriz[0].length)] = doente;

		while (execute) {
			printMatriz(matriz);
			atualizacao++;
			Integer infectantes = 0;
			Integer sadios = 0;
			Integer pseudoImunes = 0;
			Integer imunes = 0;
			Integer doentes = 0;
			for (int i = 0; i < matriz.length; i++) {
				for (int j = 0; j < matriz[i].length; j++) {
					Individuo atual = matriz[i][j];
					if (atual != null) {//Nao Morto
						if (atual.estaInfectado() && atual.getIdade() > 0) {
							infectantes += infectarVizinhos(matriz, i, j);
						}
						atual.aniversario();

						String tipo = atual.toString();
						if ("".equals(tipo)) {
							sadios++;
						} else if ("*".equals(tipo)) {
							imunes++;
						} else if ("@".equals(tipo)) {
							pseudoImunes++;
						} else {
							doentes++;
						}
					}
				}
			}
			mortes(matriz);

			Map<String, Integer> map = new HashMap<String, Integer>();

			map.put("imunes", imunes);
			map.put("pseudoImunes", pseudoImunes);
			map.put("infectantes", infectantes);
			map.put("doentes", doentes);
			map.put("acidentados", acidentes(matriz));
			map.put("sadios", sadios);
			map.put("nascidos", nascimentos(matriz));

			dados.add(new Dados(map));

			execute = infectantes > 0 || atualizacao == 1;

		}

		printResults();
	}

	private void printResults() {
		System.out.println("\n\n Resultados \n\n\n");

		// System.out.println("Imunes\tPseudo-Imunes\tInfectantes\tDoentes\tAcidentados\tsadios\tNascidos");
		System.out.println("Im\tP\tIn\tD\tA\tS\tN");
		for (Dados dado : dados) {
			System.out.println(dado.toString());
		}
	}

	private Integer acidentes(Individuo[][] matriz) {
		Integer acidentados = 0;

		for (int i = 0; i < matriz.length; i++) {
			for (int j = 0; j < matriz[i].length; j++) {
				Individuo atual = matriz[i][j];
				if (atual != null && random.nextInt(100) < acidente) {
					acidentados++;
					matriz[i][j] = null;
				}
			}

		}

		return acidentados;
	}

	private Integer nascimentos(Individuo[][] matriz) {
		Integer nascimentos = 0;

		for (int i = 0; i < matriz.length; i++) {
			for (int j = 0; j < matriz[i].length; j++) {
				Individuo atual = matriz[i][j];
				if (atual == null && random.nextInt(100) < nascimento) {
					nascimentos++;
					matriz[i][j] = nascimento(Boolean.TRUE);
				}
			}

		}

		return nascimentos;
	}

	private void mortes(Individuo[][] matriz) {
		for (int i = 0; i < matriz.length; i++) {
			for (int j = 0; j < matriz[i].length; j++) {
				Individuo atual = matriz[i][j];
				if(atual != null){
					String tipo = atual.toString();
					switch (atual.getIdade()) {
					case 10:
						if ("".equals(tipo) || "*".equals(tipo)) {
							matriz[i][j] = null;
						}
						break;
					case 4:
						if ("@".equals(tipo)) {
							matriz[i][j] = null;
						}
						break;
					case 3:
						if ("O".contains(tipo)) {
							matriz[i][j] = null;
						}
						break;
					}
				}
			}
		}
	}
}
