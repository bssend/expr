package bssend.expr.parser;

import bssend.expr.node.INode;

public interface IParser {
    INode parse(final String s);
}
