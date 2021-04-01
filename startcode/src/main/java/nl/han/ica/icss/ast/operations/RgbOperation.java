package nl.han.ica.icss.ast.operations;

import nl.han.ica.icss.ast.Operation;
import nl.han.ica.icss.ast.literals.ColorLiteral;
import nl.han.ica.icss.ast.literals.ScalarLiteral;
import nl.han.ica.icss.ast.types.ExpressionType;

public class RgbOperation extends Operation {
    public RgbOperation() {
        super(3);
    }

    @Override
    public String getNodeLabel() {
        return "Rgb";
    }

    @Override
    public ColorLiteral calculate() {
        return new ColorLiteral(
                ((ScalarLiteral) operands[0]).value,
                ((ScalarLiteral) operands[1]).value,
                ((ScalarLiteral) operands[2]).value
        );
    }

    @Override
    public String validate(ExpressionType[] types) {
        for (int i = 0; i < 3; i++) {
            if (!types[i].equals(ExpressionType.SCALAR)) {
                return "RGB is only allowed with scalars, not with " + types[i] + ".";
            }
        }

        return null;
    }
}
