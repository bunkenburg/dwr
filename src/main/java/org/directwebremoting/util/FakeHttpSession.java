package org.directwebremoting.util;

import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import cat.inspiracio.servlet.http.InitialHttpSession;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * For the benefit of anyone that wants to create a fake HttpSession
 * that doesn't do anything other than not be null.
 * @author Joe Walker [joe at getahead dot ltd dot uk]
 */
public class FakeHttpSession extends InitialHttpSession {
    /**
     * Setup the creation time
     */
    public FakeHttpSession()
    {
        creationTime = System.currentTimeMillis();
    }

    /**
     * Setup the creation time
     * @param id The new session id
     */
    public FakeHttpSession(String id) {
        this.id = id;
        creationTime = System.currentTimeMillis();
    }

    @Override public long getCreationTime()
    {
        return creationTime;
    }

    @Override public String getId() {
        if (id == null) {
            log.warn("Inventing data in FakeHttpSession.getId() to remain plausible.");
            id = "fake";
        }
        return id;
    }

    @Override public long getLastAccessedTime()
    {
        return creationTime;
    }

    @Override public void setMaxInactiveInterval(int maxInactiveInterval) {
        this.maxInactiveInterval = maxInactiveInterval;
    }

    @Override public int getMaxInactiveInterval()
    {
        return maxInactiveInterval;
    }

    @Override public Object getAttribute(String name)
    {
        return attributes.get(name);
    }

    @Override public Enumeration<String> getAttributeNames()
    {
        return Collections.enumeration(attributes.keySet());
    }

    @Override public void setAttribute(String name, Object value)
    {
        attributes.put(name, value);
    }

    @Override public void removeAttribute(String name)
    {
        attributes.remove(name);
    }

    /**
     * The session id
     */
    private String id = null;

    /**
     * The list of attributes
     */
    private final Map<String, Object> attributes = new HashMap<String, Object>();

    /**
     * When were we created
     */
    private final long creationTime;

    /**
     * How long before we timeout?
     */
    private int maxInactiveInterval = 30 * 60 * 1000;

    /**
     * The log stream
     */
    private static final Log log = LogFactory.getLog(FakeHttpSession.class);
}
