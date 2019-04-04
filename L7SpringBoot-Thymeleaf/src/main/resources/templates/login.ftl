<#import "common.ftl" as c>

<@c.page>
    <h5>Login</h5>

    <form method="post" action="/j_spring_security_check">
        <div class="form-group row">
            <label class="col-sm-2 col-form-label">Username</label>
            <div class="col-sm-6">
                <input type="user" name="username" class="form-control" placeholder="username"/>
            </div>
        </div>
        <div class="form-group row">
            <label class="col-sm-2 col-form-label">Password:</label>
            <div class="col-sm-6">
                <input type="password" name="password" class="form-control" placeholder="password"/>
            </div>
        </div>

        <button class="btn btn-primary" type="submit">Login</button>
    </form>
<p></p>
    <#if message??>
        <div class="alert alert-danger" role="alert">
            ${message}
        </div>
    </#if>

</@c.page>