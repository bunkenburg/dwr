package org.directwebremoting.util;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;

import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;

/**
 * Mock implementation of the RequestDispatcher interface.
 * @author Rod Johnson
 * @author Juergen Hoeller
 * @author Joe Walker [joe at getahead dot ltd dot uk]
 */
public class FakeRequestDispatcher implements RequestDispatcher {
    /**
     * Create a new FakeRequestDispatcher for the given URL.
     * @param url the URL to dispatch to.
     */
    public FakeRequestDispatcher(String url)
    {
        this.url = url;
    }

    public void forward(ServletRequest request, ServletResponse response) {
        if (response.isCommitted()) {
            throw new IllegalStateException("Cannot perform forward - response is already committed");
        }

        if (!(response instanceof FakeHttpServletResponse)) {
            throw new IllegalArgumentException("FakeRequestDispatcher requires FakeHttpServletResponse");
        }

        ((FakeHttpServletResponse) response).setForwardedUrl(url);

        if (log.isDebugEnabled()) {
            log.debug("FakeRequestDispatcher: forwarding to URL [" + url + "]");
        }
    }

    public void include(ServletRequest request, ServletResponse response) {
        if (!(response instanceof FakeHttpServletResponse)) {
            throw new IllegalArgumentException("FakeRequestDispatcher requires FakeHttpServletResponse");
        }

        ((FakeHttpServletResponse) response).setIncludedUrl(url);

        if (log.isDebugEnabled()) {
            log.debug("FakeRequestDispatcher: including URL [" + url + "]");
        }
    }

    private final String url;

    /**
     * The log stream
     */
    private static final Log log = LogFactory.getLog(FakeRequestDispatcher.class);
}
