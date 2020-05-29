package league;

public enum EventType {
    HOST_GOAL,
    GUEST_GOAL,
    OFFSIDE,
    FOUL,
    RED_TICKET,
    YELLOW_TICKET,
    INJURY,
    PLAYER_SWITCH;

    public static EventType fromInteger(int x) {
        switch(x) {
            case 0:
                return HOST_GOAL;
            case 1:
                return GUEST_GOAL;
            case 2:
                return OFFSIDE;
            case 3:
                return FOUL;
            case 4:
                return RED_TICKET;
            case 5:
                return YELLOW_TICKET;
            case 6:
                return INJURY;
            case 7:
                return PLAYER_SWITCH;
        }
        return null;
    }
}
