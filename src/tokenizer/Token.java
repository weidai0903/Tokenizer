package tokenizer;
/**
 * A Token has a type and a text. 
 * @author Wei Dai
 *
 */
public class Token {
	public final TokenType type;
	public final String text;
    /**
     * Constructor
     * @param type
     * @param text
     */
    public Token(TokenType type, String text) {
    	this.type=type;
    	this.text=text;
    }
    public boolean equals(Object obj) {
    	Token that=(Token)obj;
    	return this.type==that.type&&this.text.equals(that.text);
    }
    /**
     * returns a string of the form type:text (no spaces).
     */
    public String toString() {
    	return type+":"+text;
    }
    public int hashCode() {
    	return this.text.hashCode()+this.type.hashCode();
    }
}
