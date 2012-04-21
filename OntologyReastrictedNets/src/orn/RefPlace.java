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
 * A representation of the model object '<em><b>Ref Place</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link orn.RefPlace#getPragmatics <em>Pragmatics</em>}</li>
 * </ul>
 * </p>
 *
 * @see orn.OrnPackage#getRefPlace()
 * @model
 * @generated
 */
public interface RefPlace extends org.pnml.tools.epnk.pnmlcoremodel.RefPlace {
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
	 * @see orn.OrnPackage#getRefPlace_Pragmatics()
	 * @model containment="true"
	 * @generated
	 */
	EList<Pragmatics> getPragmatics();

} // RefPlace
