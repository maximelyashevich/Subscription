package com.elyashevich.subscription.command;

import com.elyashevich.subscription.service.*;

public enum CommandType {
    LOGIN(new LoginCommand(new UserService())),
    LOGOUT(new LogoutCommand()),
    REGISTRATION(new RegistrationCommand(new RegistrationService())),
    LANGUAGE(new LocaleCommand()),
    PROFILE(new ProfileCommand(new UserService())),
    SEARCH(new SearchCommand(new PaperService())),
    MONEY(new MoneyCommand(new UserService())),
    BASKET(new BasketCommand(new PaperService())),
    DELETE_PAPER_ITEM(new BasketDeleteCommand(new PaperService())),
    SUBSCRIPTION(new SubscriptionCommand(new SubscriptionService())),
    BLOCK_UNBLOCK_USER(new BlockCommand(new UserService())),
    UPDATE_PAPER(new UpdatePaperCommand(new PaperService())),
    HIDE_PAPER(new HidePaperCommand(new PaperService())),
    ADD_NEW_PAPER(new AddPaperCommand(new PaperService()));
    private ActionCommand command;

    CommandType(ActionCommand actionCommand) {
        this.command = actionCommand;
    }

    public ActionCommand getCurrentCommand() {
        return command;
    }
}
