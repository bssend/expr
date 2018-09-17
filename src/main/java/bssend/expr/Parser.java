package bssend.expr;

import bssend.expr.util.ITokenSequence;
import bssend.expr.util.StringSequence;
import lombok.NonNull;
import lombok.var;

import java.util.ArrayList;
import java.util.Arrays;

import static bssend.expr.TokenType.*;

public class Parser implements IParser {

    private final IScanner scanner = new Scanner();

    @Override
    public INode parse(@NonNull final String s) {
        return logicalNode(scanner.scan(new StringSequence(s)));
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
    final INode valueNode (@NonNull final ITokenSequence tokens) {

        if (tokens.peek().getType() == STRING)
            return ValueNode.of(tokens.next().getContent());

        if (tokens.peek().getType() == INTEGER)
            return ValueNode.of(
                    Integer.valueOf(tokens.next().getContent()));

        if (tokens.peek().getType() == DECIMAL)
            return ValueNode.of(
                    Double.valueOf(tokens.next().getContent()));

        if (tokens.peek().getType() == BOOLEAN)
            return ValueNode.of(
                    Boolean.valueOf(tokens.next().getContent()));

        throw new RuntimeException("can not value.");
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
    final INode logicalNode(@NonNull final ITokenSequence tokens) {
        var returnNode = compareNode(tokens);

        while (!tokens.isEnd()) {

            if (!Arrays.asList(AND_OP, OR_OP)
                    .contains(tokens.peek().getType()))
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
    final INode compareNode(@NonNull final ITokenSequence tokens) {
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
    final INode exprNode(@NonNull final ITokenSequence tokens) {
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
    final INode termNode(@NonNull final ITokenSequence tokens) {
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
    final INode factorNode(@NonNull final ITokenSequence tokens) {

        if (tokens.peek().getType() != LPAREN &&
            tokens.peek().getType() == IDENTIFIER)
            return functionCallNode(tokens);

        if (tokens.peek().getType() != LPAREN &&
            tokens.peek().getType() != IDENTIFIER)
            return valueNode(tokens);

        var leftParenIgnore = tokens.next();
        var exprNode = exprNode(tokens);
        var rightParenIgnore = tokens.next();

        return exprNode;
    }

    /**
     * <pre>
     * function_call =
     *     [IDENTIFIER] [LPAREN] factor *([COMMA]factor) [RPAREN]
     * </pre>
     * @param tokens
     * @return
     */
    final INode functionCallNode(@NonNull final ITokenSequence tokens) {

        if (tokens.peek().getType() != IDENTIFIER)
            throw new RuntimeException("It is not function call.");

        var functionName = tokens.next().getContent();

        if (tokens.next().getType() != LPAREN)
            throw new RuntimeException("It is not function call.");

        var arguments = new ArrayList<INode>();

        while (tokens.peek().getType() != RPAREN) {
            if (arguments.size() > 0 && tokens.next().getType() != COMMA)
                throw new RuntimeException("It is not function call.");

            var argument = factorNode(tokens);
            arguments.add(argument);
        }

        if (tokens.next().getType() != RPAREN)
            throw new RuntimeException("It is not function call.");

        return FunctionCallNode.builder()
                .functionName(functionName)
                .arguments(arguments)
                .build();
    }

    BinaryOperatorType getBinaryOperator(TokenType tokenType) {
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
        throw new RuntimeException("can't convert binary operator.");
    }
}
