package nl.han.ica.icss.ast.operations;

import nl.han.ica.icss.ast.Operation;
import nl.han.ica.icss.ast.literals.ColorLiteral;
import nl.han.ica.icss.ast.literals.ScalarLiteral;
import nl.han.ica.icss.ast.types.ExpressionType;

public class HsvOperation extends Operation {
    public HsvOperation() {
        super(3);
    }

    @Override
    public String getNodeLabel() {
        return "Hsv";
    }

    @Override
    public ColorLiteral calculate() {
        double h = Math.max(0, Math.min(360, ((ScalarLiteral) operands[0]).value)) / 360f;
        double s = Math.max(0, Math.min(100, ((ScalarLiteral) operands[1]).value)) / 100f;
        double v = Math.max(0, Math.min(100, ((ScalarLiteral) operands[2]).value)) / 100f;

        int i = (int) Math.floor(h * 6);

        double f = h * 6 - i;
        double p = v * (1 - s);
        double q = v * (1 - f * s);
        double t = v * (1 - (1 - f) * s);

        double r = 0;
        double g = 0;
        double b = 0;

        switch (i % 6) {
            case 0: r = v; g = t; b = p; break;
            case 1: r = q; g = v; b = p; break;
            case 2: r = p; g = v; b = t; break;
            case 3: r = p; g = q; b = v; break;
            case 4: r = t; g = p; b = v; break;
            case 5: r = v; g = p; b = q; break;
        }

        return new ColorLiteral(
                (int) (r * 255),
                (int) (g * 255),
                (int) (b * 255)
        );
    }

    @Override
    public String validate(ExpressionType[] types) {
        for (int i = 0; i < 3; i++) {
            if (!types[i].equals(ExpressionType.SCALAR)) {
                return "HSV is only allowed with scalars, not with " + types[i] + ".";
            }
        }

        return null;
    }
}
