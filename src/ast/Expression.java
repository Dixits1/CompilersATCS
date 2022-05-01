package ast;

import emitter.Emitter;

/**
 * Abstract class representing an expression in the AST.
 * @author Arjun Dixit
 * @version 04/17/22
 */
public abstract class Expression
{
    /**
     * Evaluates the expression.
     * @param env specifies the environment in which the expression is evaluated
     * @return returns the result of the expression as an integer
     * @throws Exception if the expression is invalid
     */
    public abstract int eval(Environment env) throws Exception;


    /**
     * Compiles the class into assembly code and emits the assembly code to an output file.
     * @param e the emitter which emits the assembly code to the output file
     */
    public abstract void compile(Emitter e);
}
