package nl.han.ica.icss.ast.operations;

import nl.han.ica.icss.ast.Operation;
import nl.han.ica.icss.ast.literals.NumberLiteral;
import nl.han.ica.icss.ast.literals.ScalarLiteral;
import nl.han.ica.icss.ast.types.ExpressionType;

public class SpaceshipOperation extends Operation {
    public SpaceshipOperation() {
        super(2);
    }

    @Override
    public String getNodeLabel() {
        return "Spaceship";
    }

    @Override
    public ScalarLiteral calculate() {
        int value1 = ((NumberLiteral) operands[0]).value;
        int value2 = ((NumberLiteral) operands[1]).value;

        if (value1 < value2) {
            return new ScalarLiteral(-1);
        } else if (value1 > value2) {
            return new ScalarLiteral(1);
        }

        return new ScalarLiteral(0);
    }

    @Override
    public String validate(ExpressionType[] types) {
        if (!types[0].equals(ExpressionType.PERCENTAGE) && !types[0].equals(ExpressionType.PIXEL) && !types[0].equals(ExpressionType.SCALAR)) {
            return "Spaceship is only allowed with numeric values, not with " + types[0] + ".";
        } else if (!types[1].equals(ExpressionType.PERCENTAGE) && !types[1].equals(ExpressionType.PIXEL) && !types[1].equals(ExpressionType.SCALAR)) {
            return "Spaceship is only allowed with numeric values, not with " + types[1] + ".";
        } else if (!types[0].equals(types[1])) {
            return "Spaceship is only allowed with two values of the same type.";
        }

        return null;
    }
}
