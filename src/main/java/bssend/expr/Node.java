package bssend.expr;

import jdk.nashorn.internal.objects.annotations.Getter;

public abstract class Node {

    private final NodeType nodeType;

    public Node(NodeType nodeType) {
        this.nodeType = nodeType;
    }

    public NodeType getNodeType() {
        return nodeType;
    }
}
