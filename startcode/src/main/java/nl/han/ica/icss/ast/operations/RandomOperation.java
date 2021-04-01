package nl.han.ica.icss.ast.operations;

import nl.han.ica.icss.ast.Operation;
import nl.han.ica.icss.ast.literals.*;
import nl.han.ica.icss.ast.types.ExpressionType;

public class RandomOperation extends Operation {
    public RandomOperation() {
        super(2);
    }

    @Override
    public String getNodeLabel() {
        return "Random";
    }

    @Override
    public NumberLiteral calculate() {
        int value1 = ((NumberLiteral) operands[0]).value;
        int value2 = ((NumberLiteral) operands[1]).value;

        int min = Math.min(value1, value2);
        int max = Math.max(value1, value2);

        int value = (int) Math.floor(Math.random() * ((max - min + 1)) + min);

        if (operands[0] instanceof PercentageLiteral && operands[1] instanceof PercentageLiteral) {
            return new PercentageLiteral(value);
        } else if (operands[0] instanceof PixelLiteral && operands[1] instanceof PixelLiteral) {
            return new PixelLiteral(value);
        } else if (operands[0] instanceof ScalarLiteral && operands[1] instanceof ScalarLiteral) {
            return new ScalarLiteral(value);
        }

        return null;
    }

    @Override
    public String validate(ExpressionType[] types) {
        if (!types[0].equals(ExpressionType.PERCENTAGE) && !types[0].equals(ExpressionType.PIXEL) && !types[0].equals(ExpressionType.SCALAR)) {
            return "Random is only allowed with numeric values, not with " + types[0] + ".";
        } else if (!types[1].equals(ExpressionType.PERCENTAGE) && !types[1].equals(ExpressionType.PIXEL) && !types[1].equals(ExpressionType.SCALAR)) {
            return "Random is only allowed with numeric values, not with " + types[1] + ".";
        } else if (!types[0].equals(types[1])) {
            return "Random is only allowed with two values of the same type.";
        }

        return null;
    }
}
