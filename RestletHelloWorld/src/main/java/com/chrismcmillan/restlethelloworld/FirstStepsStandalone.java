/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.chrismcmillan.restlethelloworld;

import org.restlet.Component;
import org.restlet.data.Protocol;

/**
 *
 * @author cjmcmill
 */
public class FirstStepsStandalone {

    public static void main(String[] args) {
        try {
            // Create a new component.
            Component component = new Component();

            // Add a new HTTP server listening on port 8182
            component.getServers().add(Protocol.HTTP, 8182);

            // Attach the sample application.
            component.getDefaultHost().attach(new FirstStepsApplication());

            // Start the component.
            component.start();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}
