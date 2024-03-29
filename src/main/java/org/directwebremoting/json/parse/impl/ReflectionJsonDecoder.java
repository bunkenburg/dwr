package org.directwebremoting.json.parse.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.Collection;

import org.directwebremoting.json.parse.JsonDecoder;
import org.directwebremoting.json.parse.JsonParseException;
import org.directwebremoting.util.LocalUtil;

/**
 * A {@link JsonDecoder} that de-serializes the data into an existing set of
 * objects.
 * @author Joe Walker [joe at getahead dot ltd dot uk]
 */
public class ReflectionJsonDecoder extends StatefulJsonDecoder
{
    private final Class<?> marshallInto;

    /**
     * @param marshallInto ...
     */
    public ReflectionJsonDecoder(Class<?> marshallInto)
    {
        this.marshallInto = marshallInto;
    }

    @Override
    protected Object createObject(Object parent, String propertyName) throws JsonParseException
    {
        return createArray(parent, propertyName);
    }

    @Override
    protected Object createArray(Object parent, String propertyName) throws JsonParseException
    {
        if (parent == null)
        {
            return createType(marshallInto);
        }
        else
        {
            Class<?> type = LocalUtil.getPropertyType(parent.getClass(), propertyName);
            return createType(type);
        }
    }

    @Override
    protected void addMemberToObject(Object parent, String propertyName, Object member) throws JsonParseException
    {
        try
        {
            LocalUtil.setProperty(parent, propertyName, member);
        }
        catch (SecurityException | InvocationTargetException | IllegalArgumentException | NoSuchMethodException |
               IllegalAccessException ex)
        {
            throw new JsonParseException(ex);
        }
    }

    @Override
    protected void addMemberToArray(Object parent, Object member)
    {
        @SuppressWarnings("unchecked")
        Collection<Object> col = (Collection<Object>) parent;
        col.add(member);
    }

    /**
     * Create an instance of a given type, wrapping any resulting exceptions in
     * a {@link JsonParseException}
     */
    private Object createType(Class<?> type) throws JsonParseException
    {
        try
        {
            return type.getDeclaredConstructor().newInstance();
        }
        catch (Exception ex)
        {
            throw new JsonParseException(ex);
        }
    }
}
