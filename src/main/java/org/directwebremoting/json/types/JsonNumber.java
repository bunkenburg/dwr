package org.directwebremoting.json.types;

/**
 * The Json version of a Number
 * @author Joe Walker [joe at getahead dot ltd dot uk]
 */
public class JsonNumber extends JsonValue
{
    /**
     * All JsonNumbers wrap something stored as a double
     * @param value ...
     */
    public JsonNumber(int value)
    {
        this.value = value;
    }

    /**
     * All JsonNumbers wrap something stored as a double
     * @param value ...
     */
    public JsonNumber(long value)
    {
        this.value = value;
    }

    /**
     * All JsonNumbers wrap something stored as a double
     * @param value ...
     */
    public JsonNumber(double value)
    {
        this.value = value;
    }

    /**
     * Parse the input string as a double
     * @param text ...
     */
    public JsonNumber(String text)
    {
        this.value = Double.parseDouble(text);
    }

    @Override
    public double getDouble()
    {
        return value;
    }

    @Override
    public long getLong()
    {
        return (long) value;
    }

    @Override
    public int getInteger()
    {
        return (int) value;
    }

    @Override
    public String toExternalRepresentation()
    {
        return Double.toString(value);
    }

    @Override
    public String toString()
    {
        return toExternalRepresentation();
    }

    /**
     * The string value that we wrap
     */
    private final double value;
}
