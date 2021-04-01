package nl.han.ica.icss.ast.literals;

import java.util.Objects;

public class ScalarLiteral extends NumberLiteral {
    public ScalarLiteral(int value) {
        this.value = value;
    }

    public ScalarLiteral(String text) {
        try {
            this.value = Integer.parseInt(text);
        } catch (NumberFormatException ignored) {
            this.setError("This scalar value does not contain a parsable integer.");
        }
    }

    @Override
    public String getNodeLabel() {
        return "Scalar literal (" + value + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        ScalarLiteral that = (ScalarLiteral) o;
        return value == that.value;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
