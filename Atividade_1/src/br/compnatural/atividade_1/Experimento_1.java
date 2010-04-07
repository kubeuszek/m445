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

public class Experimento_1 implements Runnable {

	private Experiment experiment = new Experiment("Primeira questão");

	public Experimento_1() {

		List<MathFunction> functions = new ArrayList<MathFunction>();
		functions.add(new FunctionUnid(Boolean.TRUE));
		functions.add(new FunctionGriewank(Boolean.TRUE));
		functions.add(new FunctionSumPow(Boolean.TRUE));

		experiment.setAlgorithms(new ArrayList<Experiment.AlgorithmWrapper>(4));
		experiment.getAlgorithms().add(
				new Experiment.AlgorithmWrapper(new HillClimbing(Boolean.TRUE), functions));
		//experiment.getAlgorithms().add(new Experiment.AlgorithmWrapper(new HillClimbingIterated(), functions));
	}

	@Override
	public void run() {

		
		List<ReportUnit> ds = new ArrayList<ReportUnit>(1000);

		

		Specification specification = null;

		for (Experiment.AlgorithmWrapper algorithm : experiment.getAlgorithms()) {
			for (MathFunction mathFunction : algorithm.getFunctionUnid()) {

				for (int it = 10; it <= 1000; it *= 10) {
					for (int i = 0; i < 10; i++) {

						ReportUnit reportUnit = new ReportUnit();

						reportUnit.setAlgorithm(algorithm);
						reportUnit.setFunction(mathFunction);

						long ini = System.nanoTime();

						specification = getSpecification(mathFunction);

						eval(mathFunction.getMax(), specification, algorithm, mathFunction, it,
								reportUnit);

						reportUnit.setTime(System.nanoTime() - ini);

						reportUnit.setTotalIteraction(new Double(it));

						ds.add(reportUnit);
					}
				}

			}

		}

		Map parameters = new HashMap();
		parameters.put("nome", experiment.getName());
		parameters.put("ds", ds);

		ReportManager
				.saveReport("/otimizacao.jrxml", parameters, "experimento_1.pdf");

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
		Experimento_1 teste = new Experimento_1();
		teste.run();
	}

}
