package bssend.expr;

import bssend.expr.util.IStringSequence;
import bssend.expr.util.ITokenSequence;
import bssend.expr.util.TokenSequence;
import lombok.NonNull;
import lombok.var;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

import static bssend.expr.TokenType.STRING;
import static bssend.expr.TokenType.WHITESPACE;
import static java.util.Comparator.comparing;

public class Scanner implements IScanner {

    public final ITokenSequence scan(@NonNull final IStringSequence seq) {

        var tokenTypes = Arrays.asList(TokenType.values()).stream()
                .sorted(comparing(TokenType::getPriority))
                .collect(Collectors.toList());

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
