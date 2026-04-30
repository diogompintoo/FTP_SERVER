public enum Commands {

    HELP("HELP","Show all commands"),
    LS("LS","List all files on server"),
    MKDIR("MKDIR",""),
    GET("GET",""),
    PUT("PUT",""),
    DELETE("DELETE",""),
    QUIT("QUIT",""),
    BYE("BYE",""),
    DISCONNECT("DISCONNECT",""),
    UNKNOWN("UNKNOWN","");

    private final String commandText;
    private final String description;

    Commands(String commandText, String description) {
        this.commandText = commandText;
        this.description = description;
    }

    public String getCommandText() {
        return commandText;     }

    public String getDescription() {
        return description;     }
}
