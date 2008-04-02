/*
 * $Id: RenderKitImpl.java,v 1.14 2003/12/17 15:13:48 rkitain Exp $
 */

/*
 * Copyright 2003 Sun Microsystems, Inc. All rights reserved.
 * SUN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

// RenderKitImpl.java

package com.sun.faces.renderkit;

import com.sun.faces.renderkit.html_basic.HtmlResponseWriter;
import com.sun.faces.util.Util;

import org.xml.sax.InputSource;

import com.sun.faces.util.Util;




import org.xml.sax.Attributes;

import java.io.IOException;
import java.io.Writer;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Stack;
import java.util.Set;
import java.util.NoSuchElementException;

import javax.faces.FacesException;
import javax.faces.component.UIComponent;
import javax.faces.context.ResponseWriter;
import javax.faces.context.ResponseStream;
import javax.faces.render.RenderKit;
import javax.faces.render.Renderer;
import javax.faces.render.ResponseStateManager;

/**
 *
 *  <B>RenderKitImpl</B> is a class ...
 *
 * <B>Lifetime And Scope</B> <P>
 *
 * @version $Id: RenderKitImpl.java,v 1.14 2003/12/17 15:13:48 rkitain Exp $
 * 
 * @see	Blah
 * @see	Bloo
 *
 */

public class RenderKitImpl extends RenderKit {

//
// Protected Constants
//

//
// Class Variables
//

//
// Instance Variables
//
    // used for ResponseWriter creation;
    private static String HTML_CONTENT_TYPE = "text/html"; 
    private static String CHAR_ENCODING = "ISO-8859-1";
//
// Ivars used during actual client lifetime
//

// Relationship Instance Variables

    /**

    * Keys are String rendererType, values are HtmlBasicRenderer instances

    */

    private HashMap renderersByRendererType;
    private ResponseStateManager responseStateManager = null;
//
// Constructors and Initializers    
//

    public RenderKitImpl() {
        super();
        renderersByRendererType = new HashMap();
    }


    //
    // Class methods
    //

    //
    // General Methods
    //

    //
    // Methods From RenderKit
    //

    public void addRenderer(String rendererType, Renderer renderer) {
        if (rendererType == null || renderer == null) {
            throw new NullPointerException(Util.getExceptionMessage(Util.NULL_PARAMETERS_ERROR_MESSAGE_ID));
        }

        synchronized(renderersByRendererType) {
	    renderersByRendererType.put(rendererType, renderer);
        }
    }

    public Renderer getRenderer(String rendererType) {

        if (rendererType == null) {
            throw new NullPointerException(Util.getExceptionMessage(Util.NULL_PARAMETERS_ERROR_MESSAGE_ID));
        }

        Util.doAssert(renderersByRendererType != null);

        Renderer renderer = null;
        synchronized(renderersByRendererType) {
            renderer = (Renderer)renderersByRendererType.get(rendererType);
        }

        return renderer;
    }

    public synchronized ResponseStateManager getResponseStateManager() {
        if (responseStateManager == null) {
            responseStateManager = new ResponseStateManagerImpl();
        }
        return responseStateManager;
    }

    public ResponseWriter createResponseWriter(Writer writer, String contentTypeList, 
        String characterEncoding) {
        if (writer == null) {
	    return null;
	}
	// Set the default content type to html;  However, if a content type list
	// argument was specified, make sure it contains an html content type;
	// PENDING(rogerk) ideally, we want to analyze the content type string
	// in more detail, to determine the preferred content type - as outlined in 
	// http://www.ietf.org/rfc/rfc2616.txt?number=2616 - Section 14.1
	// (since this is not an html renderkit);
	//
        String contentType = HTML_CONTENT_TYPE;
	if (contentTypeList != null) {
	    if (contentTypeList.indexOf(contentType) < 0) {
	        throw new IllegalArgumentException(Util.getExceptionMessage(
		    Util.CONTENT_TYPE_ERROR_MESSAGE_ID));
	    }
	}
	if (characterEncoding == null) {
	    characterEncoding = CHAR_ENCODING;
	}
		
        return new HtmlResponseWriter(writer, contentType, characterEncoding);
    }

    
    public ResponseStream createResponseStream(OutputStream out) {
        final OutputStream output = out;
        return new ResponseStream() {
            public void write(int b) throws IOException {
                output.write(b);
            }
            public void write(byte b[]) throws IOException {
                output.write(b);
            }

            public void write(byte b[], int off, int len) throws IOException {
                output.write(b, off, len);
            }

            public void flush() throws IOException {
                output.flush();
            }

            public void close() throws IOException {
                output.close();
            }
        };
    }       
    // The test for this class is in TestRenderKit.java

} // end of class RenderKitImpl

