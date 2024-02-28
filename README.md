# reproducer-for-support-case

To start the test, execute the main method of the class ch.basler.playground.**UserInformationStarter**. The test needs these **env vars** to be set:

```
AUTH_SERVER_URL=<set the token entpoint url of your OIDC token provider>
CLIENT_ID=<set the client id or the resource name to use to create a valid token for the request>
CLIENT_SECRET=<set the secret of the client id>
USER_ID=<set the userid of the user that tries to access>
PASSWORD=<add his password here>
```