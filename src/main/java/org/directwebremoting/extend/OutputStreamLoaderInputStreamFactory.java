package org.directwebremoting.extend;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.directwebremoting.io.FileTransfer;
import org.directwebremoting.io.InputStreamFactory;
import org.directwebremoting.io.OutputStreamLoader;

/**
 * Used when the {@link FileTransfer} has a {@link OutputStreamLoader}, but what
 * it really wants is an {@link InputStreamFactory}
 * @author Joe Walker [joe at getahead dot ltd dot uk]
 */
public class OutputStreamLoaderInputStreamFactory implements InputStreamFactory
{
    public OutputStreamLoaderInputStreamFactory(OutputStreamLoader outputStreamLoader)
    {
        this.outputStreamLoader = outputStreamLoader;
    }

    public InputStream getInputStream() throws IOException
    {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        outputStreamLoader.load(out);
        return new ByteArrayInputStream(out.toByteArray());
    }

    public void close() throws IOException
    {
        outputStreamLoader = null;
    }

    /**
     * The object we are proxying to
     */
    private OutputStreamLoader outputStreamLoader;
}
