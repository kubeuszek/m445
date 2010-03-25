package br.compnatural.atividade_1;

import br.compnatural.MathFunction;
import br.compnatural.OptimizationAlgorithm;
import br.compnatural.Specification;
import br.compnatural.State;
import br.compnatural.experiment.report.ReportUnit;

public class HillClimbing extends OptimizationAlgorithm{
	
	
	
	public State hillClimbingStandard(int max_it, State g, MathFunction function, Specification specification, ReportUnit report){
		
		State x = initialize(specification);
		x.setValue( function.eval(x)) ;
		
		report.setInitialState(x);
		
		log.info("para x = "+x.getCoordinate().get(0).getValue() +" func="+x.getValue());
		
		int t = 0;
		
		Integer it_first_best = null;
		
		while(t < max_it && !equals_witherror( x, g)){
			State x_linha = perturb(x, specification);
			x_linha.setValue( function.eval(x_linha));
			log.info("para x_linha = "+x_linha.getCoordinate().get(0).getValue() +" func="+x_linha.getValue());
			
			if(x_linha.getValue() > x.getValue()){
				x = x_linha;
				
				if(null == it_first_best){
					it_first_best = t;
				}
			}
			
			t++;
		}
		
		report.setFirstBestSoluctionIteraction(it_first_best);
		
		if(t != max_it){
			report.setBestSoluctionIteraction(t);
		}
		report.setBestSoluctionSoFar(x.getValue());
		return x;
	}

	@Override
	public String getName() {
		return "hillclimbing";
	}
}
