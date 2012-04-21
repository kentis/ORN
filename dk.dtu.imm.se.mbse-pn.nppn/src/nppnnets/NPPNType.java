/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package nppnnets;

import org.eclipse.emf.common.util.EList;

import org.pnml.tools.epnk.pnmlcoremodel.PetriNetType;
import org.pnml.tools.epnk.structuredpntypemodel.StructuredPetriNetType;
import org.pnml.tools.epnk.pntypes.hlpng.pntd.hlpngdefinition.HLPNG;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>NPPN Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link nppnnets.NPPNType#getEReference0 <em>EReference0</em>}</li>
 *   <li>{@link nppnnets.NPPNType#getPragmatics <em>Pragmatics</em>}</li>
 * </ul>
 * </p>
 *
 * @see nppnnets.NppnnetsPackage#getNPPNType()
 * @model
 * @generated
 */
public interface NPPNType extends PetriNetType {
	/**
	 * Returns the value of the '<em><b>EReference0</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>EReference0</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>EReference0</em>' reference.
	 * @see #setEReference0(NPPNType)
	 * @see nppnnets.NppnnetsPackage#getNPPNType_EReference0()
	 * @model
	 * @generated
	 */
	NPPNType getEReference0();

	/**
	 * Sets the value of the '{@link nppnnets.NPPNType#getEReference0 <em>EReference0</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>EReference0</em>' reference.
	 * @see #getEReference0()
	 * @generated
	 */
	void setEReference0(NPPNType value);

	/**
	 * Returns the value of the '<em><b>Pragmatics</b></em>' reference list.
	 * The list contents are of type {@link nppnnets.Pragmatics}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Pragmatics</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Pragmatics</em>' reference list.
	 * @see nppnnets.NppnnetsPackage#getNPPNType_Pragmatics()
	 * @model
	 * @generated
	 */
	EList<Pragmatics> getPragmatics();

} // NPPNType
