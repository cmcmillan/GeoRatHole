/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.chrismcmillan.restlethelloworld;

import org.restlet.Application;
import org.restlet.Restlet;
import org.restlet.routing.Router;

/**
 *
 * @author cjmcmill
 */
public class FirstStepsApplication extends Application{

    /**
     * Creates a root Restlet that will recieve all incoming calls.
     */
    @Override
    public Restlet createRoot()
    {
        // Create a router Restlet that routes each call to a
        // new instance of HelloWorldResource.
        Router router = new Router(getContext());

        // Defines only one route
        router.attachDefault(HelloWorldResource.class);

        return router;
    }
}
