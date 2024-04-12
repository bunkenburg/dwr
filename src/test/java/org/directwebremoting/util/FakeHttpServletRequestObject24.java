package org.directwebremoting.util;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.security.Principal;
import java.util.Arrays;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import jakarta.servlet.*;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Backing class for Servlet 2.4 fake requests.
 * <p>
 * Note: does not implement interface as we are mapping versions in runtime.
 */
class FakeHttpServletRequestObject24 {

    public String getAuthType()
    {
        return null;
    }

    public Cookie[] getCookies()
    {
        return null;
    }

    public long getDateHeader(String name)
    {
        return -1;
    }

    public String getHeader(String name)
    {
        return null;
    }

    public Enumeration<String> getHeaders(String name) {return Collections.enumeration(Collections.<String>emptySet());}

    public Enumeration<String> getHeaderNames()
    {
        return Collections.enumeration(Collections.<String>emptySet());
    }

    public int getIntHeader(String name)
    {
        return -1;
    }

    public String getMethod()
    {
        return method;
    }

    /**
     * @see #getMethod()
     */
    public void setMethod(String method)
    {
        this.method = method;
    }

    /**
     * @see #getMethod()
     */
    private String method = "GET";

    public String getPathInfo()
    {
        return pathInfo;
    }

    /**
     * @see #getPathInfo()
     */
    public void setPathInfo(String pathInfo)
    {
        this.pathInfo = pathInfo;
    }

    /**
     * @see #getPathInfo()
     */
    private String pathInfo;

    public String getPathTranslated()
    {
        return null;
    }

    public String getContextPath() {
        log.warn("Inventing data in FakeHttpServletRequest.getContextPath() to remain plausible.");
        return "";
    }

    public String getQueryString()
    {
        return null;
    }

    public String getRemoteUser()
    {
        return null;
    }

    public boolean isUserInRole(String role)
    {
        return roles.contains(role);
    }

    /**
     * @see #isUserInRole(String)
     */
    public void addUserRole(String role)
    {
        roles.add(role);
    }

    private final Set<String> roles = new HashSet<String>();

    public Principal getUserPrincipal()
    {
        return null;
    }

    public String getRequestedSessionId()
    {
        return null;
    }

    public String getRequestURI() {
        log.warn("Inventing data in FakeHttpServletRequest.getRequestURI() to remain plausible.");
        return "/";
    }

    public StringBuffer getRequestURL() {
        log.warn("Inventing data in FakeHttpServletRequest.getRequestURL() to remain plausible.");
        return new StringBuffer("http://localhost/");
    }

    public String getServletPath() {
        log.warn("Inventing data in FakeHttpServletRequest.getServletPath() to remain plausible.");
        return "";
    }

    public HttpSession getSession(boolean create) {
        if (!create) {
            return null;
        }

        return new FakeHttpSession();
    }

    public HttpSession getSession()
    {
        return null;
    }

    public boolean isRequestedSessionIdValid()
    {
        return false;
    }

    public boolean isRequestedSessionIdFromCookie()
    {
        return false;
    }

    public boolean isRequestedSessionIdFromURL()
    {
        return false;
    }

    @Deprecated public boolean isRequestedSessionIdFromUrl()
    {
        return false;
    }

    public Object getAttribute(String name)
    {
        return attributes.get(name);
    }

    public Enumeration<String> getAttributeNames()
    {
        return Collections.enumeration(attributes.keySet());
    }

    public String getCharacterEncoding()
    {
        return characterEncoding;
    }

    public void setCharacterEncoding(String characterEncoding) throws UnsupportedEncodingException {this.characterEncoding = characterEncoding;}

    public int getContentLength()
    {
        return content.length;
    }

    public String getContentType()
    {
        return contentType;
    }

    public void setContentType(String contentType)
    {
        this.contentType = contentType;
    }

    private String contentType = "text/plain";

    public ServletInputStream getInputStream() throws IOException {
        return new ServletInputStream() {

            private final ByteArrayInputStream proxy = new ByteArrayInputStream(content);

            @Override public boolean isFinished() {return false;}

            @Override public boolean isReady() {return false;}

            @Override public void setReadListener(ReadListener readListener) {}

            @Override public int read() throws IOException {return proxy.read();}

            @Override public int available() throws IOException {return proxy.available();}

            @Override public synchronized void mark(int readLimit)
            {
                proxy.mark(readLimit);
            }

            @Override public boolean markSupported()
            {
                return proxy.markSupported();
            }

            @Override public int read(byte[] b, int off, int len) throws IOException {return proxy.read(b, off, len);}

            @Override public void close() throws IOException {proxy.close();}

            @Override public int read(byte[] b) throws IOException {return proxy.read(b);}

            @Override public synchronized void reset() throws IOException {proxy.reset();}

            @Override public long skip(long n) throws IOException {return proxy.skip(n);}
        };
    }

    /**
     * @see #getInputStream()
     */
    public void setContent(byte[] content)
    {
        this.content = content;
    }

    /**
     * @see #getInputStream()
     */
    public void setContent(String content)
    {
        this.content = content.getBytes();
    }

    /**
     * @see #getInputStream()
     */
    protected byte[] content;

    /**
     * @return "127.0.0.1"
     */
    public String getLocalAddr() {
        log.warn("Inventing data in FakeHttpServletRequest.getLocalAddr() to remain plausible.");
        return "127.0.0.1";
    }

    /**
     * @return "localhost"
     */
    public String getLocalName() {
        log.warn("Inventing data in FakeHttpServletRequest.getLocalName() to remain plausible.");
        return "localhost";
    }

    /**
     * @return 80
     */
    public int getLocalPort() {
        log.warn("Inventing data in FakeHttpServletRequest.getLocalPort() to remain plausible.");
        return 80;
    }

    public String getParameter(String name)
    {
        return null;
    }

    public Enumeration<String> getParameterNames()
    {
        return Collections.enumeration(Collections.<String>emptySet());
    }

    public String[] getParameterValues(String name)
    {
        return null;
    }

    public Map<String, String[]> getParameterMap()
    {
        return Collections.emptyMap();
    }

    public String getProtocol() {
        log.warn("Inventing data in FakeHttpServletRequest.getProtocol() to remain plausible.");
        return "HTTP/1.1";
    }

    public String getScheme() {
        log.warn("Inventing data in FakeHttpServletRequest.getScheme() to remain plausible.");
        return "http";
    }

    public String getServerName() {
        log.warn("Inventing data in FakeHttpServletRequest.getServerName() to remain plausible.");
        return "localhost";
    }

    public int getServerPort() {
        log.warn("Inventing data in FakeHttpServletRequest.getServerPort() to remain plausible.");
        return 80;
    }

    public BufferedReader getReader() throws IOException {
        return new BufferedReader(new InputStreamReader(getInputStream()));
    }

    public String getRemoteAddr() {
        log.warn("Inventing data in FakeHttpServletRequest.getRemoteAddr() to remain plausible.");
        return "localhost";
    }

    public String getRemoteHost() {
        log.warn("Inventing data in FakeHttpServletRequest.getRemoteHost() to remain plausible.");
        return "localhost";
    }

    /**
     * @return 80
     */
    public int getRemotePort() {
        log.warn("Inventing data in FakeHttpServletRequest.getRemotePort() to remain plausible.");
        return 80;
    }

    public void setAttribute(String name, Object o)
    {
        attributes.put(name, o);
    }

    public void removeAttribute(String name)
    {
        attributes.remove(name);
    }

    public Locale getLocale()
    {
        return Locale.getDefault();
    }

    public Enumeration<Locale> getLocales()
    {
        return Collections.enumeration(Arrays.asList(Locale.getDefault()));
    }

    public boolean isSecure()
    {
        return false;
    }

    public RequestDispatcher getRequestDispatcher(String path) {
        return new RequestDispatcher() {
            public void include(ServletRequest request, ServletResponse response) throws ServletException, IOException {}
            public void forward(ServletRequest request, ServletResponse response) throws ServletException, IOException {}
        };
    }

    @Deprecated public String getRealPath(String path)
    {
        return null;
    }

    private String characterEncoding = null;

    private final Map<String, Object> attributes = new HashMap<String, Object>();

    private static final Log log = LogFactory.getLog(FakeHttpServletRequestObject24.class);
}
