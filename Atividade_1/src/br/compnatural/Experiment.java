package br.compnatural;

import java.util.List;

import br.compnatural.atividade_1.FunctionUnid;
import br.compnatural.experiment.report.ReportUnit;

public class Experiment {
	
	public Experiment(String name){
		this.name = name;
	}

	protected String name;
	
	List<AlgorithmWrapper> algorithms;
	
	
	public List<AlgorithmWrapper> getAlgorithms() {
		return algorithms;
	}

	public void setAlgorithms(List<AlgorithmWrapper> algorithms) {
		this.algorithms = algorithms;
	}

	public String getName() {
		return name;
	}

	public static class AlgorithmWrapper{
		
		private OptimizationAlgorithm optimizationAlgorithm;
		private List<MathFunctionWrapper> mathFunctionWrapper;
		
		public AlgorithmWrapper(OptimizationAlgorithm algorithm, List<MathFunctionWrapper> mathFunctionWrapper){
			this.optimizationAlgorithm = algorithm;
			this.mathFunctionWrapper = mathFunctionWrapper;
		}
		
		public List<MathFunctionWrapper> getMathFunctionWrapper() {
			return mathFunctionWrapper;
		}
		
		public OptimizationAlgorithm getOptimizationAlgorithm() {
			return optimizationAlgorithm;
		}
	}
	
	public static class MathFunctionWrapper{
		private FunctionUnid function;
		private List<ReportUnit> report;

		public FunctionUnid getFunction() {
			return function;
		}

		public MathFunctionWrapper(FunctionUnid function, List<ReportUnit> reports) {
			this.function = function;
			setReport(reports);
		}

		private void setReport(List<ReportUnit> report) {
			this.report = report;
		}

		public List<ReportUnit> getReport() {
			return report;
		}
	}
	
	
}
