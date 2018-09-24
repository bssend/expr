package bssend.expr.node;

import lombok.Builder;
import lombok.NonNull;

import java.util.ArrayList;
import java.util.List;

@Builder
public class FunctionCallNode extends Node implements IFunctionCallNode {

    private final String functionName;
    private final List<INode> arguments;

    public FunctionCallNode(
            @NonNull final String functionName,
            @NonNull final List<INode> arguments) {
        super(NodeType.FunctionCall);
        this.functionName = functionName;
        this.arguments = new ArrayList<INode>(arguments);
    }

    @Override
    public String getFunctionName() {
        return functionName;
    }

    @Override
    public List<INode> getArguments() {
        return arguments;
    }
}
