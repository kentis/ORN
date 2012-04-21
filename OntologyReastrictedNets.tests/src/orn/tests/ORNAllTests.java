/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package orn.tests;

import junit.framework.Test;
import junit.framework.TestSuite;

import junit.textui.TestRunner;

/**
 * <!-- begin-user-doc -->
 * A test suite for the '<em><b>ORN</b></em>' model.
 * <!-- end-user-doc -->
 * @generated
 */
public class ORNAllTests extends TestSuite {

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static void main(String[] args) {
		TestRunner.run(suite());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static Test suite() {
		TestSuite suite = new ORNAllTests("ORN Tests");
		suite.addTest(OrnTests.suite());
		return suite;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ORNAllTests(String name) {
		super(name);
	}

} //ORNAllTests
