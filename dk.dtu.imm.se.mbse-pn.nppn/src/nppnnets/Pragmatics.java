/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package nppnnets;

import java.util.Map;

import org.pnml.tools.epnk.structuredpntypemodel.StructuredLabel;
import org.pnml.tools.epnk.pntypes.hlpng.pntd.hlpngdefinition.HLAnnotation;
import org.pnml.tools.epnk.pnmlcoremodel.Label;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Pragmatics</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link nppnnets.Pragmatics#getStructure <em>Structure</em>}</li>
 * </ul>
 * </p>
 *
 * @see nppnnets.NppnnetsPackage#getPragmatics()
 * @model
 * @generated
 */
public interface Pragmatics extends StructuredLabel {

	/**
	 * Returns the value of the '<em><b>Structure</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Structure</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Structure</em>' containment reference.
	 * @see #setStructure(PragStructure)
	 * @see nppnnets.NppnnetsPackage#getPragmatics_Structure()
	 * @model containment="true"
	 * @generated
	 */
	PragStructure getStructure();

	/**
	 * Sets the value of the '{@link nppnnets.Pragmatics#getStructure <em>Structure</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Structure</em>' containment reference.
	 * @see #getStructure()
	 * @generated
	 */
	void setStructure(PragStructure value);
	
	/**
	 * 
	 * @return
	 * @generated NOT
	 */
	public String getName();
	/**
	 * 
	 * @return
	 * @generated NOT
	 */
	public Map getArguments();
} // Pragmatics
