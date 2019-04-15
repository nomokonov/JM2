$(document).ready(function () {
    listUsers();
    getRoles();
});


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
        // modal.find('#listRoles').replaceWith(rolesstr);
        checkUserRoles(data.roles);
        modal.find('#description').val(data.description);
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
        $("#listusers").replaceWith('<tbody>' + listUsers + '</tbody>');
    });
}

function getRoles(){
    $.getJSON('/rest/role', function (data) {
        $('#listRoles').replaceWith(rolesForForm(data));
    });

}
function rolesForForm(allroles){
    var listRoles = '';
    $.each(allroles, function (index, value) {
        listRoles += '<div class="input-group mb-3" >' +
        '<div class="input-group-prepend"> '+
        '<div class="input-group-text">' +
        '<input type="checkbox" id="role_id_' + value.id + '" name="listRoles"';

        listRoles += 'aria-label="Checkbox for following text input"> '+
        '</div>' +
        '</div>' +
        '<input type="text" class="form-control" aria-label="Text input with checkbox"';
        listRoles += ' value="' + value.description + ' (' + value.name + ')"';
        listRoles += ' readonly> </div>';

        });
    return listRoles;
}

function checkUserRoles(userRoles){
    var form = $('#editUserForm');
    form.find('input[type=checkbox]').each(function() {
        this.checked = false;
    });

    $.each(userRoles, function(index, value){
        form.find('#role_id_' + value.id).prop('checked', true);
    });

}