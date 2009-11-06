package com.chrismcmillan.restlethelloworld;

import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

/**
 * Hello world!
 *
 */
public class HelloWorldResource extends ServerResource
{
    @Get
    public String represent()
    {
        return "hello, world";
    }
}
