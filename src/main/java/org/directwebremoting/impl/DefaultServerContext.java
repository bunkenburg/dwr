package org.directwebremoting.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.directwebremoting.Container;
import org.directwebremoting.ScriptSession;
import org.directwebremoting.ScriptSessionFilter;
import org.directwebremoting.ServerContext;
import org.directwebremoting.extend.ConverterManager;
import org.directwebremoting.extend.PageScriptSessionFilter;
import org.directwebremoting.extend.ScriptSessionManager;
import org.directwebremoting.servlet.UrlProcessor;
import org.directwebremoting.util.VersionUtil;

/**
 * The Default implementation of ServerContext
 * @author Joe Walker [joe at getahead dot ltd dot uk]
 */
public class DefaultServerContext implements ServerContext
{
    @Deprecated public Collection<ScriptSession> getAllScriptSessions() {
        return getScriptSessionManager().getAllScriptSessions();
    }

    @Deprecated  public Collection<ScriptSession> getScriptSessionsByPage(String url) {
        List<ScriptSession> matching = new ArrayList<ScriptSession>();
        Collection<ScriptSession> allScriptSessions = getScriptSessionManager().getAllScriptSessions();
        ScriptSessionFilter filter = new PageScriptSessionFilter(this, url);

        for (ScriptSession session : allScriptSessions)
        {
            if (filter.match(session))
            {
                matching.add(session);
            }
        }

        return matching;
    }


    @Deprecated public ScriptSession getScriptSessionById(String sessionId) {
        // ScriptSessionManager().getScriptSession() can take a page and
        // httpSessionId so it can associate them, but we will have already done
        // that if we can as a result of work done creating a WebContext. For
        // use in a ServerContext we won't know, so we can pass in null
        return getScriptSessionManager().getScriptSessionById(sessionId);
    }

    /**
     * Injection point for Container
     * @param container ...
     */
    public void setContainer(Container container)
    {
        this.container = container;
    }

    public Container getContainer()
    {
        return container;
    }

    /**
     * Injection point for ServletConfig
     * @param servletConfig ...
     */
    public void setServletConfig(ServletConfig servletConfig)
    {
        this.servletConfig = servletConfig;
    }

    public ServletConfig getServletConfig()
    {
        return servletConfig;
    }

    /**
     * Injection point for ServletContext
     * @param servletContext ...
     */
    public void setServletContext(ServletContext servletContext)
    {
        this.servletContext = servletContext;
    }

    public ServletContext getServletContext()
    {
        return servletContext;
    }

    public String getVersion()
    {
        return VersionUtil.getLabel();
    }

    public String getContextPath()
    {
        UrlProcessor urlProcessor = container.getBean(UrlProcessor.class);
        return urlProcessor.getContextPath();
    }

    /**
     * Internal helper for getting at a ScriptSessionManager
     * @return Our ScriptSessionManager
     */
    protected ScriptSessionManager getScriptSessionManager()
    {
        if (sessionManager == null)
        {
            sessionManager = container.getBean(ScriptSessionManager.class);
        }

        return sessionManager;
    }

    /**
     * Internal helper for getting at a ConverterManager
     * @return Our ConverterManager
     */
    protected ConverterManager getConverterManager()
    {
        if (converterManager == null)
        {
            converterManager = container.getBean(ConverterManager.class);
        }

        return converterManager;
    }

    @Override
    public String toString()
    {
        return "DefaultServerContext[path=" + servletConfig.getServletName() + ", container=" + container.getClass().getSimpleName() + "]";
    }

    @Override
    public boolean equals(Object obj)
    {
        log.warn("Comparing ServerContexts is dangerous because DefaultWebContext inherits from DefaultServerContext. It is probably better to compare ServletContexts instead.");
        return super.equals(obj);
    }

    /**
     * The ServletConfig associated with the current request
     */
    private ServletConfig servletConfig = null;

    /**
     * The ServletContext associated with the current request
     */
    private ServletContext servletContext = null;

    /**
     * The IOC container implementation
     */
    private Container container = null;

    /**
     * The session manager for sessions keyed off script ids
     */
    private ScriptSessionManager sessionManager = null;

    /**
     * How we convert to Javascript objects
     */
    private ConverterManager converterManager = null;

    /**
     * The log stream
     */
    private static final Log log = LogFactory.getLog(DefaultServerContext.class);
}
