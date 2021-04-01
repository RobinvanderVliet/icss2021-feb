package nl.han.ica.icss.transforms;

import nl.han.ica.icss.ast.AST;

public interface Transform {
    /**
     * Apply the transformation to an AST object.
     * @param ast The AST object.
     */
    void apply(AST ast);
}
