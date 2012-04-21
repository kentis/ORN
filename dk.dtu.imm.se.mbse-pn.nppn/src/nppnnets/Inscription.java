/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package nppnnets;

import org.eclipse.emf.ecore.EObject;
import org.pnml.tools.epnk.pnmlcoremodel.Label;
import org.pnml.tools.epnk.pntypes.hlpng.pntd.hlpngdefinition.HLAnnotation;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Inscription</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link nppnnets.Inscription#getText <em>Text</em>}</li>
 * </ul>
 * </p>
 *
 * @see nppnnets.NppnnetsPackage#getInscription()
 * @model
 * @generated
 */
public interface Inscription extends Label {

	/**
	 * Returns the value of the '<em><b>Text</b></em>' attribute.
	 * The default value is <code>""</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Text</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Text</em>' attribute.
	 * @see #setText(String)
	 * @see nppnnets.NppnnetsPackage#getInscription_Text()
	 * @model default=""
	 * @generated
	 */
	String getText();

	/**
	 * Sets the value of the '{@link nppnnets.Inscription#getText <em>Text</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Text</em>' attribute.
	 * @see #getText()
	 * @generated
	 */
	void setText(String value);

} // Inscription
