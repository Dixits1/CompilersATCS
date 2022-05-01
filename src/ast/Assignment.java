package ast;

import emitter.Emitter;
/**
 * Assignment specifies a statement representing an assignment of a value to a variable.
 * Contains method exec, which executes the assignment statement.
 * @author Arjun Dixit
 * @version 4/17/22
 * 
 */
public class Assignment extends Statement 
{
    private String var;
    private Expression exp;

    /**
     * Creates an assigment object with the variable name var on the left hand side
     * and the expression exp on the right hand side.
     * @param var the variable name on the left hand side of the assignment
     * @param exp the expression on the right hand side of the assignment
     */
    public Assignment(String var, Expression exp) 
    {
        this.var = var;
        this.exp = exp;
    }

    /**
     * Executes the assignment statement.
     * @param env the environment in which the assignment statement is executed
     * @throws Exception if the assignment statement is invalid
     */
    public void exec(Environment env) throws Exception 
    {
        env.setVariable(var, exp.eval(env));
    }

    /**
     * Compiles the assignment into assembly code and emits the assembly code to an output file.
     * @param e the emitter which emits the assembly code to the output file
     */
    public void compile(Emitter e)
    {
        exp.compile(e);
        e.emit("la $t0, " + var);
        e.emit("sw $v0, ($t0)");
    }
}
