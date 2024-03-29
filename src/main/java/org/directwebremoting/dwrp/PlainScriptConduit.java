package org.directwebremoting.dwrp;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.directwebremoting.extend.EnginePrivate;
import org.directwebremoting.extend.ProtocolConstants;
import org.directwebremoting.util.MimeConstants;

/**
 * A ScriptConduit for use with plain Javascript output.
 * <p>Scripts are plain Javascript without 'execute-in-parent-context' wrapping,
 * but with script-start and script-end markers.
 * <p>If this conduit is used the client should direct the output to an iframe
 * and then poll, looking for new data into the iframe. The html tags should be
 * removed and script between script-start and script-end tags eval()ed.
 * <p>This conduit is useful for Firefox. It will not work as it stands with IE
 * 6/7 because they don't allow the browser to see data entering an iframe until
 * it overflows a 4k buffer.
 * @author Joe Walker [joe at getahead dot ltd dot uk]
 */
public class PlainScriptConduit extends BaseScriptConduit
{
    public PlainScriptConduit(PrintWriter out, String instanceId, String scriptTagProtection)
    {
        super(out, instanceId);
        this.scriptTagProtection = scriptTagProtection;
    }

    public String getOutboundMimeType()
    {
        return MimeConstants.MIME_JS;
    }

    public void beginStreamAndChunk()
    {
        if (scriptTagProtection != null)
        {
            out.println(scriptTagProtection);
        }
    }

    public void beginChunk()
    {
        if (!chunkOpen) {
            out.println(ProtocolConstants.SCRIPT_START_MARKER);
            out.println(EnginePrivate.remoteBeginWrapper(instanceId, false, null));
            chunkOpen = true;
        }
    }

    public void sendScript(String script)
    {
        if (log.isDebugEnabled()) {
            log.debug("Execution time: " + new Date().toString() + " - Writing to response: " + script);
        }
        beginChunk();
        out.println(script);
    }

    public void endChunk()
    {
        if (chunkOpen) {
            out.println(EnginePrivate.remoteEndWrapper(instanceId, false));
            out.println(ProtocolConstants.SCRIPT_END_MARKER);
            chunkOpen = false;
        }
    }

    public void endStreamAndChunk() throws IOException
    {
        endChunk();
    }

    private final String scriptTagProtection;

    /**
     * Is a chunk section currently open on the output?
     */
    private boolean chunkOpen = false;

    /**
     * The log stream
     */
    private static final Log log = LogFactory.getLog(PlainScriptConduit.class);
}
