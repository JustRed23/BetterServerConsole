package dev.JustRed23.Version;

public class Version {

    private final int major, minor;

    public Version(int major, int minor) {
        this.major = major;
        this.minor = minor;
    }

    public int getMajor() {
        return major;
    }

    public int getMinor() {
        return minor;
    }

    public String toString() {
        return String.format("Version %s.%s", major, minor);
    }
}
