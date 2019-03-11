<#import "common.ftl" as c>

<@c.page>

    <h2>${title}</h2>
    <form action="adduser" method="post">
    <div class="form-group">
      <label for="Username">Username</label>
      <input type="text" class="form-control" id="Username" placeholder="Username" value="<#if user??>${user.name}</#if>">
    </div>
    <div class="form-group">
            <label for="Password">Password</label>
            <input type="text" class="form-control" id="Password" placeholder="Password" value="<#if user??>${user.password}</#if>" >
    </div>
    <div class="form-group">
            <label for="Description">Description</label>
            <input type="text" class="form-control" id="Description" placeholder="Description" value="<#if user??>${user.description}</#if>">
    </div>
    <button type="button" class="btn btn-primary">Save user</button>
    <button type="button" class="btn btn-danger">Delete user</button>
    </form>

</@c.page>