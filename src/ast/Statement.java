package ast;

/**
 * Class representing a statement to be executed in the AST.
 * @author Arjun Dixit
 * @version 4/17/22
 */
public abstract class Statement
{
    /**
     * Executes the statement.
     * @param env specifies the environment in which the statement is executed
     * @throws Exception if an error occurs during the statement execution
     */
    public abstract void exec(Environment env) throws Exception;
}
