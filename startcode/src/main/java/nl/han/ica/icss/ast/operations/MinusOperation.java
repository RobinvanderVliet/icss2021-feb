package nl.han.ica.icss.ast.operations;

import nl.han.ica.icss.ast.Operation;
import nl.han.ica.icss.ast.literals.*;
import nl.han.ica.icss.ast.types.ExpressionType;

public class MinusOperation extends Operation {
    public MinusOperation() {
        super(1);
    }

    @Override
    public String getNodeLabel() {
        return "Minus";
    }

    @Override
    public NumberLiteral calculate() {
        int value = -((NumberLiteral) operands[0]).value;

        if (operands[0] instanceof PercentageLiteral) {
            return new PercentageLiteral(value);
        } else if (operands[0] instanceof PixelLiteral) {
            return new PixelLiteral(value);
        } else if (operands[0] instanceof ScalarLiteral) {
            return new ScalarLiteral(value);
        }

        return null;
    }

    @Override
    public String validate(ExpressionType[] types) {
        if (!types[0].equals(ExpressionType.PERCENTAGE) && !types[0].equals(ExpressionType.PIXEL) && !types[0].equals(ExpressionType.SCALAR)) {
            return "Minus is only allowed with numeric values, not with " + types[0] + ".";
        }

        return null;
    }
}
