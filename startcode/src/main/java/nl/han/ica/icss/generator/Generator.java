package nl.han.ica.icss.generator;

import nl.han.ica.icss.ast.*;
import nl.han.ica.icss.ast.literals.*;

public class Generator {
    /**
     * The indentation used for each scope level.
     */
    public final String indentation = "  ";

    /**
     * Generate CSS based on an AST object.
     * @param ast The AST used to generate CSS.
     */
    public String generate(AST ast) {
        StringBuilder output = new StringBuilder();

        ast.root.getChildren().stream()
                .filter(child -> child instanceof Stylerule)
                .forEach(child -> output.append(generateStylerule(child)));

        return output.toString().trim();
    }

    /**
     * Generate a stylerule based on a node object.
     * @param node The node object used to generate the stylerule.
     * @return The generated stylerule.
     */
    private String generateStylerule(ASTNode node) {
        StringBuilder output = new StringBuilder();
        boolean empty = true;

        for (ASTNode child : node.getChildren()) {
            if (child instanceof Selector) {
                output.append(child.toString());
                output.append(" {\n");
            } else if (child instanceof Declaration) {
                String declaration = generateDeclaration(child);

                if (!declaration.equals("")) {
                    output.append(declaration);
                    empty = false;
                }
            }
        }

        //If there are no declarations, an empty string can just be returned.
        return empty ? "" : output.append("}\n\n").toString();
    }

    /**
     * Generate a declaration based on a node object.
     * @param node The node object used to generate the declaration.
     * @return The generated declaration.
     */
    private String generateDeclaration(ASTNode node) {
        StringBuilder output = new StringBuilder();

        String key = null;

        for (ASTNode child : node.getChildren()) {
            if (child instanceof PropertyName) {
                key = ((PropertyName) child).name;
            } else if (key != null) {
                String value = null;

                if (child instanceof ColorLiteral) {
                    value = ((ColorLiteral) child).value;
                } else if (child instanceof PercentageLiteral) {
                    value = ((PercentageLiteral) child).value + "%";
                } else if (child instanceof PixelLiteral) {
                    value = ((PixelLiteral) child).value + "px";
                } else if (child instanceof ScalarLiteral) {
                    value = String.valueOf(((ScalarLiteral) child).value);
                }

                if (value != null) {
                    output.append(indentation);
                    output.append(key);
                    output.append(": ");
                    output.append(value);
                    output.append(";\n");

                    key = null;
                }
            }
        }

        return output.toString();
    }
}
