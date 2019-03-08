<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>${title}!</title>
</head>
    <body>
        <h2>User list</h2>

        <#list users>
          <ul>
            <#items as user>
                <li>${user.name} ( ${user.description})</li>
            </#items>
          </ul>
        <#else>
          <p>No users
        </#list>
    </body>
</html>