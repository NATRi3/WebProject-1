package edu.epam.demoproject.command.impl;

import edu.epam.demoproject.command.*;
import edu.epam.demoproject.constant.*;
import edu.epam.demoproject.controller.request.RequestContext;
import edu.epam.demoproject.service.UserService;


public class SignInCommand implements Command {
    private static final UserService userService = new UserService();

    @Override
    public CommandResult execute(RequestContext requestContext) {
        String page;
        String login = requestContext.getParameter(RequestParameter.LOGIN);
        String password = requestContext.getParameter(RequestParameter.PASSWORD);
        if (userService.checkUserByLoginAndPassword(login, password)) {
            requestContext.addSessionAttribute(SessionAttribute.USER, login);
            if (userService.checkAdminRole(login)){
                requestContext.addSessionAttribute(SessionAttribute.ROLE, RoleType.ADMIN);
                page = PagePath.ADMIN;
            }else{
                requestContext.addSessionAttribute(SessionAttribute.ROLE, RoleType.USER);
                page = PagePath.USER;
            }
            requestContext.addSessionAttribute(SessionAttribute.CURRENT_PAGE, page);
            return CommandResult.setRedirectPage(page);
        } else {
            requestContext.addAttribute(RequestAttribute.ERROR_MESSAGE, Message.WRONG_AUTH);
            page = PagePath.LOGIN;
            requestContext.addSessionAttribute(SessionAttribute.CURRENT_PAGE, page);
            return CommandResult.setForwardPage(page);
        }
    }
}
