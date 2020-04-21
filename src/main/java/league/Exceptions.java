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

class SameControllerToDifferentLeagues extends Exception {
    public SameControllerToDifferentLeagues(String errorMessage) {
        super(errorMessage);
    }
}

class IllegalYear extends Exception{
    public IllegalYear() {
        super("Year must be positive number");
    }
}
class SeasonAlreadyExist extends Exception{
    public SeasonAlreadyExist() {
        super("Cannot have two seasons in the same year");
    }
}

class SeasonControllerAlreadyExist extends Exception{
    public SeasonControllerAlreadyExist() {
        super("Cannot have two seasons controller in the same year");
    }
}