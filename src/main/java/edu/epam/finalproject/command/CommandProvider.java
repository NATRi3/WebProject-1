package edu.epam.finalproject.command;

import edu.epam.finalproject.command.impl.general.EmptyCommand;

public class CommandProvider {
    private CommandProvider() {
    }

    public static Command provideCommand(String command) {
        Command currentCommand;

        if (command == null || command.isEmpty()) {
            return new EmptyCommand();
        }
        try {
            currentCommand = CommandType.valueOf(command.toUpperCase()).getCommand();
        } catch (IllegalArgumentException exp) {
            currentCommand = new EmptyCommand();
        }
        return currentCommand;
    }
}
