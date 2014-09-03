package com.imath.connect.security;

import javax.ws.rs.core.SecurityContext;

/**
 * The SecurityManager class, providing an extra layer of security 
 * @author imath
 *
 */
public class SecurityManager {

    public static void secureBasic(String userName, SecurityContext sc) throws Exception {
        if (sc==null) return; // If sc==null is used for integration tests
        if (sc.getUserPrincipal()==null) throw new Exception();
        if (!sc.getUserPrincipal().getName().equals(userName)) throw new Exception();
    }
}
