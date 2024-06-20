package org.directwebremoting.util;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.Collection;
import java.util.Locale;

import cat.inspiracio.servlet.http.InitialHttpServletResponse;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpServletResponseWrapper;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Used by ExecutionContext to forward results back via javascript.
 * @author Joe Walker [joe at getahead dot ltd dot uk]
 */
public final class SwallowingHttpServletResponse extends InitialHttpServletResponse {

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

    @Override public void addCookie(Cookie cookie) {}

    @Override public void addDateHeader(String name, long value) {}

    @Override public void addHeader(String name, String value) {}

    @Override public void addIntHeader(String name, int value) {}

    @Override public boolean containsHeader(String name) {return false;}

    @Override public void flushBuffer() throws IOException {pout.flush();}

    @Override public int getBufferSize()
    {
        return bufferSize;
    }

    @Override public String getCharacterEncoding()
    {
        return characterEncoding;
    }

    /**
     * @return The MIME type of the content
     * @see jakarta.servlet.ServletResponse#setContentType(String)
     */
    @Override public String getContentType()
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

    @Override public Locale getLocale()
    {
        return locale;
    }

    @Override public ServletOutputStream getOutputStream()
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
    @Override public int getStatus()
    {
        return status;
    }

    @Override public PrintWriter getWriter()
    {
        return pout;
    }

    @Override public boolean isCommitted() {return false;}

    @Override public void reset() {}

    @Override public void resetBuffer() {}

    @Override public void sendError(int newStatus) {
        if (committed) {
            throw new IllegalStateException("Cannot set error status - response is already committed");
        }
        log.warn("Ignoring call to sendError(" + newStatus + ')');
        status = newStatus;
        committed = true;
    }

    @Override public void sendError(int newStatus, String newErrorMessage) {
        if (committed) {
            throw new IllegalStateException("Cannot set error status - response is already committed");
        }
        log.warn("Ignoring call to sendError(" + newStatus + ", " + newErrorMessage + ')');
        status = newStatus;
        errorMessage = newErrorMessage;
        committed = true;
    }

    @Override public void sendRedirect(String location) {
        if (committed) {
            throw new IllegalStateException("Cannot send redirect - response is already committed");
        }
        log.warn("Ignoring call to sendRedirect(" + location + ')');
        redirectedUrl = location;
        committed = true;
    }

    @Override public void setBufferSize(int bufferSize)
    {
        this.bufferSize = bufferSize;
    }

    /**
     * @param characterEncoding The new encoding to use for response strings
     * @see jakarta.servlet.ServletResponseWrapper#getCharacterEncoding()
     */
    @Override public void setCharacterEncoding(String characterEncoding)
    {
        this.characterEncoding = characterEncoding;
    }

    /** Does nothing.
     * The content length of the original document is not likely to be the
     * same as the content length of the new document.
     * */
    @Override public void setContentLength(int i) {}

    /** Does nothing.
     * The content length of the original document is not likely to be the
     * same as the content length of the new document.
     * */
    @Override public void setContentLengthLong(long l) {}

    @Override public void setContentType(String contentType)
    {
        this.contentType = contentType;
    }

    @Override public void setDateHeader(String name, long value) {}

    @Override public void setHeader(String name, String value) {}

    @Override public void setIntHeader(String name, int value) {}

    @Override public void setLocale(Locale locale)
    {
        this.locale = locale;
    }

    @Override public void setStatus(int status) {
        this.status = status;
        log.warn("Ignoring call to setStatus(" + status + ')');
    }

    @Override public String encodeURL(String paramString) {return null;}

    @Override public String encodeRedirectURL(String paramString)
    {
        return null;
    }

    @Override public String getHeader(String paramString)
    {
        return null;
    }

    @Override public Collection<String> getHeaders(String paramString)
    {
        return null;
    }

    @Override public Collection<String> getHeaderNames()
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
