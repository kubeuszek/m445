package br.unifor.mia.formais.teorema.test;

import br.unifor.mia.formais.teorema.expression.Expression;
import br.unifor.mia.formais.teorema.expression.OrExpression;
import br.unifor.mia.formais.teorema.expression.Variable;
import junit.framework.TestCase;

public class OrExpressionTest extends TestCase {

	protected void setUp() throws Exception {
		super.setUp();
	}

	public void testEvaluate() {
		Variable V = new Variable(true,"V");
		Variable F = new Variable(false,"F");
		Variable N = new Variable(null,"Null");
		
		
		Expression and1 = new OrExpression(V, V);
		Expression and2 = new OrExpression(V, F);
		Expression and3 = new OrExpression(F, V);
		Expression and4 = new OrExpression(F, F);
		
		Expression and5 = new OrExpression(F, N);
		Expression and6 = new OrExpression(V, N);
		Expression and7 = new OrExpression(N, F);
		Expression and8 = new OrExpression(N, V);
		
		assertEquals(Boolean.TRUE, and1.evaluate());
		assertEquals(Boolean.TRUE, and2.evaluate());
		assertEquals(Boolean.TRUE, and3.evaluate());
		assertEquals(Boolean.FALSE, and4.evaluate());
		
		assertEquals(null, and5.evaluate());
		assertEquals(Boolean.TRUE, and6.evaluate());
		assertEquals(null, and7.evaluate());
		assertEquals(Boolean.TRUE, and8.evaluate());
	}

	public void testSetValue() {
		Variable V = new Variable(true,"V");
		Variable F = new Variable(false,"F");
		
		Variable A1 = new Variable(null,"A1");
		Variable A2 = new Variable(null,"A2");
		Variable A3 = new Variable(null,"A3");
		Variable A4 = new Variable(null,"A4");
		Variable A5 = new Variable(null,"A5");
		Variable A6 = new Variable(null,"A6");
		Variable A7 = new Variable(null,"A7");
		Variable A8 = new Variable(null,"A8");
		
		
		
		Expression and1 = new OrExpression(V, A1);
		and1.setValue(Boolean.TRUE);
		Expression and2 = new OrExpression(V, A2);
		and2.setValue(Boolean.FALSE);
		Expression and3 = new OrExpression(F, A3);
		and3.setValue(Boolean.TRUE);
		Expression and4 = new OrExpression(F, A4);
		and4.setValue(Boolean.FALSE);
		
		Expression and5 = new OrExpression(A5, V);
		and5.setValue(Boolean.TRUE);
		Expression and6 = new OrExpression(A6, V);
		and6.setValue(Boolean.FALSE);
		Expression and7 = new OrExpression(A7, F);
		and7.setValue(Boolean.TRUE);
		Expression and8 = new OrExpression(A8, F);
		and8.setValue(Boolean.FALSE);
		
		assertEquals(null, A1.evaluate());
		assertEquals(null, A2.evaluate());
		assertEquals(Boolean.TRUE, A3.evaluate());
		assertEquals(Boolean.FALSE, A4.evaluate());
		
		assertEquals(null, A5.evaluate());
		assertEquals(null, A6.evaluate());
		assertEquals(Boolean.TRUE, A7.evaluate());
		assertEquals(Boolean.FALSE, A8.evaluate());
	}

}
