package ast;

/**
 * Class that represents a condition / boolean expression, with a relational 
 * operator and two expressions on each side of the operator.
 * Contains the method eval which evaluates the condition.
 * @author Arjun Dixit
 * @version 4/17/22
 */
public class Condition
{
    private String relop;
    private Expression exp1;
    private Expression exp2;

    /**
     * Creates a condition object with the relational operator relop, the expression on the left
     * side of the operator, and the expression on the right side of the operator.
     * @param relop the relational operator
     * @param exp1 the expression on the left side of the operator
     * @param exp2 the expression on the right side of the operator
     */
    public Condition(String relop, Expression exp1, Expression exp2) 
    {
        this.relop = relop;
        this.exp1 = exp1;
        this.exp2 = exp2;
    }

    /**
     * Evaluates the condition.
     * @param env specifies the environment in which the condition is evaluated
     * @return returns true if condition is true; false otherwise
     * @throws Exception if the operator used in the condition is invalid
     */
    public boolean eval(Environment env) throws Exception 
    {
        int val1 = exp1.eval(env);
        int val2 = exp2.eval(env);

        if (relop.equals("=="))
            return val1 == val2;
        else if (relop.equals("<>"))
            return val1 != val2;
        else if (relop.equals(">"))
            return val1 > val2;
        else if (relop.equals("<"))
            return val1 < val2;
        else if (relop.equals(">="))
            return val1 >= val2;
        else if (relop.equals("<="))
            return val1 <= val2;
        else
            throw new RuntimeException("Unknown operator: " + relop + ".");
    }

}
