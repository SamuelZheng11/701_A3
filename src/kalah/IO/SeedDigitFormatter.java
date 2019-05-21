package kalah.IO;

import kalah.misc.LogicalConstants;

public class SeedDigitFormatter {
    public String formatWhiteSpaceForNumber(int value) {
        // used to format spacing on large and small numbers (" [9]" vs "[10]")
        if (value < LogicalConstants.FORMAT_TRIGGER_VALUE) {
            return " " + value;
        } else {
            return "" + value;
        }
    }
}
