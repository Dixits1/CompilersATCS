package ast;

import java.util.List;

import emitter.Emitter;

/**
 * Class representing a program in the AST.
 * @author Arjun Dixit
 * @version 04/17/22
 */
public class Program extends Statement
{
    private List<String> variables;
    private List<ProcedureDeclaration> procedures;
    private Statement stmt;

    /**
     * Creates a program object with the list of procedures and statement to be
     * executed.
     * @param procedures specifies the list of procedures as ProcedureDeclaration objects
     * @param variables specifies the list of variables as strings
     * @param stmt specifies the statement to be executed
     */
    public Program(List<String> variables, List<ProcedureDeclaration> procedures, Statement stmt)
    {
        this.variables = variables;
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
        for (String var : variables)
        {
            env.declareVariable(var, 0);
        }
        for (ProcedureDeclaration pd : procedures)
        {
            pd.exec(env);
        }
        stmt.exec(env);
    }

    /**
     * Compiles the program into assembly code and emits the assembly code to an output file.
     * @param e the emitter which emits the assembly code to the output file
     */
    public void compile(Emitter e) 
    {
        String[] dataHeader = new String[] {
            ".data",
            "newLine: .asciiz \"\\n\""
        };

        String[] init = new String[] {
            ".text 0x00400000",
            ".globl main",
            "main:"
        };
        
        String[] end = new String[] {
            "li $v0, 10",
            "syscall"};

        for (String line : dataHeader)
        {
            e.emit(line);
        }

        for (String var : variables)
        {
            e.emit(var + ": .word 0");
        }

        for (String line : init)
        {
            e.emit(line);
        }

        stmt.compile(e);

        for (String line : end)
        {
            e.emit(line);
        }

        e.close();
    }
}
