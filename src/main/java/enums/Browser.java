package enums;

public enum Browser {
    CHROME, FIREFOX, EDGE;

    // Критерий: регистр значения параметра не влияет на результат
    public static Browser fromString(String browserName) {
        if (browserName == null || browserName.trim().isEmpty()) {
            return CHROME; // default
        }
        return Browser.valueOf(browserName.trim().toUpperCase());
    }
}