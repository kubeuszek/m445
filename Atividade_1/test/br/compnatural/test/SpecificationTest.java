package br.compnatural.test;

import java.util.logging.Logger;

import br.compnatural.Specification;
import br.compnatural.State;
import br.compnatural.coordinate.RealCoordinate;
import junit.framework.TestCase;

public class SpecificationTest extends TestCase {
	
	private Specification specification = new Specification();
	protected Logger log = Logger.getLogger(SpecificationTest.class.getName());

	public void testInitializeRange_zero_um() {
		log.info(">>Inicio ");
		int min = 0;
		int max = 1; 
		specification.addCoordinate("x", min, max);
		
		 
		
		State state = specification.initialize();
		assertEquals(1,state.getCoordinate().size());
		assertEquals(true, ((RealCoordinate)state.getCoordinate().get(0)).getValue() <= (double) max && ((RealCoordinate)state.getCoordinate().get(0)).getValue() >= (double) min);
		
		state = specification.initialize();
		assertEquals(1,state.getCoordinate().size());
		assertEquals(true, ((RealCoordinate)state.getCoordinate().get(0)).getValue() <= (double) max && ((RealCoordinate)state.getCoordinate().get(0)).getValue() >= (double) min);
		
		
		
		state = specification.initialize();
		assertEquals(1,state.getCoordinate().size());
		assertEquals(true, ((RealCoordinate)state.getCoordinate().get(0)).getValue() <= (double) max && ((RealCoordinate)state.getCoordinate().get(0)).getValue() >= (double) min);
		
		
		state = specification.initialize();
		assertEquals(1,state.getCoordinate().size());
		assertEquals(true, ((RealCoordinate)state.getCoordinate().get(0)).getValue() <= (double) max && ((RealCoordinate)state.getCoordinate().get(0)).getValue() >= (double) min);
		
	}
	
	public void testInitializeRange_range() {
		log.info(">>Inicio ");
		int min = 0;
		int max = 3;
		specification.addCoordinate("x", min , max);
		
		 
		
		State state = specification.initialize();
		assertEquals(1,state.getCoordinate().size());
		assertEquals(true, ((RealCoordinate)state.getCoordinate().get(0)).getValue() <= (double) max && ((RealCoordinate)state.getCoordinate().get(0)).getValue() >= (double) min);
		
		state = specification.initialize();
		assertEquals(1,state.getCoordinate().size());
		assertEquals(true, ((RealCoordinate)state.getCoordinate().get(0)).getValue() <= (double) max && ((RealCoordinate)state.getCoordinate().get(0)).getValue() >= (double) min);
		
		
		
		state = specification.initialize();
		assertEquals(1,state.getCoordinate().size());
		assertEquals(true, ((RealCoordinate)state.getCoordinate().get(0)).getValue() <= (double) max && ((RealCoordinate)state.getCoordinate().get(0)).getValue() >= (double) min);
		
		
		state = specification.initialize();
		assertEquals(1,state.getCoordinate().size());
		assertEquals(true, ((RealCoordinate)state.getCoordinate().get(0)).getValue() <= (double) max && ((RealCoordinate)state.getCoordinate().get(0)).getValue() >= (double) min);
		
		state = specification.initialize();
		assertEquals(1,state.getCoordinate().size());
		assertEquals(true, ((RealCoordinate)state.getCoordinate().get(0)).getValue() <= (double) max && ((RealCoordinate)state.getCoordinate().get(0)).getValue() >= (double) min);
		
		state = specification.initialize();
		assertEquals(1,state.getCoordinate().size());
		assertEquals(true, ((RealCoordinate)state.getCoordinate().get(0)).getValue() <= (double) max && ((RealCoordinate)state.getCoordinate().get(0)).getValue() >= (double) min);
		
		
		
		state = specification.initialize();
		assertEquals(1,state.getCoordinate().size());
		assertEquals(true, ((RealCoordinate)state.getCoordinate().get(0)).getValue() <= (double) max && ((RealCoordinate)state.getCoordinate().get(0)).getValue() >= (double) min);
		
		
		state = specification.initialize();
		assertEquals(1,state.getCoordinate().size());
		assertEquals(true, ((RealCoordinate)state.getCoordinate().get(0)).getValue() <= (double) max && ((RealCoordinate)state.getCoordinate().get(0)).getValue() >= (double) min);
		
		state = specification.initialize();
		assertEquals(1,state.getCoordinate().size());
		assertEquals(true, ((RealCoordinate)state.getCoordinate().get(0)).getValue() <= (double) max && ((RealCoordinate)state.getCoordinate().get(0)).getValue() >= (double) min);
		
		state = specification.initialize();
		assertEquals(1,state.getCoordinate().size());
		assertEquals(true, ((RealCoordinate)state.getCoordinate().get(0)).getValue() <= (double) max && ((RealCoordinate)state.getCoordinate().get(0)).getValue() >= (double) min);
		
		
		
		state = specification.initialize();
		assertEquals(1,state.getCoordinate().size());
		assertEquals(true, ((RealCoordinate)state.getCoordinate().get(0)).getValue() <= (double) max && ((RealCoordinate)state.getCoordinate().get(0)).getValue() >= (double) min);
		
		
		state = specification.initialize();
		assertEquals(1,state.getCoordinate().size());
		assertEquals(true, ((RealCoordinate)state.getCoordinate().get(0)).getValue() <= (double) max && ((RealCoordinate)state.getCoordinate().get(0)).getValue() >= (double) min);
		
	}

	public void testInitializeRange_negativo() {
		log.info(">>Inicio ");
		int min = -2;
		int max = 2;
		specification.addCoordinate("x", min , max);
		
		 
		
		State state = specification.initialize();
		assertEquals(1,state.getCoordinate().size());
		assertEquals(true, ((RealCoordinate)state.getCoordinate().get(0)).getValue() <= (double) max && ((RealCoordinate)state.getCoordinate().get(0)).getValue() >= (double) min);
		
		state = specification.initialize();
		assertEquals(1,state.getCoordinate().size());
		assertEquals(true, ((RealCoordinate)state.getCoordinate().get(0)).getValue() <= (double) max && ((RealCoordinate)state.getCoordinate().get(0)).getValue() >= (double) min);
		
		
		
		state = specification.initialize();
		assertEquals(1,state.getCoordinate().size());
		assertEquals(true, ((RealCoordinate)state.getCoordinate().get(0)).getValue() <= (double) max && ((RealCoordinate)state.getCoordinate().get(0)).getValue() >= (double) min);
		
		
		state = specification.initialize();
		assertEquals(1,state.getCoordinate().size());
		assertEquals(true, ((RealCoordinate)state.getCoordinate().get(0)).getValue() <= (double) max && ((RealCoordinate)state.getCoordinate().get(0)).getValue() >= (double) min);
		
		state = specification.initialize();
		assertEquals(1,state.getCoordinate().size());
		assertEquals(true, ((RealCoordinate)state.getCoordinate().get(0)).getValue() <= (double) max && ((RealCoordinate)state.getCoordinate().get(0)).getValue() >= (double) min);
		
		state = specification.initialize();
		assertEquals(1,state.getCoordinate().size());
		assertEquals(true, ((RealCoordinate)state.getCoordinate().get(0)).getValue() <= (double) max && ((RealCoordinate)state.getCoordinate().get(0)).getValue() >= (double) min);
		
		
		
		state = specification.initialize();
		assertEquals(1,state.getCoordinate().size());
		assertEquals(true, ((RealCoordinate)state.getCoordinate().get(0)).getValue() <= (double) max && ((RealCoordinate)state.getCoordinate().get(0)).getValue() >= (double) min);
		
		
		state = specification.initialize();
		assertEquals(1,state.getCoordinate().size());
		assertEquals(true, ((RealCoordinate)state.getCoordinate().get(0)).getValue() <= (double) max && ((RealCoordinate)state.getCoordinate().get(0)).getValue() >= (double) min);
		
		state = specification.initialize();
		assertEquals(1,state.getCoordinate().size());
		assertEquals(true, ((RealCoordinate)state.getCoordinate().get(0)).getValue() <= (double) max && ((RealCoordinate)state.getCoordinate().get(0)).getValue() >= (double) min);
		
		state = specification.initialize();
		assertEquals(1,state.getCoordinate().size());
		assertEquals(true, ((RealCoordinate)state.getCoordinate().get(0)).getValue() <= (double) max && ((RealCoordinate)state.getCoordinate().get(0)).getValue() >= (double) min);
		
		
		
		state = specification.initialize();
		assertEquals(1,state.getCoordinate().size());
		assertEquals(true, ((RealCoordinate)state.getCoordinate().get(0)).getValue() <= (double) max && ((RealCoordinate)state.getCoordinate().get(0)).getValue() >= (double) min);
		
		
		state = specification.initialize();
		assertEquals(1,state.getCoordinate().size());
		assertEquals(true, ((RealCoordinate)state.getCoordinate().get(0)).getValue() <= (double) max && ((RealCoordinate)state.getCoordinate().get(0)).getValue() >= (double) min);
		
	}
	
	public void testPerturbeRange_zero_um() {
		log.info(">>Inicio ");
		int min = 0;
		int max = 1; 
		specification.addCoordinate("x", min, max);
		
		 
		
		State state = specification.initialize();
		assertEquals(1,state.getCoordinate().size());
		assertEquals(true, ((RealCoordinate)state.getCoordinate().get(0)).getValue() <= (double) max && ((RealCoordinate)state.getCoordinate().get(0)).getValue() >= (double) min);
		
		state = specification.perturb(state);
		assertEquals(1,state.getCoordinate().size());
		assertEquals(true, ((RealCoordinate)state.getCoordinate().get(0)).getValue() <= (double) max && ((RealCoordinate)state.getCoordinate().get(0)).getValue() >= (double) min);
		
		
		
		state = specification.perturb(state);
		assertEquals(1,state.getCoordinate().size());
		assertEquals(true, ((RealCoordinate)state.getCoordinate().get(0)).getValue() <= (double) max && ((RealCoordinate)state.getCoordinate().get(0)).getValue() >= (double) min);
		
		
		state = specification.perturb(state);
		assertEquals(1,state.getCoordinate().size());
		assertEquals(true, ((RealCoordinate)state.getCoordinate().get(0)).getValue() <= (double) max && ((RealCoordinate)state.getCoordinate().get(0)).getValue() >= (double) min);
		
	}
	
	public void testPerturbRange_negativo_negativo() {
		log.info(">>Inicio ");
		int min = -2;
		int max = 2;
		specification.addCoordinate("x", min , max);
		
		 
		
		State state = specification.initialize();
		((RealCoordinate)state.getCoordinate().get(0)).setValue(-1.96d);
		
		state =  specification.perturb(state);
		assertEquals(1,state.getCoordinate().size());
		assertEquals(true, ((RealCoordinate)state.getCoordinate().get(0)).getValue() <= (double) max && ((RealCoordinate)state.getCoordinate().get(0)).getValue() >= (double) min);
		
		
		
		state =  specification.perturb(state);
		assertEquals(1,state.getCoordinate().size());
		assertEquals(true, ((RealCoordinate)state.getCoordinate().get(0)).getValue() <= (double) max && ((RealCoordinate)state.getCoordinate().get(0)).getValue() >= (double) min);
		
		
		state =  specification.perturb(state);
		assertEquals(1,state.getCoordinate().size());
		assertEquals(true, ((RealCoordinate)state.getCoordinate().get(0)).getValue() <= (double) max && ((RealCoordinate)state.getCoordinate().get(0)).getValue() >= (double) min);
		
		state =  specification.perturb(state);
		assertEquals(1,state.getCoordinate().size());
		assertEquals(true, ((RealCoordinate)state.getCoordinate().get(0)).getValue() <= (double) max && ((RealCoordinate)state.getCoordinate().get(0)).getValue() >= (double) min);
		
		state =  specification.perturb(state);
		assertEquals(1,state.getCoordinate().size());
		assertEquals(true, ((RealCoordinate)state.getCoordinate().get(0)).getValue() <= (double) max && ((RealCoordinate)state.getCoordinate().get(0)).getValue() >= (double) min);
		
		
		
		state =  specification.perturb(state);
		assertEquals(1,state.getCoordinate().size());
		assertEquals(true, ((RealCoordinate)state.getCoordinate().get(0)).getValue() <= (double) max && ((RealCoordinate)state.getCoordinate().get(0)).getValue() >= (double) min);
		
		
		state =  specification.perturb(state);
		assertEquals(1,state.getCoordinate().size());
		assertEquals(true, ((RealCoordinate)state.getCoordinate().get(0)).getValue() <= (double) max && ((RealCoordinate)state.getCoordinate().get(0)).getValue() >= (double) min);
		
		state =  specification.perturb(state);
		assertEquals(1,state.getCoordinate().size());
		assertEquals(true, ((RealCoordinate)state.getCoordinate().get(0)).getValue() <= (double) max && ((RealCoordinate)state.getCoordinate().get(0)).getValue() >= (double) min);
		
		state =  specification.perturb(state);
		assertEquals(1,state.getCoordinate().size());
		assertEquals(true, ((RealCoordinate)state.getCoordinate().get(0)).getValue() <= (double) max && ((RealCoordinate)state.getCoordinate().get(0)).getValue() >= (double) min);
		
		
		
		state =  specification.perturb(state);
		assertEquals(1,state.getCoordinate().size());
		assertEquals(true, ((RealCoordinate)state.getCoordinate().get(0)).getValue() <= (double) max && ((RealCoordinate)state.getCoordinate().get(0)).getValue() >= (double) min);
		
		
		state =  specification.perturb(state);
		assertEquals(1,state.getCoordinate().size());
		assertEquals(true, ((RealCoordinate)state.getCoordinate().get(0)).getValue() <= (double) max && ((RealCoordinate)state.getCoordinate().get(0)).getValue() >= (double) min);
		
	}

}
