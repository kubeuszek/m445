package br.compnatural.atividade_3;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import br.compnatural.Experiment;
import br.compnatural.State;
import br.compnatural.algorithm.GeneticAlgorithm;
import br.compnatural.experiment.report.ReportGraphInfo;
import br.compnatural.experiment.report.ReportManager;
import br.compnatural.experiment.report.ReportUnit;
import br.compnatural.function.EQMFunction;
import br.compnatural.rna.Pattern;
import br.compnatural.rna.RnaResult;
import br.compnatural.rna.network.MultilayerPerceptron;
import br.compnatural.specification.RealSpecification;

public class Experimento_2GA {

	private static final int GENERATIONS = 1000;
	Logger log = Logger.getLogger(Experimento_2GA.class.getName());
	public static final int MAX_IT = 30;

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

			State.x = pCorreto;

			double[] weights = { 2, -2 };

			int[] hiddens = { 5, 10, 25 };
			
			List<RnaResult> results = new ArrayList<RnaResult>(20);
			
			
			ReportUnit reportUnit = null;
			State bestState = null;
			
			for (int hidden : hiddens) {
				for (int i = 0; i < weights.length; i += 2) {
					List<ReportGraphInfo> graphInfo = new ArrayList<ReportGraphInfo>(GENERATIONS+ 1);
					List<ReportUnit> ds = new ArrayList<ReportUnit>(MAX_IT);
					
					for (int k = 0; k < GENERATIONS; k++) {
						graphInfo.add(new ReportGraphInfo(0d, 0d, 0));
					}

					RealSpecification specification = new RealSpecification();
					EQMFunction function = new EQMFunction(Boolean.TRUE, hidden);
					MultilayerPerceptron perceptron = MultilayerPerceptron
							.getMultilayerPerceptronTangenteOneHidden(hidden,
									pCorreto.getD().length,
									pCorreto.getX()[0].length, weights[i + 1],
									weights[i], true);
					State state = EQMFunction.buildState(perceptron);

					for (int j = 0; j < state.getCoordinate().size(); j++) {
						specification.addCoordinate("x", weights[i + 1],
								weights[i]);
					}

					log.fine("Inicio [" + weights[i + 1] + "," + weights[i]
							+ "] - hidden(" + hidden + ")");

					for (int m = 0; i < MAX_IT; i++) {
						reportUnit = new ReportUnit();
						GeneticAlgorithm lGenetic = new GeneticAlgorithm(50,
								0.75f, 0.1f, Boolean.TRUE);

						reportUnit
								.setAlgorithm(new Experiment.AlgorithmWrapper(
										lGenetic, null));
						reportUnit.setFunction(function);

						long ini = System.nanoTime();

						specification.pm = new Float(lGenetic.pm);

						state = lGenetic.optimize(GENERATIONS, function,
								specification, reportUnit);
						
						if(bestState != null){
							if(bestState.getValue() < state.getValue()){
								bestState = state;
							}
						}else{
							bestState = state;
						}
						
						reportUnit.setTime(System.nanoTime() - ini);

						reportUnit.setTotalIteraction((double) hidden);

						ds.add(reportUnit);
						
						sum(graphInfo, reportUnit.getReportGraphInfos());

					}
					Map parameters = new HashMap();
					parameters.put("nome", "FInal");
					parameters.put("ds", ds);
					
					avg(graphInfo, MAX_IT);
					reportUnit
							.setReportGraphInfo(new JRBeanCollectionDataSource(
									graphInfo));
					ReportManager.saveReport("/otimizacao_grafico.jrxml",
							parameters, "experimento_2GA_"+hidden+".pdf");
					log.fine("Fim");
				}
				
				MultilayerPerceptron perceptron = new EQMFunction(Boolean.FALSE, 1).buildPerceptron(bestState);

				int number = eval(pCorreto, perceptron);
				log.fine(number + " de " + pCorreto.getX().length
						+ " sem erro");
				results.add(new RnaResult(-2.0, 2.0, 0, 0,
						number, pCorreto.getX().length, 0));

				for (Pattern pattern : patterns) {
					number = eval(pattern, perceptron);
					log.fine(number + " de " + pCorreto.getX().length
							+ " sem erro");
					results.add(new RnaResult(-2.0, 2.0,
							hidden, 0, number, pCorreto.getX().length,
							pattern.erro));
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
	
	private void avg(List<ReportGraphInfo> avgGraphInfo, int it) {
		for (int i = 0; i < GENERATIONS; i++) {
			avgGraphInfo.get(i).setAvg_population(
					avgGraphInfo.get(i).getAvg_population() / it);
			avgGraphInfo.get(i).setBest_particle(
					avgGraphInfo.get(i).getBest_particle() / it);
			avgGraphInfo.get(i).setGeneration(i);
		}
	}

	private void sum(List<ReportGraphInfo> avgGraphInfo,
			List<ReportGraphInfo> graphInfo) {
		for (int i = 0; i < GENERATIONS; i++) {
			avgGraphInfo.get(i).setAvg_population(
					graphInfo.get(i).getAvg_population()
							+ avgGraphInfo.get(i).getAvg_population());
			avgGraphInfo.get(i).setBest_particle(
					graphInfo.get(i).getBest_particle()
							+ avgGraphInfo.get(i).getBest_particle());
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
				if (value > max) {
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

		Experimento_2GA exp = new Experimento_2GA();
		exp.run();
	}
}