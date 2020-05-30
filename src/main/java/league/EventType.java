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

    public static EventType fromString(String x) {
        switch(x) {
            case "HOST_GOAL":
                return HOST_GOAL;
            case "GUEST_GOAL":
                return GUEST_GOAL;
            case "OFFSIDE":
                return OFFSIDE;
            case "FOUL":
                return FOUL;
            case "RED_TICKET":
                return RED_TICKET;
            case "YELLOW_TICKET":
                return YELLOW_TICKET;
            case "INJURY":
                return INJURY;
            case "PLAYER_SWITCH":
                return PLAYER_SWITCH;
        }
        return null;
    }
}
