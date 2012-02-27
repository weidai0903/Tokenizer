package tokenizer;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 * Get the Tokens
 * @author Wei Dai
 *
 */

public class Tokenizer implements Iterator<Token>{
	private String input;
	private int post=0;
	private int pre=0;
	private boolean backUp=false;
	Pattern p_INT=Pattern.compile("\\s*[0-9]++");
	Pattern p_FLOAT=Pattern.compile("(\\s*)((\\d+\\.\\d+[eE][+-]*\\d{1,3}+)|(\\d+\\.\\d++)|(\\d+[eE][+-]*\\d{1,3}+))");
	Pattern p_NAME=Pattern.compile("\\s*[a-zA-Z_]\\w*_*");
	Pattern p_GROUPING=Pattern.compile("\\s*[\\(\\)\\[\\]\\{\\}]");
	Pattern p_OPERATOR=Pattern.compile("\\s*(\\+=)|(-=)|(\\*=)|(/=)|(%=)|(==)|(!=)|(<=)|(>=)|(\\s*[-+~`!@$%^&\\*\\=\\|\\\\<,>\\./\\?])");
	Pattern p_STRING=Pattern.compile("\\s*(\".*?\")|(\'.*?\')");
	Pattern p_COMMENT=Pattern.compile("\\s*#.*+(?m)$");
	Pattern p_spaces=Pattern.compile("\\s*");
	Matcher m_INT;
	Matcher m_FLOAT;
	Matcher m_NAME;
	Matcher m_GROUPING;
	Matcher m_OPERATOR;
	Matcher m_STRING;
	Matcher m_COMMENT;
	
	/**
	 * constructor (sets the String to be tokenized).
	 * @param input
	 */
	public Tokenizer(String input) {
		this.input=input.trim();
		m_INT=p_INT.matcher(input);
		m_FLOAT=p_FLOAT.matcher(input);
		m_NAME=p_NAME.matcher(input);
		m_GROUPING=p_GROUPING.matcher(input);
		m_OPERATOR=p_OPERATOR.matcher(input);
		m_STRING=p_STRING.matcher(input);
		m_COMMENT=p_COMMENT.matcher(input);
		setRegion(0);	
	}
	/**
	 * returns true if there are more tokens to be returned.
	 */
	@Override
	public boolean hasNext() {
		if(post<input.length()) return true;
		else return false;
	}
	/**
	 * returns the next token from the string.
	 */
	@Override
	public Token next() {
		if(!backUp) {
			if(!hasNext()) {
				throw new NoSuchElementException();
			}
			else setRegion(post);
		}
		if(m_FLOAT.lookingAt()) {
			pre=m_FLOAT.start();
			post=m_FLOAT.end();
			backUp=false;
			return new Token(TokenType.FLOAT,input.substring(pre, post).trim());
		}
		else if(m_INT.lookingAt()) {
			pre=m_INT.start();
			post=m_INT.end();
			backUp=false;
			return new Token(TokenType.INT,input.substring(pre, post).trim());	
		}
		else if(m_NAME.lookingAt()) {
			pre=m_NAME.start();
			post=m_NAME.end();
			backUp=false;
			return new Token(TokenType.NAME,input.substring(pre, post).trim());
		}
		else if(m_GROUPING.lookingAt()) {
			pre=m_GROUPING.start();
			post=m_GROUPING.end();
			backUp=false;
			return new Token(TokenType.GROUPING_SYMBOL,input.substring(pre, post).trim());
		}
		else if(m_OPERATOR.lookingAt()) {
			pre=m_OPERATOR.start();
			post=m_OPERATOR.end();
			backUp=false;
			return new Token(TokenType.OPERATOR,input.substring(pre, post).trim());
		}
		else if(m_STRING.lookingAt()) {
			pre=m_STRING.start();
			post=m_STRING.end();
			backUp=false;
			return new Token(TokenType.STRING,input.substring(pre, post).trim());
		}
		else if(m_COMMENT.lookingAt()) {
			pre=m_COMMENT.start();
			post=m_COMMENT.end();
			backUp=false;
			return new Token(TokenType.COMMENT,input.substring(pre, post).trim());
		}
		else {
			pre=m_INT.regionStart();
			post=pre+1;
			backUp=false;
			return new Token(TokenType.ERROR,input.substring(pre, post));
		}
	}
	/**
	 * set the region for the matchers
	 * @param n
	 */
	private void setRegion(int n) {
		m_INT.region(n, input.length());
		m_FLOAT.region(n, input.length());
		m_NAME.region(n, input.length());	
		m_GROUPING.region(n, input.length());
		m_OPERATOR.region(n, input.length());
		m_STRING.region(n, input.length());
		m_COMMENT.region(n, input.length());
	}
	/**
	 * "backs up" one token, so that whatever was returned from the most recent call to next() will be returned again the next time next() is called.
	 */
	public void backUp() {
		backUp=true;
	}
	
	@Override
	public void remove() {
		// TODO Auto-generated method stub
		
	}





}
