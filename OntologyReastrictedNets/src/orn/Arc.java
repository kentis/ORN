/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package orn;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Arc</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link orn.Arc#getInscription <em>Inscription</em>}</li>
 * </ul>
 * </p>
 *
 * @see orn.OrnPackage#getArc()
 * @model
 * @generated
 */
public interface Arc extends org.pnml.tools.epnk.pntypes.hlpng.pntd.hlpngdefinition.Arc {
	/**
	 * Returns the value of the '<em><b>Inscription</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Inscription</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Inscription</em>' containment reference.
	 * @see #setInscription(Inscription)
	 * @see orn.OrnPackage#getArc_Inscription()
	 * @model containment="true" required="true"
	 * @generated
	 */
	Inscription getInscription();

	/**
	 * Sets the value of the '{@link orn.Arc#getInscription <em>Inscription</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Inscription</em>' containment reference.
	 * @see #getInscription()
	 * @generated
	 */
	void setInscription(Inscription value);

} // Arc
