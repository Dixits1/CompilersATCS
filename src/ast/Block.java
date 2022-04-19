package ast;

import java.util.List;

/**
 * Class representing a block of statements.
 * 
 * @author Arjun Dixit
 * @version 4/17/22
 */
public class Block extends Statement 
{
    private List<Statement> stmts;

    /**
     * Creates a block object with the list of statements stmts.
     * @param stmts specifies the list of statements
     */
    public Block(List<Statement> stmts) 
    {
        this.stmts = stmts;
    }

    /**
     * Executes the block of statements.
     * @param env specifies the environment in which the block of statements is executed
     * @throws Exception if the block of statements is invalid
     */
    public void exec(Environment env) throws Exception 
    {
        for (Statement stmt : stmts) 
        {
            stmt.exec(env);
        }
    }
}
