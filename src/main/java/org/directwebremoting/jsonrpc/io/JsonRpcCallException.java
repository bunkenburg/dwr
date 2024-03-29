package org.directwebremoting.jsonrpc.io;

import javax.servlet.http.HttpServletResponse;

import org.directwebremoting.jsonrpc.JsonRpcConstants;

/**
 * Thrown when a JSON-RPC request is not valid
 * @author Joe Walker [joe at getahead dot ltd dot uk]
 */
public class JsonRpcCallException extends RuntimeException
{
    /**
     * Constructor will all possible fields setup
     * @param id ...
     * @param version ...
     * @param reason ...
     * @param jsonRpcErrorCode ...
     * @param httpStatusCode ...
     * @param data ...
     */
    public JsonRpcCallException(String id, String version, String reason, int jsonRpcErrorCode, int httpStatusCode, String data)
    {
        super(reason);
        this.jsonRpcErrorCode = jsonRpcErrorCode;
        this.httpStatusCode = httpStatusCode;
        this.id = id;
        this.version = version;
        this.data = data;
    }

    /**
     * All exceptions need these values to aid response generation
     * @param id ...
     * @param version ...
     * @param reason ...
     * @param jsonRpcErrorCode ...
     * @param httpStatusCode ...
     */
    public JsonRpcCallException(String id, String version, String reason, int jsonRpcErrorCode, int httpStatusCode)
    {
        super(reason);
        this.jsonRpcErrorCode = jsonRpcErrorCode;
        this.httpStatusCode = httpStatusCode;
        this.id = id;
        this.version = version;
        this.data = null;
    }

    /**
     * Setup using values from JsonRpcCalls
     * @param calls ...
     * @param reason ...
     * @param jsonRpcErrorCode ...
     * @param httpStatusCode ...
     */
    public JsonRpcCallException(JsonRpcCalls calls, String reason, int jsonRpcErrorCode, int httpStatusCode)
    {
        super(reason);
        this.jsonRpcErrorCode = jsonRpcErrorCode;
        this.httpStatusCode = httpStatusCode;
        this.id = calls.getBatchId();
        this.version = calls.getVersion();
        this.data = null;
    }

    /**
     * @return the JSON-RPC error code that we should send in the error body.
     * Should be one of the constants from {@link JsonRpcConstants}, like
     * {@link JsonRpcConstants#ERROR_CODE_NO_METHOD}.
     */
    public int getJsonRpcErrorCode()
    {
        return jsonRpcErrorCode;
    }

    /**
     * @return the HTTP status code that we should send to the client.
     * Should be one of the constants from {@link HttpServletResponse}, like
     * {@link HttpServletResponse#SC_NOT_FOUND}.
     */
    public int getHttpStatusCode()
    {
        return httpStatusCode;
    }

    /**
     * A Request identifier that SHOULD be a JSON scalar (String, Number, True,
     * False), but SHOULD normally not be Null [1].
     * If omitted, the Request is a Notification.
     * @return ...
     */
    public String getId()
    {
        return id;
    }

    /**
     * Additional information, may be omitted. Its contents is entirely defined
     * by the application (e.g. detailed error information, nested errors etc.).
     * @return ...
     */
    public Object getData()
    {
        return data;
    }

    /**
     * @return the version number as passed in by the JSON-RPC request.
     * Should default to 2.0 if no information is available
     */
    public String getVersion()
    {
        return version;
    }

    /**
     * @see #getId()
     */
    private final String id;

    /**
     * @see #getVersion()
     */
    private final String version;

    /**
     * @see #getJsonRpcErrorCode()
     */
    private final int jsonRpcErrorCode;

    /**
     * @see #getHttpStatusCode()
     */
    private final int httpStatusCode;

    /**
     * @see #getData()
     */
    private final Object data;
}
