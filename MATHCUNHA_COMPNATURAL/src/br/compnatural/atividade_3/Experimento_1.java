package br.compnatural.atividade_3;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
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

	public static void main(String args[]) {
		try {
			Experimento_1 exp = new Experimento_1();
			Pattern pat = exp.loadFile120_8(new File(Experimento_1.class.getResource(
					"/br/compnatural/atividade_3/char8_12x10.txt").toURI()),
					new File(Experimento_1.class.getResource(
							"/br/compnatural/atividade_3/char8_8x8_1.txt")
							.toURI()));
			SinglePerceptron single = new SinglePerceptron(8, -5d, 5d);
			single.perceptron(10, 0.1, pat);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
	}
}
