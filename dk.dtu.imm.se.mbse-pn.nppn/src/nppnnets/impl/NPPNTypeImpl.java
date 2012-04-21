/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package nppnnets.impl;

import java.util.Collection;

import nppnnets.NPPNType;
import nppnnets.NppnnetsPackage;
import nppnnets.Pragmatics;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.emf.ecore.util.EObjectResolvingEList;

import org.pnml.tools.epnk.pnmlcoremodel.impl.PetriNetTypeImpl;
import org.pnml.tools.epnk.structuredpntypemodel.Linker;
import org.pnml.tools.epnk.structuredpntypemodel.impl.StructuredPetriNetTypeImpl;
import org.pnml.tools.epnk.pntypes.hlpng.pntd.hlpngdefinition.impl.HLPNGImpl;
//import org.pnml.tools.epnk.pntypes.hlpng.pntd.hlpngdefinition.impl.HLPNGLinker;
import org.pnml.tools.epnk.pntypes.hlpngs.datatypes.booleans.Not;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>NPPN Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link nppnnets.impl.NPPNTypeImpl#getEReference0 <em>EReference0</em>}</li>
 *   <li>{@link nppnnets.impl.NPPNTypeImpl#getPragmatics <em>Pragmatics</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class NPPNTypeImpl extends PetriNetTypeImpl implements NPPNType {
	/**
	 * The cached value of the '{@link #getEReference0() <em>EReference0</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEReference0()
	 * @generated
	 * @ordered
	 */
	protected NPPNType eReference0;

	/**
	 * The cached value of the '{@link #getPragmatics() <em>Pragmatics</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPragmatics()
	 * @generated
	 * @ordered
	 */
	protected EList<Pragmatics> pragmatics;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public NPPNTypeImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return NppnnetsPackage.Literals.NPPN_TYPE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NPPNType getEReference0() {
		if (eReference0 != null && eReference0.eIsProxy()) {
			InternalEObject oldEReference0 = (InternalEObject)eReference0;
			eReference0 = (NPPNType)eResolveProxy(oldEReference0);
			if (eReference0 != oldEReference0) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, NppnnetsPackage.NPPN_TYPE__EREFERENCE0, oldEReference0, eReference0));
			}
		}
		return eReference0;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NPPNType basicGetEReference0() {
		return eReference0;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setEReference0(NPPNType newEReference0) {
		NPPNType oldEReference0 = eReference0;
		eReference0 = newEReference0;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, NppnnetsPackage.NPPN_TYPE__EREFERENCE0, oldEReference0, eReference0));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Pragmatics> getPragmatics() {
		if (pragmatics == null) {
			pragmatics = new EObjectResolvingEList<Pragmatics>(Pragmatics.class, this, NppnnetsPackage.NPPN_TYPE__PRAGMATICS);
		}
		return pragmatics;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case NppnnetsPackage.NPPN_TYPE__EREFERENCE0:
				if (resolve) return getEReference0();
				return basicGetEReference0();
			case NppnnetsPackage.NPPN_TYPE__PRAGMATICS:
				return getPragmatics();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case NppnnetsPackage.NPPN_TYPE__EREFERENCE0:
				setEReference0((NPPNType)newValue);
				return;
			case NppnnetsPackage.NPPN_TYPE__PRAGMATICS:
				getPragmatics().clear();
				getPragmatics().addAll((Collection<? extends Pragmatics>)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case NppnnetsPackage.NPPN_TYPE__EREFERENCE0:
				setEReference0((NPPNType)null);
				return;
			case NppnnetsPackage.NPPN_TYPE__PRAGMATICS:
				getPragmatics().clear();
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case NppnnetsPackage.NPPN_TYPE__EREFERENCE0:
				return eReference0 != null;
			case NppnnetsPackage.NPPN_TYPE__PRAGMATICS:
				return pragmatics != null && !pragmatics.isEmpty();
		}
		return super.eIsSet(featureID);
	}

	@Override
	public String toString(){
		return "NPPNNet";
	}
	
	Linker linker;
	public Linker getLinker() {
				
		return null;
	}	
	
	@Override
	// @generated NOT
	// @author kifs	
	public NPPNType createPetriNetType() {
		return NppnnetsFactoryImpl.eINSTANCE.createNPPNType();
	}
} //NPPNTypeImpl
