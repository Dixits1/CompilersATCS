package ast;

import java.util.List;

/**
 * A class to represent a procedure call, with a procedure name and a list of arguments.
 * Contains method exec which executes the procedure call.
 * @author Arjun Dixit
 * @version 4/17/22
 */
public class ProcedureCall extends Expression
{
    private String name;
    private List<Expression> args;

    /**
     * Creates a procedure call object with the procedure name and the list of arguments.
     * @param name specifies the name of the procedure
     * @param args specifies the list of arguments
     */
    public ProcedureCall(String name, List<Expression> args)
    {
        this.name = name;
        this.args = args;
    }

    /**
     * Retrieves the procedure from the environment, evaluates the arguments, sets each of the
     * arguments in the environment based on the parameter names , and executes the procedure call.
     * @param env specifies the environment in which the procedure call is executed
     * @throws Exception if an error occurs during the procedure call
     * @return returns 0
     */
    public int eval(Environment env) throws Exception
    {
        env = new Environment(env);

        ProcedureDeclaration proc = env.getProcedure(name);

        List<String> procArgs = proc.getArgs();

        if (args.size() != procArgs.size())
        {
            throw new Exception("Wrong number of arguments in procedure call");
        }
        else 
        {
            for (int i = 0; i < args.size(); i++)
            {
                env.declareVariable(proc.getArgs().get(i), args.get(i).eval(env));
            }
            env.declareVariable(name, 0);

            proc.getStatement().exec(env);
        }

        return env.getVariable(name);
    }
}
