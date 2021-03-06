package nl.han.ica.icss.ast.literals;

import java.util.Objects;

public class PixelLiteral extends NumberLiteral {
    public PixelLiteral(int value) {
        this.value = value;
    }

    public PixelLiteral(String text) {
        try {
            this.value = Integer.parseInt(text.substring(0, text.length() - 2));
        } catch (NumberFormatException ignored) {
            this.setError("This pixel value does not contain a parsable integer.");
        }
    }

    @Override
    public String getNodeLabel() {
        return "Pixel literal (" + value + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        PixelLiteral that = (PixelLiteral) o;
        return value == that.value;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
