package bssend.expr;

import bssend.expr.util.IStringSequence;
import bssend.expr.util.ITokenSequence;

public interface IScanner {
    ITokenSequence scan(final IStringSequence seq);
}
