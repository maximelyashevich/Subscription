package com.elyashevich.subscription.customtag;

import com.elyashevich.subscription.command.client.ClientType;
import com.elyashevich.subscription.entity.User;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
@SuppressWarnings("serial")
public class UserRoleTag extends TagSupport {
    private User user;
    public void setUser(User user) {
        this.user = user;
    }
    @Override
    public int doStartTag() throws JspException {
        try {
            String to;
            if (ClientType.ADMIN==user.getType()) {
                to = "ADMINISTRATOR";
            } else {
                to = user.getFirstName();
            }
            pageContext.getOut().write(to);
        } catch (IOException e) {
            throw new JspException(e.getMessage());
        }
        return SKIP_BODY;
    }
}