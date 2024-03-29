package org.directwebremoting.extend;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.directwebremoting.ConversionException;

/**
 * A Property for when we've been given override information in a signatures
 * element.
 * @author Joe Walker [joe at getahead dot ltd dot uk]
 */
public class OverrideProperty implements Property
{
    /**
     * All OverrideProperties need a name and the type they are providing
     * @param propertyType ...
     */
    public OverrideProperty(Class<?> propertyType)
    {
        this.propertyType = propertyType;
    }

    public Property createChild(int index)
    {
        return new NestedProperty(this, null, null, 0, index);
    }

    public String getName()
    {
        return "OverrideProperty";
    }

    public Class<?> getPropertyType()
    {
        return propertyType;
    }

    public Object getValue(Object bean) throws ConversionException
    {
        log.error("Attempt to getValue() on Override Property.");
        return null;
    }

    public void setValue(Object bean, Object value) throws ConversionException
    {
        log.error("Attempt to setValue() on Override Property.");
    }

    /**
     * @see org.directwebremoting.extend.Property#getName()
     */
    private final Class<?> propertyType;

    /**
     * The log stream
     */
    private static final Log log = LogFactory.getLog(OverrideProperty.class);
}
