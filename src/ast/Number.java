package ast;

/**
 * Class representing a number in the AST.
 * Contains method eval which returns the value of the number.
 * @author Arjun Dixit
 * @version 4/17/22
 */
public class Number extends Expression 
{
    private int value;

    /**
     * Creates a number object with the value val.
     * @param value the value of the number
     */
    public Number(int value) 
    {
        this.value = value;
    }

    /**
     * Returns the value of the number as an integer.
     * @param env specifies the environment in which the number is evaluated
     * @return returns the value of the number as an integer
     */
    public int eval(Environment env) 
    {
        return value;
    }
}
