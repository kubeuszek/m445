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
import br.compnatural.algorithm.SimulatedAnnealing;
import br.compnatural.experiment.report.ReportManager;
import br.compnatural.experiment.report.ReportUnit;
import br.compnatural.function.FunctionGriewank;
import br.compnatural.function.FunctionSumPow;
import br.compnatural.function.FunctionUnid;
import br.compnatural.function.MathFunction;

public class Experimento_5 implements Runnable {

	private Experiment experiment = new Experiment("Quinta questão");

	public Experimento_5() {

		List<MathFunction> functions = new ArrayList<MathFunction>();
		functions.add(new FunctionUnid(Boolean.FALSE));
		functions.add(new FunctionGriewank(Boolean.FALSE));
		functions.add(new FunctionSumPow(Boolean.FALSE));

		experiment.setAlgorithms(new ArrayList<Experiment.AlgorithmWrapper>(4));
		experiment.getAlgorithms().add(
				new Experiment.AlgorithmWrapper(new SimulatedAnnealing(100,1, 0.95,10), functions));
		experiment.getAlgorithms().add(
				new Experiment.AlgorithmWrapper(new SimulatedAnnealing(100,1, 0.5,10), functions));
		experiment.getAlgorithms().add(
				new Experiment.AlgorithmWrapper(new SimulatedAnnealing(100,0.1, 0.5,10), functions));
		experiment.getAlgorithms().add(
				new Experiment.AlgorithmWrapper(new SimulatedAnnealing(1000,1, 0.5,1), functions));
		
	}

	@Override
	public void run() {
		
		List<ReportUnit> ds = new ArrayList<ReportUnit>(1000);

		Specification specification = null;
		double j = 0;
		for (Experiment.AlgorithmWrapper algorithm : experiment.getAlgorithms()) {
			for (MathFunction mathFunction : algorithm.getFunctionUnid()) {
					
					for (int i = 0; i < 10; i++) {

						ReportUnit reportUnit = new ReportUnit();

						reportUnit.setAlgorithm(algorithm);
						reportUnit.setFunction(mathFunction);

						long ini = System.nanoTime();

						specification = getSpecification(mathFunction);

						eval(mathFunction.getMax(), specification, algorithm, mathFunction, 10,
								reportUnit);

						reportUnit.setTime(System.nanoTime() - ini);
						
						reportUnit.setTotalIteraction(j);

						ds.add(reportUnit);
					}
					j += 1;
				

			}

		}

		Map parameters = new HashMap();
		parameters.put("nome", experiment.getName());
		parameters.put("ds", ds);

		ReportManager
				.saveReport("/otimizacao.jrxml", parameters, "experimento_5.pdf");

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

		if (algorithm.getOptimizationAlgorithm() instanceof SimulatedAnnealing) {

			SimulatedAnnealing simulated = (SimulatedAnnealing) algorithm
					.getOptimizationAlgorithm();

			simulated.optimize(g, function, specification, reportUnit);
		}

	}

	public static void main(String[] args) {
		Experimento_5 teste = new Experimento_5();
		teste.run();
	}

}
