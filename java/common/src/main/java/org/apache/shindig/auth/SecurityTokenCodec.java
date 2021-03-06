/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.apache.shindig.auth;

import com.google.inject.ImplementedBy;

import java.util.Map;

/**
 *  Handles verification of gadget security tokens.
 *
 * @since 2.0.0
 */
@ImplementedBy(DefaultSecurityTokenCodec.class)
public interface SecurityTokenCodec {

  /**
   * The security token value must be passed on a map value referenced by this key. Additional
   * parameters can be passed as seen fit.
   */
  String SECURITY_TOKEN_NAME = "token";

  /**
   * Active URL for the request.  Must include protocol, host, and port.  May include path
   * and may include query.
   */
  String ACTIVE_URL_NAME = "activeUrl";

  /**
   * The configuration parameter for security token time-to-lives.
   */
  String SECURITY_TOKEN_TTL_CONFIG = "gadgets.securityTokenTTL";

  /**
   * Decrypts and verifies a gadget security token to return a gadget token.
   *
   * @param tokenParameters Map containing a entry 'token' in wire format (probably encrypted.)
   * @return the decrypted and verified token.
   * @throws SecurityTokenException If tokenString is not a valid token
   */
  SecurityToken createToken(Map<String, String> tokenParameters)
      throws SecurityTokenException;

  String encodeToken(SecurityToken token) throws SecurityTokenException;

  /**
   * This method is deprecated in favor of {@link SecurityTokenCodec#getTokenTimeToLive(String)}.
   * Implementations should only rely on this method to return the default time-to-live of tokens
   * generated by this codec in the case where <code>getTokenTimeToLive(String)</code> fails.
   *
   * @return The default amount of time a token generated by this codec can be expected to live.
   * @see SecurityTokenCodec#getTokenTimeToLive(String)
   */
  @Deprecated
  int getTokenTimeToLive();

  /**
   * @param container
   *          The container the token is for
   * @return The amount of time a token generated by this codec within the given container can be
   *         expected to live.
   */
  int getTokenTimeToLive(String container);
}
