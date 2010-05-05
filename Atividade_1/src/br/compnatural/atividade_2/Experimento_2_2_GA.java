package br.compnatural.atividade_2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.compnatural.Experiment;
import br.compnatural.algorithm.GeneticAlgorithm;
import br.compnatural.experiment.report.ReportManager;
import br.compnatural.experiment.report.ReportUnit;
import br.compnatural.function.FunctionGriewank;
import br.compnatural.function.FunctionRosenbrock;
import br.compnatural.function.FunctionUnid;
import br.compnatural.function.MathFunction;
import br.compnatural.specification.RealSpecification;

public class Experimento_2_2_GA implements Runnable {
	private Experiment experiment = new Experiment(
			"Segunda questao / atividade #2");

	public Experimento_2_2_GA() {
		List<MathFunction> functions = new ArrayList<MathFunction>();
		functions.add(new FunctionUnid(Boolean.TRUE));
		functions.add(new FunctionRosenbrock(Boolean.TRUE));
		functions.add(new FunctionGriewank(Boolean.TRUE));
		

		experiment.setAlgorithms(new ArrayList<Experiment.AlgorithmWrapper>(10));

		experiment.getAlgorithms().add(
				new Experiment.AlgorithmWrapper(new GeneticAlgorithm(50, 0.75f, 0.1f, Boolean.TRUE), functions));
		
		experiment.getAlgorithms().add(
				new Experiment.AlgorithmWrapper(new GeneticAlgorithm(50, 0.75f, 0.1f, Boolean.FALSE), functions));
		
		experiment.getAlgorithms().add(
				new Experiment.AlgorithmWrapper(new GeneticAlgorithm(100, 0.75f, 0.1f, Boolean.TRUE), functions));
		
		experiment.getAlgorithms().add(
				new Experiment.AlgorithmWrapper(new GeneticAlgorithm(100, 0.75f, 0.1f, Boolean.FALSE), functions));
	}

	@Override
	public void run() {

		List<ReportUnit> ds = new ArrayList<ReportUnit>(1000);

		RealSpecification specification = null;

		int it = 0;
		for (Experiment.AlgorithmWrapper algorithm : experiment.getAlgorithms()) {
			for (MathFunction mathFunction : algorithm.getFunctionUnid()) {
				
				for (int i = 0; i < 10; i++) {
					ReportUnit reportUnit = new ReportUnit();

					reportUnit.setAlgorithm(algorithm);
					reportUnit.setFunction(mathFunction);

					long ini = System.nanoTime();

					specification = getSpecification(mathFunction, algorithm);

					eval(specification, algorithm, mathFunction, reportUnit);

					reportUnit.setTime(System.nanoTime() - ini);

					reportUnit.setTotalIteraction((double)it);

					ds.add(reportUnit);
				}
				it++;
			}

		}

		Map parameters = new HashMap();
		parameters.put("nome", experiment.getName());
		parameters.put("ds", ds);
		
		ReportManager.saveReport("/otimizacao.jrxml", parameters,
		"experimento_2_2_GA.pdf");
	}
	
	private RealSpecification getSpecification(MathFunction mathFunction, Experiment.AlgorithmWrapper algorithm) {
		
		RealSpecification specification = new RealSpecification();
		if(algorithm.getOptimizationAlgorithm() instanceof GeneticAlgorithm){
			GeneticAlgorithm lAlgorithmn = (GeneticAlgorithm) algorithm.getOptimizationAlgorithm();
			specification = new RealSpecification(!lAlgorithmn.subsDeterministc, !lAlgorithmn.subsDeterministc);
		}
		
		if(mathFunction instanceof FunctionGriewank){
			
			for (int i = 1; i <= 10; i++) {
				specification.addCoordinate("x"+i, -600, 600);
			}
		}else if(mathFunction instanceof FunctionRosenbrock){
			
			for (int i = 1; i <= 10; i++) {
				specification.addCoordinate("x"+i, -2.048, 2.048);
			}
		}else{
			
			specification.addCoordinate("x", 0, 1);
		}

		return specification;
	}
	
	private void eval(RealSpecification specification,
			Experiment.AlgorithmWrapper algorithm, MathFunction function, ReportUnit reportUnit) {
		
		if (algorithm.getOptimizationAlgorithm() instanceof GeneticAlgorithm) {
			
			GeneticAlgorithm lGeneticAlgorithm = (GeneticAlgorithm)algorithm.getOptimizationAlgorithm();
			
			specification.pm = new Float (lGeneticAlgorithm.pm);
			
			lGeneticAlgorithm.optimize(2000 / lGeneticAlgorithm.lenPopulation, function, specification, reportUnit);
			
			specification.pm = null;
		}
	}
	
	public static void main(String[] args) {
		Experimento_2_2_GA teste = new Experimento_2_2_GA();
		teste.run();
	}
}
