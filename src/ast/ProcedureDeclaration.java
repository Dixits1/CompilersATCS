package ast;

import java.util.List;

/**
 * Class representing a procedure declaration in the AST.
 * 
 * @author Arjun Dixit
 * @version 4/17/22
 */
public class ProcedureDeclaration extends Statement
{
    private String name;
    private List<String> args;
    private Statement stmt;

    /**
     * Creates a procedure declaration object with the procedure name and
     * the statement to be executed when the procedure is called.
     * @param name specifies the name of the procedure
     * @param args specifies the arguments of the procedure
     * @param stmt specifies the statement to be executed when the procedure is called
     */
    public ProcedureDeclaration(String name, List<String> args, Statement stmt) 
    {
        this.name = name;
        this.args = args;
        this.stmt = stmt;
    }

    /**
     * Executes the procedure declaration.
     * @param env specifies the environment in which the procedure declaration is executed
     * @throws Exception if an error occurs during the procedure declaration execution
     */
    public void exec(Environment env) throws Exception
    {
        env.setProcedure(name, this);
    }

    /**
     * @return returns the arguments as a lsit of strings.
     */
    public List<String> getArgs() 
    {
        return args;
    }

    /**
     * @return returns the statement of the procedure.
     */
    public Statement getStatement() 
    {
        return stmt;
    }
}
