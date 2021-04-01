package nl.han.ica.icss.ast.operations;

import nl.han.ica.icss.ast.Literal;
import nl.han.ica.icss.ast.Operation;
import nl.han.ica.icss.ast.literals.BoolLiteral;
import nl.han.ica.icss.ast.types.ExpressionType;

public class ConditionalOperation extends Operation {
    public ConditionalOperation() {
        super(3);
    }

    @Override
    public String getNodeLabel() {
        return "Conditional";
    }

    @Override
    public Literal calculate() {
        return (Literal) operands[((BoolLiteral) operands[0]).value ? 1 : 2];
    }

    @Override
    public String validate(ExpressionType[] types) {
        if (!types[0].equals(ExpressionType.BOOL)) {
            return "The first operand of a conditional must be a bool, not " + types[0] + ".";
        } else if (!types[1].equals(types[2])) {
            return "The second and third operands of a conditional must be of the same type.";
        }

        return null;
    }
}
