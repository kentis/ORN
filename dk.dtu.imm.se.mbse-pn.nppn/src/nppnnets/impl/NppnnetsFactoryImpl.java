/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package nppnnets.impl;

import nppnnets.*;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EFactoryImpl;

import org.eclipse.emf.ecore.plugin.EcorePlugin;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class NppnnetsFactoryImpl extends EFactoryImpl implements NppnnetsFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static NppnnetsFactory init() {
		try {
			NppnnetsFactory theNppnnetsFactory = (NppnnetsFactory)EPackage.Registry.INSTANCE.getEFactory("http://k1s.org/epnk/nppn"); 
			if (theNppnnetsFactory != null) {
				return theNppnnetsFactory;
			}
		}
		catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new NppnnetsFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NppnnetsFactoryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EObject create(EClass eClass) {
		switch (eClass.getClassifierID()) {
			case NppnnetsPackage.NPPN_TYPE: return createNPPNType();
			case NppnnetsPackage.PLACE: return createPlace();
			case NppnnetsPackage.TRANSITION: return createTransition();
			case NppnnetsPackage.PRAGMATICS: return createPragmatics();
			case NppnnetsPackage.ARC: return createArc();
			case NppnnetsPackage.INSCRIPTION: return createInscription();
			case NppnnetsPackage.PRAG_STRUCTURE: return createPragStructure();
			case NppnnetsPackage.REF_PLACE: return createRefPlace();
			default:
				throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NPPNType createNPPNType() {
		NPPNTypeImpl nppnType = new NPPNTypeImpl();
		return nppnType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Place createPlace() {
		PlaceImpl place = new PlaceImpl();
		return place;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Transition createTransition() {
		TransitionImpl transition = new TransitionImpl();
		return transition;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Pragmatics createPragmatics() {
		PragmaticsImpl pragmatics = new PragmaticsImpl();
		return pragmatics;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Arc createArc() {
		ArcImpl arc = new ArcImpl();
		return arc;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Inscription createInscription() {
		InscriptionImpl inscription = new InscriptionImpl();
		return inscription;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PragStructure createPragStructure() {
		PragStructureImpl pragStructure = new PragStructureImpl();
		return pragStructure;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public RefPlace createRefPlace() {
		RefPlaceImpl refPlace = new RefPlaceImpl();
		return refPlace;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NppnnetsPackage getNppnnetsPackage() {
		return (NppnnetsPackage)getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static NppnnetsPackage getPackage() {
		return NppnnetsPackage.eINSTANCE;
	}

} //NppnnetsFactoryImpl
