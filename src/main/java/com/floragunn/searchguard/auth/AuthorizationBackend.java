/*
 * Copyright 2015-2017 floragunn GmbH
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 */

package com.floragunn.searchguard.auth;

import org.elasticsearch.ElasticsearchSecurityException;

import com.floragunn.searchguard.user.AuthCredentials;
import com.floragunn.searchguard.user.User;

/**
 * Search Guard custom authorization backends need to implement this interface.
 * <p/>
 * Authorization backends populate a prior authenticated {@link User} with roles who's the user is a member of.
 * <p/>
 * Implementation classes must provide a public constructor
 * <p/>
 * {@code public MyHTTPAuthenticator(org.elasticsearch.common.settings.Settings settings, java.nio.file.Path configPath)}
 * <p/>
 * The constructor should not throw any exception in case of an initialization problem.
 * Instead catch all exceptions and log a appropriate error message. A logger can be instantiated like:
 * <p/>
 * {@code private final Logger log = LogManager.getLogger(this.getClass());}
 *
 * <p/>
 * <b>Custom authorizers is a commercial feature. To make them work you need to obtain a license here:
 * https://floragunn.com
 * </b>
 */
public interface AuthorizationBackend {

    /**
     * The type (name) of the authorizer. Only for logging.  
     * @return the type
     */
    String getType();

    /**
     * Populate a {@link User} with roles. This method will not be called for cached users.
     * <p/>
     * Add them by calling either {@code user.addRole()} or {@code user.addRoles()}
     * </P>
     * @param user The authenticated user to populate with roles, never null
     * @param credentials Credentials to authenticate to the authorization backend, maybe null.
     * <em>This parameter is for future usage, currently always empty credentials are passed!</em> 
     * @throws ElasticsearchSecurityException in case when the authorization backend cannot be reached 
     * or the {@code credentials} are insufficient to authenticate to the authorization backend.
     */
    void fillRoles(User user, AuthCredentials credentials) throws ElasticsearchSecurityException;

}
