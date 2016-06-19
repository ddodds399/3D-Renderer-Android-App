package gd2.gd2render.OFFImporting;

import java.io.BufferedReader;
import java.io.IOException;

/**
 * Created by Dean on 30/11/2015.
 */
public class OFFLoaderHelper {

    private static final char COMMENT_SYMBOL = '#';
    private static final String WHITE_SPACE_PATTERN = "[\\s]+";

    /**
     * Used to parse string data from an .OFF file to an int/float to check for a colour segment.
     * @param text - The string data read from an .OFF file
     * @return - Data parsed to int/float
     * @throws IOException
     */
    public static float parseColorSegmentSafe(String text) throws IOException {
        try {
            return parseIntSafe(text) / 255.0f;
        } catch (NumberFormatException ex) {
            return parseFloatSafe(text);

        }
    }

    /**
     * Used to parse string data from an .OFF file to an int with error checking.
     * @param text  - The string data read from an .OFF file
     * @return - Data parsed to int
     * @throws IOException
     */
    public static int parseIntSafe(String text) throws IOException {
        try {
            return Integer.parseInt(text);
        } catch (NumberFormatException ex) {
            throw new IOException("Expected an integer but read differently.", ex);
        }
    }

    /**
     * Used to parse string data from an .OFF file to an float with error checking.
     * @param text  - The string data read from an .OFF file
     * @return - Data parsed to float
     * @throws IOException
     */
    public static float parseFloatSafe(String text) throws IOException {
        try {
            return Float.parseFloat(text);
        } catch (NumberFormatException ex) {
            throw new IOException("Expected a float but read differently.", ex);
        }
    }

    /**
     * Used to parse string data from an .OFF file to an short with error checking.
     * @param text  - The string data read from an .OFF file
     * @return - Data parsed to short
     * @throws IOException
     */
    public static short parseShortSafe(String text) throws IOException {
        try {
            return Short.parseShort(text);
        } catch (NumberFormatException ex) {
            throw new IOException("Expected a float but read differently.", ex);
        }
    }

    /**
     * Used to read a line in an .OFF file containing multiple values that are needed.
     * @param reader - reader containing file data.
     * @return - return contents of line split by spaces.
     * @throws IOException
     */
    public static String[] readContentLineMultiple(BufferedReader reader) throws IOException {
        final String line = readContentLineSingle(reader);
        return line.split(WHITE_SPACE_PATTERN);
    }

    /**
     * Used to read a line in an .OFF file containing a single value.
     * @param reader - reader containing file data.
     * @return - return contents of line.
     * @throws IOException
     */
    public static String readContentLineSingle(BufferedReader reader) throws IOException {
        String line = readCleanedUpLine(reader);
        while (line.isEmpty()) {
            line = readCleanedUpLine(reader);
        }
        return line;
    }

    /**
     * Used to read a line in an .OFF file containing a value and check if it ended unexpectedly.
     * @param reader - reader containing file data.
     * @return - return contents of line that has been trimmed.
     * @throws IOException
     */
    private static String readCleanedUpLine(BufferedReader reader) throws IOException {
        final String result = reader.readLine();
        if (result == null) {
            throw new IOException("The file has ended unexpectedly.");
        }
        return removeCommentFromLine(result.trim());
    }

    /**
     * Used to remove the comment from the end of a line in an .OFF file.
     * @param line - string of a value or values read from file.
     * @return - string with comment removed.
     * @throws IOException
     */
    private static String removeCommentFromLine(String line) {
        final int commentIndex = line.indexOf(COMMENT_SYMBOL);
        if (commentIndex != -1) {
            return line.substring(0, commentIndex).trim();
        }
        return line;
    }

}
