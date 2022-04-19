package ast;

/**
 * Class representing a while loop in the AST.
 * Contains method exec which executes the loop.
 * @author Arjun Dixit
 * @version 4/17/22
 */
public class While extends Statement 
{
    private Condition condition;
    private Statement statement;

    /**
     * Creates a while loop object with the condition and the statement to be executed
     * in the loop while the condition is true.
     * @param condition the condition
     * @param statement the statement to be executed while the condition is true
     */
    public While(Condition condition, Statement statement) 
    {
        this.condition = condition;
        this.statement = statement;
    }

    /**
     * Executes the while loop.
     * @param env specifies the environment in which the while loop is executed
     * @throws Exception if an error occurs during the loop execution
     */
    public void exec(Environment env) throws Exception 
    {
        while (condition.eval(env)) 
        {
            statement.exec(env);
        }
    }
}
