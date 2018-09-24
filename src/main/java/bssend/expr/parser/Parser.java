package bssend.expr.parser;

import bssend.expr.BinaryOperatorType;
import bssend.expr.TokenType;
import bssend.expr.exception.ExprCompileException;
import bssend.expr.node.BinaryExprNode;
import bssend.expr.node.FunctionCallNode;
import bssend.expr.node.INode;
import bssend.expr.node.ValueNode;
import bssend.expr.parser.ILexer;
import bssend.expr.parser.IParser;
import bssend.expr.parser.Lexer;
import bssend.expr.util.ITokenSequence;
import bssend.expr.util.StringSequence;
import lombok.NonNull;
import lombok.var;

import java.util.ArrayList;
import java.util.Arrays;

import static bssend.expr.TokenType.*;

public class Parser implements IParser {

    private final ILexer scanner = new Lexer();

    @Override
    public INode parse(@NonNull final String s) {
        return andNode(scanner.scan(new StringSequence(s)));
    }

    /**
     * value =
     *     string |
     *     integer |
     *     decimal |
     *     boolean
     * @param tokens
     * @return
     */
    private INode valueNode (@NonNull final ITokenSequence tokens) {

        switch (tokens.peek().getType()) {
            case STRING:
                return ValueNode.of(
                        tokens.next().getContent());
            case INTEGER:
                return ValueNode.of(
                        Integer.valueOf(tokens.next().getContent()));
            case DECIMAL:
                return ValueNode.of(
                        Double.valueOf(tokens.next().getContent()));
            case BOOLEAN:
                return ValueNode.of(
                        Boolean.valueOf(tokens.next().getContent()));
        }

        throw new IllegalArgumentException("Undefined value.");
    }

    /**
     * logical =
     *     compare
     *     compare "&&" compare
     *     compare "||" compare
     *
     * @param tokens
     * @return
     */
    private INode andNode(@NonNull final ITokenSequence tokens) {
        var returnNode = orNode(tokens);

        while (!tokens.isEnd()) {

            if (AND_OP != tokens.peek().getType())
                break;

            var operator = getBinaryOperator(tokens.next().getType());
            var rightNode = orNode(tokens);

            returnNode = BinaryExprNode.builder()
                    .leftNode(returnNode)
                    .rightNode(rightNode)
                    .operator(operator)
                    .build();
        }

        return returnNode;
    }
    /**
     * logical =
     *     compare
     *     compare "&&" compare
     *     compare "||" compare
     *
     * @param tokens
     * @return
     */
    private INode orNode(@NonNull final ITokenSequence tokens) {
        var returnNode = compareNode(tokens);

        while (!tokens.isEnd()) {

            if (OR_OP != tokens.peek().getType())
                break;

            var operator = getBinaryOperator(tokens.next().getType());
            var rightNode = compareNode(tokens);

            returnNode = BinaryExprNode.builder()
                    .leftNode(returnNode)
                    .rightNode(rightNode)
                    .operator(operator)
                    .build();
        }

        return returnNode;
    }

    /**
     * compare =
     *     expr
     *     expr "==" expr
     *     expr "!=" expr
     *     expr ">" expr
     *     expr "<" expr
     *     expr ">=" expr
     *     expr "<=" expr
     * @param tokens
     * @return
     */
    private INode compareNode(@NonNull final ITokenSequence tokens) {
        var returnNode = exprNode(tokens);

        while (!tokens.isEnd()) {

            if (!Arrays.asList(EQ_OP, NE_OP, LT_OP, LTE_OP, GT_OP, GTE_OP)
                    .contains(tokens.peek().getType()))
                break;

            var operator = getBinaryOperator(tokens.next().getType());
            var rightNode = exprNode(tokens);

            returnNode = BinaryExprNode.builder()
                    .leftNode(returnNode)
                    .rightNode(rightNode)
                    .operator(operator)
                    .build();
        }

        return returnNode;
    }


    /**
     * expr =
     *     term
     *     term + term
     *     term - term
     * @param tokens
     * @return
     */
    private INode exprNode(@NonNull final ITokenSequence tokens) {
        var returnNode = termNode(tokens);

        while (!tokens.isEnd()) {

            if (!Arrays.asList(ADD_OP, SUB_OP)
                    .contains(tokens.peek().getType()))
                break;

            var operator = getBinaryOperator(tokens.next().getType());
            var rightNode = termNode(tokens);

            returnNode = BinaryExprNode.builder()
                    .leftNode(returnNode)
                    .rightNode(rightNode)
                    .operator(operator)
                    .build();
        }

        return returnNode;
    }

    /**
     * term =
     *     factor
     *     factor * factor
     *     factor / factor
     *     factor % factor
     *
     * @param tokens
     * @return
     */
    private INode termNode(@NonNull final ITokenSequence tokens) {
        var returnNode = factorNode(tokens);

        while (!tokens.isEnd()) {

            if (!Arrays.asList(MUL_OP, DIV_OP, MOD_OP)
                    .contains(tokens.peek().getType()))
                break;

            var operator = getBinaryOperator(tokens.next().getType());
            var rightNode = factorNode(tokens);

            returnNode = BinaryExprNode.builder()
                    .leftNode(returnNode)
                    .rightNode(rightNode)
                    .operator(operator)
                    .build();
        }

        return returnNode;
    }


    /**
     * factor =
     *     value |
     *     function_call |
     *     [LPAREN] expr [RPAREN]
     * @param tokens
     * @return
     */
    private INode factorNode(@NonNull final ITokenSequence tokens) {

        if (tokens.isEnd())
            throw new IllegalArgumentException("Tokens must not be terminated.");

        switch (tokens.peek().getType()) {
            case IDENTIFIER:
                return functionCallNode(tokens);
            case STRING:
            case INTEGER:
            case DECIMAL:
            case BOOLEAN:
                return valueNode(tokens);
            case LPAREN:
                tokens.next(); // LPAREN

                var exprNode = exprNode(tokens);

                if (tokens.isEnd() || tokens.next().getType() != RPAREN)
                    throw new ExprCompileException("RPAREN is required at the end of factor.");

                return exprNode;
            default:
                break;
        }
        throw new IllegalArgumentException(".");

//        // function call
//        if (tokens.peek().getType() == IDENTIFIER)
//            return functionCallNode(tokens);
//
//        // value
//        if (Arrays.asList(STRING, INTEGER, DECIMAL, BOOLEAN)
//                    .contains(tokens.peek().getType()))
//            return valueNode(tokens);
//
//        // factor
//        if (tokens.next().getType() == LPAREN) {
//
//            var exprNode = exprNode(tokens);
//
//            if (tokens.isEnd() || tokens.next().getType() != RPAREN)
//                throw new ExprCompileException("RPAREN not found.");
//
//            return exprNode;
//        }
//
//        if (tokens.isEnd() || tokens.next().getType() != LPAREN)
//            throw new ExprCompileException("LPAREN not found.");
//
//        var exprNode = exprNode(tokens);
//
//        if (tokens.isEnd() || tokens.next().getType() != RPAREN)
//            throw new ExprCompileException("RPAREN not found.");

//        return exprNode;
    }

    /**
     * <pre>
     * function_call =
     *     [IDENTIFIER] [LPAREN] factor *([COMMA]factor) [RPAREN]
     * </pre>
     * @param tokens
     * @return
     */
    private INode functionCallNode(@NonNull final ITokenSequence tokens) {

        if (tokens.isEnd() || tokens.peek().getType() != IDENTIFIER)
            throw new IllegalArgumentException("first token must be IDENTIFIER.");

        var functionName = tokens.next().getContent();

        if (tokens.isEnd() || tokens.next().getType() != LPAREN)
            throw new ExprCompileException(
                    "LPAREN is required at the beginning of function call.");

        var arguments = new ArrayList<INode>();

        while (!tokens.isEnd() && tokens.peek().getType() != RPAREN) {
            if (arguments.size() > 0 && tokens.next().getType() != COMMA)
                throw new ExprCompileException(
                        "COMMA is required to delimit arguments of function call.");

            var argument = factorNode(tokens);
            arguments.add(argument);
        }

        if (tokens.isEnd() || tokens.next().getType() != RPAREN)
            throw new ExprCompileException(
                    "RPAREN is required at the end of function call.");

        return FunctionCallNode.builder()
                .functionName(functionName)
                .arguments(arguments)
                .build();
    }

    private BinaryOperatorType getBinaryOperator(TokenType tokenType) {
        switch (tokenType) {
            case ADD_OP:
                return BinaryOperatorType.Add;
            case SUB_OP:
                return BinaryOperatorType.Sub;
            case MUL_OP:
                return BinaryOperatorType.Mul;
            case DIV_OP:
                return BinaryOperatorType.Div;
            case MOD_OP:
                return BinaryOperatorType.Mod;
            case EQ_OP:
                return BinaryOperatorType.Equal;
            case NE_OP:
                return BinaryOperatorType.NotEqual;
            case LT_OP:
                return BinaryOperatorType.LessThan;
            case LTE_OP:
                return BinaryOperatorType.LessThanEqual;
            case GT_OP:
                return BinaryOperatorType.GreaterThan;
            case GTE_OP:
                return BinaryOperatorType.GreaterThanEqual;
            case AND_OP:
                return BinaryOperatorType.And;
            case OR_OP:
                return BinaryOperatorType.Or;
        }
        throw new IllegalArgumentException("can't convert binary operator.");
    }
}
