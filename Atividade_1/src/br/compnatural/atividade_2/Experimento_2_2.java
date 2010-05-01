package br.compnatural.atividade_2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.compnatural.Experiment;
import br.compnatural.algorithm.ParticleSwarm;
import br.compnatural.experiment.report.ReportManager;
import br.compnatural.experiment.report.ReportUnit;
import br.compnatural.function.FunctionGriewank;
import br.compnatural.function.FunctionSumPow;
import br.compnatural.function.FunctionUnid;
import br.compnatural.function.MathFunction;
import br.compnatural.specification.RealSpecification;

public class Experimento_2_2 implements Runnable {
	private Experiment experiment = new Experiment(
			"Segunda questao / atividade #2");

	public Experimento_2_2() {
		List<MathFunction> functions = new ArrayList<MathFunction>();
		//functions.add(new FunctionUnid(Boolean.TRUE));
		//functions.add(new FunctionGriewank(Boolean.TRUE));
		functions.add(new FunctionSumPow(Boolean.TRUE));

		experiment.setAlgorithms(new ArrayList<Experiment.AlgorithmWrapper>(10));
//		experiment.getAlgorithms().add(
//				new Experiment.AlgorithmWrapper(new ParticleSwarm(20, 1, 1,
//						2.05, 2.05), functions));
//		
//		
//		experiment.getAlgorithms().add(
//				new Experiment.AlgorithmWrapper(new ParticleSwarm(20, 1, 1,
//						0.5, 0.5), functions));
//		
//		
//		experiment.getAlgorithms().add(
//				new Experiment.AlgorithmWrapper(new ParticleSwarm(100, 1, 1,
//						2.05, 2.05), functions));
//		
//		
//		experiment.getAlgorithms().add(
//				new Experiment.AlgorithmWrapper(new ParticleSwarm(20, 0.01, 0.01,
//						2.05, 2.05), functions));
//		
//		
		experiment.getAlgorithms().add(
				new Experiment.AlgorithmWrapper(new ParticleSwarm(20, 0.9, 0.1,
						2.05, 2.05), functions));
//
//		experiment.getAlgorithms().add(
//				new Experiment.AlgorithmWrapper(new ParticleSwarm(20, 1, 1,
//						2.05, 2.05, Boolean.FALSE), functions));
	}

	@Override
	public void run() {

		List<ReportUnit> ds = new ArrayList<ReportUnit>(1000);

		RealSpecification specification = null;

		for (Experiment.AlgorithmWrapper algorithm : experiment.getAlgorithms()) {
			for (MathFunction mathFunction : algorithm.getFunctionUnid()) {
				for (int i = 0; i < 10; i++) {
					ReportUnit reportUnit = new ReportUnit();

					reportUnit.setAlgorithm(algorithm);
					reportUnit.setFunction(mathFunction);

					long ini = System.nanoTime();

					specification = getSpecification(mathFunction);

					eval(specification, algorithm, mathFunction, reportUnit);

					reportUnit.setTime(System.nanoTime() - ini);

					

					ds.add(reportUnit);
				}
			}

		}

		Map parameters = new HashMap();
		parameters.put("nome", experiment.getName());
		parameters.put("ds", ds);
		
		ReportManager.saveReport("/otimizacao.jrxml", parameters,
		"experimento_2_2.pdf");
	}
	
	private RealSpecification getSpecification(MathFunction mathFunction) {
		RealSpecification specification;
		if(mathFunction instanceof FunctionGriewank){
			specification = new RealSpecification();
			for (int i = 1; i <= 10; i++) {
				specification.addCoordinate("x"+i, -600, 600);
			}
		}else if(mathFunction instanceof FunctionSumPow){
			specification = new RealSpecification();
			for (int i = 1; i <= 10; i++) {
				specification.addCoordinate("x"+i, -1, 1);
			}
		}else{
			specification = new RealSpecification();
			specification.addCoordinate("x", 0, 1);
		}

		return specification;
	}
	
	private void eval(RealSpecification specification,
			Experiment.AlgorithmWrapper algorithm, MathFunction function, ReportUnit reportUnit) {
		
		if (algorithm.getOptimizationAlgorithm() instanceof ParticleSwarm) {
			ParticleSwarm lParticleSwarm = (ParticleSwarm)algorithm.getOptimizationAlgorithm();
			lParticleSwarm.optimize(5000, function, specification, reportUnit);
			reportUnit.setTotalIteraction(lParticleSwarm.c1);
		}
	}
	
	public static void main(String[] args) {
		Experimento_2_2 teste = new Experimento_2_2();
		teste.run();
	}
}
