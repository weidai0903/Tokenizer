package tokenizer;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

/**
 * @author David Matuszek
 */
public class OfficialTokenTest {
    Token token1, token2;

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
        token1 = new Token(TokenType.FLOAT, "12.34");
        token2 = new Token(TokenType.FLOAT, "12.34");
    }

    /**
     * Test method for {@link tokenizer.Token#hashCode()}.
     */
    @Test
    public final void testHashCode() {
        assertEquals(token1.hashCode(), token2.hashCode());
    }

    /**
     * Test method for {@link tokenizer.Token#Token(tokenizer.TokenType, java.lang.String)}.
     */
    @Test
    public final void testToken() {
        assertEquals(TokenType.FLOAT, token1.type);
        assertEquals("12.34", token1.text);
    }

    /**
     * Test method for {@link tokenizer.Token#toString()}.
     */
    @Test
    public final void testToString() {
        assertEquals("FLOAT:12.34", token1.toString());
    }

    /**
     * Test method for {@link tokenizer.Token#equals(java.lang.Object)}.
     */
    @Test
    public final void testEqualsObject() {
        assertEquals(token1, token2);
        assertFalse(token1.equals("12.34"));
    }

}
