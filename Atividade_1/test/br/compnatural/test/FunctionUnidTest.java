package br.compnatural.test;

import br.compnatural.Specification;
import br.compnatural.State;
import br.compnatural.function.FunctionUnid;
import junit.framework.TestCase;

public class FunctionUnidTest extends TestCase {

	public void testEval() {
		FunctionUnid function = new FunctionUnid(Boolean.TRUE);
		
		Specification specification = new Specification();   
		specification.addCoordinate("x", 0, 1);
		
		 
		
		State state = specification.initialize();
		function.eval(state);
		System.out.println("para x = "+state.getCoordinate().get(0).getValue() +" func="+function.eval(state));
		state.getCoordinate().get(0).setValue(1);
		function.eval(state);
		System.out.println("para x = "+state.getCoordinate().get(0).getValue() +" func="+function.eval(state));
		state.getCoordinate().get(0).setValue(0.1d);
		assertEquals(1.0, function.eval(state));
		System.out.println("para x = "+state.getCoordinate().get(0).getValue() +" func="+function.eval(state));
		state.getCoordinate().get(0).setValue(0);
		function.eval(state);
		System.out.println("para x = "+state.getCoordinate().get(0).getValue() +" func="+function.eval(state));
		
		state.getCoordinate().get(0).setValue(0.12508182100847443);
		assertEquals(0.6192054032978683, function.eval(state));
		
		state.getCoordinate().get(0).setValue(0.13755535260587587);
		assertEquals(0.3284843419174891, function.eval(state));
		
		state.getCoordinate().get(0).setValue(0.3067682741640493);
		assertEquals(0.8983962057670858, function.eval(state));
		
		state.getCoordinate().get(0).setValue(0.8361000255062404);
		System.out.println("parsa x = "+state.getCoordinate().get(0).getValue() +" func="+function.eval(state));
		
		state.getCoordinate().get(0).setValue(0.5);
		System.out.println("parsa x = "+state.getCoordinate().get(0).getValue() +" func="+function.eval(state));
		//assertEquals(0.98037551231628, function.eval(state));
		
		
		
	}

}
