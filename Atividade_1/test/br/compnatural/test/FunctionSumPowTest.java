package br.compnatural.test;

import br.compnatural.Specification;
import br.compnatural.State;
import br.compnatural.function.FunctionSumPow;
import junit.framework.TestCase;

public class FunctionSumPowTest extends TestCase {

	public void testEval() {
		FunctionSumPow function = new FunctionSumPow(Boolean.TRUE);
		
		Specification specification = new Specification();
		for (int i = 1; i <= 10; i++) {
			specification.addCoordinate("x"+i, -1, 1);
		}
		State state = specification.initialize();
		function.eval(state);
		System.out.println("para x = "+state+" func="+function.eval(state));
		
		state = specification.perturb(state);
		function.eval(state);
		System.out.println("para x = "+state+" func="+function.eval(state));
		
		state = specification.perturb(state);
		function.eval(state);
		System.out.println("para x = "+state+" func="+function.eval(state));
		
		state = specification.perturb(state);
		function.eval(state);
		System.out.println("para x = "+state+" func="+function.eval(state));
		
		assertTrue(Boolean.TRUE);
	}

}
