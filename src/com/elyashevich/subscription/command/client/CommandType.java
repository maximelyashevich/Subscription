package com.elyashevich.subscription.command.client;

import com.elyashevich.subscription.command.*;
import com.elyashevich.subscription.service.LoginService;
import com.elyashevich.subscription.service.RegistrationService;

public enum CommandType {
    LOGIN(new LoginCommand(new LoginService())),
    LOGOUT(new LogoutCommand()),
    REGISTRATION(new RegistrationCommand(new RegistrationService())),
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
