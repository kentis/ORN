/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package inhibitornets;

import java.math.BigInteger;

import org.pnml.tools.epnk.pnmlcoremodel.Attribute;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Marking</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link inhibitornets.Marking#getText <em>Text</em>}</li>
 * </ul>
 * </p>
 *
 * @see inhibitornets.InhibitornetsPackage#getMarking()
 * @model
 * @generated
 */
public interface Marking extends Attribute {
	/**
	 * Returns the value of the '<em><b>Text</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Text</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Text</em>' attribute.
	 * @see #setText(BigInteger)
	 * @see inhibitornets.InhibitornetsPackage#getMarking_Text()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.NonNegativeInteger"
	 * @generated
	 */
	BigInteger getText();

	/**
	 * Sets the value of the '{@link inhibitornets.Marking#getText <em>Text</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Text</em>' attribute.
	 * @see #getText()
	 * @generated
	 */
	void setText(BigInteger value);

} // Marking
