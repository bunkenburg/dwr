package org.directwebremoting.json.parse;

/**
 * Used by {@link JsonParser} to allow the parse process to be mostly stateless,
 * and to abstract the process of creating objects. It is very likely that
 * {@link org.directwebremoting.json.parse.impl.StatefulJsonDecoder} will be
 * an easier start point than this.
 * @author Joe Walker [joe at getahead dot ltd dot uk]
 */
public interface JsonDecoder
{
    /**
     * When the parse is finished, this gives access to the result
     * @return ...
     * @throws JsonParseException ...
     */
    Object getRoot() throws JsonParseException;

    /**
     * We have encountered a {.
     * If this object is a top level object, or the object is being added to an
     * array, then the <code>propertyName</code> argument will be null,
     * otherwise it will contain the name that this object will be assigned to
     * in the parent object.
     * What follows is a series of addXxxx() calls, possibly nested
     * arrays and objects, followed by a call to {@link #endObject(String)}
     * @param propertyName ...
     * @throws JsonParseException ...
     */
    void beginObject(String propertyName) throws JsonParseException;

    /**
     * We have encountered a }.
     * This is called in a matching pair to {@link #beginObject(String)}
     * The <code>propertyName</code> argument will match the value given in the
     * corresponding {@link #beginObject(String)} call.
     * @param propertyName ...
     * @throws JsonParseException ...
     */
    void endObject(String propertyName) throws JsonParseException;

    /**
     * We have encountered a [.
     * If this object is a top level object, or the object is being added to an
     * array, then the <code>propertyName</code> argument will be null,
     * otherwise it will contain the name that this object will be assigned to
     * in the parent object.
     * What follows is a series of addXxxxx() calls, possibly including nested
     * arrays and objects, followed by a call to {@link #endArray(String)}.
     * @param propertyName ...
     * @throws JsonParseException ...
     */
    void beginArray(String propertyName) throws JsonParseException;

    /**
     * We have encountered a ].
     * This is called in a matching pair to {@link #beginArray(String)}
     * The <code>propertyName</code> argument will match the value given in the
     * corresponding {@link #beginArray(String)} call.
     * @param propertyName ...
     * @throws JsonParseException ...
     * @see #beginArray(String)
     */
    void endArray(String propertyName) throws JsonParseException;

    /**
     * Add a string member.
     * If the member is added to an object then the <code>propertyName</code>
     * argument will be the part of the JSON string before the ':'. If the
     * member is added to an array, then <code>propertyName</code> will be null.
     * See the note on {@link #beginObject(String)}
     * @param propertyName ...
     * @param value ...
     * @throws JsonParseException ...
     */
    void addString(String propertyName, String value) throws JsonParseException;

    /**
     * Add a numeric member.
     * See the note on {@link #addString(String, String)} for the meaning of the
     * <code>propertyName</code> argument.
     * The 3 parts are put together like this:
     * <code>{intPart}[.{floatPart}][E{expPart}]</code>. For example:
     * <ul>
     * <li>JSON=3 results in addNumber("3", null, null);
     * <li>JSON=3.14 results in addNumber("3", "14", null);
     * <li>JSON=2.9E8 results in addNumber("2", "9", "8");
     * </ul>
     * @param propertyName ...
     * @param intPart ...
     * @param floatPart ...
     * @param expPart ...
     * @throws JsonParseException ...
     */
    void addNumber(String propertyName, String intPart, String floatPart, String expPart)throws JsonParseException;

    /**
     * Add a boolean member.
     * See the note on {@link #addString(String, String)} for the meaning of the
     * <code>propertyName</code> argument.
     * @param propertyName ...
     * @param value ...
     * @throws JsonParseException ...
     */
    void addBoolean(String propertyName, boolean value) throws JsonParseException;

    /**
     * Add a null member.
     * See the note on {@link #addString(String, String)} for the meaning of the
     * <code>propertyName</code> argument.
     * @param propertyName ...
     * @throws JsonParseException ...
     */
    void addNull(String propertyName) throws JsonParseException;
}
