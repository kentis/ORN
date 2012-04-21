/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package orn;

import org.pnml.tools.epnk.structuredpntypemodel.StructuredLabel;
import org.pnml.tools.epnk.pnmlcoremodel.Attribute;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Ontology</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link orn.Ontology#getStructure <em>Structure</em>}</li>
 * </ul>
 * </p>
 *
 * @see orn.OrnPackage#getOntology()
 * @model
 * @generated
 */
public interface Ontology extends StructuredLabel {

	/**
	 * Returns the value of the '<em><b>Structure</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Structure</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Structure</em>' containment reference.
	 * @see #setStructure(OntologyStructure)
	 * @see orn.OrnPackage#getOntology_Structure()
	 * @model containment="true"
	 * @generated
	 */
	OntologyStructure getStructure();

	/**
	 * Sets the value of the '{@link orn.Ontology#getStructure <em>Structure</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Structure</em>' containment reference.
	 * @see #getStructure()
	 * @generated
	 */
	void setStructure(OntologyStructure value);
} // Ontology
