/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package inhibitornets.tests;

import inhibitornets.InhibitorArc;
import inhibitornets.InhibitornetsFactory;

import junit.textui.TestRunner;

/**
 * <!-- begin-user-doc -->
 * A test case for the model object '<em><b>Inhibitor Arc</b></em>'.
 * <!-- end-user-doc -->
 * @generated
 */
public class InhibitorArcTest extends ArcTest {

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static void main(String[] args) {
		TestRunner.run(InhibitorArcTest.class);
	}

	/**
	 * Constructs a new Inhibitor Arc test case with the given name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public InhibitorArcTest(String name) {
		super(name);
	}

	/**
	 * Returns the fixture for this Inhibitor Arc test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected InhibitorArc getFixture() {
		return (InhibitorArc)fixture;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see junit.framework.TestCase#setUp()
	 * @generated
	 */
	@Override
	protected void setUp() throws Exception {
		setFixture(InhibitornetsFactory.eINSTANCE.createInhibitorArc());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see junit.framework.TestCase#tearDown()
	 * @generated
	 */
	@Override
	protected void tearDown() throws Exception {
		setFixture(null);
	}

} //InhibitorArcTest
