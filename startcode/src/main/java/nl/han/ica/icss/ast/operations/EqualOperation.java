package nl.han.ica.icss.ast.operations;

import nl.han.ica.icss.ast.Operation;
import nl.han.ica.icss.ast.literals.BoolLiteral;
import nl.han.ica.icss.ast.types.ExpressionType;

public class EqualOperation extends Operation {
    public EqualOperation() {
        super(2);
    }

    @Override
    public String getNodeLabel() {
        return "Equal";
    }

    @Override
    public BoolLiteral calculate() {
        return new BoolLiteral(operands[0].equals(operands[1]));
    }

    @Override
    public String validate(ExpressionType[] types) {
        //All types are allowed, so nothing has to be validated.
        return null;
    }
}
