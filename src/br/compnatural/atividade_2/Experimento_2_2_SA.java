package br.compnatural.atividade_2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.compnatural.Experiment;

import br.compnatural.algorithm.SimulatedAnnealing;

import br.compnatural.experiment.report.ReportManager;
import br.compnatural.experiment.report.ReportUnit;
import br.compnatural.function.FunctionGriewank;
import br.compnatural.function.FunctionRosenbrock;

import br.compnatural.function.FunctionUnid;
import br.compnatural.function.MathFunction;
import br.compnatural.specification.RealSpecification;

public class Experimento_2_2_SA implements Runnable {
	private Experiment experiment = new Experiment(
			"Segunda questao / atividade #2 / SA");

	public Experimento_2_2_SA() {
		List<MathFunction> functions = new ArrayList<MathFunction>();
		functions.add(new FunctionUnid(Boolean.FALSE));

		functions.add(new FunctionRosenbrock(Boolean.FALSE));
		functions.add(new FunctionGriewank(Boolean.FALSE));

		experiment.setAlgorithms(new ArrayList<Experiment.AlgorithmWrapper>(2));
		experiment.getAlgorithms().add(
				new Experiment.AlgorithmWrapper(new SimulatedAnnealing(1000,0.035, 0.95,100), functions));

	}

	@Override
	public void run() {

		int it = 0;
		List<ReportUnit> ds = new ArrayList<ReportUnit>(10);
		for (Experiment.AlgorithmWrapper algorithm : experiment.getAlgorithms()) {
			for (MathFunction mathFunction : algorithm.getFunctionUnid()) {

				

				RealSpecification specification = null;
				ReportUnit reportUnit = null;

				String nome = null;

				for (int i = 0; i < 10; i++) {
					reportUnit = new ReportUnit();

					reportUnit.setAlgorithm(algorithm);
					reportUnit.setFunction(mathFunction);

					long ini = System.nanoTime();

					specification = getSpecification(mathFunction);

					nome = eval(specification, algorithm, mathFunction,
							reportUnit);

					reportUnit.setTime(System.nanoTime() - ini);

					reportUnit.setTotalIteraction((double) it);

					ds.add(reportUnit);

					reportUnit.setReportGraphInfos(null);
				}

				++it;
				

			}

		}
		
		Map parameters = new HashMap();
		parameters.put("nome", experiment.getName());
		parameters.put("ds", ds);

		ReportManager.saveReport("/otimizacao.jrxml", parameters,
				"experimento_2_2_SA.pdf");
	}

	private RealSpecification getSpecification(MathFunction mathFunction) {
		RealSpecification specification;
		if (mathFunction instanceof FunctionGriewank) {
			specification = new RealSpecification();
			for (int i = 1; i <= 10; i++) {
				specification.addCoordinate("x" + i, -600, 600);
			}
		} else if (mathFunction instanceof FunctionRosenbrock) {
			specification = new RealSpecification();
			for (int i = 1; i <= 10; i++) {
				specification.addCoordinate("x" + i, -2.048, 2.048);
			}
		} else {
			specification = new RealSpecification();
			specification.addCoordinate("x", 0, 1);
		}
		
		specification.pm = 1f;

		return specification;
	}

	private String eval(RealSpecification specification,
			Experiment.AlgorithmWrapper algorithm, MathFunction function,
			ReportUnit reportUnit) {

		if (algorithm.getOptimizationAlgorithm() instanceof SimulatedAnnealing) {
			SimulatedAnnealing lAlgo = (SimulatedAnnealing) algorithm
					.getOptimizationAlgorithm();
			lAlgo.optimize(function.getMax(), function, specification,
					reportUnit);

			return lAlgo.getT_max() + " " + lAlgo.getT_min() + " "
					+ lAlgo.getBeta() + " " + lAlgo.getK() + " ";
		}

		return null;
	}

	public static void main(String[] args) {
		Experimento_2_2_SA teste = new Experimento_2_2_SA();
		teste.run();
	}
}
