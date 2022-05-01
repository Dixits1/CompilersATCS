package ast;

import emitter.Emitter;

/**
 * Class representing a print statement in the AST.
 * Contains method exec which executes the print statement.
 * @author Arjun Dixit
 * @version 4/17/22
 */
public class Writeln extends Statement
{
    private Expression exp;

    /**
     * Creates a print statement object with the expression whose returned value
     * is to be printed.
     * @param exp specifies the expression whose returned value is to be printed
     */
    public Writeln(Expression exp)
    {
        this.exp = exp;
    }

    /**
     * Evaluates the expression and prints the value of the expression.
     * @param env specifies the environment in which the print statement is executed
     * @throws Exception if an error occurs during the print statement execution or expression
     * evaluation.
     */
    public void exec(Environment env) throws Exception 
    {
        System.out.println(exp.eval(env));
    }

    /**
     * Compiles the writeln statement into assembly code and emits the assembly code to 
     * an output file.
     * @param e the emitter which emits the assembly code to the output file
     */
    public void compile(Emitter e)
    {
        exp.compile(e);
        e.emit("move $a0, $v0");
        e.emit("li $v0, 1");
        e.emit("syscall");
        e.emit("la $a0, newLine");
        e.emit("li $v0, 4");
        e.emit("syscall");
    }
}