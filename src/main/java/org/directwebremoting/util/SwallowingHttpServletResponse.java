package org.directwebremoting.util;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.Collection;
import java.util.Locale;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Used by ExecutionContext to forward results back via javascript.
 * @author Joe Walker [joe at getahead dot ltd dot uk]
 */
public final class SwallowingHttpServletResponse implements HttpServletResponse {

    /**
     * Create a new HttpServletResponse that allows you to catch the body
     * @param response The original HttpServletResponse
     * @param sout The place we copy responses to
     * @param characterEncoding The output encoding
     */
    public SwallowingHttpServletResponse(HttpServletResponse response, Writer sout, String characterEncoding) {
        pout = new PrintWriter(sout);
        outputStream = new WriterOutputStream(sout, characterEncoding);

        this.characterEncoding = characterEncoding;
    }

    public void addCookie(Cookie cookie) {}

    public void addDateHeader(String name, long value) {}

    public void addHeader(String name, String value) {}

    public void addIntHeader(String name, int value) {}

    public boolean containsHeader(String name)
    {
        return false;
    }

    public void flushBuffer() throws IOException {pout.flush();}

    public int getBufferSize()
    {
        return bufferSize;
    }

    public String getCharacterEncoding()
    {
        return characterEncoding;
    }

    /**
     * @return The MIME type of the content
     * @see javax.servlet.ServletResponse#setContentType(String)
     */
    public String getContentType()
    {
        return contentType;
    }

    /**
     * Accessor for any error messages set using {@link #sendError(int)} or
     * {@link #sendError(int, String)}
     * @return The current error message
     */
    public String getErrorMessage()
    {
        return errorMessage;
    }

    public Locale getLocale()
    {
        return locale;
    }

    public ServletOutputStream getOutputStream()
    {
        return outputStream;
    }

    /**
     * Accessor for the redirect URL set using {@link #sendRedirect(String)}
     * @return The redirect URL
     */
    public String getRedirectedUrl()
    {
        return redirectedUrl;
    }

    /**
     * What HTTP status code should be returned?
     * @return The current http status code
     */
    public int getStatus()
    {
        return status;
    }

    public PrintWriter getWriter()
    {
        return pout;
    }

    public boolean isCommitted()
    {
        return false;
    }

    public void reset() {}

    public void resetBuffer() {}

    public void sendError(int newStatus) {
        if (committed) {
            throw new IllegalStateException("Cannot set error status - response is already committed");
        }

        log.warn("Ignoring call to sendError(" + newStatus + ')');

        status = newStatus;
        committed = true;
    }

    public void sendError(int newStatus, String newErrorMessage) {
        if (committed) {
            throw new IllegalStateException("Cannot set error status - response is already committed");
        }

        log.warn("Ignoring call to sendError(" + newStatus + ", " + newErrorMessage + ')');

        status = newStatus;
        errorMessage = newErrorMessage;
        committed = true;
    }

    public void sendRedirect(String location) {
        if (committed) {
            throw new IllegalStateException("Cannot send redirect - response is already committed");
        }

        log.warn("Ignoring call to sendRedirect(" + location + ')');

        redirectedUrl = location;
        committed = true;
    }

    public void setBufferSize(int bufferSize)
    {
        this.bufferSize = bufferSize;
    }

    /**
     * @param characterEncoding The new encoding to use for response strings
     * @see javax.servlet.ServletResponseWrapper#getCharacterEncoding()
     */
    public void setCharacterEncoding(String characterEncoding)
    {
        this.characterEncoding = characterEncoding;
    }

    /** Does nothing.
     * The content length of the original document is not likely to be the
     * same as the content length of the new document.
     * */
    public void setContentLength(int i) {}

    /** Does nothing.
     * The content length of the original document is not likely to be the
     * same as the content length of the new document.
     * */
    @Override public void setContentLengthLong(long l) {}

    public void setContentType(String contentType)
    {
        this.contentType = contentType;
    }

    public void setDateHeader(String name, long value) {}

    public void setHeader(String name, String value) {}

    public void setIntHeader(String name, int value) {}

    public void setLocale(Locale locale)
    {
        this.locale = locale;
    }

    public void setStatus(int status) {
        this.status = status;
        log.warn("Ignoring call to setStatus(" + status + ')');
    }

    /**
     * @see javax.servlet.http.HttpServletResponse#setStatus(int, java.lang.String)
     * @deprecated
     */
    @Deprecated public void setStatus(int newStatus, String newErrorMessage) {
        status = newStatus;
        errorMessage = newErrorMessage;
        log.warn("Ignoring call to setStatus(" + newStatus + ", " + newErrorMessage + ')');
    }

    public String encodeURL(String paramString)
    {
        return null;
    }

    public String encodeRedirectURL(String paramString)
    {
        return null;
    }

    @Deprecated public String encodeUrl(String paramString) {return null;}

    @Deprecated public String encodeRedirectUrl(String paramString)
    {
        return null;
    }

    public String getHeader(String paramString)
    {
        return null;
    }

    public Collection<String> getHeaders(String paramString)
    {
        return null;
    }

    public Collection<String> getHeaderNames()
    {
        return null;
    }

    /**
     * The ignored buffer size
     */
    private int bufferSize = 0;

    /**
     * The character encoding used
     */
    private String characterEncoding;

    /**
     * Has the response been committed
     */
    private boolean committed = false;

    /**
     * The MIME type of the output body
     */
    private String contentType = null;

    /**
     * The error message sent with a status != HttpServletResponse.SC_OK
     */
    private String errorMessage = null;

    /**
     * Locale setting: defaults to platform default
     */
    private Locale locale = Locale.getDefault();

    /**
     * The forwarding output stream
     */
    private final ServletOutputStream outputStream;

    /**
     * The forwarding output stream
     */
    private final PrintWriter pout;

    /**
     * Where are we to redirect the user to?
     */
    private String redirectedUrl = null;

    /**
     * The HTTP status
     */
    private int status = HttpServletResponse.SC_OK;

    /**
     * The log stream
     */
    private static final Log log = LogFactory.getLog(SwallowingHttpServletResponse.class);
}
