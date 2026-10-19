# Sive.Host deployment plan

The intended architecture is:

Android phone
  -> HTTPS
  -> Nginx
  -> ASP.NET Core StyleDrop.Api
  -> MySQL

SSH is only for administration/deployment.

Recommended deployment order:
1. Verify .NET runtime/SDK on the VPS.
2. Verify/install MySQL or MariaDB.
3. Create the `styledrop` database and restricted API database user.
4. Publish `StyleDrop.Api`.
5. Configure production secrets as environment variables/server configuration.
6. Create a systemd service.
7. Configure Nginx reverse proxy.
8. Configure HTTPS.
9. Run the API health endpoint.
10. Connect Android Retrofit to the HTTPS endpoint.
