package org.directwebremoting.extend;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.directwebremoting.io.FileTransfer;
import org.directwebremoting.io.InputStreamFactory;
import org.directwebremoting.io.OutputStreamLoader;
import org.directwebremoting.util.CopyUtils;

/**
 * Used when the {@link FileTransfer} has a {@link InputStreamFactory}, but what
 * it really wants is an {@link OutputStreamLoader}
 * @author Joe Walker [joe at getahead dot ltd dot uk]
 */
public final class InputStreamFactoryOutputStreamLoader implements OutputStreamLoader
{
    public InputStreamFactoryOutputStreamLoader(InputStreamFactory inputStreamFactory)
    {
        this.inputStreamFactory = inputStreamFactory;
    }

    public void load(OutputStream out) throws IOException
    {
        InputStream in = inputStreamFactory.getInputStream();
        CopyUtils.copy(in, out);
    }

    public void close() throws IOException
    {
        inputStreamFactory.close();
    }

    /**
     * The object we are proxying to
     */
    private final InputStreamFactory inputStreamFactory;
}
