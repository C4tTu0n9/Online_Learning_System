<%@page contentType="text/html" pageEncoding="UTF-8"%>
<div class="modal fade" id="createQuestion">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header bg-primary">
                <h5 class="modal-title">Add Question</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <form id="createQuestionForm" action="question?action=create" method="POST" onsubmit="return validateForm(event)">
                    <input type="hidden" name="midCreate" value="${midCreate}"/>
                    <input type="hidden" name="cidCreate" value="${cidCreate}"/>
                    <div class="form-group row">
                        <label for="questionNoInput" class="col-form-label col-md-3">Question No:</label>
                        <div class="col-md-9">
                            <input type="number" min="1" max="20" class="form-control" id="questionNoInput" name="questionNumber">
                            <div id="questionNoError" class="error"></div>
                        </div>
                    </div>
                    <div class="form-group row">
                        <label for="titleInput" class="col-form-label col-md-3">Title:</label>
                        <div class="col-md-9">
                            <input type="text" class="form-control" id="titleInput" name="titleQuestion">
                            <div id="titleError" class="error"></div>
                        </div>
                    </div>
                    <div class="form-group row" hidden="">
                        <label for="typeQuestion" class="col-form-label col-md-3">Type:</label>
                        <div class="col-md-4">
                            <select class="custom-select" id="typeQuestion" name="typeQuestion" onchange="updateAnswerType()">
                                <option value="checkBox">Check Box</option>
                                <option value="radioBox">Radio Box</option>
                            </select>
                        </div>
                    </div>
                    <div class="form-group row">
                        <label for="answers-container" class="col-form-label col-md-3">Answers:</label>
                        <div class="col-md-9" id="answers-container"></div>
                    </div>

                    <div class="form-group row">
                        <div class="col-md-9 offset-md-3">
                            <button type="button" id="add-answer" class="btn btn-primary">Add Answer</button>
                        </div>
                    </div>
                    <div class="form-group row">
                        <div class="col-md-9 offset-md-3">
                            <button type="submit" id="save" class="btn btn-success">Save</button>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script>
    function updateAnswerType() {
        const typeQuestion = document.getElementById('typeQuestion').value;
        const answerInputs = document.querySelectorAll('#answers-container .answer-row input[type="checkbox"], #answers-container .answer-row input[type="radio"]');

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

        document.getElementById('add-answer').addEventListener('click', function () {
            var container = document.getElementById('answers-container');
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
            });

            answerRow.appendChild(deleteButton);
            answerRow.appendChild(input);
            answerRow.appendChild(correctAnswerInput);
            container.appendChild(answerRow);
        });
    });

    function validateForm(event) {
        let isValid = true;
        let number = $('#questionNoInput').val();
        let title = $('#titleInput').val();
        let answers = $('#answers-container .answer-row');
        let hasEmptyAnswer = false;
        let hasCorrectAnswer = false;

        // Clear current error messages
        $('.error').html('');

        if (number === '') {
            $('#questionNoError').html('Question No cannot be empty');
            isValid = false;
        }
        if (title === '') {
            $('#titleError').html('Title of Question cannot be empty');
            isValid = false;
        }

        answers.each(function () {
            let answerText = $(this).find('input[type="text"]').val();
            let correctAnswer = $(this).find('input[type="checkbox"], input[type="radio"]').is(':checked');

            if (answerText === '') {
                hasEmptyAnswer = true;
                $(this).find('input[type="text"]').addClass('is-invalid');
                isValid = false;
            } else {
                $(this).find('input[type="text"]').removeClass('is-invalid');
            }

            if (correctAnswer) {
                hasCorrectAnswer = true;
            }
        });

        if (hasEmptyAnswer) {
            $('<div class="error">All answers must be filled out</div>').insertAfter('#answers-container');
        }

        if (!hasCorrectAnswer) {
            $('<div class="error">At least one correct answer must be selected</div>').insertAfter('#answers-container');
            isValid = false;
        }

        if (!isValid) {
            event.preventDefault();
        }

        return isValid;
    }
</script>
