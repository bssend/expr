package vashee.expr;

import lombok.NonNull;
import lombok.var;

import java.util.ArrayList;
import java.util.stream.Collectors;

import static vashee.expr.TokenType.STRING;
import static vashee.expr.TokenType.WHITESPACE;

public class Scanner implements IScanner {

    public final ITokenSequence scan(@NonNull final IStringSequence seq) {

        var tokenTypes = TokenType.values();
        var tokens = new ArrayList<Token>();

        while (!seq.isEnd()) {
            Token token = null;
            for (var tokenType : tokenTypes) {

                var length = seq.match(tokenType.getPattern());

                if (length == 0)
                    continue;

                var content = seq.next(length);

                if (tokenType == STRING)
                    content = content.substring(1, content.length() - 1);

                token = Token.builder()
                        .type(tokenType)
                        .content(content)
                        .build();
                break;
            }

            if (token == null)
                throw new RuntimeException("not match token.");

            tokens.add(token);
        }

        return new TokenSequence(tokens.stream()
                .filter(t -> t.getType() != WHITESPACE)
                .collect(Collectors.toList()));
    }
}
