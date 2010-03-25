package br.compnatural.atividade_1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.compnatural.Experiment;
import br.compnatural.Specification;
import br.compnatural.State;
import br.compnatural.experiment.report.ReportManager;
import br.compnatural.experiment.report.ReportUnit;

public class Experimento_1 implements Runnable {

	private Experiment experiment = new Experiment("Primeira questão");

	public Experimento_1() {

		List<Experiment.MathFunctionWrapper> functions = new ArrayList<Experiment.MathFunctionWrapper>();
		functions.add(new Experiment.MathFunctionWrapper(new FunctionUnid(),
				new ArrayList<ReportUnit>()));

		experiment.setAlgorithms(new ArrayList<Experiment.AlgorithmWrapper>(4));
		experiment.getAlgorithms().add(
				new Experiment.AlgorithmWrapper(new HillClimbing(), functions));
		experiment.getAlgorithms().add(
				new Experiment.AlgorithmWrapper(new HillClimbingIterated(),
						functions));
	}

	@Override
	public void run() {

		State g = State.getState();
		List<ReportUnit> ds = new ArrayList<ReportUnit>(1000);

		g.setValue(1);

		Specification specification = null;

		for (Experiment.AlgorithmWrapper algorithm : experiment.getAlgorithms()) {
			for (Experiment.MathFunctionWrapper mathFunction : algorithm
					.getMathFunctionWrapper()) {

				for (int it = 10; it <= 1000; it *= 10) {
					for (int i = 0; i < 10; i++) {

						ReportUnit reportUnit = new ReportUnit();

						reportUnit.setAlgorithm(algorithm);
						reportUnit.setFunction(mathFunction);

						long ini = System.nanoTime();

						specification = new Specification();

						specification.addCoordinate("x", 0, 1);

						eval(g, specification, algorithm, mathFunction, it,
								reportUnit);

						reportUnit.setTime(System.nanoTime() - ini);

						reportUnit.setTotalIteraction(it);

						ds.add(reportUnit);
					}
				}

			}

		}

		Map parameters = new HashMap();
		parameters.put("NOME", "opaman");
		parameters.put("ds", ds);

		ReportManager
				.saveReport("/otimizacao.jrxml", parameters, "matheus.pdf");

	}

	private void eval(State g, Specification specification,
			Experiment.AlgorithmWrapper algorithm,
			Experiment.MathFunctionWrapper mathFunction, int it,
			ReportUnit reportUnit) {

		if (algorithm.getOptimizationAlgorithm() instanceof HillClimbingIterated) {

			HillClimbingIterated hillClimbing = (HillClimbingIterated) algorithm
					.getOptimizationAlgorithm();

			hillClimbing.optimize(10, it, g, mathFunction.getFunction(),
					specification, reportUnit);
		} else if (algorithm.getOptimizationAlgorithm() instanceof HillClimbing) {
			HillClimbing hillClimbing = (HillClimbing) algorithm
					.getOptimizationAlgorithm();

			hillClimbing.optimize(it, g, mathFunction.getFunction(),
					specification, reportUnit);
		}

	}

	public static void main(String[] args) {
		Experimento_1 teste = new Experimento_1();
		teste.run();
	}

}
