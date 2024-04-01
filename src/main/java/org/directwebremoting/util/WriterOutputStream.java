package org.directwebremoting.util;

import java.io.IOException;
import java.io.Writer;

import javax.servlet.ServletOutputStream;
import javax.servlet.WriteListener;

/**
 * This is not the evil hack you are looking for.
 * @author Joe Walker [joe at getahead dot ltd dot uk]
 */
public final class WriterOutputStream extends ServletOutputStream {

    /**
     * Constructor that allows us to specify how strings are created
     * @param writer The stream that we proxy to
     * @param encoding The string encoding of data that we write to the stream
     */
    public WriterOutputStream(Writer writer, String encoding) {
        this.writer = writer;
        this.encoding = encoding;
    }

    @Override public void print(String s) throws IOException {writer.write(s);}

    /** @return true */
    @Override public boolean isReady() {return true;}

    /** Does nothing. */
    @Override public void setWriteListener(WriteListener writeListener) {}

    @Override public void write(byte[] ba) throws IOException {
        if (encoding == null) {
            writer.write(new String(ba));
        } else {
            writer.write(new String(ba, encoding));
        }
    }

    @Override public void write(byte[] ba, int off, int len) throws IOException {
        if (encoding == null) {
            writer.write(new String(ba, off, len));
        } else {
            writer.write(new String(ba, off, len, encoding));
        }
    }

    @Override public synchronized void write(int bite) throws IOException {
        buffer[0] = (byte) bite;
        write(buffer);
    }

    @Override public void close() throws IOException {
        if (writer != null) {
            writer.close();
            writer = null;
            encoding = null;
        }
    }

    @Override public void flush() throws IOException {writer.flush();}

    /**
     * The destination of all our printing
     */
    private Writer writer;

    /**
     * What string encoding should we use
     */
    private String encoding = null;

    /**
     * Buffer for write(int)
     */
    private final byte[] buffer = new byte[1];
}
