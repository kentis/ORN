/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package orn.tests;

import junit.framework.TestCase;

import junit.textui.TestRunner;

import orn.OntologyStructure;
import orn.OrnFactory;

/**
 * <!-- begin-user-doc -->
 * A test case for the model object '<em><b>Ontology Structure</b></em>'.
 * <!-- end-user-doc -->
 * @generated
 */
public class OntologyStructureTest extends TestCase {

	/**
	 * The fixture for this Ontology Structure test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected OntologyStructure fixture = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static void main(String[] args) {
		TestRunner.run(OntologyStructureTest.class);
	}

	/**
	 * Constructs a new Ontology Structure test case with the given name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public OntologyStructureTest(String name) {
		super(name);
	}

	/**
	 * Sets the fixture for this Ontology Structure test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void setFixture(OntologyStructure fixture) {
		this.fixture = fixture;
	}

	/**
	 * Returns the fixture for this Ontology Structure test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected OntologyStructure getFixture() {
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
		setFixture(OrnFactory.eINSTANCE.createOntologyStructure());
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

} //OntologyStructureTest
