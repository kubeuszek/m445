package br.compnatural.atividade_2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import br.compnatural.Experiment;
import br.compnatural.algorithm.ParticleSwarm;
import br.compnatural.experiment.report.ReportGraphInfo;
import br.compnatural.experiment.report.ReportManager;
import br.compnatural.experiment.report.ReportUnit;
import br.compnatural.function.FunctionGriewank;
import br.compnatural.function.FunctionRosenbrock;
import br.compnatural.function.FunctionSumPow;
import br.compnatural.function.FunctionUnid;
import br.compnatural.function.MathFunction;
import br.compnatural.specification.RealSpecification;

public class Experimento_2_2 implements Runnable {
	private Experiment experiment = new Experiment(
			"Segunda questao / atividade #2");

	private final static int MAX_IT = 2000;
	
	public Experimento_2_2() {
		List<MathFunction> functions = new ArrayList<MathFunction>();
		functions.add(new FunctionUnid(Boolean.TRUE));
		
		functions.add(new FunctionRosenbrock(Boolean.TRUE));
		functions.add(new FunctionGriewank(Boolean.TRUE));

		experiment.setAlgorithms(new ArrayList<Experiment.AlgorithmWrapper>(10));
		experiment.getAlgorithms().add(
				new Experiment.AlgorithmWrapper(new ParticleSwarm(20, 1, 1,
						2.05, 2.05), functions));
		
		
		experiment.getAlgorithms().add(
				new Experiment.AlgorithmWrapper(new ParticleSwarm(20, 1, 1,
						0.5, 0.5), functions));
		
		
		experiment.getAlgorithms().add(
				new Experiment.AlgorithmWrapper(new ParticleSwarm(100, 1, 1,
						2.05, 2.05), functions));
		
		
		experiment.getAlgorithms().add(
				new Experiment.AlgorithmWrapper(new ParticleSwarm(20, 0.01, 0.01,
						2.05, 2.05), functions));
		
		
		experiment.getAlgorithms().add(
				new Experiment.AlgorithmWrapper(new ParticleSwarm(20, 0.9, 0.1,
						2.05, 2.05), functions));

		experiment.getAlgorithms().add(
				new Experiment.AlgorithmWrapper(new ParticleSwarm(20, 1, 1,
						2.05, 2.05, Boolean.FALSE), functions));
		
		
		experiment.getAlgorithms().add(
				new Experiment.AlgorithmWrapper(new ParticleSwarm(20, 1, 1,
						0.5, 0.5, Boolean.FALSE), functions));
		
		
		experiment.getAlgorithms().add(
				new Experiment.AlgorithmWrapper(new ParticleSwarm(100, 1, 1,
						2.05, 2.05, Boolean.FALSE), functions));
		
		
		experiment.getAlgorithms().add(
				new Experiment.AlgorithmWrapper(new ParticleSwarm(20, 0.01, 0.01,
						2.05, 2.05, Boolean.FALSE), functions));
		
		
		experiment.getAlgorithms().add(
				new Experiment.AlgorithmWrapper(new ParticleSwarm(20, 0.9, 0.1,
						2.05, 2.05, Boolean.FALSE), functions));
	}

	@Override
	public void run() {

		int it = 0;
		for (Experiment.AlgorithmWrapper algorithm : experiment.getAlgorithms()) {
			for (MathFunction mathFunction : algorithm.getFunctionUnid()) {
				
				List<ReportUnit> ds = new ArrayList<ReportUnit>(10);
				
				RealSpecification specification = null;
				ReportUnit reportUnit = null;
				List<ReportGraphInfo> graphInfo = new ArrayList<ReportGraphInfo>(MAX_IT+1);
				String nome = null;
				for (int i = 0; i < MAX_IT; i++) {
					graphInfo.add(new ReportGraphInfo(0d, 0d, 0));
				}
				
				for (int i = 0; i < 10; i++) {
					reportUnit = new ReportUnit();

					reportUnit.setAlgorithm(algorithm);
					reportUnit.setFunction(mathFunction);

					long ini = System.nanoTime();

					specification = getSpecification(mathFunction);

					nome = eval(specification, algorithm, mathFunction, reportUnit);

					reportUnit.setTime(System.nanoTime() - ini);

					reportUnit.setTotalIteraction((double)it);

					ds.add(reportUnit);
					
					sum(graphInfo, reportUnit.getReportGraphInfos());
					reportUnit.setReportGraphInfos(null);
				}
				
				++it;
				Map parameters = new HashMap();
				parameters.put("nome", experiment.getName());
				parameters.put("ds", ds);
				
				avg(graphInfo, 10);
				reportUnit.setReportGraphInfo(new JRBeanCollectionDataSource(graphInfo));
				
				
				ReportManager.saveReport("/otimizacao_grafico.jrxml", parameters,
				"experimento_2_2_PSO_"+nome+it+".pdf");
				
			}

		}
	}
	
	private void avg(List<ReportGraphInfo> avgGraphInfo, int it){
		for (int i = 0; i < MAX_IT; i++) {
			avgGraphInfo.get(i).setAvg_population(avgGraphInfo.get(i).getAvg_population()/it);
			avgGraphInfo.get(i).setBest_particle(avgGraphInfo.get(i).getBest_particle()/it);
			avgGraphInfo.get(i).setGeneration(i);
		}
	}
	
	private void sum(List<ReportGraphInfo> avgGraphInfo, List<ReportGraphInfo> graphInfo){
		for (int i = 0; i < MAX_IT; i++) {
			avgGraphInfo.get(i).setAvg_population(graphInfo.get(i).getAvg_population() + avgGraphInfo.get(i).getAvg_population());
			avgGraphInfo.get(i).setBest_particle(graphInfo.get(i).getBest_particle() + avgGraphInfo.get(i).getBest_particle());
		}
	}
	
	private RealSpecification getSpecification(MathFunction mathFunction) {
		RealSpecification specification;
		if(mathFunction instanceof FunctionGriewank){
			specification = new RealSpecification();
			for (int i = 1; i <= 10; i++) {
				specification.addCoordinate("x"+i, -600, 600);
			}
		}else if(mathFunction instanceof FunctionRosenbrock){
			specification = new RealSpecification();
			for (int i = 1; i <= 10; i++) {
				specification.addCoordinate("x"+i,-2.048, 2.048);
			}
		}else{
			specification = new RealSpecification();
			specification.addCoordinate("x", 0, 1);
		}

		return specification;
	}
	
	private String eval(RealSpecification specification,
			Experiment.AlgorithmWrapper algorithm, MathFunction function, ReportUnit reportUnit) {
		
		if (algorithm.getOptimizationAlgorithm() instanceof ParticleSwarm) {
			ParticleSwarm lParticleSwarm = (ParticleSwarm)algorithm.getOptimizationAlgorithm();
			lParticleSwarm.optimize(MAX_IT, function, specification, reportUnit);
			
			return lParticleSwarm.lBest +" "+lParticleSwarm.lenPopulation+" "+lParticleSwarm.c1+ " "+lParticleSwarm.c2+" "+lParticleSwarm.w1+ " "+lParticleSwarm.w2+" ";
		}
		
		return null;
	}
	
	public static void main(String[] args) {
		Experimento_2_2 teste = new Experimento_2_2();
		teste.run();
	}
}
