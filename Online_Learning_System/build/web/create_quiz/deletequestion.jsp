<%-- 
    Document   : DeleteProductModal
    Created on : Mar 11, 2024, 4:12:50 AM
    Author     : hatro
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<div class="modal fade" id="delete-question-modal" >
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header bg-primary">
                <h5 class="modal-title" id="delete-modal-label">Delete</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <p>Are you sure you want to delete this question ?</p>
            </div>
            <div class="modal-footer">
                <form action="question?action=delete" method="POST">
                    <div class="form-group" style="display: none">
                        <input type="text" class="form-control" id="idDeleteInput" name="id">
                    </div>
                    <button type="button" class="btn btn-secondary" data-dismiss="modal">No</button>
                    <button type="submit" class="btn btn-danger">Yes</button>
                </form>
            </div>
        </div>
    </div>
</div>
<div id="delete-question-modal" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="deleteQuestionModalLabel"
     aria-hidden="true">
    <!-- Your delete modal content here -->
</div>
<script>
    function deleteQuestionModal(id) {
        let inputId = document.querySelector("#idDeleteInput");
        inputId.value = id;
    }

</script>
