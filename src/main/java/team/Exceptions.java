package team;

class IncorrectPlayerDetails extends Exception {
    public IncorrectPlayerDetails(String errorMessage) {
        super(errorMessage);
    }
}

class notOwnerOfTeam extends Exception{
    public notOwnerOfTeam(String errorMessage) {
        super(errorMessage);
    }
}

class InvalidSubscription extends Exception{
    public InvalidSubscription(String errorMessage) {
        super(errorMessage);
    }
}
class removeAssetException extends Exception{
    public removeAssetException(String errorMessage) {
        super(errorMessage);
    }
}

class authorizationException extends Exception{
    public authorizationException(String errorMessage){super(errorMessage);}
}
