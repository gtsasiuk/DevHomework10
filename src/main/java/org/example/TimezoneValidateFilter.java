package org.example;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.TimeZone;

@WebFilter(value = "/time")
public class TimezoneValidateFilter extends HttpFilter {

    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
        String timezone = req.getParameter("timezone");

        if (timezone != null && !timezone.isEmpty() && !isValidTimezone(timezone)) {
            res.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid timezone");
            return;
        }

        chain.doFilter(req, res);
    }

    private boolean isValidTimezone(String timezone) {
        String[] availableIDs = TimeZone.getAvailableIDs();
        for (String id : availableIDs) {
            if (id.equals(timezone)) {
                return true;
            }
        }
        return false;
    }
}
