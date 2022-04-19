package ast;


/**
 * A class to represent an if statement, with a condition, statement for if the condition is true,
 * and statement for if the condition is false (else statement is optional).
 * Contains method exec which executes the if statement.
 * @author Arjun Dixit
 * @version 4/17/22
 */
public class If extends Statement 
{

    private Condition condition;
    private Statement ifStatement;
    private Statement elseStatement;

    /**
     * Creates an if statement object with the condition, the statement for if the 
     * condition is true, and the statement for if the condition is false.
     * @param condition the condition
     * @param thenStatement the statement for if the condition is true
     * @param elseStatement the statement for if the condition is false
     */
    public If(Condition condition, Statement thenStatement, Statement elseStatement) 
    {
        this.condition = condition;
        this.ifStatement = thenStatement;
        this.elseStatement = elseStatement;
    }

    /**
     * Creates an if statement object with the condition and the statement for if the 
     * condition is true.
     * @param condition the condition
     * @param thenStatement the statement for if the condition is true
     */
    public If(Condition condition, Statement thenStatement) 
    {
        this.condition = condition;
        this.ifStatement = thenStatement;
        this.elseStatement = null;
    }

    /**
     * Executes the if statement.
     * @param env specifies the environment in which the if statement is executed
     * @throws Exception if the statement is invalid
     */
    public void exec(Environment env) throws Exception 
    {
        if (condition.eval(env)) 
        {
            ifStatement.exec(env);
        } 
        else 
        {
            if(elseStatement != null)
            {
                elseStatement.exec(env);
            }
        }
    }

}
