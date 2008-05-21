package org.jquantlib.testsuite.math;

import static org.junit.Assert.assertEquals;

import org.jquantlib.math.BrentSolver1D;
import org.jquantlib.math.UnaryFunctionDouble;
import org.junit.Test;

/**
 * @author Richard Gomes
 */
public class BrentSolver1dTest {

	@Test
	public void testInvertSquare() {
		
		UnaryFunctionDouble square = new UnaryFunctionDouble() {

			public double evaluate(double x) {
				return x*x-1;
			}
			
		};
	
	BrentSolver1D brent = new BrentSolver1D();
	
	double soln = brent.solve(square, 1.0e-15, 0.01, 0, 2);

	assertEquals(1.0, soln,1.0e-15);
	assertEquals(10, brent.getNumEvaluations());
	
	soln = brent.solve(square, 1.0e-16, 0.01, 0.1);

	assertEquals(1.0, soln,1.0e-15);
	assertEquals(13, brent.getNumEvaluations());
	}
	
}
