package ast;

import java.util.List;

/**
 * Class representing a program in the AST.
 * @author Arjun Dixit
 * @version 04/17/22
 */
public class Program extends Statement
{
    private List<ProcedureDeclaration> procedures;
    private Statement stmt;

    /**
     * Creates a program object with the list of procedures and statement to be
     * executed.
     * @param procedures specifies the list of procedures
     * @param stmt specifies the statement to be executed
     */
    public Program(List<ProcedureDeclaration> procedures, Statement stmt)
    {
        this.procedures = procedures;
        this.stmt = stmt;
    }

    /**
     * Executes the program, i.e., stores the procedures in the environment 
     * and executes the statement.
     * @param env specifies the environment in which the program is executed
     * @throws Exception if an error occurs during the program execution
     */
    public void exec(Environment env) throws Exception
    {
        for (ProcedureDeclaration pd : procedures)
        {
            pd.exec(env);
        }
        stmt.exec(env);
    }
}
