/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package nppnnets.tests;

import junit.framework.TestCase;

import junit.textui.TestRunner;

import nppnnets.Inscription;
import nppnnets.NppnnetsFactory;

/**
 * <!-- begin-user-doc -->
 * A test case for the model object '<em><b>Inscription</b></em>'.
 * <!-- end-user-doc -->
 * @generated
 */
public class InscriptionTest extends TestCase {

	/**
	 * The fixture for this Inscription test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected Inscription fixture = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static void main(String[] args) {
		TestRunner.run(InscriptionTest.class);
	}

	/**
	 * Constructs a new Inscription test case with the given name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public InscriptionTest(String name) {
		super(name);
	}

	/**
	 * Sets the fixture for this Inscription test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void setFixture(Inscription fixture) {
		this.fixture = fixture;
	}

	/**
	 * Returns the fixture for this Inscription test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected Inscription getFixture() {
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
		setFixture(NppnnetsFactory.eINSTANCE.createInscription());
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

} //InscriptionTest
