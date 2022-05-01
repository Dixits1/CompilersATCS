package ast;

import emitter.Emitter;

/**
 * Class representing a variable in the AST.
 * Contains method eval which returns the value of the variable.
 * @author Arjun Dixit
 * @version 4/17/22
 */
public class Variable extends Expression 
{
    /**
     * The prefix before every variable name.
     */
    public static final String NAME_PREFIX = "var";

    private String name;

    /**
     * Creates a variable object with the name name.
     * @param name specifies the name of the variable
     */
    public Variable(String name) 
    {
        this.name = name;
    }

    /**
     * Returns the value of the variable as an integer.
     * @param env specifies the environment in which the variable's value in retrieved
     * @return returns the value of the variable as an integer
     */
    public int eval(Environment env) 
    {
        return env.getVariable(name);
    }

    /**
     * Compiles the variable into assembly code and emits the assembly code to an output file.
     * @param e the emitter which emits the assembly code to the output file
     */
    public void compile(Emitter e)
    {
        e.emit("la $t0, " + name);
        e.emit("lw $v0, ($t0)");
    }
}
