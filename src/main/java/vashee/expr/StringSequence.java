package vashee.expr;

import lombok.NonNull;
import lombok.var;

import java.util.regex.Pattern;

public class StringSequence implements IStringSequence {

    private final String source;
    private int position;

    public StringSequence(@NonNull final String source) {
        this.source = source;
        this.position = 0;
    }

    public int match(@NonNull final String pattern) {
        var matcher = Pattern.compile(pattern)
                .matcher(source.substring(position));

        if (!matcher.find())
            return 0;

        return matcher.group(0).length();
    }

    public String peek(int length) {
        return source.substring(position, position + length);
    }

    public String next(int length) {
        var result = source.substring(position, position + length);
        position = position + length;
        return result;
    }

    public boolean isEnd() {
        return position > source.length() - 1;
    }
}
