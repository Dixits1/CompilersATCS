package ast;

import emitter.Emitter;

/**
 * A class to represent an if statement, with a condition, statement for if the condition is true,
 * and statement for if the condition is false (else statement is optional).
 * Contains method exec which executes the if statement.
 * @author Arjun Dixit
 * @version 4/17/22
 */
public class If extends Statement 
{

    private Condition condition;
    private Statement ifStatement;
    private Statement elseStatement;

    /**
     * Creates an if statement object with the condition, the statement for if the 
     * condition is true, and the statement for if the condition is false.
     * @param condition the condition
     * @param thenStatement the statement for if the condition is true
     * @param elseStatement the statement for if the condition is false
     */
    public If(Condition condition, Statement thenStatement, Statement elseStatement) 
    {
        this.condition = condition;
        this.ifStatement = thenStatement;
        this.elseStatement = elseStatement;
    }

    /**
     * Creates an if statement object with the condition and the statement for if the 
     * condition is true.
     * @param condition the condition
     * @param thenStatement the statement for if the condition is true
     */
    public If(Condition condition, Statement thenStatement) 
    {
        this.condition = condition;
        this.ifStatement = thenStatement;
        this.elseStatement = null;
    }

    /**
     * Executes the if statement.
     * @param env specifies the environment in which the if statement is executed
     * @throws Exception if the statement is invalid
     */
    public void exec(Environment env) throws Exception 
    {
        if (condition.eval(env)) 
        {
            ifStatement.exec(env);
        } 
        else 
        {
            if(elseStatement != null)
            {
                elseStatement.exec(env);
            }
        }
    }

    /**
     * Compiles the if statement into assembly code and emits the assembly code to an output file.
     * @param e the emitter which emits the assembly code to the output file
     */
    public void compile(Emitter e)
    {
        String ifID =String.valueOf(e.nextLabelID());
        String ifLabel = "ifthen" + ifID; 
        String elseLabel = "ifelse" + ifID;
        String afterLabel = "ifafter" + ifID;

        if(elseStatement == null)
        {
            condition.compile(e, afterLabel);
            e.emit("j " + ifLabel);

            e.emit(ifLabel + ":");
            ifStatement.compile(e);
            e.emit("j " + afterLabel);
        }
        else
        {
            condition.compile(e, elseLabel);
            e.emit("j " + ifLabel);

            e.emit(ifLabel + ":");
            ifStatement.compile(e);
            e.emit("j " + afterLabel);

            e.emit(elseLabel + ":");
            elseStatement.compile(e);
            e.emit("j " + afterLabel);
        }

        e.emit(afterLabel + ":");
    }
}
