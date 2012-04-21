/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package nppnnets.tests;

import junit.framework.Test;
import junit.framework.TestSuite;

import junit.textui.TestRunner;

/**
 * <!-- begin-user-doc -->
 * A test suite for the '<em><b>NPPNNets</b></em>' model.
 * <!-- end-user-doc -->
 * @generated
 */
public class NPPNNetsAllTests extends TestSuite {

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
		TestSuite suite = new NPPNNetsAllTests("NPPNNets Tests");
		suite.addTest(NppnnetsTests.suite());
		return suite;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NPPNNetsAllTests(String name) {
		super(name);
	}

} //NPPNNetsAllTests
