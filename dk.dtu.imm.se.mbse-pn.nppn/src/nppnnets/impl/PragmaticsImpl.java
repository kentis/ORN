/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package nppnnets.impl;

import groovy.lang.GroovyShell;

import java.util.HashMap;
import java.util.Map;

import javax.management.RuntimeErrorException;

import nppnnets.NppnnetsPackage;
import nppnnets.PragStructure;
import nppnnets.Pragmatics;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.EObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.pnml.tools.epnk.structuredpntypemodel.Linker;
import org.pnml.tools.epnk.structuredpntypemodel.impl.StructuredLabelImpl;
import org.pnml.tools.epnk.pntypes.hlpng.pntd.hlpngdefinition.impl.HLAnnotationImpl;
import org.pnml.tools.epnk.pnmlcoremodel.impl.LabelImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Pragmatics</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link nppnnets.impl.PragmaticsImpl#getStructure <em>Structure</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class PragmaticsImpl extends StructuredLabelImpl implements Pragmatics {
	/**
	 * The cached value of the '{@link #getStructure() <em>Structure</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getStructure()
	 * @generated
	 * @ordered
	 */
	protected PragStructure structure;
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected PragmaticsImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return NppnnetsPackage.Literals.PRAGMATICS;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PragStructure getStructure() {
		return structure;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetStructure(PragStructure newStructure, NotificationChain msgs) {
		PragStructure oldStructure = structure;
		structure = newStructure;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, NppnnetsPackage.PRAGMATICS__STRUCTURE, oldStructure, newStructure);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setStructure(PragStructure newStructure) {
		if (newStructure != structure) {
			NotificationChain msgs = null;
			if (structure != null)
				msgs = ((InternalEObject)structure).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - NppnnetsPackage.PRAGMATICS__STRUCTURE, null, msgs);
			if (newStructure != null)
				msgs = ((InternalEObject)newStructure).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - NppnnetsPackage.PRAGMATICS__STRUCTURE, null, msgs);
			msgs = basicSetStructure(newStructure, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, NppnnetsPackage.PRAGMATICS__STRUCTURE, newStructure, newStructure));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case NppnnetsPackage.PRAGMATICS__STRUCTURE:
				return basicSetStructure(null, msgs);
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
			case NppnnetsPackage.PRAGMATICS__STRUCTURE:
				return getStructure();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case NppnnetsPackage.PRAGMATICS__STRUCTURE:
				setStructure((PragStructure)newValue);
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
			case NppnnetsPackage.PRAGMATICS__STRUCTURE:
				setStructure((PragStructure)null);
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
			case NppnnetsPackage.PRAGMATICS__STRUCTURE:
				return structure != null;
		}
		return super.eIsSet(featureID);
	}

	/**
	 * 
	 * @generated NOT
	 */
	@Override
	public EObject parse(String str){
		//throw  new RuntimeException("PARSING!!");
		PragStructure struct = prag(str);
		return struct;
	}

	public Linker getLinker(){
		return null;
		
	}
	
	static PragStructure prag(String elem){
		PragStructure retval = NppnnetsFactoryImpl.eINSTANCE.createPragStructure();
		if(elem == null ) return retval;
		String elemName = elem;
		
		
		
		
		String pragDef = elemName;
		//println "pragdef: $pragDef , $pragStart, $pragEnd"
		retval.setName(pragDef.subSequence(0, pragDef.indexOf("(")).toString());
		String d = pragDef.substring(pragDef.indexOf('(')+1, pragDef.indexOf(')'));
		//println "d: ${d.bytes}"
		if(d.length() == 0) retval.setArguments(new HashMap());
		else retval.setArguments(new GroovyShell().evaluate("["+d+"]"));
		
		return retval;
	}	
	
	/**
	 * @generated NOT
	 */
	@Override
	public String getName() {
		if(getStructure() == null && getText() != null){
			structure  = (PragStructure)parse(getText());
			return getStructure() == null? null : getStructure().getName();
		}else if(getStructure() != null){
			return getStructure().getName();
		}
		return null;
	}


	/**
	 * @generated NOT
	 */
	@Override
	public Map getArguments() {
		if(getStructure() == null && getText() != null){
			structure = (PragStructure)parse(getText());
			return getStructure() == null? null :(Map)getStructure().getArguments();
		}else if(getStructure() != null){
			return (Map)getStructure().getArguments();
		}

		return null;
	}

} //PragmaticsImpl
