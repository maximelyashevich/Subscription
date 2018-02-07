package com.elyashevich.subscription.tag;

import com.elyashevich.subscription.command.ClientType;
import com.elyashevich.subscription.entity.User;
import com.elyashevich.subscription.util.TextConstant;

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
            if (user!=null) {
                if (ClientType.ADMIN == user.getType()) {
                    to = TextConstant.ADMINISTRATOR;
                } else {
                    to = user.getFirstName();
                }
                pageContext.getOut().write(to);
            }
        } catch (IOException e) {
            throw new JspException(e.getMessage());
        }
        return SKIP_BODY;
    }
}