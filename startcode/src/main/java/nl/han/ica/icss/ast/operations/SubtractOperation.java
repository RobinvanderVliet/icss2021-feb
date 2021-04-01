package nl.han.ica.icss.ast.operations;

import nl.han.ica.icss.ast.Operation;
import nl.han.ica.icss.ast.literals.NumberLiteral;
import nl.han.ica.icss.ast.literals.PercentageLiteral;
import nl.han.ica.icss.ast.literals.PixelLiteral;
import nl.han.ica.icss.ast.literals.ScalarLiteral;
import nl.han.ica.icss.ast.types.ExpressionType;

public class SubtractOperation extends Operation {
    public SubtractOperation() {
        super(2);
    }

    @Override
    public String getNodeLabel() {
        return "Subtract";
    }

    @Override
    public NumberLiteral calculate() {
        int value = ((NumberLiteral) operands[0]).value - ((NumberLiteral) operands[1]).value;

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
            return "Subtraction is only allowed with numeric values, not with " + types[0] + ".";
        } else if (!types[1].equals(ExpressionType.PERCENTAGE) && !types[1].equals(ExpressionType.PIXEL) && !types[1].equals(ExpressionType.SCALAR)) {
            return "Subtraction is only allowed with numeric values, not with " + types[1] + ".";
        } else if (!types[0].equals(types[1])) {
            return "Subtraction is only allowed with two values of the same type.";
        }

        return null;
    }
}
