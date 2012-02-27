package tokenizer;

import static org.junit.Assert.*;

import java.util.NoSuchElementException;

import org.junit.Before;
import org.junit.Test;

public class TokenizerTest {
	Tokenizer intTok;
	Tokenizer floatTok;
	Tokenizer nameTok;
	Tokenizer groupTok;
	Tokenizer operatorTok;
	Tokenizer stringTok;
	Tokenizer commentTok;
	Tokenizer emptyTok;
	Tokenizer errorTok;
	Tokenizer complexTok;

	@Before
	public void setUp() throws Exception {
	}
	@Test
	public void testIntegers() {
		String integers="12345 5";
		intTok=new Tokenizer(integers);
		Token token1=new Token(TokenType.INT,"12345");
		Token token2=new Token(TokenType.INT,"5");
		assertEquals(intTok.next(),token1);
		assertEquals(intTok.next(),token2);

	}
	@Test 
	public void testFloats() {
		String floats="1.5e-10 1.123 23e22 1.1234567e111";
		floatTok=new Tokenizer(floats);
		Token token1=new Token(TokenType.FLOAT,"1.5e-10");
		Token token2=new Token(TokenType.FLOAT,"1.123");
		Token token3=new Token(TokenType.FLOAT,"23e22");
		Token token4=new Token(TokenType.FLOAT,"1.1234567e111");

		assertEquals(floatTok.next(),token1);
		assertEquals(floatTok.next(),token2);
		assertEquals(floatTok.next(),token3);
		assertEquals(floatTok.next(),token4);
	}
	@Test
	public void testNames() {
		String names="aaa      A_bc     _abEEa       ";
		nameTok=new Tokenizer(names);
		
		Token token1=new Token(TokenType.NAME,"aaa");
		Token token2=new Token(TokenType.NAME,"A_bc");
		Token token3=new Token(TokenType.NAME,"_abEEa");

		assertEquals(nameTok.next(),token1);
		assertEquals(nameTok.next(),token2);
		assertEquals(nameTok.next(),token3);
		
	}
	@Test 
	public void testFloatsWithNamesWithIntegers() {
		String floats="1.5e-10name1 1.123name23e22 1.1234567e111 5.0E1234567890";
		floatTok=new Tokenizer(floats);
		Token token1=new Token(TokenType.FLOAT,"1.5e-10");
		Token token5=new Token(TokenType.NAME,"name1");
		Token token2=new Token(TokenType.FLOAT,"1.123");
		Token token6=new Token(TokenType.NAME,"name23e22");
		Token token4=new Token(TokenType.FLOAT,"1.1234567e111");
		Token token7=new Token(TokenType.FLOAT,"5.0E123");
		Token token8=new Token(TokenType.INT,"4567890");

		assertEquals(floatTok.next(),token1);
		assertEquals(floatTok.next(),token5);
		assertEquals(floatTok.next(),token2);
		assertEquals(floatTok.next(),token6);
		assertEquals(floatTok.next(),token4);
		assertEquals(floatTok.next(),token7);
		assertEquals(floatTok.next(),token8);

		//assertTrue(tokenizer1.hasNext());
	}
	public void testGrouping() {
		String groups="(aaa)[A_bc]_abEEa";
		groupTok=new Tokenizer(groups);
		Token token4=new Token(TokenType.GROUPING_SYMBOL,"(");
		Token token1=new Token(TokenType.NAME,"aaa");
		Token token5=new Token(TokenType.GROUPING_SYMBOL,")");
		Token token6=new Token(TokenType.GROUPING_SYMBOL,"[");

		Token token2=new Token(TokenType.NAME,"A_bc");
		Token token7=new Token(TokenType.GROUPING_SYMBOL,"]");
		Token token3=new Token(TokenType.NAME,"_abEEa");

		assertEquals(nameTok.next(),token4);
		assertEquals(nameTok.next(),token1);
		assertEquals(nameTok.next(),token5);
		assertEquals(nameTok.next(),token6);
		assertEquals(nameTok.next(),token2);
		assertEquals(nameTok.next(),token7);
		assertEquals(nameTok.next(),token3);
	}
	@Test
	public void testGroupingWithFloatIntegerNames() {
		String groups="(a[a]a) 12345{ (1.56e5[A_bc])_abEEa}";
		groupTok=new Tokenizer(groups);
		Token token4=new Token(TokenType.GROUPING_SYMBOL,"(");
		Token token8=new Token(TokenType.NAME,"a");
		Token token9=new Token(TokenType.GROUPING_SYMBOL,"[");
		Token token1=new Token(TokenType.NAME,"a");
		Token token10=new Token(TokenType.GROUPING_SYMBOL,"]");
		Token token11=new Token(TokenType.NAME,"a");
		Token token12=new Token(TokenType.GROUPING_SYMBOL,")");
		Token token13=new Token(TokenType.INT,"12345");
		Token token14=new Token(TokenType.GROUPING_SYMBOL,"{");
		Token token5=new Token(TokenType.GROUPING_SYMBOL,"(");
		Token token15=new Token(TokenType.FLOAT,"1.56e5");
		Token token16=new Token(TokenType.GROUPING_SYMBOL,"[");
		Token token2=new Token(TokenType.NAME,"A_bc");
		Token token7=new Token(TokenType.GROUPING_SYMBOL,"]");
		Token token17=new Token(TokenType.GROUPING_SYMBOL,")");
		Token token3=new Token(TokenType.NAME,"_abEEa");
		Token token18=new Token(TokenType.GROUPING_SYMBOL,"}");

		assertEquals(groupTok.next(),token4);
		assertEquals(groupTok.next(),token8);
		assertEquals(groupTok.next(),token9);
		assertEquals(groupTok.next(),token1);
		assertEquals(groupTok.next(),token10);
		assertEquals(groupTok.next(),token11);
		assertEquals(groupTok.next(),token12);
		assertEquals(groupTok.next(),token13);
		assertEquals(groupTok.next(),token14);
		assertEquals(groupTok.next(),token5);
		assertEquals(groupTok.next(),token15);
		assertEquals(groupTok.next(),token16);
		assertEquals(groupTok.next(),token2);
		assertEquals(groupTok.next(),token7);
		assertEquals(groupTok.next(),token17);
		assertEquals(groupTok.next(),token3);
		assertEquals(groupTok.next(),token18);
	}
	@Test
	public void testOperators() {
		String operators="aaa*A_&bc _ab*=EE\\a";
		operatorTok=new Tokenizer(operators);
		
		Token token1=new Token(TokenType.NAME,"aaa");
		Token token4=new Token(TokenType.OPERATOR,"*");
		Token token2=new Token(TokenType.NAME,"A_");
		Token token3=new Token(TokenType.OPERATOR,"&");
		Token token5=new Token(TokenType.NAME,"bc");
		Token token6=new Token(TokenType.NAME,"_ab");
		Token token7=new Token(TokenType.OPERATOR,"*=");
		Token token8=new Token(TokenType.NAME,"EE");
		Token token9=new Token(TokenType.OPERATOR,"\\");
		Token token10=new Token(TokenType.NAME,"a");

		assertEquals(operatorTok.next(),token1);
		assertEquals(operatorTok.next(),token4);
		assertEquals(operatorTok.next(),token2);
		assertEquals(operatorTok.next(),token3);
		assertEquals(operatorTok.next(),token5);
		assertEquals(operatorTok.next(),token6);
		assertEquals(operatorTok.next(),token7);
		assertEquals(operatorTok.next(),token8);
		assertEquals(operatorTok.next(),token9);
		assertEquals(operatorTok.next(),token10);
	}
	@Test
	public void testOperatorsFloatsIntegersNamesGroupings() {
		String operators="{1245/(23.12E123)  +  [aaa*A_&bc]} 10e+ 100.233e-";
		operatorTok=new Tokenizer(operators);
		Token token1=new Token(TokenType.GROUPING_SYMBOL,"{");
		Token token2=new Token(TokenType.INT,"1245");
		Token token3=new Token(TokenType.OPERATOR,"/");
		Token token4=new Token(TokenType.GROUPING_SYMBOL,"(");
		Token token5=new Token(TokenType.FLOAT,"23.12E123");
		Token token6=new Token(TokenType.GROUPING_SYMBOL,")");
		Token token7=new Token(TokenType.OPERATOR,"+");
		Token token8=new Token(TokenType.GROUPING_SYMBOL,"[");
		Token token9=new Token(TokenType.NAME,"aaa");
		Token token10=new Token(TokenType.OPERATOR,"*");
		Token token11=new Token(TokenType.NAME,"A_");
		Token token12=new Token(TokenType.OPERATOR,"&");
		Token token13=new Token(TokenType.NAME,"bc");
		Token token14=new Token(TokenType.GROUPING_SYMBOL,"]");
		Token token15=new Token(TokenType.GROUPING_SYMBOL,"}");
		Token token16=new Token(TokenType.INT,"10");
		Token token17=new Token(TokenType.NAME,"e");
		Token token18=new Token(TokenType.OPERATOR,"+");
		Token token19=new Token(TokenType.FLOAT,"100.233");
		Token token20=new Token(TokenType.NAME,"e");
		Token token21=new Token(TokenType.OPERATOR,"-");

		assertEquals(operatorTok.next(),token1);
		assertEquals(operatorTok.next(),token2);
		assertEquals(operatorTok.next(),token3);
		assertEquals(operatorTok.next(),token4);
		assertEquals(operatorTok.next(),token5);
		assertEquals(operatorTok.next(),token6);
		assertEquals(operatorTok.next(),token7);
		assertEquals(operatorTok.next(),token8);
		assertEquals(operatorTok.next(),token9);
		assertEquals(operatorTok.next(),token10);
		assertEquals(operatorTok.next(),token11);
		assertEquals(operatorTok.next(),token12);
		assertEquals(operatorTok.next(),token13);
		assertEquals(operatorTok.next(),token14);
		assertEquals(operatorTok.next(),token15);
		assertEquals(operatorTok.next(),token16);
		assertEquals(operatorTok.next(),token17);
		assertEquals(operatorTok.next(),token18);
		assertEquals(operatorTok.next(),token19);
		assertEquals(operatorTok.next(),token20);
		assertEquals(operatorTok.next(),token21);
	}
	
	
	@Test
	public void testStrings() {
		String strings="\"aaa\"   \"_ss\"\'1/2*322=*4   9e33.ddd\'";
		stringTok=new Tokenizer(strings);
		Token token1=new Token(TokenType.STRING,"\"aaa\"");
		Token token2=new Token(TokenType.STRING,"\"_ss\"");
		Token token3=new Token(TokenType.STRING,"\'1/2*322=*4   9e33.ddd\'");
		assertEquals(stringTok.next(),token1);
		assertEquals(stringTok.next(),token2);
		assertEquals(stringTok.next(),token3);

	}
	
	@Test
	public void testComments() {
		String comments="# sdjd*dw90e)()*))(#&#&&#& kdkskd   sdasda  \n";
		commentTok=new Tokenizer(comments);
		Token token1=new Token(TokenType.COMMENT,"# sdjd*dw90e)()*))(#&#&&#& kdkskd   sdasda");
		assertEquals(commentTok.next(),token1);
	}
	
	@Test
	public void testOperatorsFloatsIntegersNamesGroupingsStringsComments() {
		String complex="{1245/\"Hello World!!!??\"(23.12E123)  +  [aaa*A_&bc]} #foo bar   \n 100  ";
		complexTok=new Tokenizer(complex);
		Token token1=new Token(TokenType.GROUPING_SYMBOL,"{");
		Token token2=new Token(TokenType.INT,"1245");
		Token token3=new Token(TokenType.OPERATOR,"/");
		Token token17=new Token(TokenType.STRING,"\"Hello World!!!??\"");
		Token token4=new Token(TokenType.GROUPING_SYMBOL,"(");
		Token token5=new Token(TokenType.FLOAT,"23.12E123");
		Token token6=new Token(TokenType.GROUPING_SYMBOL,")");
		Token token7=new Token(TokenType.OPERATOR,"+");
		Token token8=new Token(TokenType.GROUPING_SYMBOL,"[");
		Token token9=new Token(TokenType.NAME,"aaa");
		Token token10=new Token(TokenType.OPERATOR,"*");
		Token token11=new Token(TokenType.NAME,"A_");
		Token token12=new Token(TokenType.OPERATOR,"&");
		Token token13=new Token(TokenType.NAME,"bc");
		Token token14=new Token(TokenType.GROUPING_SYMBOL,"]");
		Token token15=new Token(TokenType.GROUPING_SYMBOL,"}");
		Token token18=new Token(TokenType.COMMENT,"#foo bar");
		Token token16=new Token(TokenType.INT,"100");

		assertEquals(complexTok.next(),token1);
		assertEquals(complexTok.next(),token2);
		assertEquals(complexTok.next(),token3);
		assertEquals(complexTok.next(),token17);
		assertEquals(complexTok.next(),token4);
		assertEquals(complexTok.next(),token5);
		assertEquals(complexTok.next(),token6);
		assertEquals(complexTok.next(),token7);
		assertEquals(complexTok.next(),token8);
		assertEquals(complexTok.next(),token9);
		assertEquals(complexTok.next(),token10);
		assertEquals(complexTok.next(),token11);
		assertEquals(complexTok.next(),token12);
		assertEquals(complexTok.next(),token13);
		assertEquals(complexTok.next(),token14);
		assertEquals(complexTok.next(),token15);
		assertEquals(complexTok.next(),token18);
		assertEquals(complexTok.next(),token16);
	}
	
	@Test(expected=NoSuchElementException.class)
	public void testNoMoreTokens() {
		String empty="";
		emptyTok=new Tokenizer(empty);
		emptyTok.next();
	}
	@Test
	public void testErrorTokens() {
		String error="\"jdddk **** #%$";
		errorTok=new Tokenizer(error);
		Token errorToken=new Token(TokenType.ERROR,"\"");
		assertEquals(errorTok.next(),errorToken);
	}
	
	
	@Test
	public void testHasNext() {
		String names="CDdd  ";
		nameTok=new Tokenizer(names);
		Token token1=new Token(TokenType.NAME,"CDdd");
		assertEquals(nameTok.next(),token1);
		assertFalse(nameTok.hasNext());
		String names2="CDdd   ssd";
		nameTok=new Tokenizer(names2);
		nameTok.next();
		assertTrue(nameTok.hasNext());
		nameTok.next();
		assertFalse(nameTok.hasNext());
	}
	@Test
	public void testBackUp() {
		String backups="abc def 123";
		nameTok=new Tokenizer(backups);
		Token token1=new Token(TokenType.NAME,"abc");
		assertEquals(nameTok.next(),token1);
		nameTok.backUp();
		assertEquals(nameTok.next(),token1);
		nameTok.backUp();
		assertEquals(nameTok.next(),token1);
		nameTok.backUp();
		nameTok.backUp();
		nameTok.backUp();
		nameTok.backUp();
		assertTrue(nameTok.hasNext());
		assertTrue(nameTok.hasNext());
		assertEquals(nameTok.next(),token1);
		Token token2=new Token(TokenType.NAME,"def");
		assertEquals(nameTok.next(),token2);
		assertTrue(nameTok.hasNext());
		assertTrue(nameTok.hasNext());
		Token token3=new Token(TokenType.INT,"123");
		assertEquals(nameTok.next(),token3);
		nameTok.backUp();
		assertEquals(nameTok.next(),token3);
		assertFalse(nameTok.hasNext());
		assertFalse(nameTok.hasNext());
	}
}
