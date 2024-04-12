package org.directwebremoting.servlet;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.directwebremoting.extend.Handler;

/**
 * @author Mike Wilson
 */
public class PublicRevalidatingResponse implements ResponseHandler {

    public void handle(Handler handler, HttpServletRequest request, HttpServletResponse response) {
        // Set standard HTTP/1.1 cache headers.
        response.setHeader("Cache-Control", "public, must-revalidate");

        // Set to expire far in the past. Prevents caching at the proxy server
        response.setDateHeader("Expires", 0);
    }
}

