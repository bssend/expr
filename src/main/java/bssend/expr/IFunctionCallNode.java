package bssend.expr;

import java.util.List;

public interface IFunctionCallNode extends INode {
    String getFunctionName();
    List<INode> getArguments();
}
