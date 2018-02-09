package com.elyashevich.subscription.command;

import com.elyashevich.subscription.service.impl.PaperServiceImpl;
import com.elyashevich.subscription.service.impl.RegistrationServiceImpl;
import com.elyashevich.subscription.service.impl.SubscriptionServiceImpl;
import com.elyashevich.subscription.service.impl.UserServiceImpl;

public enum CommandType {
    LOGIN(new LoginCommand(new UserServiceImpl())),
    LOGOUT(new LogoutCommand()),
    REGISTRATION(new RegistrationCommand(new RegistrationServiceImpl())),
    LANGUAGE(new LocaleCommand()),
    PROFILE(new ProfileCommand(new UserServiceImpl())),
    SEARCH(new SearchCommand(new PaperServiceImpl())),
    MONEY(new MoneyCommand(new UserServiceImpl())),
    BASKET(new BasketCommand(new PaperServiceImpl())),
    DELETE_PAPER_ITEM(new BasketDeleteCommand(new PaperServiceImpl())),
    SUBSCRIPTION(new SubscriptionCommand(new SubscriptionServiceImpl())),
    BLOCK_UNBLOCK_USER(new BlockCommand(new UserServiceImpl())),
    UPDATE_PAPER(new UpdatePaperCommand(new PaperServiceImpl())),
    HIDE_PAPER(new HidePaperCommand(new PaperServiceImpl())),
    ADD_NEW_PAPER(new AddPaperCommand(new PaperServiceImpl()));
    private ActionCommand command;

    CommandType(ActionCommand actionCommand) {
        this.command = actionCommand;
    }

    public ActionCommand getCurrentCommand() {
        return command;
    }
}
