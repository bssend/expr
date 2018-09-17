package vashee.expr;

import lombok.NonNull;
import lombok.var;

import java.util.List;

public class TokenSequence implements ITokenSequence {

    private final List<Token> tokens;
    private int position;

    public TokenSequence(@NonNull final List<Token> tokens) {
        this.tokens = tokens;
        this.position = 0;
    }

    public Token next() {
        var next = tokens.get(position);
        position++;
        return next;
    }

    public Token peek() {
        return tokens.get(position);
    }

    public boolean isEnd() {
        return position > tokens.size() - 1;
    }
}
