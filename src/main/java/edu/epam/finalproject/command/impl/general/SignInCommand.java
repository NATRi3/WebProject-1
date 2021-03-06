package edu.epam.finalproject.command.impl.general;

import edu.epam.finalproject.command.*;
import edu.epam.finalproject.constant.*;
import edu.epam.finalproject.controller.request.RequestContext;
import edu.epam.finalproject.service.ServiceException;
import edu.epam.finalproject.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class SignInCommand implements Command {
    private static final UserService userService = new UserService();
    private static final Logger logger = LogManager.getLogger(SignInCommand.class);

    @Override
    public CommandResult execute(RequestContext requestContext) {
        String page;
        String login = requestContext.getParameter(RequestParameter.LOGIN);
        String password = requestContext.getParameter(RequestParameter.PASSWORD);
        boolean isUserExist = false;
        try {
            isUserExist = userService.checkUserByLoginAndPassword(login, password);
        } catch (ServiceException e) {
            e.printStackTrace();
            logger.error(e);
        }
        if (isUserExist) {
            boolean isUserNotBlocked = false;
            try {
                isUserNotBlocked = userService.isUserNotBlocked(login);
            } catch (ServiceException e) {
                logger.error(e);
                e.printStackTrace();
            }
            if (isUserNotBlocked) {
                boolean isAdmin = false;
                try {
                    isAdmin = userService.checkAdminRole(login);
                } catch (ServiceException e) {
                    logger.error(e);
                    e.printStackTrace();
                }
                if (isAdmin) {
                    requestContext.addSessionAttribute(SessionAttribute.ROLE, RoleType.ADMIN);
                    page = PagePath.ADMIN;
                } else {
                    requestContext.addSessionAttribute(SessionAttribute.ROLE, RoleType.USER);
                    page = PagePath.USER;
                }
                requestContext.addSessionAttribute(SessionAttribute.USER, login);
                requestContext.addSessionAttribute(SessionAttribute.CURRENT_PAGE, page);
                return CommandResult.setRedirectPage(page);
            } else {
                requestContext.addAttribute(RequestAttribute.ERROR_MESSAGE, Message.USER_BLOCKED);
                page = PagePath.LOGIN;
                requestContext.addSessionAttribute(SessionAttribute.CURRENT_PAGE, page);
                return CommandResult.setForwardPage(page);
            }
        } else {
            requestContext.addAttribute(RequestAttribute.ERROR_MESSAGE, Message.WRONG_AUTH);
            page = PagePath.LOGIN;
            requestContext.addSessionAttribute(SessionAttribute.CURRENT_PAGE, page);
            return CommandResult.setForwardPage(page);
        }
    }
}
