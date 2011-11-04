package br.unifor.mia.formais.teorema.test;

import br.unifor.mia.formais.teorema.expression.ImpExpression;
import br.unifor.mia.formais.teorema.expression.Expression;
import br.unifor.mia.formais.teorema.expression.Variable;
import junit.framework.TestCase;

public class ImpExpressionTest extends TestCase {

	protected void setUp() throws Exception {
		super.setUp();
	}

	public void testEvaluate() {
		Variable V = new Variable(true,"V");
		Variable F = new Variable(false,"F");
		
		
		Expression and1 = new ImpExpression(V, V);
		Expression and2 = new ImpExpression(V, F);
		Expression and3 = new ImpExpression(F, V);
		Expression and4 = new ImpExpression(F, F);
		
		assertEquals(Boolean.TRUE, and1.evaluate());
		assertEquals(Boolean.FALSE, and2.evaluate());
		assertEquals(Boolean.TRUE, and3.evaluate());
		assertEquals(Boolean.TRUE, and4.evaluate());
		
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
		
		
		
		Expression and1 = new ImpExpression(V, A1);
		and1.setValue(Boolean.TRUE);
		Expression and2 = new ImpExpression(V, A2);
		and2.setValue(Boolean.FALSE);
		Expression and3 = new ImpExpression(F, A3);
		and3.setValue(Boolean.TRUE);
		Expression and4 = new ImpExpression(F, A4);
		and4.setValue(Boolean.FALSE);
		
		Expression and5 = new ImpExpression(A5, V);
		and5.setValue(Boolean.TRUE);
		Expression and6 = new ImpExpression(A6, V);
		and6.setValue(Boolean.FALSE);
		Expression and7 = new ImpExpression(A7, F);
		and7.setValue(Boolean.TRUE);
		Expression and8 = new ImpExpression(A8, F);
		and8.setValue(Boolean.FALSE);
		
		assertEquals(Boolean.TRUE, A1.evaluate());
		assertEquals(Boolean.FALSE, A2.evaluate());
		assertEquals(null, A3.evaluate());
		assertEquals(null, A4.evaluate());
		
		assertEquals(null, A5.evaluate());
		assertEquals(null, A6.evaluate());
		assertEquals(Boolean.FALSE, A7.evaluate());
		assertEquals(Boolean.TRUE, A8.evaluate());
	}

}
