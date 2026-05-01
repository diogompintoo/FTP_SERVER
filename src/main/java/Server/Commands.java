package Server;

public enum Commands {

    HELP("HELP", "Show all commands"),
    LS("LS", "List all files on server"),
    MKDIR("MKDIR", "Create a directory on the server"),
    GET("GET", "Get a file from the server"),
    PUT("PUT", "Upload a file to the server"),
    DELETE("DELETE", "Delete a file to the server"),
    QUIT("QUIT", "Terminate the connection"),
    BYE("BYE", "Terminate the connection"),
    DISCONNECT("DISCONNECT", "Terminate the connection"),
    UNKNOWN("UNKNOWN", "");

    private final String commandText;
    private final String description;

    Commands(String commandText, String description) {
        this.commandText = commandText;
        this.description = description;
    }

    public String getCommandText() {
        return commandText;
    }

    public String getDescription() {
        return description;
    }


    public static Commands getCommand(String text) {
            for (Commands cmn : Commands.values()) {
                if (cmn.getCommandText().equalsIgnoreCase(text)) {
                    return cmn;
                }
            }
            return UNKNOWN;
        }
}
