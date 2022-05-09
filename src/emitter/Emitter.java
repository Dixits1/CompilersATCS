package emitter;
import java.io.*;

/**
 * Emitter class for the compiler, which emits the assembly code to an output file.
 * 
 * @author Arjun Dixit
 * @author Ms. Datar
 * @version 5/1/22
 */
public class Emitter
{
    private int labelCount;
    private PrintWriter out;

    /**
     * creates an emitter for writing to a new file with given name
     * @param outputFileName the name of the file to write to
     */
    public Emitter(String outputFileName)
    {
        labelCount = 1;
        try
        {
            out = new PrintWriter(new FileWriter(outputFileName), true);
        }
        catch(IOException e)
        {
            throw new RuntimeException(e);
        }
    }

    /** 
     * prints one line of code to file (with non-labels indented)
     * @param code the string of code to print
     */
    public void emit(String code)
    {
        if (!code.endsWith(":"))
            code = "\t" + code;
        out.println(code);
    }

    /**
     * closes the file.  should be called after all calls to emit.
     */
    public void close()
    {
        out.close();
    }

    /**
     * pushes a value onto the stack
     * @param reg specifies the register that stores the value to be pushed
     */
    public void emitPush(String reg)
    {
        emit("subu $sp, $sp, 4 /t # push " + reg);
        emit("sw " + reg + ", ($sp)");
    }

    /**
     * pops a value off the stack
     * @param reg specifies the register that will store the value popped off the stack
     */
    public void emitPop(String reg) 
    {
        emit("lw " + reg + ", ($sp) /t # pop " + reg);
        emit("addu $sp, $sp, 4");
    }

    /**
     * @return returns the next label's ID as the currenlt label count and increments the
	 * label count.
     */
    public int nextLabelID()
    {
        return labelCount++;
    }
}