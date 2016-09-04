/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2015 Oracle and/or its affiliates. All rights reserved.
 *
 * The contents of this file are subject to the terms of either the GNU
 * General Public License Version 2 only ("GPL") or the Common Development
 * and Distribution License("CDDL") (collectively, the "License").  You
 * may not use this file except in compliance with the License.  You can
 * obtain a copy of the License at
 * http://glassfish.java.net/public/CDDL+GPL_1_1.html
 * or packager/legal/LICENSE.txt.  See the License for the specific
 * language governing permissions and limitations under the License.
 *
 * When distributing the software, include this License Header Notice in each
 * file and include the License file at packager/legal/LICENSE.txt.
 *
 * GPL Classpath Exception:
 * Oracle designates this particular file as subject to the "Classpath"
 * exception as provided by Oracle in the GPL Version 2 section of the License
 * file that accompanied this code.
 *
 * Modifications:
 * If applicable, add the following below the License Header, with the fields
 * enclosed by brackets [] replaced by your own identifying information:
 * "Portions Copyright [year] [name of copyright owner]"
 *
 * Contributor(s):
 * If you wish your version of this file to be governed by only the CDDL or
 * only the GPL Version 2, indicate your decision by adding "[Contributor]
 * elects to include this software in this distribution under the [CDDL or GPL
 * Version 2] license."  If you don't indicate a single choice of license, a
 * recipient has the option to distribute your version of this file under
 * either the CDDL, the GPL Version 2 or to extend the choice of license to
 * its licensees as provided above.  However, if you add GPL Version 2 code
 * and therefore, elected the GPL Version 2 license, then the option applies
 * only if the new code is made subject to such option by the copyright
 * holder.
 */
package javax.security.identitystore;

import javax.resource.spi.AuthenticationMechanism;
import javax.security.CallerPrincipal;
import javax.security.auth.message.module.ServerAuthModule;
import javax.security.identitystore.credential.Credential;

/**
 * <code>IdentityStore</code> is a mechanism for validating a Caller's
 * credentials and accessing a Caller's identity attributes, and would be used
 * by an authentication mechanism, such as the JSR 375 {@link AuthenticationMechanism}
 * or the JSR 196 (JASPIC) {@link ServerAuthModule}.
 * <p>
 * Stores which do only the authentication or authorization is allowed. Authentication only should use the Status
 * AUTHENTICATED.
 * <p>
 * <p>
 * An <code>IdentityStore</code> obtains identity data from a persistence mechanism,
 * such as a file, database, or LDAP.
 */
public interface IdentityStore {

    /**
     * Validates the given credential.
     *
     * @param credential The credential
     * @param callerPrincipal The current CallerPrincipal if user is already authenticated. Value van be null.
     * @return The validation result, including associated caller roles and
     * groups when Authorization is performed (see validationType() )
     */
    CredentialValidationResult validate(Credential credential, CallerPrincipal callerPrincipal);

    /**
     * Determines the order of multiple <code>IdentityStore</code>s. Stores are consulted lower number first.
     * @return the priority value. Lower values first.
     */
    default int priority() {
        return 100;
    }

    /**
     * Determines the type of validation the IdentityStore performs. By default, it performs authentication AND authorization.
     * @return Type of validation.
     */
    default ValidationType validationType() {
        return ValidationType.BOTH;
    }

    /**
     * Determines the type of validation
     */
    enum ValidationType {
        /**
         * Only Authentication is performed, so no roles and groups are determined.
         **/
        AUTHENTICATION,
        /**
         * Only Authorization is performed, so only roles and groups for a principal established by another IdentityStore are determined.
         */
        AUTHORIZATION,
        BOTH
    }
}
