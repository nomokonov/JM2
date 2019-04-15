
$('#userModal').on('show.bs.modal', function (event) {
    var button = $(event.relatedTarget) // Button that triggered the modal
    var userid = button.data('whatever') // Extract info from data-* attributes
    var username =  button.data('username')
    // If necessary, you could initiate an AJAX request here (and then do the updating in a callback).
    // Update the modal's content. We'll use jQuery here, but you could use a data binding library or other methods instead.
    //  $('#editUserForm').load('/admin/ediuser?id=' + userid)
    var modal = $(this)
    modal.find('.modal-title').text('Edit user ' + username)
    modal.find('.modal-body').load('edituser?id=' + userid)
    //
    // $('#editUserForm').html('<div>AAAAAAAAAAAAAAAAAA</div>')




})

