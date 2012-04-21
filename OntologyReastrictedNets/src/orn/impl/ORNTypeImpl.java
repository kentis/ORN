/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package orn.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.pnml.tools.epnk.pnmlcoremodel.impl.PetriNetTypeImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import orn.ORNType;
import orn.Ontology;
import orn.OrnPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>ORN Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * </p>
 *
 * @generated
 */
public class ORNTypeImpl extends PetriNetTypeImpl implements ORNType {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public ORNTypeImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return OrnPackage.Literals.ORN_TYPE;
	}

	@Override
	public String toString(){
		return "org.k1s.epnk.orn";
	}
	
} //ORNTypeImpl
