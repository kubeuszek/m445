package br.compnatural.atividade_1;

import java.util.ArrayList;
import java.util.List;

import br.compnatural.Experiment;
import br.compnatural.Specification;
import br.compnatural.State;
import br.compnatural.experiment.report.ReportUnit;

public class Experimento_1 implements Runnable {
	
	private Experiment dez_iteracoes = new Experiment("Primeira questão, 10 iterações");
	private Experiment cem_iteracoes = new Experiment("Primeira questão, 100 iterações");

	public Experimento_1() {
		
		

		List<Experiment.MathFunctionWrapper> functions = new ArrayList<Experiment.MathFunctionWrapper>();
		functions.add(new Experiment.MathFunctionWrapper(new FunctionUnid(), new ArrayList<ReportUnit>()));
		
		

		dez_iteracoes.setAlgorithms(new ArrayList<Experiment.AlgorithmWrapper>(4));
		dez_iteracoes.getAlgorithms()
				.add(new Experiment.AlgorithmWrapper(new HillClimbing(), functions));
		
		cem_iteracoes.setAlgorithms(dez_iteracoes.getAlgorithms());

	}

	@Override
	public void run() {

		State g = State.getState();
		
		g.setValue(1);

		Specification specification = null;
		for (int i = 0; i < 10; i++) {

			for (Experiment.AlgorithmWrapper algorithm : dez_iteracoes.getAlgorithms()) {
				for (Experiment.MathFunctionWrapper mathFunction : algorithm
						.getMathFunctionWrapper()) {
					ReportUnit reportUnit = new ReportUnit();
					long ini = System.nanoTime();
					
					specification = new Specification();

					specification.addCoordinate("x", 0, 1);

					HillClimbing hillClimbing = (HillClimbing) algorithm
							.getOptimizationAlgorithm();
					
					hillClimbing.hillClimbingStandard(1000, g, mathFunction
							.getFunction(), specification, reportUnit);
					
					reportUnit.setTime(System.nanoTime() - ini);
					
					mathFunction.getReport().add(reportUnit);
					
				}
			}

		}
	}
	
	public static void main(String[] args){
		Experimento_1 teste = new Experimento_1();
		teste.run();
	}

}
