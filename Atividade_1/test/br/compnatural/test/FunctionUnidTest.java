package br.compnatural.test;

import br.compnatural.Specification;
import br.compnatural.State;
import br.compnatural.atividade_1.FunctionUnid;
import junit.framework.TestCase;

public class FunctionUnidTest extends TestCase {

	public void testEval() {
		FunctionUnid function = new FunctionUnid();
		
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
		
		state.getCoordinate().get(0).setValue(0.2931445169958321);
		assertEquals(0.9060124029472527, function.eval(state));
		
	}

}
