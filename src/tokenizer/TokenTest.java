package tokenizer;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class TokenTest {
	Token t;

	@Before
	public void setUp() throws Exception {
		t=new Token(TokenType.INT,"123456");
	}

	@Test
	public void testEquals() {
		Token t2=new Token(TokenType.INT,"123456");
		assertTrue(t.equals(t2));
	}
	@Test
	public void testToString() {
		assertEquals(t.toString(),TokenType.INT.toString()+":123456");
	}
	@Test
	public void testHashCode() {
		Token t2=new Token(TokenType.INT,"123456");
		Token t3=new Token(TokenType.FLOAT,"123456");
		Token t4=new Token(TokenType.FLOAT,"123456");
		Token t5=new Token(TokenType.GROUPING_SYMBOL,"(");
		Token t6=new Token(TokenType.GROUPING_SYMBOL,"(");
		assertEquals(t.hashCode(),t2.hashCode());
		assertEquals(t3.hashCode(),t4.hashCode());
		assertEquals(t5.hashCode(),t6.hashCode());

	}

}
