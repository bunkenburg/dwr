package org.directwebremoting.impl;

public class DefaultServerLoadMonitor extends AbstractServerLoadMonitor
{

    public boolean supportsStreaming()
    {
        return true;
    }

    public long getConnectedTime()
    {
        return connectedTime;
    }

    public int getDisconnectedTime()
    {
        return disconnectedTime;
    }

    /**
     * Setter to allow configuration from the container (f ex init-param)
     * @param connectedTime ...
     */
    public void setConnectedTime(long connectedTime)
    {
        this.connectedTime = connectedTime;
    }

    /**
     * Setter to allow configuration from the container (f ex init-param)
     * @param disconnectedTime ...
     */
    public void setDisconnectedTime(int disconnectedTime)
    {
        this.disconnectedTime = disconnectedTime;
    }

    /**
     * The time we are currently waiting before sending a browser away and
     * asking it to reconnect.
     */
    protected long connectedTime = 60000;

    /**
     * How long are we telling users to wait before they come back next
     */
    protected int disconnectedTime = 0;
}
