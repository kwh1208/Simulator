package dbps.dbps.service;

public record ComboItem(String key, String displayText) {
    @Override
    public String toString() {
        return displayText;
    }
}
