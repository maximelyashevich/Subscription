package com.elyashevich.subscription.command.client;

import com.elyashevich.subscription.command.*;
import com.elyashevich.subscription.service.*;

public enum CommandType {
    LOGIN(new LoginCommand(new LoginService())),
    LOGOUT(new LogoutCommand()),
    REGISTRATION(new RegistrationCommand(new RegistrationService())),
    MAIL(new MailCommand()),
    LANGUAGE(new LocaleCommand()),
    PROFILE(new ProfileCommand(new UserService())),
    SEARCH(new SearchCommand(new PaperService())),
    MONEY(new MoneyCommand(new UserService())),
    BASKET(new BasketCommand(new PaperService())),
    DELETE_PAPER_ITEM(new BasketDeleteCommand(new PaperService())),
    SUBSCRIPTION(new SubscriptionCommand(new SubscriptionService()));
    private ActionCommand command;

    CommandType(ActionCommand actionCommand) {
        this.command = actionCommand;
    }

    public ActionCommand getCurrentCommand() {
        return command;
    }
}
