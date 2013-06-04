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
			return new Individuo(100, "*");
		case 1:
			// pseudo Imune
			return new Individuo(pseudoImune, "@");
		case 2:
			// Sadio
			return new Individuo(0, "");
		case 3:
			// Infectado
			Individuo atual = new Individuo(0, "O");
			atual.setId(++totalInfectados);
			atual.setRecemInfectado(Boolean.FALSE);
			return atual;
		}
		throw new NullPointerException("nenhum individuo nasceu");
	}

	private Integer infectarVizinhos(Individuo[][] matriz, Integer i, Integer j) {

		Integer iMenosUm = i - 1;
		Integer jMenosUm = j - 1;
		Integer iMaisUm = i + 1;
		Integer jMaisUm = j + 1;
		Integer infectantes = 0;

		// System.out.println("i=" + i + " j=" + j);

		if (iMenosUm >= 0) {
			Individuo atual = matriz[iMenosUm][j];
			if (atual != null && atual.infectar()) {
				atual.setId(++totalInfectados);
				atual.setRecemInfectado(Boolean.FALSE);
				infectantes++;
			}
		}

		if (jMenosUm >= 0) {
			Individuo atual = matriz[i][jMenosUm];
			if (atual != null && atual.infectar()) {
				atual.setId(++totalInfectados);
				atual.setRecemInfectado(Boolean.FALSE);
				infectantes++;
			}
		}

		if (jMaisUm < matriz[i].length) {
			Individuo atual = matriz[i][jMaisUm];
			if (atual != null && atual.infectar()) {
				atual.setId(++totalInfectados);
				atual.setRecemInfectado(Boolean.TRUE);
				infectantes++;
			}
		}

		if (iMaisUm < matriz.length) {
			Individuo atual = matriz[iMaisUm][j];
			if (atual != null && atual.infectar()) {
				atual.setId(++totalInfectados);
				atual.setRecemInfectado(Boolean.TRUE);
				infectantes++;
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

		Individuo doente = new Individuo(0, "O");
		doente.setId(++totalInfectados);
		doente.setRecemInfectado(Boolean.FALSE);
		matriz[random.nextInt(matriz.length)][random.nextInt(matriz[0].length)] = doente;

		printMatriz(matriz);

		while (execute) {

			atualizacao++;
			Integer infectantes = 0;
			Integer sadios = 0;
			Integer pseudoImunes = 0;
			Integer imunes = 0;
			Integer doentes = 0;
			for (int i = 0; i < matriz.length; i++) {
				for (int j = 0; j < matriz[i].length; j++) {
					Individuo atual = matriz[i][j];
					if (atual != null) {// Nao Morto
						if (atual.estaInfectado() && !atual.getRecemInfectado()) {
							infectantes += infectarVizinhos(matriz, i, j);
						}
						atual.aniversario();
						atual.setRecemInfectado(atual.estaInfectado() ? Boolean.FALSE
								: null);

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

			printMatriz(matriz);
			System.out.println("infectantes " + infectantes);

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
				if (atual != null) {
					String tipo = atual.toString();

					if (("".equals(tipo) || "*".equals(tipo))
							&& atual.getIdade() >= 10) {
						matriz[i][j] = null;
					}

					if ("@".equals(tipo) && atual.getIdade() >= 4) {
						matriz[i][j] = null;
					}

					if ("O".indexOf(tipo) != -1 && atual.getIdade() >= 4) {
						matriz[i][j] = null;
					}

				}
			}
		}
	}
	
	public Integer getPseudoImune(){
		return pseudoImune;
	}
}
