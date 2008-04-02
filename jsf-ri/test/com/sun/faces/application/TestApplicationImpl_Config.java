/*
 * $Id: TestApplicationImpl_Config.java,v 1.3 2003/05/01 18:04:07 eburns Exp $
 */

/*
 * Copyright 2003 Sun Microsystems, Inc. All rights reserved.
 * SUN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

// TestApplicationImpl_Config.java

package com.sun.faces.application;

import com.sun.faces.application.ApplicationFactoryImpl;
import com.sun.faces.application.ApplicationImpl;
import com.sun.faces.application.NavigationHandlerImpl;
import com.sun.faces.el.PropertyResolverImpl;
import com.sun.faces.el.VariableResolverImpl;

import javax.faces.application.Application;
import javax.faces.application.ApplicationFactory;
import javax.faces.application.NavigationHandler;
import javax.faces.el.PropertyResolver;
import javax.faces.el.ReferenceSyntaxException;
import javax.faces.el.VariableResolver;
import javax.faces.event.ActionListener;
import javax.faces.event.PhaseId;
import javax.faces.event.ActionEvent;
import javax.faces.FactoryFinder;
import javax.faces.component.*;
import javax.faces.convert.Converter;
import com.sun.faces.convert.*;

import org.mozilla.util.Assert;
import com.sun.faces.ServletFacesTestCase;
import com.sun.faces.TestComponent;
import com.sun.faces.TestConverter;
import javax.faces.FacesException;

import java.util.HashMap;
import java.util.Iterator;

/**
 *
 *  <B>TestApplicationImpl_Config</B> is a class ...
 *
 * <B>Lifetime And Scope</B> <P>
 *
 * @version $Id: TestApplicationImpl_Config.java,v 1.3 2003/05/01 18:04:07 eburns Exp $
 * 
 * @see	Blah
 * @see	Bloo
 *
 */

public class TestApplicationImpl_Config extends ServletFacesTestCase {
//
// Protected Constants
//
//
// Class Variables
//

//
// Instance Variables
//
    private ApplicationImpl application = null;

// Attribute Instance Variables

// Relationship Instance Variables

//
// Constructors and Initializers    
//

    public TestApplicationImpl_Config() {super("TestApplicationImpl_Config");}
    public TestApplicationImpl_Config(String name) {super(name);}
//
// Class methods
//

//
// General Methods
//

    public void setUp() {
	super.setUp();
        ApplicationFactory aFactory = 
	    (ApplicationFactory)FactoryFinder.getFactory(FactoryFinder.APPLICATION_FACTORY);
        application = (ApplicationImpl) aFactory.getApplication();
    }
	

    //
    // Test Config related methods
    //

    public void testComponentPositive() {
	TestComponent 
	    newTestComponent = null,
	    testComponent = new TestComponent();
	UIComponent uic = null;
	
	// runtime addition
	
	application.addComponent(testComponent.getComponentType(),
				 "com.sun.faces.TestComponent");
	assertTrue(null != (newTestComponent = (TestComponent)
			    application.getComponent(testComponent.getComponentType())));
	assertTrue(newTestComponent != testComponent);

	// built-in components
	assertTrue(null != (uic = application.getComponent("Command")));
	assertTrue(uic instanceof UICommand);
	
	assertTrue(null != (uic = application.getComponent("Form")));
	assertTrue(uic instanceof UIForm);
	
	assertTrue(null != (uic = application.getComponent("Graphic")));
	assertTrue(uic instanceof UIGraphic);
	
	assertTrue(null != (uic = application.getComponent("Input")));
	assertTrue(uic instanceof UIInput);
	
	assertTrue(null != (uic = application.getComponent("NamingContainer")));
	assertTrue(uic instanceof UINamingContainer);
	
	assertTrue(null != (uic = application.getComponent("Output")));
	assertTrue(uic instanceof UIOutput);
	
	assertTrue(null != (uic = application.getComponent("Panel")));
	assertTrue(uic instanceof UIPanel);
	
	assertTrue(null != (uic = application.getComponent("Parameter")));
	assertTrue(uic instanceof UIParameter);
	

	assertTrue(null != (uic = application.getComponent("SelectBoolean")));
	assertTrue(uic instanceof UISelectBoolean);
	
	assertTrue(null != (uic = application.getComponent("SelectItem")));
	assertTrue(uic instanceof UISelectItem);
	
	assertTrue(null != (uic = application.getComponent("SelectItems")));
	assertTrue(uic instanceof UISelectItems);
	
	assertTrue(null != (uic = application.getComponent("SelectMany")));
	assertTrue(uic instanceof UISelectMany);
	
	assertTrue(null != (uic = application.getComponent("SelectOne")));
	assertTrue(uic instanceof UISelectOne);
	
    }
	
    public void testComponentNegative() {
	boolean exceptionThrown = false;
	
	// componentType/componentClass with non-existent class
	try {
	    application.addComponent("William",
				     "BillyBoy");
	    application.getComponent("William");
	}
	catch (FacesException e) {
	    exceptionThrown = true;
	}
	assertTrue(exceptionThrown);

	// non-existent mapping
	exceptionThrown = false;
	try {
	    application.getComponent("Joebob");
	}
	catch (FacesException e) {
	    exceptionThrown = true;
	}
	assertTrue(exceptionThrown);
	
    }

    public void testGetComponentTypes() {
	Iterator iter = application.getComponentTypes();
	assertTrue(null != iter);
	String standardComponentTypes[] = {
	    "Command",
	    "Form",
	    "Graphic",
	    "Input",
	    "NamingContainer",
	    "Output",
	    "Panel",
	    "Parameter",
	    "SelectBoolean",
	    "SelectItem",
	    "SelectItems",
	    "SelectMany",
	    "SelectOne"
	};

	while (iter.hasNext()) {
	    assertTrue(isMember((String) iter.next(), 
					 standardComponentTypes));
	}
    }

    public void testConverterPositive() {
	TestConverter 
	    newTestConverter = null,
	    testConverter = new TestConverter();
	Converter conv = null;
	
	// runtime addition
	
	application.addConverter(testConverter.getConverterId(),
				 "com.sun.faces.TestConverter");
	assertTrue(null != (newTestConverter = (TestConverter)
			    application.getConverter(testConverter.getConverterId())));
	assertTrue(newTestConverter != testConverter);

	// built-in components
	assertTrue(null != (conv = application.getConverter("Date")));
	assertTrue(conv instanceof DateConverter);

	assertTrue(null != (conv = application.getConverter("DateFormat")));
	assertTrue(conv instanceof DateConverter);

	assertTrue(null != (conv = application.getConverter("DateTime")));
	assertTrue(conv instanceof DateTimeConverter);

	assertTrue(null != (conv = application.getConverter("Number")));
	assertTrue(conv instanceof NumberConverter);

	assertTrue(null != (conv = application.getConverter("NumberFormat")));
	assertTrue(conv instanceof NumberConverter);

	assertTrue(null != (conv = application.getConverter("Time")));
	assertTrue(conv instanceof TimeConverter);

	assertTrue(null != (conv = application.getConverter("Boolean")));
	assertTrue(conv instanceof BooleanConverter);
	
    }
	
    public void testConverterNegative() {
	boolean exceptionThrown = false;
	
	// componentType/componentClass with non-existent class
	try {
	    application.addConverter("William",
				     "BillyBoy");
	    application.getConverter("William");
	}
	catch (FacesException e) {
	    exceptionThrown = true;
	}
	assertTrue(exceptionThrown);

	// non-existent mapping
	exceptionThrown = false;
	try {
	    application.getConverter("Joebob");
	}
	catch (FacesException e) {
	    exceptionThrown = true;
	}
	assertTrue(exceptionThrown);
	
    }

    public void testGetConverterIds() {
	Iterator iter = application.getConverterIds();
	assertTrue(null != iter);
	String standardConverterIds[] = {
	    "Date",
	    "DateFormat",
	    "DateTime",
	    "Number",
	    "NumberFormat",
	    "Time",
	    "Boolean"
	};

	while (iter.hasNext()) {
	    assertTrue(isMember((String) iter.next(), 
					 standardConverterIds));
	}
    }

} // end of class TestApplicationImpl_Config
