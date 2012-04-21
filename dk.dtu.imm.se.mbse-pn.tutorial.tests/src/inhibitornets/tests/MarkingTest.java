/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package inhibitornets.tests;

import inhibitornets.InhibitornetsFactory;
import inhibitornets.Marking;

import junit.framework.TestCase;

import junit.textui.TestRunner;

/**
 * <!-- begin-user-doc -->
 * A test case for the model object '<em><b>Marking</b></em>'.
 * <!-- end-user-doc -->
 * @generated
 */
public class MarkingTest extends TestCase {

	/**
	 * The fixture for this Marking test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected Marking fixture = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static void main(String[] args) {
		TestRunner.run(MarkingTest.class);
	}

	/**
	 * Constructs a new Marking test case with the given name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MarkingTest(String name) {
		super(name);
	}

	/**
	 * Sets the fixture for this Marking test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void setFixture(Marking fixture) {
		this.fixture = fixture;
	}

	/**
	 * Returns the fixture for this Marking test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected Marking getFixture() {
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
		setFixture(InhibitornetsFactory.eINSTANCE.createMarking());
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

} //MarkingTest
