package com.company.project.core.util;

import jakarta.servlet.http.HttpServletRequest;

public class RequestUtils {

    public static String getParameter(HttpServletRequest request,
                                      String paramName) {
        return getParameterOrDefault(request, paramName, null);
    }

    public static String getParameterOrDefault(HttpServletRequest request,
                                               String paramName,
                                               String defaultValue) {
        String value = request.getParameter(paramName);
        return value != null ? value : defaultValue;
    }

    public static String getParameterNotBlank(HttpServletRequest request,
                                              String paramName,
                                              String defaultValue) {
        String value = request.getParameter(paramName);
        return value != null && !value.trim().isEmpty() ? value : defaultValue;
    }

    public static Long getParameterAsLong(HttpServletRequest request,
                                          String paramName) {
        return getParameterAsLong(request, paramName, null);
    }

    public static Long getParameterAsLong(HttpServletRequest request,
                                            String paramName,
                                            Long defaultValue) {
        String value = request.getParameter(paramName);
        try {
            return value != null ? Long.parseLong(value) : defaultValue;
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }
}
