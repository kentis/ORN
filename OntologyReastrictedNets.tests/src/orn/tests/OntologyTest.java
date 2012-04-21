/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package orn.tests;

import junit.framework.TestCase;

import junit.textui.TestRunner;

import orn.Ontology;
import orn.OrnFactory;

/**
 * <!-- begin-user-doc -->
 * A test case for the model object '<em><b>Ontology</b></em>'.
 * <!-- end-user-doc -->
 * @generated
 */
public class OntologyTest extends TestCase {

	/**
	 * The fixture for this Ontology test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected Ontology fixture = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static void main(String[] args) {
		TestRunner.run(OntologyTest.class);
	}

	/**
	 * Constructs a new Ontology test case with the given name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public OntologyTest(String name) {
		super(name);
	}

	/**
	 * Sets the fixture for this Ontology test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void setFixture(Ontology fixture) {
		this.fixture = fixture;
	}

	/**
	 * Returns the fixture for this Ontology test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected Ontology getFixture() {
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
		setFixture(OrnFactory.eINSTANCE.createOntology());
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

} //OntologyTest
