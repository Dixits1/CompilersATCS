package ast;

import emitter.Emitter;

/**
 * Class that reperesents a binary operation within an expression. Stores the expression
 * on the left side of the operator and the expression on the right side of the operator,
 * as well as the operator itself.
 * Contains the method eval which evaluates the binary operation.
 * @author Arjun Dixit
 * @version 4/17/22
 */
public class BinOp extends Expression
{
    private String op;
    private Expression exp1;
    private Expression exp2;

    /**
     * Creates a binary operation object with the operator op, the expression on the left
     * side of the operator, and the expression on the right side of the operator.
     * @param op the operator
     * @param exp1 the expression on the left side of the operator
     * @param exp2 the expression on the right side of the operator
     */
    public BinOp(String op, Expression exp1, Expression exp2) 
    {
        this.op = op;
        this.exp1 = exp1;
        this.exp2 = exp2;
    }

    /**
     * Evaluates the operation.
     * @param env specifies the environment in which the operation is evaluated
     * @return returns the result of the operation
     * @throws Exception if the operator is unknown
     */
    public int eval(Environment env) throws Exception 
    {
        int val1 = exp1.eval(env);
        int val2 = exp2.eval(env);
        if (op.equals("+"))
            return val1 + val2;
        else if (op.equals("-"))
            return val1 - val2;
        else if (op.equals("*"))
            return val1 * val2;
        else if (op.equals("/"))
            return val1 / val2;
        else
            throw new Exception("Unknown operator: " + op);
    }
    
    /**
     * Compiles the BinOp into assembly code and emits the assembly code to an output file.
     * @param e the emitter which emits the assembly code to the output file
     */
    public void compile(Emitter e) 
    {
        e.emit("# evaluate binary operation and store result in $v0");

        exp1.compile(e);
        e.emitPush("$v0");
        exp2.compile(e);
        e.emitPop("$t0");
        
        if(op.equals("+"))
            e.emit("addu $v0, $t0, $v0");
        else if(op.equals("-"))
            e.emit("subu $v0, $t0, $v0");
        else if(op.equals("*"))
        {
            e.emit("mult $t0, $v0");
            e.emit("mflo $v0");
        }
        else if(op.equals("/"))
        {
            e.emit("div $t0, $v0");
            e.emit("mflo $v0");
        }
    }
}
