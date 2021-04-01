package nl.han.ica.icss.ast;

import nl.han.ica.icss.ast.types.ExpressionType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public abstract class Operation extends Expression {
    public Expression[] operands;

    /**
     * Create a new operation instance with a specific arity.
     * @param arity The amount of expression parameters the operation has.
     */
    public Operation(int arity) {
        this.operands = new Expression[arity];
    }

    @Override
    public ArrayList<ASTNode> getChildren() {
        ArrayList<ASTNode> children = new ArrayList<>();

        Arrays.stream(this.operands)
                .filter(Objects::nonNull)
                .forEach(children::add);

        return children;
    }

    @Override
    public ASTNode addChild(ASTNode child) {
        for (int i = 0; i < this.operands.length; i++) {
            if (this.operands[i] == null) {
                this.operands[i] = (Expression) child;
                break;
            }
        }

        return this;
    }

    /**
     * Execute the operation.
     * @return The result of the calculation.
     */
    public abstract Literal calculate();

    /**
     * Validate the operation with specific types.
     * @param types The types of the operands.
     * @return The error message if something went wrong, otherwise null.
     */
    public abstract String validate(ExpressionType[] types);
}
