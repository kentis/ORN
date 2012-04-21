/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package inhibitornets;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Arc Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link inhibitornets.ArcType#getType <em>Type</em>}</li>
 *   <li>{@link inhibitornets.ArcType#getText <em>Text</em>}</li>
 * </ul>
 * </p>
 *
 * @see inhibitornets.InhibitornetsPackage#getArcType()
 * @model
 * @generated
 */
public interface ArcType extends EObject {
	/**
	 * Returns the value of the '<em><b>Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Type</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Type</em>' reference.
	 * @see #setType(Arc)
	 * @see inhibitornets.InhibitornetsPackage#getArcType_Type()
	 * @model
	 * @generated
	 */
	Arc getType();

	/**
	 * Sets the value of the '{@link inhibitornets.ArcType#getType <em>Type</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Type</em>' reference.
	 * @see #getType()
	 * @generated
	 */
	void setType(Arc value);

	/**
	 * Returns the value of the '<em><b>Text</b></em>' attribute.
	 * The literals are from the enumeration {@link inhibitornets.TYPES}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Text</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Text</em>' attribute.
	 * @see inhibitornets.TYPES
	 * @see #setText(TYPES)
	 * @see inhibitornets.InhibitornetsPackage#getArcType_Text()
	 * @model
	 * @generated
	 */
	TYPES getText();

	/**
	 * Sets the value of the '{@link inhibitornets.ArcType#getText <em>Text</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Text</em>' attribute.
	 * @see inhibitornets.TYPES
	 * @see #getText()
	 * @generated
	 */
	void setText(TYPES value);

} // ArcType
