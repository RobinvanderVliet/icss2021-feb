package nl.han.ica.icss.ast.literals;

import nl.han.ica.icss.ast.Literal;

/**
 * A simple abstract class which contains just the number of a actual numeric literal.
 */
public abstract class NumberLiteral extends Literal {
    public int value;
}
