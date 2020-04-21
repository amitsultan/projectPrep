package league;

class TimeLimitPass extends Exception {
    public TimeLimitPass() {
        super("ERROR: cannot edit because 5 hours passed since Event created");
    }
}

class NoGameFound extends Exception {
    public NoGameFound(String str) {
        super(str);
    }
}

