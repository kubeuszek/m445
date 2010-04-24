package br.compnatural.test;

import java.util.logging.Logger;

import br.compnatural.State;
import br.compnatural.coordinate.BinaryCoordinate;
import br.compnatural.function.FunctionHamming;
import br.compnatural.specification.BinarySpecification;
import br.compnatural.specification.Specification;
import junit.framework.TestCase;

public class FunctionHammingTest extends TestCase {
	
	Logger log = Logger.getLogger(FunctionHammingTest.class.getName());
	private static final boolean ARRAY_4_3[] = new boolean[]{false, true, false,
		false, true, false,
		false, true, false,
		false, true, false};

private static final boolean ARRAY_12_9[] = new boolean[]{false, false, false, true, true, true, false, false, false,
		false, false, false, true, true, true, false, false, false,
		false, false, false, true, true, true, false, false, false,
		false, false, false, true, true, true, false, false, false,
		false, false, false, true, true, true, false, false, false,
		false, false, false, true, true, true, false, false, false,
		false, false, false, true, true, true, false, false, false,
		false, false, false, true, true, true, false, false, false,
		false, false, false, true, true, true, false, false, false,
		false, false, false, true, true, true, false, false, false,
		false, false, false, true, true, true, false, false, false,
		false, false, false, true, true, true, false, false, false
		};

private static final boolean ARRAY_40_30[] = new boolean[]{false, false, false, false, false, false, false, false, false, false, true, true, true, true, true, true, true, true, true, true, false, false, false, false, false, false, false, false, false, false, 
		false, false, false, false, false, false, false, false, false, false, true, true, true, true, true, true, true, true, true, true, false, false, false, false, false, false, false, false, false, false, 
		false, false, false, false, false, false, false, false, false, false, true, true, true, true, true, true, true, true, true, true, false, false, false, false, false, false, false, false, false, false, 
		false, false, false, false, false, false, false, false, false, false, true, true, true, true, true, true, true, true, true, true, false, false, false, false, false, false, false, false, false, false, 
		false, false, false, false, false, false, false, false, false, false, true, true, true, true, true, true, true, true, true, true, false, false, false, false, false, false, false, false, false, false, 
		false, false, false, false, false, false, false, false, false, false, true, true, true, true, true, true, true, true, true, true, false, false, false, false, false, false, false, false, false, false, 
		false, false, false, false, false, false, false, false, false, false, true, true, true, true, true, true, true, true, true, true, false, false, false, false, false, false, false, false, false, false, 
		false, false, false, false, false, false, false, false, false, false, true, true, true, true, true, true, true, true, true, true, false, false, false, false, false, false, false, false, false, false, 
		false, false, false, false, false, false, false, false, false, false, true, true, true, true, true, true, true, true, true, true, false, false, false, false, false, false, false, false, false, false, 
		false, false, false, false, false, false, false, false, false, false, true, true, true, true, true, true, true, true, true, true, false, false, false, false, false, false, false, false, false, false, 
		false, false, false, false, false, false, false, false, false, false, true, true, true, true, true, true, true, true, true, true, false, false, false, false, false, false, false, false, false, false,
		false, false, false, false, false, false, false, false, false, false, true, true, true, true, true, true, true, true, true, true, false, false, false, false, false, false, false, false, false, false, 
		false, false, false, false, false, false, false, false, false, false, true, true, true, true, true, true, true, true, true, true, false, false, false, false, false, false, false, false, false, false, 
		false, false, false, false, false, false, false, false, false, false, true, true, true, true, true, true, true, true, true, true, false, false, false, false, false, false, false, false, false, false, 
		false, false, false, false, false, false, false, false, false, false, true, true, true, true, true, true, true, true, true, true, false, false, false, false, false, false, false, false, false, false, 
		false, false, false, false, false, false, false, false, false, false, true, true, true, true, true, true, true, true, true, true, false, false, false, false, false, false, false, false, false, false, 
		false, false, false, false, false, false, false, false, false, false, true, true, true, true, true, true, true, true, true, true, false, false, false, false, false, false, false, false, false, false, 
		false, false, false, false, false, false, false, false, false, false, true, true, true, true, true, true, true, true, true, true, false, false, false, false, false, false, false, false, false, false, 
		false, false, false, false, false, false, false, false, false, false, true, true, true, true, true, true, true, true, true, true, false, false, false, false, false, false, false, false, false, false, 
		false, false, false, false, false, false, false, false, false, false, true, true, true, true, true, true, true, true, true, true, false, false, false, false, false, false, false, false, false, false, 
		false, false, false, false, false, false, false, false, false, false, true, true, true, true, true, true, true, true, true, true, false, false, false, false, false, false, false, false, false, false,
		false, false, false, false, false, false, false, false, false, false, true, true, true, true, true, true, true, true, true, true, false, false, false, false, false, false, false, false, false, false, 
		false, false, false, false, false, false, false, false, false, false, true, true, true, true, true, true, true, true, true, true, false, false, false, false, false, false, false, false, false, false, 
		false, false, false, false, false, false, false, false, false, false, true, true, true, true, true, true, true, true, true, true, false, false, false, false, false, false, false, false, false, false, 
		false, false, false, false, false, false, false, false, false, false, true, true, true, true, true, true, true, true, true, true, false, false, false, false, false, false, false, false, false, false, 
		false, false, false, false, false, false, false, false, false, false, true, true, true, true, true, true, true, true, true, true, false, false, false, false, false, false, false, false, false, false, 
		false, false, false, false, false, false, false, false, false, false, true, true, true, true, true, true, true, true, true, true, false, false, false, false, false, false, false, false, false, false, 
		false, false, false, false, false, false, false, false, false, false, true, true, true, true, true, true, true, true, true, true, false, false, false, false, false, false, false, false, false, false, 
		false, false, false, false, false, false, false, false, false, false, true, true, true, true, true, true, true, true, true, true, false, false, false, false, false, false, false, false, false, false, 
		false, false, false, false, false, false, false, false, false, false, true, true, true, true, true, true, true, true, true, true, false, false, false, false, false, false, false, false, false, false, 
		false, false, false, false, false, false, false, false, false, false, true, true, true, true, true, true, true, true, true, true, false, false, false, false, false, false, false, false, false, false,
		false, false, false, false, false, false, false, false, false, false, true, true, true, true, true, true, true, true, true, true, false, false, false, false, false, false, false, false, false, false, 
		false, false, false, false, false, false, false, false, false, false, true, true, true, true, true, true, true, true, true, true, false, false, false, false, false, false, false, false, false, false, 
		false, false, false, false, false, false, false, false, false, false, true, true, true, true, true, true, true, true, true, true, false, false, false, false, false, false, false, false, false, false, 
		false, false, false, false, false, false, false, false, false, false, true, true, true, true, true, true, true, true, true, true, false, false, false, false, false, false, false, false, false, false, 
		false, false, false, false, false, false, false, false, false, false, true, true, true, true, true, true, true, true, true, true, false, false, false, false, false, false, false, false, false, false, 
		false, false, false, false, false, false, false, false, false, false, true, true, true, true, true, true, true, true, true, true, false, false, false, false, false, false, false, false, false, false, 
		false, false, false, false, false, false, false, false, false, false, true, true, true, true, true, true, true, true, true, true, false, false, false, false, false, false, false, false, false, false, 
		false, false, false, false, false, false, false, false, false, false, true, true, true, true, true, true, true, true, true, true, false, false, false, false, false, false, false, false, false, false, 
		false, false, false, false, false, false, false, false, false, false, true, true, true, true, true, true, true, true, true, true, false, false, false, false, false, false, false, false, false, false
		};
	
	public void testEval_4_3() {
		int max = 12;
		FunctionHamming function = new FunctionHamming(Boolean.TRUE, max);
		Specification specification = new BinarySpecification(12);
		State state = specification.initialize();
		
		((BinaryCoordinate)state.getCoordinate().get(0)).setValue(ARRAY_4_3.clone());
		state.setValue(function.eval(state));
		assertEquals((double)max, state.getValue());
		log.info("valor "+state.getValue());
		
		
		state = specification.perturb(state);
		state.setValue(function.eval(state));
		assertEquals((double)(max-1), state.getValue());
		log.info("valor "+state.getValue());
		
		
		state = specification.perturb(state);
		state.setValue(function.eval(state));
		assertEquals(Boolean.TRUE, new Boolean (state.getValue() >= (double)(max-2)));
		log.info("valor "+state.getValue());
	}
	
	public void testEval_12_9() {
		int max = 108;
		FunctionHamming function = new FunctionHamming(Boolean.TRUE, max);
		Specification specification = new BinarySpecification(max);
		State state = specification.initialize();
		
		((BinaryCoordinate)state.getCoordinate().get(0)).setValue(ARRAY_12_9.clone());
		state.setValue(function.eval(state));
		assertEquals((double)max, state.getValue());
		log.info("valor "+state.getValue());
		
		
		state = specification.perturb(state);
		state.setValue(function.eval(state));
		assertEquals((double)(max-1), state.getValue());
		log.info("valor "+state.getValue());
		
		
		state = specification.perturb(state);
		state.setValue(function.eval(state));
		assertEquals(Boolean.TRUE, new Boolean (state.getValue() >= (double)(max-2)));
		log.info("valor "+state.getValue());
	}
	
	public void testEval_40_30() {
		int max = 1200;
		FunctionHamming function = new FunctionHamming(Boolean.TRUE, max);
		Specification specification = new BinarySpecification(max);
		State state = specification.initialize();
		
		((BinaryCoordinate)state.getCoordinate().get(0)).setValue(ARRAY_40_30.clone());
		state.setValue(function.eval(state));
		assertEquals((double)max, state.getValue());
		log.info("valor "+state.getValue());
		
		
		state = specification.perturb(state);
		state.setValue(function.eval(state));
		assertEquals((double)(max-1), state.getValue());
		log.info("valor "+state.getValue());
		
		
		state = specification.perturb(state);
		state.setValue(function.eval(state));
		assertEquals(Boolean.TRUE, new Boolean (state.getValue() >= (double)(max-2)));
		log.info("valor "+state.getValue());
	}
}
