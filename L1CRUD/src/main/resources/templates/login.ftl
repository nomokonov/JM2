<#import "common.ftl" as c>

<@c.page>
    <h5>${username}</h5>
    ${message?ifExists}
    <form method="post">
        <div class="form-group row">
            <label class="col-sm-2 col-form-label">Username</label>
            <div class="col-sm-6">
                <input type="user" name="username" class="form-control" placeholder="username"  />
            </div>
        </div>
        <div class="form-group row">
            <label class="col-sm-2 col-form-label">Password:</label>
            <div class="col-sm-6">
                <input type="password" name="password" class="form-control" placeholder="password" />
            </div>
        </div>

        <button class="btn btn-primary" type="submit">Login</button>
    </form>
</@c.page>
