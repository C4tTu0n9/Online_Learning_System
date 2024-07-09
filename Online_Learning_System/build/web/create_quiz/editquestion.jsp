<%@page contentType="text/html" pageEncoding="UTF-8"%>
<div class="modal fade" id="editQuestionModal">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header bg-primary">
                <h5 class="modal-title">Edit Question</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <form id="editQuestionForm" action="question?action=edit" method="POST">
                    <!--id-->
                    <div class="form-group" style="display: none">
                        <input type="text" class="form-control" id="idEditInput" name="id">
                    </div>
                    <div class="form-group row">
                        <label for="questionNoInput" class="col-form-label col-md-3">Question No:</label>
                        <div class="col-md-9">
                            <input type="number" min="1" max="200" class="form-control" id="questionNoEditInput" name="number">
                            <div id="questionNoEditError" class="error"></div>
                        </div>
                    </div>
                    <div class="form-group row">
                        <label for="titleInput" class="col-form-label col-md-3">Title:</label>
                        <div class="col-md-9">
                            <input type="text" class="form-control" id="titleEditInput" name="title">
                            <div id="titleEditError" class="error"></div>
                        </div>
                    </div>
                    <div class="form-group row" hidden="">
                        <label for="" class="col-form-label col-md-3">Type:</label>
                        <div class="col-md-4">
                            <select class="custom-select" id="typeQuestion" name="typeQuestion" onchange="updateAnswerTypeEdit()">
                                <option value="checkBox">Check Box</option>
                                <option value="radioBox">Radio Box</option>
                            </select>
                        </div>
                    </div>
                    <div class="form-group row">
                        <label for="answers-container" class="col-form-label col-md-3">Answers:</label>
                        <div class="col-md-9" id="answers-container-edit"></div>
                    </div>

                    <div class="form-group row">
                        <div class="col-md-9 offset-md-3">
                            <button type="button" id="add-answer-edit" class="btn btn-primary">Add Answer</button>
                        </div>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-dismiss="modal">Cancel</button>
                <button type="submit" class="btn btn-primary" form="editQuestionForm" onclick="validateForm2(event)">Update</button>
            </div>
        </div>
    </div>
</div>
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>

<script>
    function validateForm2(event) {
        let number = $('#questionNoEditInput').val();
        let title = $('#titleEditInput').val();
        let answers = $('#answers-container-edit .answer-row');
        let hasEmptyAnswer = false;
        let hasCorrectAnswer = false;

        // Clear current error messages
        $('.error').html('');

        if (number === '') {
            $('#questionNoEditError').html('Questions No Can Not Be Empty');
        }
        if (title === '') {
            $('#titleEditError').html('Title Of Question Can Not Be Empty');
        }

        answers.each(function () {
            let answerText = $(this).find('input[type="text"]').val();
            let correctAnswer = $(this).find('input[type="checkbox"], input[type="radio"]').is(':checked');

            if (answerText === '') {
                hasEmptyAnswer = true;
                $(this).find('input[type="text"]').addClass('is-invalid');
            } else {
                $(this).find('input[type="text"]').removeClass('is-invalid');
            }

            if (correctAnswer) {
                hasCorrectAnswer = true;
            }
        });

        if (hasEmptyAnswer) {
            $('<div class="error">All answers must be filled out</div>').insertAfter('#answers-container-edit');
        }

        if (!hasCorrectAnswer) {
            $('<div class="error">At least one correct answer must be selected</div>').insertAfter('#answers-container-edit');
        }

        // Check if there are no errors before submitting the form
        let error = '';
        $('.error').each(function () {
            error += $(this).html();
        });
        if (error !== '') {
            event.preventDefault();
        } else {
            $('#editQuestionForm').submit();
        }
    }

    function editQuestionModal(button, id) {
        let inputId = document.querySelector("#idEditInput");
        inputId.value = id;
        // Find the closest question-number element
        var questionElement = $(button).closest('.media').find('.question-number');

        // Extract question data from data-* attributes
        var questionId = questionElement.data('question-id');
        var questionName = questionElement.data('question-name');
        var questionNum = questionElement.data('question-num');

        // Set the extracted data in the modal inputs
        $('#questionNoEditInput').val(questionNum);
        $('#titleEditInput').val(questionName);

        // Find all answers associated with the question
        var answerElements = $(button).closest('.media').find('.answer');

        // Clear existing answers in the modal
        $('#answers-container-edit').empty();

        // Populate the modal with the answers
        answerElements.each(function (index) {
            var answerChoice = $(this).data('answer-choice');
            var isCorrect = $(this).data('is-correct');

            // Create a new answer row
            var answerRow = $('<div>').addClass('answer-row d-flex align-items-center mb-2');

            // Create the input for the answer text
            var answerInput = $('<input>')
                .attr('type', 'text')
                .attr('name', 'answer')
                .addClass('form-control me-2')
                .val(answerChoice);

            // Create the checkbox/radio for the correct answer
            var correctAnswerInput = $('<input>')
                .attr('type', $('#typeQuestion').val() === 'checkBox' ? 'checkbox' : 'radio')
                .attr('name', 'correctAnswer' + (index + 1)) // Ensure unique names
                .addClass('form-check-input me-2')
                .prop('checked', isCorrect);

            // Create the remove button for the answer
            var removeButton = $('<button>')
                .attr('type', 'button')
                .addClass('btn btn-danger btn-sm')
                .text('x')
                .on('click', function () {
                    answerRow.remove();
                    updateAnswerNames();
                });

            // Append the elements to the answer row
            answerRow.append(removeButton, answerInput, correctAnswerInput);

            // Append the answer row to the container
            $('#answers-container-edit').append(answerRow);
        });
    }

    function updateAnswerNames() {
        $('#answers-container-edit .answer-row').each(function (index) {
            $(this).find('input[type="checkbox"], input[type="radio"]').attr('name', 'correctAnswer' + (index + 1));
        });
    }

    function updateAnswerType() {
        const typeQuestion = document.getElementById('typeQuestion').value;
        const answerInputs = document.querySelectorAll('#answers-container-edit .answer-row input[type="checkbox"], #answers-container-edit .answer-row input[type="radio"]');

        answerInputs.forEach(input => {
            if (typeQuestion === 'checkBox') {
                input.type = 'checkbox';
                input.name = 'correctAnswer';
                input.classList.add('custom-checkbox');
            } else {
                input.type = 'radio';
                input.name = 'correctAnswer';
                input.classList.remove('custom-checkbox');
            }
        });
    }

    document.addEventListener('DOMContentLoaded', function () {
        updateAnswerType();

        document.getElementById('add-answer-edit').addEventListener('click', function () {
            var container = document.getElementById('answers-container-edit');
            const answerRow = document.createElement('div');
            answerRow.className = 'answer-row d-flex align-items-center mb-2';

            const input = document.createElement('input');
            input.type = 'text';
            input.name = 'answer';
            input.className = 'form-control me-2';

            const correctAnswerInput = document.createElement('input');
            correctAnswerInput.type = document.getElementById('typeQuestion').value === 'checkBox' ? 'checkbox' : 'radio';
            correctAnswerInput.name = 'correctAnswer' + (container.children.length + 1);
            correctAnswerInput.className = 'form-check-input me-2';
            if (correctAnswerInput.type === 'checkbox') {
                correctAnswerInput.classList.add('custom-checkbox');
            }

            const deleteButton = document.createElement('button');
            deleteButton.type = 'button';
            deleteButton.className = 'btn btn-danger btn-sm';
            deleteButton.innerHTML = '&times;';
            deleteButton.addEventListener('click', function () {
                container.removeChild(answerRow);
                updateAnswerNames();
            });

            answerRow.appendChild(deleteButton);
            answerRow.appendChild(input);
            answerRow.appendChild(correctAnswerInput);
            container.appendChild(answerRow);
        });
    });
</script>
