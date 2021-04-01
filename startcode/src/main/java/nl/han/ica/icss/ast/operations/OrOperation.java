package nl.han.ica.icss.ast.operations;

import nl.han.ica.icss.ast.Operation;
import nl.han.ica.icss.ast.literals.BoolLiteral;
import nl.han.ica.icss.ast.types.ExpressionType;

public class OrOperation extends Operation {
    public OrOperation() {
        super(2);
    }

    @Override
    public String getNodeLabel() {
        return "Or";
    }

    @Override
    public BoolLiteral calculate() {
        return new BoolLiteral(((BoolLiteral) operands[0]).value || ((BoolLiteral) operands[1]).value);
    }

    @Override
    public String validate(ExpressionType[] types) {
        if (!types[0].equals(ExpressionType.BOOL)) {
            return "Or is only allowed with bools, not with " + types[0] + ".";
        } else if (!types[1].equals(ExpressionType.BOOL)) {
            return "Or is only allowed with bools, not with " + types[1] + ".";
        }

        return null;
    }
}
