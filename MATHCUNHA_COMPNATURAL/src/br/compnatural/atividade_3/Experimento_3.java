package br.compnatural.atividade_3;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


import br.compnatural.rna.Pattern;
import br.compnatural.rna.RnaResult;

import br.compnatural.rna.network.MultilayerPerceptron;

public class Experimento_3 {

	Logger log = Logger.getLogger(Experimento_3.class.getName());

	public Pattern loadFile120_8(InputStream filePattern, InputStream fileOut)
			throws IOException {

		int x = 120;
		int d = 8;

		Pattern retorno = new Pattern();
		retorno.setX(new double[d][x]);
		retorno.setD(new double[d][d]);

		BufferedReader readerPattern = new BufferedReader(
				new InputStreamReader(filePattern));
		BufferedReader readerDesi = new BufferedReader(new InputStreamReader(
				fileOut));

		for (int i = 0; i < 5; i++) {
			readerPattern.readLine();
			readerDesi.readLine();
		}
		readerPattern.readLine();
		readerPattern.readLine();

		int i = 0;
		while (readerPattern.ready()) {
			String[] value = readerPattern.readLine().split("    ");
			int j = 0;
			for (String string : value) {
				if (string.trim().length() > 0) {
					retorno.getX()[j++][i] = new Double(string);
				}
			}
			i++;
		}

		i = 0;
		while (readerDesi.ready()) {
			String[] value = readerDesi.readLine().split("	");
			int j = 0;
			for (String string : value) {
				if (string.trim().length() > 0) {
					retorno.getD()[j++][i] = new Double(string);
				}
			}
			i++;
		}

		readerPattern.close();
		readerDesi.close();

		retorno.buildMatrix();

		return retorno;
	}

	public void run() {
		try {

			List<Pattern> patterns = new ArrayList<Pattern>(4);

			Pattern pCorreto = loadFile120_8(
					Experimento_3.class
							.getResourceAsStream("/br/compnatural/atividade_3/char8_12x10.txt"),
					Experimento_3.class
							.getResourceAsStream("/br/compnatural/atividade_3/char8_8x8_1.txt"));

			Pattern p5Porcento = loadFile120_8(
					Experimento_3.class
							.getResourceAsStream("/br/compnatural/atividade_3/char8_12x10_5.txt"),
					Experimento_3.class
							.getResourceAsStream("/br/compnatural/atividade_3/char8_8x8_1.txt"));
			patterns.add(p5Porcento);
			p5Porcento.erro = 5;

			Pattern p10Porcento = loadFile120_8(
					Experimento_3.class
							.getResourceAsStream("/br/compnatural/atividade_3/char8_12x10_10.txt"),
					Experimento_3.class
							.getResourceAsStream("/br/compnatural/atividade_3/char8_8x8_1.txt"));
			patterns.add(p10Porcento);
			p10Porcento.erro = 10;

			Pattern p20Porcento = loadFile120_8(
					Experimento_3.class
							.getResourceAsStream("/br/compnatural/atividade_3/char8_12x10_20.txt"),
					Experimento_3.class
							.getResourceAsStream("/br/compnatural/atividade_3/char8_8x8_1.txt"));
			patterns.add(p20Porcento);
			p20Porcento.erro = 20;

			double[] weights = { 1, -1, 0.05, -0.05, 5d, -5d };
			int[] its = { 10, 100, 1000 };
			int[] hiddens = { 5, 10, 25 };
			double[] alfa = { 0.01, 0.1, 1 };
			List<RnaResult> results = new ArrayList<RnaResult>(20);
			for (int hidden : hiddens) {				
				for (int i = 0; i < weights.length; i += 2) {
										
					MultilayerPerceptron initialPerceptron = MultilayerPerceptron.getMultilayerPerceptronTangenteOneHidden(hidden, pCorreto.getD().length,pCorreto.getX()[0].length, weights[i + 1], weights[i], false);
					
					for (int it : its) {
						for (double d : alfa) {
							MultilayerPerceptron perceptron = new MultilayerPerceptron(weights[i + 1], weights[i]);
							
							log.fine("Inicio [" + weights[i + 1] + ","
									+ weights[i] + "] - alfa (" + d
									+ ") - iteracao (" + it + ") - hidden("+hidden+")");
							
							perceptron.backprop(it, 0.001d, d, pCorreto, hidden, initialPerceptron);

							int number = eval(pCorreto, perceptron);
							log.fine(number + " de " + pCorreto.getX().length
									+ " sem erro");
							results.add(new RnaResult(weights[i + 1],
									weights[i], it, d, number,
									pCorreto.getX().length, 0));

							for (Pattern pattern : patterns) {
								number = eval(pattern, perceptron);
								log.fine(number + " de "
										+ pCorreto.getX().length + " sem erro");
								results.add(new RnaResult(weights[i + 1],
										weights[i], it, d, number, pCorreto
												.getX().length, pattern.erro));
							}

							log.fine("Fim");
						}
					}
				}
			}

			Collections.sort(results, new Comparator<RnaResult>() {

				@Override
				public int compare(RnaResult o1, RnaResult o2) {

					return o2.getMatchs() - o1.getMatchs();
				}
			});
			String result = "";
			for (RnaResult rnaResult : results) {
				result += rnaResult.toString();
			}

			log.info(result);

		} catch (FileNotFoundException e) {
			log.log(Level.SEVERE, "Arquivo nao encontrado", e);
		} catch (IOException e) {
			log.log(Level.SEVERE, "Erro ao ler o arquivo", e);
		}
	}

	private int eval(Pattern pattern, MultilayerPerceptron single) {
		double[][] retorno;
		int total = 0;
		
		double max = -2;
		int index = -1;
		
		for (int i = 0; i < pattern.getX().length; i++) {
			retorno = single.run(pattern, i);

			max = -2;
			index = -1;

			for (int j = 0; j < retorno.length; j++) {
				double value = retorno[j][0];
				if (value > max){
					max = value;
					index = j;
				}
			}

			if (index == i) {
				total++;
				log.fine("Encontrou o caracter " + i + " -> "
						+ toString(retorno));
			}
		}

		return total;
	}

	public String toString(double[][] value) {
		String ret = "";
		for (double[] ds : value) {
			for (double d : ds) {
				ret += " " + d;
			}
		}

		return ret;
	}

	public static void main(String args[]) {

		Experimento_3 exp = new Experimento_3();
		exp.run();
	}
}