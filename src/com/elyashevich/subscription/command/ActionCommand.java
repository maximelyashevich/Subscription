package com.elyashevich.subscription.command;

import com.elyashevich.subscription.servlet.Router;

import javax.servlet.http.HttpServletRequest;

public interface ActionCommand {
    Router execute(HttpServletRequest request);
}
