package org.directwebremoting.convert;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.ParseException;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.directwebremoting.ConversionException;
import org.directwebremoting.convert.mapped.BeanEx;
import org.directwebremoting.convert.mapped.ObjectEx;
import org.directwebremoting.convert.mapped.ObjectForceEx;
import org.directwebremoting.extend.ConverterManager;
import org.directwebremoting.extend.InboundContext;
import org.directwebremoting.extend.InboundVariable;
import org.directwebremoting.extend.OutboundContext;
import org.directwebremoting.extend.OutboundVariable;
import org.directwebremoting.impl.TestEnvironment;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

/**
 * The tests for the <code>PrimitiveConverter</code> class.
 * @see PrimitiveConverter
 * @author Joe Walker [joe at getahead dot ltd dot uk]
 */
public class AllConverterTest
{
    /**
     *
     */
    @BeforeClass
    public static void setup() throws ParseException
    {
        testDate = new Date(1104562800000L);
        TestEnvironment.engageThread();
    }

    @Test
    public void nullConvert() throws Exception
    {
        assertOutboundConversion(null, "null");
    }

    @Test
    public void booleanConvert() throws Exception
    {
        assertInboundConversion("true", Boolean.class, Boolean.TRUE);
        assertInboundConversion("tRuE", Boolean.class, Boolean.TRUE);
        assertInboundConversion("false", Boolean.class, Boolean.FALSE);
        assertInboundConversion("FALSE", Boolean.class, Boolean.FALSE);
        assertInboundConversion("yes", Boolean.class, Boolean.FALSE);
        assertInboundConversion("null", Boolean.class, Boolean.FALSE);
        assertInboundConversion("", Boolean.class, null);
        assertInboundConversion("true", Boolean.TYPE, Boolean.TRUE);
        assertInboundConversion("tRuE", Boolean.TYPE, Boolean.TRUE);
        assertInboundConversion("false", Boolean.TYPE, Boolean.FALSE);
        assertInboundConversion("FALSE", Boolean.TYPE, Boolean.FALSE);
        assertInboundConversion("yes", Boolean.TYPE, Boolean.FALSE);
        assertInboundConversion("null", Boolean.TYPE, Boolean.FALSE);
        assertInboundConversion("", Boolean.TYPE, Boolean.FALSE);

        assertOutboundConversion(Boolean.TRUE, "true");
        assertOutboundConversion(Boolean.FALSE, "false");
    }

    @Test
    public void byteConvert() throws Exception
    {
        assertInboundConversion("127", Byte.class, Byte.valueOf("127"));
        assertInboundConversion("-128", Byte.class, Byte.valueOf("-128"));
        assertInboundConversion("0", Byte.class, Byte.valueOf("0"));
        assertInboundConversion("", Byte.class, null);
        assertInboundConversion("127", Byte.TYPE, Byte.valueOf("127"));
        assertInboundConversion("-128", Byte.TYPE, Byte.valueOf("-128"));
        assertInboundConversion("0", Byte.TYPE, Byte.valueOf("0"));
        assertInboundConversion("", Byte.TYPE, Byte.valueOf("0"));

        assertOutboundConversion(Byte.valueOf("127"), "127");
        assertOutboundConversion(Byte.valueOf("-128"), "-128");
        assertOutboundConversion(Byte.valueOf("0"), "0");

        assertInboundConversionFailure("128", Byte.class);
        assertInboundConversionFailure("-129", Byte.class);
        assertInboundConversionFailure("null", Byte.class);
        assertInboundConversionFailure("128", Byte.TYPE);
        assertInboundConversionFailure("-129", Byte.TYPE);
        assertInboundConversionFailure("null", Byte.TYPE);
    }

    @Test
    public void shortConvert() throws Exception
    {
        assertInboundConversion("-128", Short.class, Short.valueOf("-128"));
        assertInboundConversion("0", Short.class, Short.valueOf("0"));
        assertInboundConversion("", Short.class, null);
        assertInboundConversion("-128", Short.TYPE, Short.valueOf("-128"));
        assertInboundConversion("0", Short.TYPE, Short.valueOf("0"));
        assertInboundConversion("", Short.TYPE, Short.valueOf("0"));

        assertOutboundConversion(Short.valueOf("-128"), "-128");
        assertOutboundConversion(Short.valueOf("0"), "0");

        assertInboundConversionFailure("null", Short.class);
        assertInboundConversionFailure("null", Short.TYPE);
    }

    @Test
    public void testIntegerConvert() throws Exception
    {
        assertInboundConversion("-128", Integer.class, Integer.valueOf("-128"));
        assertInboundConversion("0", Integer.class, Integer.valueOf("0"));
        assertInboundConversion("", Integer.class, null);
        assertInboundConversion("-128", Integer.TYPE, Integer.valueOf("-128"));
        assertInboundConversion("0", Integer.TYPE, Integer.valueOf("0"));
        assertInboundConversion("", Integer.TYPE, Integer.valueOf("0"));

        assertOutboundConversion(Integer.valueOf("-128"), "-128");
        assertOutboundConversion(Integer.valueOf("0"), "0");

        assertInboundConversionFailure("null", Integer.class);
        assertInboundConversionFailure("null", Integer.TYPE);
    }

    @Test
    public void longConvert() throws Exception
    {
        assertInboundConversion("-128", Long.class, Long.valueOf("-128"));
        assertInboundConversion("0", Long.class, Long.valueOf("0"));
        assertInboundConversion("", Long.class, null);
        assertInboundConversion("-128", Long.TYPE, Long.valueOf("-128"));
        assertInboundConversion("0", Long.TYPE, Long.valueOf("0"));
        assertInboundConversion("", Long.TYPE, Long.valueOf("0"));

        assertOutboundConversion(Long.valueOf("-128"), "-128");
        assertOutboundConversion(Long.valueOf("0"), "0");

        assertInboundConversionFailure("null", Long.class);
        assertInboundConversionFailure("null", Long.TYPE);
    }

    @Test
    public void floatConvert() throws Exception
    {
        assertInboundConversion("-12.8", Float.class, Float.valueOf("-12.8"));
        assertInboundConversion("0", Float.class, Float.valueOf("0"));
        assertInboundConversion("", Float.class, null);
        assertInboundConversion("-12.8", Float.TYPE, Float.valueOf("-12.8"));
        assertInboundConversion("0", Float.TYPE, Float.valueOf("0"));
        assertInboundConversion("", Float.TYPE, Float.valueOf("0"));

        assertOutboundConversion(Float.valueOf("-12.8"), "-12.8");
        assertOutboundConversion(Float.valueOf("0"), "0.0");

        assertInboundConversionFailure("null", Float.class);
        assertInboundConversionFailure("null", Float.TYPE);
    }

    @Test
    public void doubleConvert() throws Exception
    {
        assertInboundConversion("-12.8", Double.class, Double.valueOf("-12.8"));
        assertInboundConversion("0", Double.class, Double.valueOf("0"));
        assertInboundConversion("", Double.class, null);
        assertInboundConversion("-12.8", Double.TYPE, Double.valueOf("-12.8"));
        assertInboundConversion("0", Double.TYPE, Double.valueOf("0"));
        assertInboundConversion("", Double.TYPE, Double.valueOf("0"));

        assertOutboundConversion(Double.valueOf("-12.8"), "-12.8");
        assertOutboundConversion(Double.valueOf("0"), "0.0");

        assertInboundConversionFailure("null", Double.class);
        assertInboundConversionFailure("null", Double.TYPE);
    }

    @Test
    public void characterConvert() throws Exception
    {
        assertInboundConversion("-", Character.class, '-');
        assertInboundConversion("0", Character.class, '0');
        assertInboundConversion("\"", Character.class, '\"');
        assertInboundConversion("'", Character.class, '\'');
        assertInboundConversion("Δ", Character.class, 'Δ');
        assertInboundConversion("-", Character.TYPE, '-');
        assertInboundConversion("0", Character.TYPE, '0');
        assertInboundConversion("\"", Character.TYPE, '\"');
        assertInboundConversion("'", Character.TYPE, '\'');
        assertInboundConversion("Δ", Character.TYPE, 'Δ');

        assertOutboundConversion('-', "\"-\"");
        assertOutboundConversion('0', "\"0\"");
        assertOutboundConversion('\"', "\"\\\"\"");
        assertOutboundConversion('\'', "\"\\'\"");
        assertOutboundConversion('Δ', "\"\\u0394\"");
        assertOutboundConversion('-', "\"-\"");
        assertOutboundConversion('0', "\"0\"");
        assertOutboundConversion('\"', "\"\\\"\"");
        assertOutboundConversion('\'', "\"\\'\"");
        assertOutboundConversion('Δ', "\"\\u0394\"");

        assertInboundConversionFailure("", Character.class);
        assertInboundConversionFailure("", Character.TYPE);
        assertInboundConversionFailure("null", Character.class);
        assertInboundConversionFailure("null", Character.TYPE);
    }

    @Test
    public void stringConvert() throws Exception
    {
        assertInboundConversion("-", String.class, "-");
        assertInboundConversion("0", String.class, "0");
        assertInboundConversion("\"", String.class, "\"");
        assertInboundConversion("'", String.class, "'");
        assertInboundConversion("Δ", String.class, "Δ");
        assertInboundConversion("", String.class, "");
        assertInboundConversion("null", String.class, "null");

        assertOutboundConversion("-", "\"-\"");
        assertOutboundConversion("0", "\"0\"");
        assertOutboundConversion("\"", "\"\\\"\"");
        assertOutboundConversion("Δ", "\"\\u0394\"");
        assertOutboundConversion("", "\"\"");
        assertOutboundConversion("null", "\"null\"");
    }

    @Test
    public void bigIntegerConvert() throws Exception
    {
        assertInboundConversion("-12", BigInteger.class, new BigInteger("-12"));
        assertInboundConversion("0", BigInteger.class, new BigInteger("0"));
        assertInboundConversion("", BigInteger.class, null);

        assertOutboundConversion(new BigInteger("-12"), "-12");
        assertOutboundConversion(new BigInteger("0"), "0");

        assertInboundConversionFailure("null", BigInteger.class);
    }

    @Test
    public void bigDecimalConvert() throws Exception
    {
        assertInboundConversion("-12", BigDecimal.class, new BigDecimal("-12"));
        assertInboundConversion("0", BigDecimal.class, new BigDecimal("0"));
        assertInboundConversion("", BigDecimal.class, null);

        assertOutboundConversion(new BigDecimal("-12"), "-12");
        assertOutboundConversion(new BigDecimal("0"), "0");

        assertInboundConversionFailure("null", BigDecimal.class);
    }

    @Test
    public void dateConvert() throws Exception
    {
        assertInboundConversion("1104562800000", Date.class, testDate);
        assertInboundConversion("null", Date.class, null);
        assertInboundConversion("1104562800000", java.sql.Date.class, testDate);
        assertInboundConversion("null", java.sql.Date.class, null);
        assertOutboundConversion(testDate, "new Date(1104562800000)");
    }

    @Ignore
    @Test
    public void beanConvert() throws Exception
    {
        assertInboundConversion("null", BeanEx.class, null);
        assertInboundConversion("{ }", BeanEx.class, new BeanEx());
        assertInboundConversion("{ name:string:fred }", BeanEx.class, new BeanEx("fred"));
        assertInboundConversion("{ wrong:string:fred }", BeanEx.class, new BeanEx());

        assertOutboundConversion(new BeanEx(), "{name:null}");
        assertOutboundConversion(new BeanEx("fred"), "{name:\"fred\"}");
        assertOutboundConversion(new BeanEx(), "{name:null}");
    }

    @Ignore
    @Test
    public void objectConvert() throws Exception
    {
        assertInboundConversion("null", ObjectEx.class, null);
        assertInboundConversion("{ }", ObjectEx.class, new ObjectEx());
        assertInboundConversion("{ name:string:fred }", ObjectEx.class, new ObjectEx("fred"));
        assertInboundConversion("{ wrong:string:fred }", ObjectEx.class, new ObjectEx());
        assertInboundConversion("{ hidden:string:fred }", ObjectEx.class, new ObjectEx());
        assertInboundConversion("null", ObjectForceEx.class, null);
        assertInboundConversion("{ }", ObjectForceEx.class, new ObjectForceEx());
        assertInboundConversion("{ name:string:fred }", ObjectForceEx.class, new ObjectForceEx("fred"));
        assertInboundConversion("{ wrong:string:fred }", ObjectForceEx.class, new ObjectForceEx());

        assertOutboundConversion(new ObjectEx(), "{name:null}");
        assertOutboundConversion(new ObjectEx("fred"), "{name:\"fred\"}");
        assertOutboundConversion(new ObjectEx(), "{name:null}");
        assertOutboundConversion(new ObjectEx(), "{name:null}");
        assertOutboundConversion(new ObjectForceEx(), "{name:null}");
        assertOutboundConversion(new ObjectForceEx("fred"), "{name:\"fred\"}");
        assertOutboundConversion(new ObjectForceEx(), "{name:null}");
    }

    /*
    @Test
    public void convert() throws Exception
    {
    }
    */

    public static void assertInboundConversion(String input, Class<?> convertTo, Object expected)
    {
        ConverterManager converterManager = TestEnvironment.getConverterManager();
        InboundContext ctx = new InboundContext();

        String explanation = "Convert \"" + input + "\" to " + convertTo.getName();

        boolean isException = expected instanceof Class && (Exception.class.isAssignableFrom((Class<?>) expected));

        if (isException)
        {
            try
            {
                InboundVariable iv=new InboundVariable(ctx, null, "type", input);
                iv.dereference();
                Assert.fail();
            }
            catch (Exception ex)
            {
                Assert.assertEquals(explanation, ex.getClass(), expected);
            }
        }
        else
        {
            try
            {
                InboundVariable iv = new InboundVariable(ctx, null, "type", input);
                iv.dereference();
                Object result = converterManager.convertInbound(convertTo, iv, null);
                Assert.assertEquals(explanation, result, expected);
            }
            catch (Exception ex)
            {
                log.error(explanation, ex);
                Assert.fail(explanation);
            }
        }
    }

    public static void assertInboundConversionFailure(String input, Class<?> convertTo)
    {
        ConverterManager converterManager = TestEnvironment.getConverterManager();
        InboundContext ctx = new InboundContext();

        String explanation = "Convert \"" + input + "\" to " + convertTo.getSimpleName();

        try
        {
            InboundVariable iv = new InboundVariable(ctx, null, "type", input);
            iv.dereference();
            converterManager.convertInbound(convertTo, iv, null);
            Assert.fail();
        }
        catch (Exception ex)
        {
            Assert.assertEquals(explanation, ex.getClass(), ConversionException.class);
        }
    }

    public static void assertOutboundConversion(Object input, String expected) throws ConversionException
    {
        ConverterManager converterManager = TestEnvironment.getConverterManager();
        OutboundContext ctx = new OutboundContext(false);

        OutboundVariable result = converterManager.convertOutbound(input, ctx);

        Assert.assertNotNull(result);

        String script = result.getDeclareCode() + result.getBuildCode() + result.getAssignCode();
        script = script.replace("\r", "");
        script = script.replace("\n", "");

        Assert.assertEquals(expected, script);
    }

    private static final Log log = LogFactory.getLog(AllConverterTest.class);
    private static Date testDate;
}
