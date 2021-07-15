package sa.pg.event.ffa.impl.string;

public class HighlightedString {

    private final String defaultColor;
    private final String highlightedColor;
    private final String text;
    private int count;

    public HighlightedString(String text, String defaultColor, String highlightedColor) {
        this.text = text;
        this.defaultColor = defaultColor;
        this.highlightedColor = highlightedColor;
        count = 0;
    }

    public String next() {
        if(count >= text.length()) {
            count = 0;
        }
        String prefix = text.replaceFirst(text.substring(count), "");
        String suffix = text.substring(count);// atw83 k4a tmam
        char charAt = text.charAt(count);
        suffix = suffix.replaceFirst("" + charAt, "");
        count++;
        return defaultColor + prefix + highlightedColor + charAt + defaultColor + suffix;
    }

}
