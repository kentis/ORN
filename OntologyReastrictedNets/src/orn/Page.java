/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package orn;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Page</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link orn.Page#getPragmatics <em>Pragmatics</em>}</li>
 *   <li>{@link orn.Page#getOntology <em>Ontology</em>}</li>
 * </ul>
 * </p>
 *
 * @see orn.OrnPackage#getPage()
 * @model
 * @generated
 */
public interface Page extends org.pnml.tools.epnk.pntypes.hlpng.pntd.hlpngdefinition.Page {
	/**
	 * Returns the value of the '<em><b>Pragmatics</b></em>' containment reference list.
	 * The list contents are of type {@link orn.Pragmatics}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Pragmatics</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Pragmatics</em>' containment reference list.
	 * @see orn.OrnPackage#getPage_Pragmatics()
	 * @model containment="true"
	 * @generated
	 */
	EList<Pragmatics> getPragmatics();

	/**
	 * Returns the value of the '<em><b>Ontology</b></em>' containment reference list.
	 * The list contents are of type {@link orn.Ontology}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Ontology</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Ontology</em>' containment reference list.
	 * @see orn.OrnPackage#getPage_Ontology()
	 * @model containment="true"
	 * @generated
	 */
	EList<Ontology> getOntology();

} // Page
