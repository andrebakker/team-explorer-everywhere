// Copyright (c) Microsoft. All rights reserved.
// Licensed under the MIT license. See License.txt in the repository root.

package com.microsoft.tfs.client.common.credentials;

import java.net.URI;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import java.util.UUID;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.microsoft.teamfoundation.sourcecontrol.webapi.GitHttpClient;
import com.microsoft.tfs.client.common.config.CommonClientConnectionAdvisor;
import com.microsoft.tfs.core.TFSConfigurationServer;
import com.microsoft.tfs.core.TFSConnection;
import com.microsoft.tfs.core.TFSTeamProjectCollection;
import com.microsoft.tfs.core.config.persistence.DefaultPersistenceStoreProvider;
import com.microsoft.tfs.core.credentials.CachedCredentials;
import com.microsoft.tfs.core.credentials.CredentialsManager;
import com.microsoft.tfs.core.httpclient.Cookie;
import com.microsoft.tfs.core.httpclient.CookieCredentials;
import com.microsoft.tfs.core.httpclient.Credentials;
import com.microsoft.tfs.core.httpclient.PreemptiveUsernamePasswordCredentials;
import com.microsoft.tfs.core.httpclient.UsernamePasswordCredentials.PatCredentials;
import com.microsoft.tfs.core.util.URIUtils;
import com.microsoft.tfs.jni.helpers.LocalHost;
import com.microsoft.tfs.util.StringHelpers;
import com.microsoft.visualstudio.services.delegatedauthorization.DelegatedAuthorizationHttpClient;
import com.microsoft.visualstudio.services.delegatedauthorization.model.SessionToken;

/**
 * Static methods to manipulate {@link SessionToken}s.
 *
 * @threadsafety thread-safe
 */

public abstract class CredentialsHelper {
    private static final Log log = LogFactory.getLog(CredentialsHelper.class);

    private static CredentialsManager gitCredentialsManager =
        EclipseCredentialsManagerFactory.getGitCredentialsManager();

    public static void createAccountCodeAccessToken(final TFSConnection connection) {
        if (connection.isHosted() && !hasAccountCodeAccessToken(connection)) {
            final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss"); //$NON-NLS-1$
            dateFormat.setTimeZone(TimeZone.getTimeZone("UTC")); //$NON-NLS-1$

            final String tokenDisplayName = MessageFormat.format(
                PatCredentials.TOKEN_DESCRIPTION,
                connection.getAuthorizedAccountName(),
                LocalHost.getShortName(),
                dateFormat.format(new Date()));

            final TFSConnection vstsConnection = getVstsRootConnection(connection);
            final DelegatedAuthorizationHttpClient authorizationClient =
                new DelegatedAuthorizationHttpClient(vstsConnection);

            final UUID accountId = getAccountId(connection);
            final String pat =
                authorizationClient.createAccountCodeAccessToken(tokenDisplayName, accountId).getAlternateToken();

            final URI baseURI = connection.getBaseURI();
            gitCredentialsManager.setCredentials(new CachedCredentials(baseURI, pat)); // $NON-NLS-1$
        }
    }

    public static void refreshAccountCodeAccessToken(final TFSConnection connection) {
        if (connection.isHosted()) {
            final URI baseURI = connection.getBaseURI();
            final CachedCredentials cachedCredentials = gitCredentialsManager.getCredentials(baseURI);

            if (cachedCredentials.isPatCredentials()) {
                gitCredentialsManager.removeCredentials(cachedCredentials);
                createAccountCodeAccessToken(connection);
            }
        }

    }

    public static boolean hasAccountCodeAccessToken(final TFSConnection connection) {
        if (connection.isHosted()) {
            final URI baseURI = connection.getBaseURI();
            final CachedCredentials cachedCredentials = gitCredentialsManager.getCredentials(baseURI);

            if (cachedCredentials != null
                && cachedCredentials.isPatCredentials()
                && !StringHelpers.isNullOrEmpty(cachedCredentials.getPassword())) {

                return true;
            }
        }

        return false;
    }

    public static boolean hasAlternateCredentials(final TFSConnection connection) {
        if (connection.isHosted()) {
            final URI baseURI = connection.getBaseURI();
            final CachedCredentials cachedCredentials = gitCredentialsManager.getCredentials(baseURI);

            if (cachedCredentials != null && cachedCredentials.isUsernamePasswordCredentials()) {
                return true;
            }
        }

        return false;
    }

    public static boolean isAccountCodeAccessTokenValid(final TFSConnection connection) {
        if (connection.isHosted()) {
            final URI baseURI = connection.getBaseURI();
            final CachedCredentials cachedCredentials = gitCredentialsManager.getCredentials(baseURI);

            if (cachedCredentials != null && cachedCredentials.isPatCredentials()) {
                final PatCredentials patCredentials = (PatCredentials) cachedCredentials.toCredentials();

                final TFSTeamProjectCollection patBasedConnection = new TFSTeamProjectCollection(
                    baseURI,
                    PreemptiveUsernamePasswordCredentials.newFrom(patCredentials),
                    new CommonClientConnectionAdvisor(Locale.getDefault(), TimeZone.getDefault()));
                final GitHttpClient client = new GitHttpClient(patBasedConnection);

                return client.checkConnection();
            }
        }

        return false;
    }

    public static UUID getAccountId(final TFSConnection connection) {
        if (connection instanceof TFSConfigurationServer) {
            return UUID.fromString(((TFSConfigurationServer) connection).getInstanceID().toString());
        } else {
            return getAccountId(((TFSTeamProjectCollection) connection).getConfigurationServer());
        }
    }

    public static Credentials getVstsRootCredentials(final TFSConnection connection) {
        final CredentialsManager credentialsManager =
            EclipseCredentialsManagerFactory.getCredentialsManager(DefaultPersistenceStoreProvider.INSTANCE);
        final CachedCredentials cachedCredentials = credentialsManager.getCredentials(URIUtils.VSTS_ROOT_URL);

        if (cachedCredentials != null) {
            return cachedCredentials.toCredentials();
        } else {
            CookieCredentials cookieCredentials = (CookieCredentials) connection.getCredentials();
            for (Cookie cookie : cookieCredentials.getCookies()) {
                cookie.setDomain(URIUtils.VSTS_SUFFIX);
            }
            return cookieCredentials;
        }
    }

    public static TFSConnection getVstsRootConnection(final TFSConnection connection) {
        final TFSConnection vstsConnection = new TFSTeamProjectCollection(
            URIUtils.VSTS_ROOT_URL,
            getVstsRootCredentials(connection),
            new CommonClientConnectionAdvisor(Locale.getDefault(), TimeZone.getDefault()));

        return vstsConnection;
    }
}