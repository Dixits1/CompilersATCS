package ast;

import java.util.HashMap;
import java.util.Map;

/**
 * The Environment class represents an environment in which code is executed, where each
 * environment has its own set of variables and procedures.
 * Contains methods for setting a variable's value and getting a variable's value, as well as
 * methods for setting and retrieving procedures.
 * @author Arjun Dixit
 * @version 04/17/22
 */
public class Environment 
{
    public Map<String, Integer> variables;
    private Map<String, ProcedureDeclaration> procedures;
    private Environment parent;

    /**
     * Creates an environment object with an empty set of variables and procedures.
     */
    public Environment()
    {
        variables = new HashMap<String, Integer>();
        procedures = new HashMap<String, ProcedureDeclaration>();
        parent = null;
    }
    
    /**
     * Creates an environment object with an empty set of variables and procedures
     * along with the given parent environment as the parent.
     * @param parent specifies the parent environment
     */
    public Environment(Environment parent)
    {
        variables = new HashMap<String, Integer>();
        procedures = new HashMap<String, ProcedureDeclaration>();
        this.parent = parent;
    }

    /**
     * Declares the variable of the specified name with the specified value in the current
     * environment.
     * @param variable specifies the name of the variable
     * @param value specifies the value to which the variable is declared
     */
    public void declareVariable(String variable, int value) 
    {
        variables.put(variable, value);
    }

    /**
     * Sets the value of the variable of the specified name to the specified value in the parent
     * environment if the variable is not already declared in the current environment and the
     * parent environment contains the variable; otherwise, declares the variable in the current
     * environment with the specified value.
     * @param variable specifies the name of the variable
     * @param value specifies the value to which the variable is set
     */
    public void setVariable(String variable, int value)
    {
        if(variables.get(variable) == null &&
                parent != null && parent.variables.get(variable) != null) 
        {
            parent.variables.put(variable, value);
        }
        else 
        {
            declareVariable(variable, value);
        }
    }

    /**
     * Gets the value of the variable with the specified name from the current environment.
     * If the value doesn't exist in the current environment and the current environment has a
     * parent, the value is retrieved from the parent environment. If no value exists in the parent
     * environment or there is no parent environment, then an exception is thrown.
     * @param variable specifies the name of the variable
     * @throws RuntimeException if the variable is not found in the local or parent enviornment.
     * @return returns the value of the variable
     */
    public int getVariable(String variable) throws RuntimeException
    {
        Integer val = variables.get(variable);

        if (val == null && parent != null) 
        {
            val = parent.getVariable(variable);
        }

        if (val == null)
        {
            throw new RuntimeException("Variable " + variable + " not found.");
        }

        return val;
    }

    /**
     * Gets the statement of the procedure with the specified name.
     * @param name specifies the name of the procedure
     * @return returns the statement that runs when the procedure is called
     */
    public ProcedureDeclaration getProcedure(String name) 
    {
        ProcedureDeclaration proc;

        if (parent != null) 
        {
            proc = parent.getProcedure(name);
        }
        else 
        {
            proc = procedures.get(name);
        }

        if (proc == null) 
        {
            throw new RuntimeException("Procedure " + proc + " not found");
        }

        return proc;
    }

    /**
     * Sets the procedure with the specified name to the specified statement.
     * @param name specifies the name of the procedure
     * @param proc specifies the procedure
     */
    public void setProcedure(String name, ProcedureDeclaration proc) 
    {
        if (parent != null)
        {
            parent.setProcedure(name, proc);
        }
        else 
        {
            procedures.put(name, proc);
        }
    }  
}
