package org.directwebremoting.json.types;

/**
 * The Json version of a boolean
 * @author Joe Walker [joe at getahead dot ltd dot uk]
 */
public class JsonBoolean extends JsonValue
{
    /**
     * All JsonBoolean wrap a Java boolean value
     * @param value ...
     */
    public JsonBoolean(boolean value)
    {
        this.value = value;
    }

    /**
     * All JsonBoolean wrap a Java boolean value
     * @param text ...
     */
    public JsonBoolean(String text)
    {
        value = Boolean.parseBoolean(text);
    }

    @Override
    public boolean getBoolean()
    {
        return value;
    }

    @Override
    public String toExternalRepresentation()
    {
        return Boolean.toString(value);
    }

    @Override
    public String toString()
    {
        return toExternalRepresentation();
    }

    /**
     * The string value that we wrap
     */
    private final boolean value;
}
