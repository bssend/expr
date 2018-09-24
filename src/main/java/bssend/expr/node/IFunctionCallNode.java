package bssend.expr.node;

import java.util.List;

public interface IFunctionCallNode extends INode {
    String getFunctionName();
    List<INode> getArguments();
}
