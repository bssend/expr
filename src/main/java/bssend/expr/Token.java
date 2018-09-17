package bssend.expr;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Token {
    private TokenType type;
    private String content;
}
