/*
 * Copyright 2002-2006 the original author or authors.
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

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.TimeZone;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Mock implementation of the HttpServletResponse interface.
 * @author Rod Johnson
 * @author Juergen Hoeller
 * @author Joe Walker [joe at getahead dot ltd dot uk]
 */
@SuppressWarnings({"MethodNamesDifferingOnlyByCase"})
public class FakeHttpServletResponse implements HttpServletResponse {

    public void setCharacterEncoding(String characterEncoding)
    {
        this.characterEncoding = characterEncoding;
    }

    public String getCharacterEncoding()
    {
        return characterEncoding;
    }

    public ServletOutputStream getOutputStream() {return outputStream;}

    public PrintWriter getWriter() throws UnsupportedEncodingException {
        if (writer == null) {
            Writer targetWriter = (characterEncoding != null ? new OutputStreamWriter(content, characterEncoding) : new OutputStreamWriter(content));
            writer = new PrintWriter(targetWriter);
        }
        return writer;
    }

    public void flushBuffer() {
        if (writer != null) {
            writer.flush();
        }

        if (outputStream != null) {
            try {
                outputStream.flush();
            } catch (IOException ex) {
                throw new IllegalStateException("Could not flush OutputStream: " + ex.getMessage());
            }
        }

        committed = true;
    }

    public void sendError(int newStatus, String newErrorMessage) throws IOException {
        if (committed) {
            throw new IllegalStateException("Cannot set error status - response is already committed");
        }

        status = newStatus;
        errorMessage = newErrorMessage;
        committed = true;
    }

    public void sendError(int newStatus) throws IOException {
        if (committed) {
            throw new IllegalStateException("Cannot set error status - response is already committed");
        }

        status = newStatus;
        committed = true;
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

    public void sendRedirect(String url) throws IOException {
        if (committed) {
            throw new IllegalStateException("Cannot send redirect - response is already committed");
        }

        redirectedUrl = url;
        committed = true;
    }

    /**
     * Accessor for the redirect URL set using {@link #sendRedirect(String)}
     * @return The redirect URL
     */
    public String getRedirectedUrl()
    {
        return redirectedUrl;
    }

    public void setStatus(int status)
    {
        this.status = status;
    }

    @Deprecated public void setStatus(int status, String errorMessage) {
        this.status = status;
        this.errorMessage = errorMessage;
    }

    /**
     * What HTTP status code should be returned?
     * @return The current http status code
     */
    public int getStatus()
    {
        return status;
    }

    /**
     * Accessor for the content of output body
     * @return A byte array of the output body
     */
    public byte[] getContentAsByteArray() {
        flushBuffer();
        return content.toByteArray();
    }

    /**
     * Accessor for the content of output body
     * @return A string of the output body
     * @throws UnsupportedEncodingException If the internal characterEncoding is incorrect
     */
    public String getContentAsString() throws UnsupportedEncodingException {
        flushBuffer();
        return (characterEncoding != null) ? content.toString(characterEncoding) : content.toString();
    }

    public void setContentLength(int contentLength) {this.contentLength = contentLength;}

    @Override public void setContentLengthLong(long l) {this.contentLength = l;}

    /**
     * Accessor for the content length of the output
     * @return The content length of the output
     */
    public int getContentLength() {return (int) contentLength;}

    public void setContentType(String contentType) {
        this.contentType = contentType;
        if (contentType != null) {
            int charsetIndex = contentType.toLowerCase().indexOf(CHARSET_PREFIX);

            if (charsetIndex != -1) {
                String encoding = contentType.substring(charsetIndex + CHARSET_PREFIX.length());
                setCharacterEncoding(encoding);
            }
        }
    }

    public String getContentType()
    {
        return contentType;
    }

    public void setBufferSize(int bufferSize)
    {
        this.bufferSize = bufferSize;
    }

    public int getBufferSize()
    {
        return bufferSize;
    }

    /**
     * @param committed Set the committed flag
     */
    public void setCommitted(boolean committed)
    {
        this.committed = committed;
    }

    public boolean isCommitted()
    {
        return committed;
    }

    public void resetBuffer() {
        if (committed) {
            throw new IllegalStateException("Cannot reset buffer - response is already committed");
        }

        content.reset();
    }

    public void reset() {
        resetBuffer();

        characterEncoding = null;
        contentLength = 0;
        contentType = null;
        locale = null;
        cookies.clear();
        headers.clear();
        status = SC_OK;
        errorMessage = null;
    }

    public void setLocale(Locale locale)
    {
        this.locale = locale;
    }

    public Locale getLocale()
    {
        return locale;
    }

    public void addCookie(Cookie cookie)
    {
        cookies.add(cookie);
    }

    /**
     * Accessor for the array of current cookies
     * @return The current set of output cookies
     */
    public Cookie[] getCookies()
    {
        return cookies.toArray(new Cookie[cookies.size()]);
    }

    /**
     * Get a cookie by a given name
     * @param name The name of the cookie to fetch
     * @return A matching cookie or null if there was no match
     */
    public Cookie getCookie(String name) {
        for (Cookie cookie : cookies) {
            if (name.equals(cookie.getName())) {
                return cookie;
            }
        }

        return null;
    }

    @Deprecated public String encodeUrl(String url)
    {
        return url;
    }

    public String encodeURL(String url)
    {
        return url;
    }

    @Deprecated public String encodeRedirectUrl(String url)
    {
        return url;
    }

    public String encodeRedirectURL(String url)
    {
        return url;
    }

    public void addHeader(String name, String value)
    {
        doAddHeader(name, value);
    }

    public void setHeader(String name, String value)
    {
        doSetHeader(name, value);
    }

    public void addDateHeader(String name, long value)
    {
        doAddHeader(name, Long.toString(value));
    }

    public void setDateHeader(String name, long value) {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z", Locale.US);
        dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
        doSetHeader(name, dateFormat.format(calendar.getTime()));
    }

    public void addIntHeader(String name, int value)
    {
        doAddHeader(name, Integer.toString(value));
    }

    public void setIntHeader(String name, int value)
    {
        doSetHeader(name, Integer.toString(value));
    }

    /**
     * Internal method to remove all previous values and replace with new
     * @param name The header name
     * @param value The replacement value
     */
    private void doSetHeader(String name, String value) {
        List<String> values = new ArrayList<String>();
        values.add(value);
        headers.put(name, values);
    }

    /**
     * Internal method to add a value to those under a name
     * @param name The header name
     * @param value The extra value
     */
    private void doAddHeader(String name, String value) {
        List<String> values = headers.get(name);
        if (values == null) {
            values = new ArrayList<String>();
            headers.put(name, values);
        }
        values.add(value);
    }

    public boolean containsHeader(String name)
    {
        return headers.containsKey(name);
    }

    /**
     * Accessor for the current set of headers
     * @return The current set of headers
     */
    public Set<String> getHeaderNames()
    {
        return headers.keySet();
    }

    /**
     * Accessor for a header by a given name
     * @param name The header name to lookup
     * @return The data behind this header
     */
    public String getHeader(String name) {
        String value = null;
        if (headers.get(name) != null) {
            value = headers.get(name).get(0);
        }
        return value;
    }

    /**
     * If there are multiple values for a given header, get them as a list
     * @param name The header name to lookup
     * @return The data behind this header
     */
    @SuppressWarnings("unchecked")
    public List<String> getHeaders(String name) {
        List<String> value = headers.get(name);
        if (value != null) {
            return value;
        } else {
            return Collections.EMPTY_LIST;
        }
    }

    //---------------------------------------------------------------------
    // Methods for FakeRequestDispatcher
    //---------------------------------------------------------------------

    /**
     * What URL are we forwarding to?
     * @param forwardedUrl What URL are we forwarding to?
     */
    public void setForwardedUrl(String forwardedUrl)
    {
        this.forwardedUrl = forwardedUrl;
    }

    /**
     * What URL are we forwarding to?
     * @return What URL are we forwarding to?
     */
    public String getForwardedUrl()
    {
        return forwardedUrl;
    }

    /**
     * What URL are we including?
     * @param includedUrl What URL are we including?
     */
    public void setIncludedUrl(String includedUrl)
    {
        this.includedUrl = includedUrl;
    }

    /**
     * What URL are we including?
     * @return What URL are we including?
     */
    public String getIncludedUrl()
    {
        return includedUrl;
    }

    private static final String CHARSET_PREFIX = "charset=";

    private String characterEncoding = "ISO-8859-1";

    private final ByteArrayOutputStream content = new ByteArrayOutputStream();

    private final DelegatingServletOutputStream outputStream = new DelegatingServletOutputStream(this.content);

    private PrintWriter writer = null;

    private long contentLength = 0;

    private String contentType = null;

    private int bufferSize = 4096;

    private boolean committed = false;

    private Locale locale = Locale.getDefault();

    private final List<Cookie> cookies = new ArrayList<Cookie>();

    private final Map<String, List<String>> headers = new HashMap<String, List<String>>();

    private int status = SC_OK;

    private String errorMessage = null;

    private String redirectedUrl = null;

    private String forwardedUrl = null;

    private String includedUrl = null;
}
