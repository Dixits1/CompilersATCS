package ast;

import emitter.Emitter;

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

    /**
     * Compiles the while loop into assembly code and emits the assembly code to an output file.
     * @param e the emitter which emits the assembly code to the output file
     */
    public void compile(Emitter e)
    {
        int labelID = e.nextLabelID();
        String whileLoop = "whileLoop" + labelID;
        String endWhileLoop = "endWhileLoop" + labelID;

        e.emit("# jump to while loop");

        e.emit("j " + whileLoop);

        e.emit(whileLoop + ":");

        e.emit("# evluate condition for while loop and jump to end while loop if false");
        condition.compile(e, endWhileLoop);
        e.emit("# contents of while loop");
        statement.compile(e);
        e.emit("j " + whileLoop);
        e.emit("# after while loop");
        e.emit(endWhileLoop + ":");
    }
}
