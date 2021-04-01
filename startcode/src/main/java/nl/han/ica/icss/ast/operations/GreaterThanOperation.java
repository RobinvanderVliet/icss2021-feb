package nl.han.ica.icss.ast.operations;

import nl.han.ica.icss.ast.Operation;
import nl.han.ica.icss.ast.literals.*;
import nl.han.ica.icss.ast.types.ExpressionType;

public class GreaterThanOperation extends Operation {
    public GreaterThanOperation() {
        super(2);
    }

    @Override
    public String getNodeLabel() {
        return "GreaterThan";
    }

    @Override
    public BoolLiteral calculate() {
        return new BoolLiteral(((NumberLiteral) operands[0]).value > ((NumberLiteral) operands[1]).value);
    }

    @Override
    public String validate(ExpressionType[] types) {
        if (!types[0].equals(ExpressionType.PERCENTAGE) && !types[0].equals(ExpressionType.PIXEL) && !types[0].equals(ExpressionType.SCALAR)) {
            return "Greater than is only allowed with numeric values, not with " + types[0] + ".";
        } else if (!types[1].equals(ExpressionType.PERCENTAGE) && !types[1].equals(ExpressionType.PIXEL) && !types[1].equals(ExpressionType.SCALAR)) {
            return "Greater than is only allowed with numeric values, not with " + types[1] + ".";
        } else if (!types[0].equals(types[1])) {
            return "Greater than is only allowed with two values of the same type.";
        }

        return null;
    }
}
