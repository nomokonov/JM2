$(document).ready(function () {
    listUsers();
    getRoles();
    //REST Update user by id on Click() button
    $('#updateUser').click(function () {
        var form = $('#updateForm');

        var obj = {};

        obj['id'] = +form.find('#userid').val();
        obj['name'] = form.find('#Username').val();
        obj['password'] = form.find('#Password').val();
        obj['email'] = form.find('#email').val();
        obj['description'] = form.find('#Description').val();
        obj['roles'] = [];
        form.find('[id^="role_id"]:checked').each(function () {
            var role = {};
            role.id = +$(this).val();

            obj['roles'].push(role);

        });
        var json = JSON.stringify(obj);
        $.ajax({
            url: "/rest/user/" + form.find('#userid').val(),
            type: "POST",
            contentType: "application/json; charset=utf-8",
            data: json,
            datatype: "json",
            success: function (data) {
                $(".modal").modal("hide");
                listUsers();
                getRoles();
            }

        });
    });
//Cancel submit action and REST request
    $('#newuser-btn').submit(function () {
        return false;
    });

    $('#newuser-btn').click(function () {
        var form = $('#newuserform');
        var obj = {};

        obj['name'] = form.find('#Username').val();
        obj['password'] = form.find('#Password').val();
        obj['email'] = form.find('#email').val();
        obj['description'] = form.find('#Description').val();
        obj['roles'] = [];
        form.find('[id^="role_id"]:checked').each(function () {
            var role = {};
            role.id = +$(this).val();

            obj['roles'].push(role);

        });
        var json = JSON.stringify(obj);
        $.ajax({
            url: "/rest/user/",
            type: "PUT",
            contentType: "application/json; charset=utf-8",
            data: json,
            datatype: "json",
            success: function (data) {
                console.log(data);
                listUsers();
                getRoles();
            }
        });
    });


})
;


$('#userModal').on('show.bs.modal', function (event) {
    var button = $(event.relatedTarget) // Button that triggered the modal
    var userid = button.data('whatever') // Extract info from data-* attributes
    var username = button.data('username')
    // If necessary, you could initiate an AJAX request here (and then do the updating in a callback).
    // Update the modal's content. We'll use jQuery here, but you could use a data binding library or other methods instead.
    //  $('#editUserForm').load('/admin/ediuser?id=' + userid)
    var modal = $(this)
    $.getJSON('/rest/user/' + userid, function (data) {
        modal.find('.modal-title').text('Edit user ' + username)
        modal.find('#userid').val(data.id);
        modal.find('#Username').val(data.name);
        modal.find('#Password').val(data.password);
        modal.find('#email').val(data.email);
        checkUserRoles(data.roles);
        modal.find('#description').val(data.description);
        modal.find('#deleteuserbtn').attr('onclick','deleteUser(' + data.id +')');
    });

})

function listUsers() {
    $.getJSON('/rest/user', function (data) {
        var listUsers = '';

        $.each(data, function (index, value) {
            listUsers += '<tr>';
            listUsers += '<td>' + value.id + '</td>';
            listUsers += '<td>';
            $.each(value.roles, function (index, role) {
                listUsers += role.name + ', ';
            });
            listUsers += '</td>';
            listUsers += '<td>' + value.name + '</td>';
            listUsers += '<td>' + value.email + '</td>';
            listUsers += '<td><button type="button" class="btn btn-info" data-toggle="modal"' +
                ' data-target="#userModal"' +
                ' data-whatever=' + value.id +
                ' data-username="' + value.name + '"' +
                ' >Edit</button></td>';

            listUsers += '</tr>';
        });
        $("#listusers").replaceWith('<tbody id="listusers">' + listUsers + '</tbody>');
    });
}

function getRoles() {
    $.getJSON('/rest/role', function (data) {
        var l = $('.listRoles').length;
        $('.listRoles').each(function () {
            $(this).html(rolesForForm(data));
        });
    });
}

function rolesForForm(allroles) {
    var listRoles = '';
    $.each(allroles, function (index, value) {
        listRoles += '<div class="input-group mb-3 "   >' +
            '<div class="input-group-prepend"> ' +
            '<div class="input-group-text">' +
            '<input type="checkbox" id="role_id_' + value.id + '" name="listRoles" value=' + value.id;

        listRoles += ' aria-label="Checkbox for following text input"> ' +
            '</div>' +
            '</div>' +
            '<input type="text" class="form-control" aria-label="Text input with checkbox"';
        listRoles += ' value="' + value.description + ' (' + value.name + ')"';
        listRoles += ' readonly> </div>';

    });
    return listRoles;
}

function checkUserRoles(userRoles) {
    var form = $('#editUserForm');
    form.find('input[type=checkbox]').each(function () {
        this.checked = false;
    });

    $.each(userRoles, function (index, value) {
        form.find('#role_id_' + value.id).prop('checked', true);
    });
}

//
function deleteUser(id){


        $.ajax({
            url: "/rest/user/"+id,
            type: "DELETE",
            contentType: "application/json; charset=utf-8",
            datatype: "json",
            success: function (data) {
                $(".modal").modal("hide");
                console.log(data);
                listUsers();
                getRoles();
            }
        });
}