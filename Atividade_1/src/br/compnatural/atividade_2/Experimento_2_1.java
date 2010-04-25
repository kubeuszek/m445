package br.compnatural.atividade_2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.compnatural.Experiment;
import br.compnatural.State;

import br.compnatural.algorithm.GeneticAlgorithm;
import br.compnatural.algorithm.HillClimbingIterated;
import br.compnatural.experiment.report.ReportManager;
import br.compnatural.experiment.report.ReportUnit;
import br.compnatural.function.FunctionHamming;
import br.compnatural.function.MathFunction;
import br.compnatural.specification.BinarySpecification;

public class Experimento_2_1 implements Runnable {

	private Experiment experiment = new Experiment(
			"Primeira questao / atividade #2");

	public Experimento_2_1() {

		List<MathFunction> functions = new ArrayList<MathFunction>();
		functions.add(new FunctionHamming(Boolean.TRUE, 12));
		functions.add(new FunctionHamming(Boolean.TRUE, 108));
		functions.add(new FunctionHamming(Boolean.TRUE, 1200));

		experiment.setAlgorithms(new ArrayList<Experiment.AlgorithmWrapper>(4));
		experiment.getAlgorithms().add(
				new Experiment.AlgorithmWrapper(new GeneticAlgorithm(0.75f), functions));
		
		functions = new ArrayList<MathFunction>();
		functions.add(new FunctionHamming(Boolean.TRUE, 12));
		functions.add(new FunctionHamming(Boolean.TRUE, 108));
		functions.add(new FunctionHamming(Boolean.TRUE, 1200));
		experiment.getAlgorithms().add(
				new Experiment.AlgorithmWrapper(new HillClimbingIterated(
						Boolean.TRUE), functions));
	}

	@Override
	public void run() {
		List<ReportUnit> ds = new ArrayList<ReportUnit>(1000);

		BinarySpecification specification = null;

		for (Experiment.AlgorithmWrapper algorithm : experiment.getAlgorithms()) {
			for (MathFunction mathFunction : algorithm.getFunctionUnid()) {
				for (int i = 0; i < 10; i++) {
					ReportUnit reportUnit = new ReportUnit();

					reportUnit.setAlgorithm(algorithm);
					reportUnit.setFunction(mathFunction);

					long ini = System.nanoTime();

					specification = getSpecification(mathFunction);

					eval(mathFunction.getMax(), specification, algorithm,
							mathFunction, reportUnit);

					reportUnit.setTime(System.nanoTime() - ini);

					ds.add(reportUnit);
				}
			}
		}

		Map parameters = new HashMap();
		parameters.put("nome", experiment.getName());
		parameters.put("ds", ds);

		ReportManager.saveReport("/otimizacao.jrxml", parameters,
				"experimento_2_1.pdf");

	}

	private void eval(State g, BinarySpecification specification,
			Experiment.AlgorithmWrapper algorithm, MathFunction function,
			ReportUnit reportUnit) {

		if (algorithm.getOptimizationAlgorithm() instanceof HillClimbingIterated) {

			HillClimbingIterated hillClimbing = (HillClimbingIterated) algorithm
					.getOptimizationAlgorithm();

			hillClimbing.optimize(500, 10, g, function, specification,
					reportUnit);
			reportUnit
					.setTotalIteraction(((FunctionHamming) function).length + 0d);
		} else if (algorithm.getOptimizationAlgorithm() instanceof GeneticAlgorithm) {
			GeneticAlgorithm genetic = (GeneticAlgorithm) algorithm
					.getOptimizationAlgorithm();
			genetic.optimize(50, 100, g, function, specification, reportUnit);
			reportUnit
					.setTotalIteraction(((FunctionHamming) function).length + 0d);
		}

	}

	private BinarySpecification getSpecification(MathFunction mathFunction) {

		BinarySpecification specification = null;
		if (mathFunction instanceof FunctionHamming) {
			specification = new BinarySpecification(
					((FunctionHamming) mathFunction).length, 0.01f);
		}

		return specification;
	}

	public static void main(String[] args) {
		Experimento_2_1 teste = new Experimento_2_1();
		teste.run();
	}
}
