package bssend.expr.parser;

import bssend.expr.util.IStringSequence;
import bssend.expr.util.ITokenSequence;

public interface ILexer {
    ITokenSequence scan(final IStringSequence seq);
}
