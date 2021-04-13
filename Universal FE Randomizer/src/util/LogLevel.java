package util;

public enum LogLevel {
    ERROR("ERROR"), WARN("WARN"), INFO("INFO"), DEBUG("DEBUG");

    String value;

    private LogLevel(String key) {
        this.value = key;
    }
}
