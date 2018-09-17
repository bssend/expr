package vashee.expr;

import lombok.NonNull;
import lombok.var;

import static vashee.expr.TokenType.*;

public class Parser implements IParser {

    @Override
    public INode parse(@NonNull final String s) {

        var seq = new StringSequence(s);
        var scanner = new Scanner();

        var tokens = scanner.scan(seq);

        return exprNode(tokens);
    }

    // value = [0-9] | [0-9]+.[0-9]+ | DOUBLE_QUOTE .* DOUBLE_QUOTE
    final INode valueNode (@NonNull final ITokenSequence tokens) {

        if (tokens.peek().getType() == STRING)
            return new StringValueNode(tokens.next().getContent());

        if (tokens.peek().getType() == INTEGER)
            return new IntegerValueNode(Integer.valueOf(tokens.next().getContent()));

        if (tokens.peek().getType() == DECIMAL)
            return new DecimalValueNode(Double.valueOf(tokens.next().getContent()));

        throw new RuntimeException("can not value.");
    }

    // expr = term, {("+", term) | ("-", term)}
    final INode exprNode(@NonNull final ITokenSequence tokens) {
        var returnNode = termNode(tokens);

        while (!tokens.isEnd()) {

            if (tokens.peek().getType() != ADD_OP &&
                tokens.peek().getType() != SUB_OP)
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

    // term = factor, {("*", factor) | ("/", factor)}
    final INode termNode(@NonNull final ITokenSequence tokens) {
        var returnNode = factorNode(tokens);

        while (!tokens.isEnd()) {

            if (tokens.peek().getType() != MUL_OP &&
                tokens.peek().getType() != DIV_OP &&
                tokens.peek().getType() != MOD_OP)
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

    // factor = ("(", expr, ")") | value
    final INode factorNode(@NonNull final ITokenSequence tokens) {

        if (tokens.peek().getType() != LPAREN)
            return valueNode(tokens);

        var leftParenIgnore = tokens.next();
        var exprNode = exprNode(tokens);
        var rightParenIgnore = tokens.next();

        return exprNode;
    }

    BinaryOperatorType getBinaryOperator(TokenType tokenType) {
        switch (tokenType) {
            case ADD_OP:
                return BinaryOperatorType.ADD;
            case SUB_OP:
                return BinaryOperatorType.SUB;
            case MUL_OP:
                return BinaryOperatorType.MUL;
            case DIV_OP:
                return BinaryOperatorType.DIV;
            case MOD_OP:
                return BinaryOperatorType.MOD;
        }
        throw new RuntimeException("can't convert binary operator.");
    }
}
