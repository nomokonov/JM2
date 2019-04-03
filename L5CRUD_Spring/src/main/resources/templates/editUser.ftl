<#import "common.ftl" as c>

<@c.page>

    <h2>${title}</h2>
    <#if message??>
        <div class="alert alert-danger" name="alert">
            ${message}
        </div>
    </#if>
    <form action="${action}" method="post">
        <#if action=="saveuser">
            <input type="hidden" name="id" id="Id" value="<#if user??>${user.id}</#if>">
        </#if>

        <div class="form-group">
            <label for="Username">Username</label>
            <input type="text" class="form-control" name="username" id="Username" placeholder="Username"
                   value="<#if user??>${user.name}</#if>">
        </div>
        <div class="form-group">
            <label for="Password">Password</label>
            <input type="password" class="form-control" name="password" id="Password" placeholder="Password"
                   value="<#if user??>${user.password}</#if>">
        </div>
        <div class="form-group">
            <label for="Role">Role</label>
            <#--UserRoles bein-->
            <#if roles??>
            <#list roles>
            <#items as name>
                <div class="input-group mb-3">
                    <div class="input-group-prepend">
                        <div class="input-group-text">
                            <input type="checkbox" value="${name.id}" name="listRoles"
                                    <#if userroles??>
                                        ${userroles?seq_contains(name.id)?string(" checked ", "")}
                                    </#if>
                                   aria-label="Checkbox for following text input">
                        </div>
                    </div>
                    <input type="text" class="form-control" aria-label="Text input with checkbox" value="${name.description}(${name.name})"
                           readonly>
                </div>

            </#items>

            <#else>
            <p>No roles
                </#list>
                </#if>
                <#--End UserRole-->

        </div>
        <div class="form-group">
            <label for="Description">Description</label>
            <input type="text" class="form-control" name="description" id="Description" placeholder="Description"
                   value="<#if user??>${user.description}</#if>">
        </div>
        <button type="submit" class="btn btn-primary">Save user</button>
    </form>
    <#if action=="saveuser">
        <p></p>
        <h3>For deleting user press button</h3>
        <form action="deleteuser" method="post">
            <input type="hidden" name="id" id="Id" value="<#if user??>${user.id}</#if>">
            <button type="submit" class="btn btn-danger btn-lg btn-block">Delete user</button>
        </form>
    </#if>

</@c.page>