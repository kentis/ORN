/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package inhibitornets.util;

import inhibitornets.InhibitornetsPackage;

import java.util.Map;

import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.resource.Resource;

import org.eclipse.emf.ecore.xmi.util.XMLProcessor;

/**
 * This class contains helper methods to serialize and deserialize XML documents
 * <!-- begin-user-doc -->
 * <!-- end-user-doc -->
 * @generated
 */
public class InhibitornetsXMLProcessor extends XMLProcessor {

	/**
	 * Public constructor to instantiate the helper.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public InhibitornetsXMLProcessor() {
		super((EPackage.Registry.INSTANCE));
		InhibitornetsPackage.eINSTANCE.eClass();
	}
	
	/**
	 * Register for "*" and "xml" file extensions the InhibitornetsResourceFactoryImpl factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected Map<String, Resource.Factory> getRegistrations() {
		if (registrations == null) {
			super.getRegistrations();
			registrations.put(XML_EXTENSION, new InhibitornetsResourceFactoryImpl());
			registrations.put(STAR_EXTENSION, new InhibitornetsResourceFactoryImpl());
		}
		return registrations;
	}

} //InhibitornetsXMLProcessor
