package dbhandler;

class DriverClassNotFound extends Exception {
    public DriverClassNotFound(String errorMessage) {
        super(errorMessage);
    }
}

class NoPrivileges extends Exception {
    public NoPrivileges(String errorMessage){super(errorMessage);}
}

class NoTeamDetected extends Exception {
    public NoTeamDetected(String errorMessage){super(errorMessage);}
}

