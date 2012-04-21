/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package orn.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import orn.Ontology;
import orn.OrnPackage;
import orn.Page;
import orn.Pragmatics;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Page</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link orn.impl.PageImpl#getPragmatics <em>Pragmatics</em>}</li>
 *   <li>{@link orn.impl.PageImpl#getOntology <em>Ontology</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class PageImpl extends org.pnml.tools.epnk.pntypes.hlpng.pntd.hlpngdefinition.impl.PageImpl implements Page {
	/**
	 * The cached value of the '{@link #getPragmatics() <em>Pragmatics</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPragmatics()
	 * @generated
	 * @ordered
	 */
	protected EList<Pragmatics> pragmatics;

	/**
	 * The cached value of the '{@link #getOntology() <em>Ontology</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOntology()
	 * @generated
	 * @ordered
	 */
	protected EList<Ontology> ontology;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected PageImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return OrnPackage.Literals.PAGE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Pragmatics> getPragmatics() {
		if (pragmatics == null) {
			pragmatics = new EObjectContainmentEList<Pragmatics>(Pragmatics.class, this, OrnPackage.PAGE__PRAGMATICS);
		}
		return pragmatics;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Ontology> getOntology() {
		if (ontology == null) {
			ontology = new EObjectContainmentEList<Ontology>(Ontology.class, this, OrnPackage.PAGE__ONTOLOGY);
		}
		return ontology;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case OrnPackage.PAGE__PRAGMATICS:
				return ((InternalEList<?>)getPragmatics()).basicRemove(otherEnd, msgs);
			case OrnPackage.PAGE__ONTOLOGY:
				return ((InternalEList<?>)getOntology()).basicRemove(otherEnd, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case OrnPackage.PAGE__PRAGMATICS:
				return getPragmatics();
			case OrnPackage.PAGE__ONTOLOGY:
				return getOntology();
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
			case OrnPackage.PAGE__PRAGMATICS:
				getPragmatics().clear();
				getPragmatics().addAll((Collection<? extends Pragmatics>)newValue);
				return;
			case OrnPackage.PAGE__ONTOLOGY:
				getOntology().clear();
				getOntology().addAll((Collection<? extends Ontology>)newValue);
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
			case OrnPackage.PAGE__PRAGMATICS:
				getPragmatics().clear();
				return;
			case OrnPackage.PAGE__ONTOLOGY:
				getOntology().clear();
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
			case OrnPackage.PAGE__PRAGMATICS:
				return pragmatics != null && !pragmatics.isEmpty();
			case OrnPackage.PAGE__ONTOLOGY:
				return ontology != null && !ontology.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} //PageImpl
