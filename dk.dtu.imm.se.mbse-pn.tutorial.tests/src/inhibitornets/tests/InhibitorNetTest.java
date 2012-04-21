/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package inhibitornets.tests;

import inhibitornets.InhibitorNet;
import inhibitornets.InhibitornetsFactory;

import junit.framework.TestCase;

import junit.textui.TestRunner;

/**
 * <!-- begin-user-doc -->
 * A test case for the model object '<em><b>Inhibitor Net</b></em>'.
 * <!-- end-user-doc -->
 * @generated
 */
public class InhibitorNetTest extends TestCase {

	/**
	 * The fixture for this Inhibitor Net test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected InhibitorNet fixture = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static void main(String[] args) {
		TestRunner.run(InhibitorNetTest.class);
	}

	/**
	 * Constructs a new Inhibitor Net test case with the given name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public InhibitorNetTest(String name) {
		super(name);
	}

	/**
	 * Sets the fixture for this Inhibitor Net test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void setFixture(InhibitorNet fixture) {
		this.fixture = fixture;
	}

	/**
	 * Returns the fixture for this Inhibitor Net test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected InhibitorNet getFixture() {
		return fixture;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see junit.framework.TestCase#setUp()
	 * @generated
	 */
	@Override
	protected void setUp() throws Exception {
		setFixture(InhibitornetsFactory.eINSTANCE.createInhibitorNet());
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

} //InhibitorNetTest
