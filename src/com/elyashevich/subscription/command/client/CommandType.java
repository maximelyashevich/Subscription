package com.elyashevich.subscription.command.client;

import com.elyashevich.subscription.command.*;
import com.elyashevich.subscription.logic.UserService;

public enum CommandType {
    LOGIN(new LoginCommand(new UserService())),
    LOGOUT(new LogoutCommand()),
    REGISTRATION(new RegistrationCommand(new UserService())),
    MAIL(new MailCommand()),
    LANGUAGE(new LocaleCommand());
    private ActionCommand command;

    CommandType(ActionCommand actionCommand) {
        this.command = actionCommand;
    }

    public ActionCommand getCurrentCommand() {
        return command;
    }
}
