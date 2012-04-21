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
import orn.RefPlace;

/**
 * <!-- begin-user-doc -->
 * A test case for the model object '<em><b>Ref Place</b></em>'.
 * <!-- end-user-doc -->
 * @generated
 */
public class RefPlaceTest extends TestCase {

	/**
	 * The fixture for this Ref Place test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected RefPlace fixture = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static void main(String[] args) {
		TestRunner.run(RefPlaceTest.class);
	}

	/**
	 * Constructs a new Ref Place test case with the given name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public RefPlaceTest(String name) {
		super(name);
	}

	/**
	 * Sets the fixture for this Ref Place test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void setFixture(RefPlace fixture) {
		this.fixture = fixture;
	}

	/**
	 * Returns the fixture for this Ref Place test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected RefPlace getFixture() {
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
		setFixture(OrnFactory.eINSTANCE.createRefPlace());
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

} //RefPlaceTest
