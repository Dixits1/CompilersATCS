package parser;

import java.util.ArrayList;
import java.util.List;

import ast.*;
import ast.Number;
import scanner.*;

/**
 * Parser contains methods that parse the input stream of tokens to produce an abstract syntax tree
 * of the program.
 * @author Arjun Dixit
 * @version 3/9/2022
 */
public class Parser 
{

    private String currentToken;
    private Scanner scanner;

    /**
     * Constructs a parser object that takes in a scanner object.
     * @param scanner the scanner that will be used by the parser to parse the input
     * @throws ScanErrorException if an error occurs while scanning
     */
    public Parser(Scanner scanner) throws ScanErrorException 
    {
        this.scanner = scanner;
        currentToken = scanner.nextToken();
    }

    /**
     * Rakes in a String representing the expected token and compares it to the current token.  
     * If the two tokens match, the current token is set to the next token in the input stream. 
     * If the two tokens do not match, an error is thrown.
     * @param expectedToken specifies the expected token
     * @throws ScanErrorException if the expected token does not match the current token
     */
    private void eat(String expectedToken) throws ScanErrorException
    {
        if (currentToken.equals(expectedToken)) 
        {
            currentToken = scanner.nextToken();
        }
        else 
        {
            throw new IllegalArgumentException(
                "Expected " + expectedToken + " but found " + currentToken
                );
        }
    }
    
    /**
     * parseNumber is a private method that parses a number token and returns the value 
     * of the number.
     * @return the value of the number
     * @throws ScanErrorException if the expected token does not match the current token
     */
    private Number parseNumber() throws ScanErrorException 
    {
        Number num = new Number(Integer.parseInt(currentToken));
        eat(currentToken);

        return num;
    }

    /**
     * Parses the program as a set of procedures followed by a statement and returns
     * a program object representing the program.
     * @return returns a program object representing the program
     * @throws ScanErrorException if an error occurs while scanning
     */
    public Program parseProgram() throws ScanErrorException
    {
        List<ProcedureDeclaration> procedures = new ArrayList<ProcedureDeclaration>();
        
        while(currentToken.equals("PROCEDURE"))
        {
            eat("PROCEDURE");
            String name = currentToken;
            eat(currentToken);
            eat("(");
            List<String> args = new ArrayList<String>();
            while(!currentToken.equals(")"))
            {
                args.add(currentToken);
                eat(currentToken);
                if(!currentToken.equals(")"))
                {
                    eat(",");
                }
            }
            eat(")");
            eat(";");
            procedures.add(new ProcedureDeclaration(name, args, parseStatement()));
        }

        return new Program(procedures, parseStatement());
    }

    /**
     * parseStatement is a private method that parses a statement and returns the statement.
     * @throws ScanErrorException if the expected token does not match the current token
     * @return returns the statement.
     */
    public Statement parseStatement() throws ScanErrorException 
    {
        Statement stmt;

        if(currentToken.equals("WRITELN")) 
        {
            eat("WRITELN");
            eat("(");
            Expression exp = parseExpr();
            eat(")");
            eat(";");
            stmt = new Writeln(exp);
        }
        else if(currentToken.equals("BEGIN"))
        {
            eat("BEGIN");

            List<Statement> stmts = new ArrayList<Statement>();
            while(!currentToken.equals("END"))
            {
                stmts.add(parseStatement());
            }

            eat("END");
            eat(";");

            stmt = new Block(stmts);
        }
        else if(currentToken.equals("IF")) 
        {
            eat("IF");
            Condition condition = parseCondition();
            eat("THEN");
            Statement ifStmt = parseStatement();
            if(currentToken.equals("ELSE")) 
            {
                eat("ELSE");
                Statement elseStmt = parseStatement();
                stmt = new If(condition, ifStmt, elseStmt);
            }
            else 
            {
                stmt = new If(condition, ifStmt);
            }
        }
        else if(currentToken.equals("WHILE"))
        {
            eat("WHILE");
            Condition condition = parseCondition();
            eat("DO");
            Statement whileStmt = parseStatement();
            stmt = new While(condition, whileStmt);
        }
        else
        {
            String varName = currentToken;
            eat(currentToken);
            eat(":=");
            Expression exp = parseExpr();
            eat(";");

            stmt = new Assignment(varName, exp);
        }

        return stmt;
    }

    /**
     * parseCondition is a private method that parses a condition and returns the condition.
     * @return returns the condition.
     * @throws ScanErrorException if the expected token does not match the current token
     */
    private Condition parseCondition() throws ScanErrorException 
    {
        Expression exp1 = parseExpr();
        String op = currentToken;
        eat(currentToken);
        Expression exp2 = parseExpr();
        return new Condition(op, exp1, exp2);
    }

    /**
     * parseFactor is a private method that parses a factor and returns the factor.
     * @return returns the factor as an Expression
     * @throws ScanErrorException if the expected token does not match the current token
     */
    private Expression parseFactor() throws ScanErrorException 
    {
        Expression num;

        if (currentToken.equals("(")) 
        {
            eat("(");
            num = parseExpr();
            eat(")");
        } 
        else if (currentToken.equals("-")) 
        {
            eat("-");
            num = new BinOp("*", new Number(-1), parseTerm());
        }
        else if (Scanner.isDigit(currentToken.charAt(0)))
        {
            num = parseNumber();
        }
        else 
        {
            String id = currentToken;
            eat(currentToken);

            if (currentToken.equals("(")) 
            {
                eat("(");
                List<Expression> args = new ArrayList<Expression>();
                while (!currentToken.equals(")")) 
                {
                    args.add(parseExpr());
                    if (!currentToken.equals(")")) 
                    {
                        eat(",");
                    }
                }
                eat(")");

                num = new ProcedureCall(id, args);
            } 
            else
            {
                num = new Variable(id);
            }
        }

        return num;
    }

    /**
     * parseTerm is a private method that parses a term and returns the term.
     * @return returns the term as an Expression
     * @throws ScanErrorException if the expected token does not match the current token
     */
    private Expression parseTerm() throws ScanErrorException
    {
        Expression exp = parseFactor();
        String op;

        while (currentToken.equals("*") || currentToken.equals("/")) 
        {
            op = currentToken;
            eat(currentToken);
            exp = new BinOp(op, exp, parseFactor());
        }

        return exp;
    }

    /**
     * parseExpr is a private method that parses an expression and returns the value of
     * the expression. An expression is defined by a term potentially followed by a sequence
     * of added or subtracted terms.
     * @return returns the value of the expression
     * @throws ScanErrorException if the expected token does not match the current token
     */
    private Expression parseExpr() throws ScanErrorException
    {
        Expression exp = parseTerm();
        String op;

        while (currentToken.equals("+") || currentToken.equals("-")) 
        {
            op = currentToken;
            eat(currentToken);
            exp = new BinOp(op, exp, parseTerm());
        }

        return exp;
    }
}
