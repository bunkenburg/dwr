package org.directwebremoting.servlet;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.directwebremoting.extend.Handler;

/**
 * @author Mike Wilson
 */
public class UncacheableUntransformableResponse extends UncacheableResponse
{
    /* (non-Javadoc)
     * @see org.directwebremoting.servlet.ResponseHandler#handle(org.directwebremoting.extend.Handler, jakarta.servlet.http.HttpServletRequest, jakarta.servlet.http.HttpServletResponse)
     */
    @Override
    public void handle(Handler handler, HttpServletRequest request, HttpServletResponse response)
    {
        super.handle(handler, request, response);
        response.addHeader("Cache-Control", "no-transform");
    }
}

