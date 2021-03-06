package edu.epam.finalproject.controller.listener;

import edu.epam.finalproject.command.PagePath;
import edu.epam.finalproject.constant.SessionAttribute;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

@WebListener
public class SessionListener implements HttpSessionListener {
    private static final String EN = "en";
    public static final Logger logger = LogManager.getLogger(SessionListener.class);

    @Override
    public void sessionCreated(HttpSessionEvent se) {
        HttpSession session = se.getSession();
        session.setAttribute(SessionAttribute.CURRENT_PAGE, PagePath.LOGIN);
        session.setAttribute(SessionAttribute.LANGUAGE, EN);
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        logger.info(se.getSession() + " destroyed");
    }
}
