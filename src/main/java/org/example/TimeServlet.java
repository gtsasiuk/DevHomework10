package org.example;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@WebServlet(value = "/time")
public class TimeServlet extends HttpServlet {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");

        String timezone = req.getParameter("timezone");

        if (timezone == null || timezone.isEmpty()) {
            Cookie[] cookies = req.getCookies();
            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    if ("lastTimezone".equals(cookie.getName())) {
                        timezone = cookie.getValue();
                        break;
                    }
                }
            }
        }

        ZoneId zoneId;
        try {
            zoneId = (timezone != null && !timezone.isEmpty()) ? ZoneId.of(timezone) : ZoneId.of("UTC");
        } catch (Exception e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid timezone");
            return;
        }

        ZonedDateTime zonedDateTime = ZonedDateTime.now(zoneId);
        String formattedTime = zonedDateTime.format(formatter);

        Cookie cookie = new Cookie("lastTimezone", zoneId.toString());
        resp.addCookie(cookie);

        req.setAttribute("currentTime", formattedTime);
        req.setAttribute("timezone", zoneId.toString());

        req.getRequestDispatcher("index.jsp").forward(req, resp);
    }
}
