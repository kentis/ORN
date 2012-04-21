/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package orn.tests;

import junit.framework.TestCase;

import junit.textui.TestRunner;

import orn.OrnFactory;
import orn.Pragmatics;

/**
 * <!-- begin-user-doc -->
 * A test case for the model object '<em><b>Pragmatics</b></em>'.
 * <!-- end-user-doc -->
 * @generated
 */
public class PragmaticsTest extends TestCase {

	/**
	 * The fixture for this Pragmatics test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected Pragmatics fixture = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static void main(String[] args) {
		TestRunner.run(PragmaticsTest.class);
	}

	/**
	 * Constructs a new Pragmatics test case with the given name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PragmaticsTest(String name) {
		super(name);
	}

	/**
	 * Sets the fixture for this Pragmatics test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void setFixture(Pragmatics fixture) {
		this.fixture = fixture;
	}

	/**
	 * Returns the fixture for this Pragmatics test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected Pragmatics getFixture() {
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
		setFixture(OrnFactory.eINSTANCE.createPragmatics());
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

} //PragmaticsTest
