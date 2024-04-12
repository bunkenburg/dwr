/*
 * Copyright 2002-2005 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.directwebremoting.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.Servlet;
import jakarta.servlet.ServletContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Backing class for Servlet 2.4 fake ServletContext.
 * <p>
 * Note: does not implement interface as we are mapping versions in runtime
 *
 * @author Rod Johnson
 * @author Juergen Hoeller
 * @author Joe Walker [joe at getahead dot ltd dot uk]
 */
class FakeServletContextObject24 {
    /**
     * Create a new FakeServletContext, using no base path and a
     * DefaultResourceLoader (i.e. the classpath root as WAR root).
     */
    public FakeServletContextObject24()
    {
        this("");
    }

    /**
     * Create a new FakeServletContext, using a DefaultResourceLoader.
     * @param resourceBasePath the WAR root directory (should not end with a slash)
     */
    public FakeServletContextObject24(String resourceBasePath) {
        this.resourceBasePath = (resourceBasePath != null ? resourceBasePath : "");

        // Use JVM temp dir as ServletContext temp dir.
        String tempDir = System.getProperty("java.io.tmpdir");
        if (tempDir != null) {
            attributes.put("jakarta.servlet.context.tempdir", new File(tempDir));
        }
    }

    /**
     * Build a full resource location for the given path,
     * prepending the resource base path of this FakeServletContext.
     * @param path the path as specified
     * @return the full resource path
     */
    protected String getResourceLocation(String path) {
        String output = path;
        if (!output.startsWith("/")) {
            output = "/" + output;
        }
        output = resourceBasePath + output;

        return output;
    }

    public ServletContext getContext(String name)
    {
        throw new UnsupportedOperationException("getContext");
    }

    public int getMajorVersion()
    {
        return 2;
    }

    public int getMinorVersion()
    {
        return 4;
    }

    public String getMimeType(String filePath)
    {
        throw new UnsupportedOperationException("getMimeType");
    }

    public Set<String> getResourcePaths(String path)
    {
        throw new UnsupportedOperationException();
    }

    public URL getResource(String path) throws MalformedURLException {throw new UnsupportedOperationException();}

    public InputStream getResourceAsStream(String path) {
        try {
            return new FileInputStream(resourceBasePath + path);
        }
        catch (FileNotFoundException ex) {
            return null;
        }
    }

    public RequestDispatcher getRequestDispatcher(String path) {
        if (!path.startsWith("/")) {
            throw new IllegalArgumentException("RequestDispatcher path at ServletContext level must start with '/'");
        }
        return new FakeRequestDispatcher(path);
    }

    public RequestDispatcher getNamedDispatcher(String path) {
        throw new UnsupportedOperationException("getNamedDispatcher");
    }

    @Deprecated public Servlet getServlet(String name)
    {
        throw new UnsupportedOperationException("getServlet");
    }

    @Deprecated public Enumeration<Servlet> getServlets()
    {
        throw new UnsupportedOperationException("getServlets");
    }

    @Deprecated public Enumeration<String> getServletNames() {
        throw new UnsupportedOperationException("getServletNames");
    }

    public void log(String message)
    {
        log.info(message);
    }

    @Deprecated public void log(Exception ex, String message)
    {
        log.warn(message, ex);
    }

    public void log(String message, Throwable ex)
    {
        log.warn(message, ex);
    }

    public String getRealPath(String path)
    {
        throw new UnsupportedOperationException();
    }

    public String getServerInfo()
    {
        return "FakeServletContext";
    }

    public String getInitParameter(String name)
    {
        return initParameters.get(name);
    }

    /**
     * Add an init parameter to the list that we hand out
     * @param name The name of the new init parameter
     * @param value The value of the new init parameter
     */
    public void addInitParameter(String name, String value)
    {
        initParameters.put(name, value);
    }

    public Enumeration<String> getInitParameterNames()
    {
        return Collections.enumeration(initParameters.keySet());
    }

    public Object getAttribute(String name)
    {
        return attributes.get(name);
    }

    public Enumeration<String> getAttributeNames()
    {
        return Collections.enumeration(attributes.keySet());
    }

    public void setAttribute(String name, Object value) {
        if (value != null) {
            attributes.put(name, value);
        } else {
            attributes.remove(name);
        }
    }

    public void removeAttribute(String name)
    {
        attributes.remove(name);
    }

    /**
     * Accessor for the servlet context name. Normally read-only, but read
     * write in this fake context
     * @param servletContextName The new servlet context name
     */
    public void setServletContextName(String servletContextName)
    {
        this.servletContextName = servletContextName;
    }

    public String getServletContextName()
    {
        return servletContextName;
    }

    /**
     * See Servlet API version 2.5
     * @return The context path for this servlet
     */
    public String getContextPath()
    {
        throw new UnsupportedOperationException("getContextPath");
    }

    /**
     * The log stream
     */
    private static final Log log = LogFactory.getLog(FakeServletContextObject24.class);

    /**
     * The resource path to allow us to fetch resources from the disk
     */
    private final String resourceBasePath;

    /**
     * The init parameters to this servlet
     */
    private final Map<String, String> initParameters = new HashMap<String, String>();

    /**
     * The servlet level attributes
     */
    private final Map<String, Object> attributes = new HashMap<String, Object>();

    /**
     * The servlet context name
     */
    private String servletContextName = "FakeServletContext";
}
