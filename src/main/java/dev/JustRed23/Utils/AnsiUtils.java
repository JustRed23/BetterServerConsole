package dev.JustRed23.Utils;

import org.fusesource.jansi.Ansi;

import java.util.ArrayList;

public class AnsiUtils implements Appendable {

    private static final char FIRST_ESC_CHAR = 27;
    private static final char SECOND_ESC_CHAR = '[';

    private final StringBuilder builder;
    private final ArrayList<Integer> attributeOptions = new ArrayList<>(5);

    public AnsiUtils() {
        this.builder = new StringBuilder(80);
    }

    public static AnsiUtils ansiUtils() {
        return new AnsiUtils();
    }

    private void flushAttributes() {
        if (attributeOptions.isEmpty())
            return;
        if (attributeOptions.size() == 1 && attributeOptions.get(0) == 0) {
            builder.append(FIRST_ESC_CHAR);
            builder.append(SECOND_ESC_CHAR);
            builder.append('m');
        } else {
            _appendEscapeSequence('m', attributeOptions.toArray());
        }
        attributeOptions.clear();
    }

    private AnsiUtils _appendEscapeSequence(char command, Object... options) {
        builder.append(FIRST_ESC_CHAR);
        builder.append(SECOND_ESC_CHAR);
        int size = options.length;
        for (int i = 0; i < size; i++) {
            if (i != 0) {
                builder.append(';');
            }
            if (options[i] != null) {
                builder.append(options[i]);
            }
        }
        builder.append(command);
        return this;
    }

    public AnsiUtils newline() {
        flushAttributes();
        builder.append(System.getProperty("line.separator"));
        return this;
    }

    public AnsiUtils fg(Ansi.Color color) {
        attributeOptions.add(color.fg());
        return this;
    }

    public AnsiUtils fgRgb(int color) {
        return fgRgb(color >> 16, color >> 8, color);
    }

    public AnsiUtils fgRgb(int r, int g, int b) {
        attributeOptions.add(38);
        attributeOptions.add(2);
        attributeOptions.add(r & 0xff);
        attributeOptions.add(g & 0xff);
        attributeOptions.add(b & 0xff);
        return this;
    }

    public AnsiUtils bg(Ansi.Color color) {
        attributeOptions.add(color.bg());
        return this;
    }

    public AnsiUtils bgRgb(int color) {
        return bgRgb(color >> 16, color >> 8, color);
    }

    public AnsiUtils bgRgb(int r, int g, int b) {
        attributeOptions.add(48);
        attributeOptions.add(2);
        attributeOptions.add(r & 0xff);
        attributeOptions.add(g & 0xff);
        attributeOptions.add(b & 0xff);
        return this;
    }

    public AnsiUtils a(Object value) {
        flushAttributes();
        builder.append(value);
        return this;
    }

    private AnsiUtils a(Ansi.Attribute attribute) {
        attributeOptions.add(attribute.value());
        return this;
    }

    public AnsiUtils reset() {
        return a(Ansi.Attribute.RESET);
    }

    public AnsiUtils bold() {
        return a(Ansi.Attribute.INTENSITY_BOLD);
    }

    public AnsiUtils boldOff() {
        return a(Ansi.Attribute.INTENSITY_BOLD_OFF);
    }

    @Override
    public AnsiUtils append(CharSequence csq) {
        builder.append(csq);
        return this;
    }

    @Override
    public AnsiUtils append(CharSequence csq, int start, int end) {
        builder.append(csq, start, end);
        return this;
    }

    @Override
    public AnsiUtils append(char c) {
        builder.append(c);
        return this;
    }

    @Override
    public String toString() {
        flushAttributes();
        return builder.toString();
    }
}
