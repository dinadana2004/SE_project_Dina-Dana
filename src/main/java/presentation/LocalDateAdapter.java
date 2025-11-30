package presentation;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.time.LocalDate;

/**
 * A custom Gson {@link TypeAdapter} for serializing and deserializing
 * {@link LocalDate} objects to and from JSON.
 *
 * <p>Since Gson does not support Java's {@link LocalDate} by default,
 * this adapter ensures dates are written as ISO-8601 strings
 * (e.g., {@code "2025-01-01"}) and correctly parsed back.</p>
 *
 * @author Dana
 * @version 1.0
 */
public class LocalDateAdapter extends TypeAdapter<LocalDate> {

    /**
     * Writes a {@link LocalDate} value to JSON as a string.
     *
     * @param out   the {@link JsonWriter} used to write the value
     * @param value the LocalDate value to write; may be {@code null}
     * @throws IOException if an I/O error occurs during writing
     */
    @Override
    public void write(JsonWriter out, LocalDate value) throws IOException {
        if (value == null) {
            out.nullValue();
            return;
        }
        out.value(value.toString());  // write as "2025-01-01"
    }

    /**
     * Reads a JSON string and converts it into a {@link LocalDate} object.
     *
     * @param in the {@link JsonReader} used to read JSON input
     * @return a LocalDate parsed from the JSON string, or {@code null} if the value is null
     * @throws IOException if an I/O error occurs during reading
     */
    @Override
    public LocalDate read(JsonReader in) throws IOException {
        String s = in.nextString();
        return s == null ? null : LocalDate.parse(s);
    }
}
