package Worker;

import Commands.RunInterface;

import java.util.HashMap;

public class CommandHandler {

    public static final CommandParser parser = new CommandParser();
    public static HashMap<String, RunInterface> commands = new HashMap<>();
    public static CommandParser parse;

    public static void handleCommand(CommandParser.commandContainer cmd) {

        if(commands.containsKey(cmd.invoke)) {

            boolean safe = commands.get(cmd.invoke).called(cmd.args, cmd.event);

            if (!safe) {

                commands.get(cmd.invoke).action(cmd.args, cmd.event);
                commands.get(cmd.invoke).executed(safe, cmd.event);
            } else {
                commands.get(cmd.invoke).executed(safe, cmd.event);
            }

        }


    }

}
