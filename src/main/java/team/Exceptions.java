package team;

class IncorrectPlayerDetails extends Exception {
    public IncorrectPlayerDetails(String errorMessage) {
        super(errorMessage);
    }
}
