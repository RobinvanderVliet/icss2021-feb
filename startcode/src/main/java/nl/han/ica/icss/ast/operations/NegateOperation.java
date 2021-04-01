package nl.han.ica.icss.ast.operations;

import nl.han.ica.icss.ast.Literal;
import nl.han.ica.icss.ast.Operation;
import nl.han.ica.icss.ast.literals.BoolLiteral;
import nl.han.ica.icss.ast.literals.ColorLiteral;
import nl.han.ica.icss.ast.types.ExpressionType;

public class NegateOperation extends Operation {
    public NegateOperation() {
        super(1);
    }

    @Override
    public String getNodeLabel() {
        return "Negate";
    }

    @Override
    public Literal calculate() {
        if (operands[0] instanceof BoolLiteral) {
            return new BoolLiteral(!((BoolLiteral) operands[0]).value);
        } else if (operands[0] instanceof ColorLiteral) {
            String color = ((ColorLiteral) operands[0]).value;
            StringBuilder newColor = new StringBuilder().append("#");

            for (int i = 1; i < color.length(); i++) {
                newColor.append(Integer.toHexString(15 - Integer.parseInt(color.substring(i, i + 1), 16)));
            }

            return new ColorLiteral(newColor.toString());
        }

        return null;
    }

    @Override
    public String validate(ExpressionType[] types) {
        if (!types[0].equals(ExpressionType.BOOL) && !types[0].equals(ExpressionType.COLOR)) {
            return "Negate is only allowed with bools and colors, not with " + types[0] + ".";
        }

        return null;
    }
}
