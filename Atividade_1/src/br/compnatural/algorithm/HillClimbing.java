package br.compnatural.algorithm;

import java.math.BigDecimal;
import java.util.Random;
import java.util.logging.Level;

import br.compnatural.Specification;
import br.compnatural.State;
import br.compnatural.experiment.report.ReportUnit;
import br.compnatural.function.MathFunction;

public class HillClimbing extends OptimizationAlgorithm{

	private final Boolean standard;
	private Random random;
	private final String name;
	
	private double T;
	
	public double getT() {
		return T;
	}


	public void setT(double t) {
		T = t;
	}


	public HillClimbing(Boolean standard){
		this.standard = standard;
		
		if(!standard){
			name = "Hill Climbing Stochastic";
			random = new Random(System.currentTimeMillis());
		}else{
			name = "Hill Climbing Standard";
		}
	}
	
	
	public State optimize(int max_it, State g, MathFunction function, Specification specification, ReportUnit report){
		
		State x = initialize(specification);
		x.setValue( function.eval(x)) ;
		
		report.setInitialState(x);
		
		log.info("para x = "+x.getCoordinate().get(0).getValue() +" func="+x.getValue());
		
		int t = 0;
		
		Integer it_first_best = 0;
		
		while(t < max_it && !equals_witherror( x, g)){
			t++;
			State x_linha = perturb(x, specification);
			x_linha.setValue( function.eval(x_linha));
			log.info("para x_linha = "+x_linha.getCoordinate().get(0).getValue() +" func="+x_linha.getValue());
			
			if(decide(x, x_linha)){
				x = x_linha;
				
				if(!equals_witherror( x, g)){
					it_first_best = t;
				}
			}
			
			
		}
		
		report.setFirstBestSoluctionIteraction(it_first_best);
		
		if(t != max_it){
			log.log(Level.INFO, "encontrou! " +x.getValue() +" "+g.getValue());
			report.setBestSoluctionIteraction(t);
		}
		if(function.hasMaximum()){
			report.setBestSoluctionSoFar(x.getValue());
		}else{
			report.setBestSoluctionSoFar((new BigDecimal(x.getValue())).negate().doubleValue());
		}
		
		return x;
	}

	private boolean decide(State x, State x_linha) {
		if(standard){
			return standard(x, x_linha);
		}else{
			return stochastic(x, x_linha);
		}
		
	}
	
	private boolean standard(State x, State x_linha) {
		return x_linha.getValue() > x.getValue();
	}
	
	private boolean stochastic(State x, State x_linha) {
		
		if(!standard(x, x_linha)){
			return false;
		}
		
		double p = random.nextDouble();
		
		double value = 1d / (1d + StrictMath.exp((x.getValue() - x_linha.getValue())/T));
		
		return p <= value;
	}

	@Override
	public String getName() {
		return name;
	}
}
