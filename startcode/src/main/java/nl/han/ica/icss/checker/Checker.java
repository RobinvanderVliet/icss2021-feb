package nl.han.ica.icss.checker;

import nl.han.ica.datastructures.HANLinkedList;
import nl.han.ica.datastructures.IHANLinkedList;
import nl.han.ica.icss.ast.*;
import nl.han.ica.icss.ast.literals.*;
import nl.han.ica.icss.ast.operations.*;
import nl.han.ica.icss.ast.types.ExpressionType;

import java.util.ArrayList;
import java.util.HashMap;

public class Checker {
    /**
     * Types of the variables.
     */
    private final IHANLinkedList<HashMap<String, ExpressionType>> variableTypes;

    /**
     * The allowed properties and their types.
     */
    private final HashMap<String, ArrayList<ExpressionType>> allowedTypes;

    /**
     * Create and initialize an instance of the checker class.
     */
    public Checker() {
        variableTypes = new HANLinkedList<>();

        //Fill the variable with the types that properties can have.
        //This array can be expanded upon to support more properties in the future if so desired.
        allowedTypes = new HashMap<>();
        allowedTypes.put("width", new ArrayList<>() {{
            add(ExpressionType.PERCENTAGE);
            add(ExpressionType.PIXEL);
        }});
        allowedTypes.put("height", new ArrayList<>() {{
            add(ExpressionType.PERCENTAGE);
            add(ExpressionType.PIXEL);
        }});
        allowedTypes.put("color", new ArrayList<>() {{
            add(ExpressionType.COLOR);
        }});
        allowedTypes.put("background-color", new ArrayList<>() {{
            add(ExpressionType.COLOR);
        }});
    }

    /**
     * Check whether an AST is valid.
     * @param ast The checked AST.
     */
    public void check(AST ast) {
        checkChildren(ast.root.getChildren());
    }

    /**
     * Check whether the children nodes are valid.
     * @param children The checked children in a list.
     */
    private void checkChildren(ArrayList<ASTNode> children) {
        HashMap<String, ExpressionType> map = new HashMap<>();
        variableTypes.addFirst(map);

        for (ASTNode child : children) {
            if (child instanceof IfClause) {
                ExpressionType calculation = getExpressionType(((IfClause) child).conditionalExpression);

                if (!calculation.equals(ExpressionType.BOOL)) {
                    child.setError("The condition of an if clause must evaluate to BOOL and not to " + calculation + ".");
                }
            } else if (child instanceof VariableAssignment) {
                String name = ((VariableAssignment) child).name.name;

                ExpressionType type = getVariableType(name);
                ExpressionType calculation = getExpressionType(((VariableAssignment) child).expression);

                if (!type.equals(ExpressionType.UNDEFINED) && !type.equals(calculation)) {
                    child.setError("The type of the variable \"" + name + "\" is " + type + " and cannot be changed to " + calculation + ".");
                } else {
                    map.put(((VariableAssignment) child).name.name, calculation);
                }
            } else if (child instanceof VariableReference) {
                VariableReference reference = (VariableReference) child;

                if (getVariableType(reference.name).equals(ExpressionType.UNDEFINED)) {
                    child.setError("The variable \"" + reference.name + "\" does not exist in this scope.");
                }
            } else if (child instanceof Operation) {
                checkOperation((Operation) child);
            } else if (child instanceof Declaration) {
                checkDeclaration((Declaration) child);
            }

            if (child instanceof VariableAssignment) {
                //Edge case: the name of a variable assignment should be ignored, it is already checked before.
                checkChildren(new ArrayList<>() {{ add(((VariableAssignment) child).expression); }});
            } else {
                checkChildren(child.getChildren());
            }
        }

        variableTypes.removeFirst();
    }

    /**
     * Check whether an operation is valid.
     * @param operation The checked operation.
     */
    private void checkOperation(Operation operation) {
        ExpressionType[] types = new ExpressionType[operation.operands.length];

        for (int i = 0; i < types.length; i++) {
            types[i] = getExpressionType(operation.operands[i]);

            if (types[i].equals(ExpressionType.UNDEFINED)) {
                return;
            }
        }

        String error = operation.validate(types);

        if (error != null) {
            operation.setError(error);
        }
    }

    /**
     * Check whether a declaration is valid.
     * @param declaration The checked declaration.
     */
    private void checkDeclaration(Declaration declaration) {
        String name = declaration.property.name;

        if (allowedTypes.containsKey(name)) {
            ExpressionType expression = getExpressionType(declaration.expression);

            if (!allowedTypes.get(name).contains(expression) && !expression.equals(ExpressionType.UNDEFINED)) {
                declaration.setError("The type of the property \"" + name + "\" cannot be " + expression + ".");
            }
        } else {
            declaration.setError("The property \"" + name + "\" is not allowed.");
        }
    }

    /**
     * Get the expression type of a variable.
     * @param name The name of the variable.
     * @return The expression type of the variable.
     */
    private ExpressionType getVariableType(String name) {
        for (int i = 0; i < variableTypes.getSize(); i++) {
            HashMap<String, ExpressionType> map = variableTypes.get(i);

            if (map.containsKey(name)) {
                return map.get(name);
            }
        }

        return ExpressionType.UNDEFINED;
    }

    /**
     * Get the expression type of the given expression.
     * @param expression The expression to check.
     * @return The type of the expression.
     */
    private ExpressionType getExpressionType(Expression expression) {
        if (expression instanceof VariableReference) {
            return getVariableType(((VariableReference) expression).name);
        } else if (expression instanceof BoolLiteral) {
            return ExpressionType.BOOL;
        } else if (expression instanceof ColorLiteral) {
            return ExpressionType.COLOR;
        } else if (expression instanceof PercentageLiteral) {
            return ExpressionType.PERCENTAGE;
        } else if (expression instanceof PixelLiteral) {
            return ExpressionType.PIXEL;
        } else if (expression instanceof ScalarLiteral) {
            return ExpressionType.SCALAR;
        } else if (expression instanceof Operation) {
            Expression[] operands = ((Operation) expression).operands;
            ExpressionType[] types = new ExpressionType[operands.length];

            for (int i = 0; i < operands.length; i++) {
                types[i] = getExpressionType(operands[i]);

                if (types[i].equals(ExpressionType.UNDEFINED)) {
                    //No operation can handle an undefined expression as an operand, so undefined should be returned.
                    return ExpressionType.UNDEFINED;
                }
            }

            if (expression instanceof AndOperation || expression instanceof EqualOperation || expression instanceof GreaterThanOperation || expression instanceof GreaterThanOrEqualOperation || expression instanceof LessThanOperation || expression instanceof LessThanOrEqualOperation || expression instanceof OrOperation || expression instanceof UnequalOperation) {
                return ExpressionType.BOOL;
            } else if (expression instanceof SpaceshipOperation) {
                return ExpressionType.SCALAR;
            } else if (expression instanceof RgbOperation || expression instanceof HsvOperation) {
                return ExpressionType.COLOR;
            } else if (expression instanceof AddOperation || expression instanceof SubtractOperation || expression instanceof NegateOperation || expression instanceof MinusOperation || expression instanceof RandomOperation) {
                return types[0];
            } else if (expression instanceof ConditionalOperation) {
                return types[1];
            } else if (expression instanceof MultiplyOperation) {
                if (types[0].equals(ExpressionType.SCALAR) && types[1].equals(ExpressionType.SCALAR)) {
                    return ExpressionType.SCALAR;
                } else if (types[0].equals(ExpressionType.SCALAR)) {
                    return types[1];
                } else if (types[1].equals(ExpressionType.SCALAR)) {
                    return types[0];
                }
            }
        }

        return ExpressionType.UNDEFINED;
    }
}
