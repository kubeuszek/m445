package br.compnatural.atividade_1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.compnatural.Experiment;
import br.compnatural.Specification;
import br.compnatural.State;
import br.compnatural.algorithm.HillClimbing;
import br.compnatural.algorithm.HillClimbingIterated;
import br.compnatural.experiment.report.ReportManager;
import br.compnatural.experiment.report.ReportUnit;
import br.compnatural.function.FunctionGriewank;
import br.compnatural.function.FunctionSumPow;
import br.compnatural.function.FunctionUnid;
import br.compnatural.function.MathFunction;

public class Experimento_2 implements Runnable {

	private Experiment experiment = new Experiment("Segunda questão");

	public Experimento_2() {

		List<MathFunction> functions = new ArrayList<MathFunction>();
		functions.add(new FunctionUnid(Boolean.TRUE));
		functions.add(new FunctionGriewank(Boolean.TRUE));
		functions.add(new FunctionSumPow(Boolean.TRUE));

		experiment.setAlgorithms(new ArrayList<Experiment.AlgorithmWrapper>(4));
		experiment.getAlgorithms().add(
				new Experiment.AlgorithmWrapper(new HillClimbing(Boolean.FALSE), functions));
	}

	@Override
	public void run() {

		
		List<ReportUnit> ds = new ArrayList<ReportUnit>(1000);

		

		Specification specification = null;

		for (Experiment.AlgorithmWrapper algorithm : experiment.getAlgorithms()) {
			for (MathFunction mathFunction : algorithm.getFunctionUnid()) {

				for (double it = 0.1; it <= 100; it *= 10) {
					for (int i = 0; i < 10; i++) {

						ReportUnit reportUnit = new ReportUnit();

						reportUnit.setAlgorithm(algorithm);
						reportUnit.setFunction(mathFunction);

						long ini = System.nanoTime();

						specification = getSpecification(mathFunction);
						
						HillClimbing hill = (HillClimbing) algorithm.getOptimizationAlgorithm();
						hill.setT(it);

						eval(mathFunction.getMax(), specification, algorithm, mathFunction, 1000,
								reportUnit);

						reportUnit.setTime(System.nanoTime() - ini);

						reportUnit.setTotalIteraction(it);

						ds.add(reportUnit);
					}
				}

			}

		}

		Map parameters = new HashMap();
		
		parameters.put("ds", ds);
		parameters.put("nome", experiment.getName());
		
		ReportManager
				.saveReport("/otimizacao.jrxml", parameters, "experimento_2.pdf");

	}

	private Specification getSpecification(MathFunction mathFunction) {
		Specification specification;
		if(mathFunction instanceof FunctionGriewank){
			specification = new Specification();
			for (int i = 1; i <= 10; i++) {
				specification.addCoordinate("x"+i, -600, 600);
			}
		}else if(mathFunction instanceof FunctionSumPow){
			specification = new Specification();
			for (int i = 1; i <= 10; i++) {
				specification.addCoordinate("x"+i, -1, 1);
			}
		}else{
			specification = new Specification();
			specification.addCoordinate("x", 0, 1);
		}

		return specification;
	}

	private void eval(State g, Specification specification,
			Experiment.AlgorithmWrapper algorithm, MathFunction function,
			int it, ReportUnit reportUnit) {

		if (algorithm.getOptimizationAlgorithm() instanceof HillClimbingIterated) {

			HillClimbingIterated hillClimbing = (HillClimbingIterated) algorithm
					.getOptimizationAlgorithm();

			hillClimbing.optimize(1000, it, g, function, specification,
					reportUnit);
		} else if (algorithm.getOptimizationAlgorithm() instanceof HillClimbing) {
			HillClimbing hillClimbing = (HillClimbing) algorithm
					.getOptimizationAlgorithm();

			hillClimbing.optimize(it, g, function, specification, reportUnit);
		}

	}

	public static void main(String[] args) {
		Experimento_2 teste = new Experimento_2();
		teste.run();
	}

}
