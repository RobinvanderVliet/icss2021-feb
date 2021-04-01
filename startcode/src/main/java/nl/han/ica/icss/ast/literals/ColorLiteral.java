package nl.han.ica.icss.ast.literals;

import nl.han.ica.icss.ast.Literal;

import java.util.Objects;

public class ColorLiteral extends Literal {
    public String value;

    /**
     * Create an instance and try normalizing the color.
     * Normalization happens by converting it to uppercase and by simplifying it if possible.
     * This way we can easily compare #fff and #FFFFFF with the equal operator and still receive TRUE.
     * @param value The color in hexadecimal format.
     */
    public ColorLiteral(String value) {
        value = value.toUpperCase();

        //Simplify "#AABBCC" to "#ABC".
        if (value.length() == 7 && value.startsWith("#")) {
            if (
                    value.substring(1, 2).equals(value.substring(2, 3)) &&
                    value.substring(3, 4).equals(value.substring(4, 5)) &&
                    value.substring(5, 6).equals(value.substring(6, 7))
            ) {
                value = "#" + value.substring(1, 2) + value.substring(3, 4) + value.substring(5, 6);
            }
        }

        this.value = value;
    }

    /**
     * Create an instance.
     * @param r The red value as a decimal between 0 and 255.
     * @param g The green value as a decimal between 0 and 255.
     * @param b The blue value as a decimal between 0 and 255.
     */
    public ColorLiteral(int r, int g, int b) {
        this("#" +
                String.format("%02X", Math.max(0, Math.min(255, r))) +
                String.format("%02X", Math.max(0, Math.min(255, g))) +
                String.format("%02X", Math.max(0, Math.min(255, b))));
    }

    @Override
    public String getNodeLabel() {
        return "Color literal (" + value + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ColorLiteral that = (ColorLiteral) o;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
