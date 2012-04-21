/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package nppnnets.util;

import java.util.Map;

import nppnnets.NppnnetsPackage;

import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.resource.Resource;

import org.eclipse.emf.ecore.xmi.util.XMLProcessor;

/**
 * This class contains helper methods to serialize and deserialize XML documents
 * <!-- begin-user-doc -->
 * <!-- end-user-doc -->
 * @generated
 */
public class NppnnetsXMLProcessor extends XMLProcessor {

	/**
	 * Public constructor to instantiate the helper.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NppnnetsXMLProcessor() {
		super((EPackage.Registry.INSTANCE));
		NppnnetsPackage.eINSTANCE.eClass();
	}
	
	/**
	 * Register for "*" and "xml" file extensions the NppnnetsResourceFactoryImpl factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected Map<String, Resource.Factory> getRegistrations() {
		if (registrations == null) {
			super.getRegistrations();
			registrations.put(XML_EXTENSION, new NppnnetsResourceFactoryImpl());
			registrations.put(STAR_EXTENSION, new NppnnetsResourceFactoryImpl());
		}
		return registrations;
	}

} //NppnnetsXMLProcessor
