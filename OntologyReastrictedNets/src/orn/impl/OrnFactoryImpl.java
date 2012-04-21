/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package orn.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EFactoryImpl;

import org.eclipse.emf.ecore.plugin.EcorePlugin;

import orn.*;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class OrnFactoryImpl extends EFactoryImpl implements OrnFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static OrnFactory init() {
		try {
			OrnFactory theOrnFactory = (OrnFactory)EPackage.Registry.INSTANCE.getEFactory("http://k1s.org/epnk/orn"); 
			if (theOrnFactory != null) {
				return theOrnFactory;
			}
		}
		catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new OrnFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public OrnFactoryImpl() {
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
			case OrnPackage.ORN_TYPE: return createORNType();
			case OrnPackage.PLACE: return createPlace();
			case OrnPackage.TRANSITION: return createTransition();
			case OrnPackage.PRAGMATICS: return createPragmatics();
			case OrnPackage.PAGE: return createPage();
			case OrnPackage.ARC: return createArc();
			case OrnPackage.INSCRIPTION: return createInscription();
			case OrnPackage.PRAG_STRUCTURE: return createPragStructure();
			case OrnPackage.REF_PLACE: return createRefPlace();
			case OrnPackage.ONTOLOGY: return createOntology();
			case OrnPackage.ONTOLOGY_STRUCTURE: return createOntologyStructure();
			default:
				throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ORNType createORNType() {
		ORNTypeImpl ornType = new ORNTypeImpl();
		return ornType;
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
	public Page createPage() {
		PageImpl page = new PageImpl();
		return page;
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
	public Ontology createOntology() {
		OntologyImpl ontology = new OntologyImpl();
		return ontology;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public OntologyStructure createOntologyStructure() {
		OntologyStructureImpl ontologyStructure = new OntologyStructureImpl();
		return ontologyStructure;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public OrnPackage getOrnPackage() {
		return (OrnPackage)getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static OrnPackage getPackage() {
		return OrnPackage.eINSTANCE;
	}

} //OrnFactoryImpl
