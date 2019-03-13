<#import "common.ftl" as c>

<@c.page>


    <h2>User list</h2>
    <form action="newuser">
      <button type="submit" class="btn btn-primary">Add user</button>
    </form>
<p></p>
    <#list users>
        <div class="list-group">
        <#items as user>
            <a href="edituser?id=${user.id}" class="list-group-item list-group-item-action">${user.name }</a>
        </#items>
        </div>
    <#else>
        <p>No users
    </#list>

</@c.page>