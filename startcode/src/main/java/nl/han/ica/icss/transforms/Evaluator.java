package nl.han.ica.icss.transforms;

import nl.han.ica.datastructures.HANLinkedList;
import nl.han.ica.datastructures.IHANLinkedList;
import nl.han.ica.icss.ast.*;
import nl.han.ica.icss.ast.literals.BoolLiteral;
import java.util.ArrayList;
import java.util.HashMap;

public class Evaluator implements Transform {
    /**
     * Values of the variables.
     */
    private final IHANLinkedList<HashMap<String, Literal>> variableValues;

    /**
     * Create and initialize an instance of the evaluator class.
     */
    public Evaluator() {
        variableValues = new HANLinkedList<>();
    }

    @Override
    public void apply(AST ast) {
        evaluateChildren(ast.root.getChildren(), ast.root);
    }

    /**
     * Evaluate the children of a node.
     * @param children The children of the node.
     * @param parent The node, the parent of those children.
     */
    private void evaluateChildren(ArrayList<ASTNode> children, ASTNode parent) {
        HashMap<String, Literal> map = new HashMap<>();
        variableValues.addFirst(map);

        for (ASTNode child : children) {
            if (child instanceof IfClause) {
                evaluateIfClause((IfClause) child, parent);
            } else if (child instanceof VariableAssignment) {
                Literal calculation = evaluateExpression(((VariableAssignment) child).expression);

                ((VariableAssignment) child).expression = calculation;
                map.put(((VariableAssignment) child).name.name, calculation);
            } else if (child instanceof Expression && parent instanceof Declaration) {
                ((Declaration) parent).expression = evaluateExpression((Expression) child);
            }

            evaluateChildren(child.getChildren(), child);
        }

        variableValues.removeFirst();
    }

    /**
     * Evaluate an if clause.
     * @param ifClause The evaluated if clause.
     * @param parent The parent of the if clause.
     * @return The nodes resulting from the evaluation.
     */
    private ArrayList<ASTNode> evaluateIfClause(IfClause ifClause, ASTNode parent) {
        ArrayList<ASTNode> values = new ArrayList<>();
        Literal literal = evaluateExpression(ifClause.conditionalExpression);

        if (literal instanceof BoolLiteral) {
            ArrayList<ASTNode> body = null;

            if (((BoolLiteral) literal).value) {
                body = ifClause.body;
            } else if (ifClause.elseClause != null) {
                body = ifClause.elseClause.body;
            }

            if (body != null) {
                for (ASTNode node : body) {
                    if (node instanceof IfClause) {
                        //Recursively evaluate the if clauses.
                        values.addAll(evaluateIfClause((IfClause) node, ifClause));
                    } else {
                        values.add(node);
                    }
                }
            }
        }

        parent.removeChild(ifClause);

        //Add the found values only to the highest level.
        if (!(parent instanceof IfClause)) {
            values.forEach(parent::addChild);
        }

        return values;
    }

    /**
     * Evaluate an expression.
     * @param expression The evaluated expression.
     * @return The literal resulting from the evaluation.
     */
    private Literal evaluateExpression(Expression expression) {
        if (expression instanceof Literal) {
            return (Literal) expression;
        } else if (expression instanceof VariableReference) {
            VariableReference variableReference = (VariableReference) expression;

            for (int i = 0; i < variableValues.getSize(); i++) {
                HashMap<String, Literal> map = variableValues.get(i);

                if (map.containsKey(variableReference.name)) {
                    return map.get(variableReference.name);
                }
            }
        } else if (expression instanceof Operation) {
            Operation operation = (Operation) expression;

            for (int i = 0; i < operation.operands.length; i++) {
                operation.operands[i] = evaluateExpression(operation.operands[i]);
            }

            return operation.calculate();
        }

        return null;
    }
}
