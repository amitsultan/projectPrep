package team;

class IncorrectPlayerDetails extends Exception {
    public IncorrectPlayerDetails(String errorMessage) {
        super(errorMessage);
    }
}
class SameControllerToDifferentLeagues extends Exception {
    public SameControllerToDifferentLeagues(String errorMessage) {
        super(errorMessage);
    }
}
