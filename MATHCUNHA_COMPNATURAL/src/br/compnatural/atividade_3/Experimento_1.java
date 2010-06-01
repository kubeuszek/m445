package br.compnatural.atividade_3;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.logging.Level;
import java.util.logging.Logger;
import br.compnatural.rna.Pattern;
import br.compnatural.rna.network.SinglePerceptron;

public class Experimento_1 {

	Logger log = Logger.getLogger(Experimento_1.class.getName());

	public Pattern loadFile120_8(File filePattern, File fileOut)
			throws IOException {

		int x = 120;
		int d = 8;

		Pattern retorno = new Pattern();
		retorno.setX(new double[d][x]);
		retorno.setD(new double[d][d]);

		BufferedReader readerPattern = new BufferedReader(new FileReader(
				filePattern));
		BufferedReader readerDesi = new BufferedReader(new FileReader(fileOut));

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
			Pattern pCorreto = loadFile120_8(new File(Experimento_1.class
					.getResource("/br/compnatural/atividade_3/char8_12x10.txt")
					.toURI()), new File(Experimento_1.class.getResource(
					"/br/compnatural/atividade_3/char8_8x8_1.txt").toURI()));

			Pattern p5Porcento = loadFile120_8(new File(Experimento_1.class
					.getResource(
							"/br/compnatural/atividade_3/char8_12x10_5.txt")
					.toURI()), new File(Experimento_1.class.getResource(
					"/br/compnatural/atividade_3/char8_8x8_1.txt").toURI()));

			Pattern p10Porcento = loadFile120_8(new File(Experimento_1.class
					.getResource(
							"/br/compnatural/atividade_3/char8_12x10_10.txt")
					.toURI()), new File(Experimento_1.class.getResource(
					"/br/compnatural/atividade_3/char8_8x8_1.txt").toURI()));

			Pattern p20Porcento = loadFile120_8(new File(Experimento_1.class
					.getResource(
							"/br/compnatural/atividade_3/char8_12x10_20.txt")
					.toURI()), new File(Experimento_1.class.getResource(
					"/br/compnatural/atividade_3/char8_8x8_1.txt").toURI()));

			double[] weights = { 1, -1, 0.05, -0.05 };
			int[] its = { 10, 100, 500 };
			double[] alfa = { 0.01, 0.1, 1 };

			for (int i = 0; i < weights.length; i += 2) {
				for (int it : its) {
					for (double d : alfa) {
						log.info("Inicio ["+weights[i + 1]+","+ weights[i]+"] - alfa ("+d+") - iteracao ("+it+")");
						SinglePerceptron single = new SinglePerceptron(8,
								weights[i + 1], weights[i]);
						single.perceptron(it, d, pCorreto);

						int number = eval(pCorreto, single);
						log.info(number + " de " + pCorreto.getX().length
								+ " sem erro");

						number = eval(p5Porcento, single);
						log.info(number + " de " + p5Porcento.getX().length
								+ " 5% de erro");

						number = eval(p10Porcento, single);
						log.info(number + " de " + p10Porcento.getX().length
								+ " 10% de erro");

						number = eval(p20Porcento, single);
						log.info(number + " de " + p20Porcento.getX().length
								+ " 20% de erro");
						
						log.info("Fim");
					}
				}
			}

		} catch (FileNotFoundException e) {
			log.log(Level.SEVERE, "Arquivo nao encontrado", e);
		} catch (IOException e) {
			log.log(Level.SEVERE, "Erro ao ler o arquivo", e);
		} catch (URISyntaxException e) {
			log.log(Level.SEVERE, "URI mal formada", e);
		}

	}

	private int eval(Pattern pattern, SinglePerceptron single) {
		double[][] retorno;
		int total = pattern.getX().length;
		int ant = 0;
		for (int i = 0; i < pattern.getX().length; i++) {
			retorno = single.run(pattern, i);

			ant = total;

			for (int j = 0; j < retorno.length; j++) {
				double value = retorno[j][0];
				if (j == i && value != 1) {
					total--;

					break;
				}

				else if (j != i && value != -1) {
					total--;

					break;
				}

			}

			if (ant == total) {
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

		Experimento_1 exp = new Experimento_1();
		exp.run();
	}
}