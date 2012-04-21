/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package inhibitornets.tests;

import inhibitornets.InhibitornetsFactory;
import inhibitornets.ReadArc;

import junit.textui.TestRunner;

/**
 * <!-- begin-user-doc -->
 * A test case for the model object '<em><b>Read Arc</b></em>'.
 * <!-- end-user-doc -->
 * @generated
 */
public class ReadArcTest extends ArcTest {

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static void main(String[] args) {
		TestRunner.run(ReadArcTest.class);
	}

	/**
	 * Constructs a new Read Arc test case with the given name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ReadArcTest(String name) {
		super(name);
	}

	/**
	 * Returns the fixture for this Read Arc test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected ReadArc getFixture() {
		return (ReadArc)fixture;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see junit.framework.TestCase#setUp()
	 * @generated
	 */
	@Override
	protected void setUp() throws Exception {
		setFixture(InhibitornetsFactory.eINSTANCE.createReadArc());
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

} //ReadArcTest
