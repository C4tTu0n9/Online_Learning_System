/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */


//module lesson

  $(document).ready(function () {
    // Lưu trạng thái mở/đóng của các modules vào localStorage
    function saveModuleState() {
        var moduleStates = {};
        $(".dev").each(function (index) {
            var isOpen = $(this).find('.module-content').is(':visible');
            moduleStates[index] = isOpen;
        });
        localStorage.setItem('moduleStates', JSON.stringify(moduleStates));
    }

    // Khôi phục trạng thái mở/đóng của các modules từ localStorage
    function restoreModuleState() {
        var moduleStates = localStorage.getItem('moduleStates');
        if (moduleStates) {
            moduleStates = JSON.parse(moduleStates);
            $(".dev").each(function (index) {
                if (moduleStates[index]) {
                    $(this).find('.module-content').show();
                    $(this).find('.btn-block-option i').removeClass('fa-chevron-down').addClass('fa-chevron-up');
                } else {
                    $(this).find('.module-content').hide();
                    $(this).find('.btn-block-option i').removeClass('fa-chevron-up').addClass('fa-chevron-down');
                }
            });
        }
    }

    // Gọi hàm khôi phục trạng thái khi trang được tải
    restoreModuleState();

    // Sự kiện click để mở/đóng module và lưu trạng thái
    $(".btn-block-option").click(function () {
        var parentBlock = $(this).closest('.dev');
        var moduleContent = parentBlock.find('.module-content');
        moduleContent.toggle();
        var icon = $(this).find('i');
        if (icon.hasClass('fa-chevron-up')) {
            icon.removeClass('fa-chevron-up').addClass('fa-chevron-down');
        } else {
            icon.removeClass('fa-chevron-down').addClass('fa-chevron-up');
        }
        saveModuleState(); // Lưu trạng thái sau khi thay đổi
    });

    // Lưu trạng thái khi người dùng chọn một bài học
    $(".module-content a").click(function () {
        saveModuleState();
    });
});




$(document).ready(function () {
    // Hàm để lấy giá trị đã lưu trữ trong localStorage
    function getActiveLesson() {
        return localStorage.getItem("activeLesson");
    }

    function getActiveQuiz() {
        return localStorage.getItem("activeQuiz");
    }

    // Hàm để set giá trị vào localStorage
    function setActiveLesson(lessonId) {
        localStorage.setItem("activeLesson", lessonId);
    }

    function setActiveQuiz(quizId) {
        localStorage.setItem("activeQuiz", quizId);
    }

    // Kiểm tra nếu có bài học hoặc quiz được lưu trong localStorage thì áp dụng lớp active
    var activeLessonId = getActiveLesson();
    var activeQuizId = getActiveQuiz();

    if (activeLessonId) {
        $(".module-content a.module-lesson").each(function () {
            if ($(this).attr("data-lessonid") === activeLessonId) {
                $(this).addClass("active-lesson");
            }
        });
    }

    if (activeQuizId) {
        $(".module-content a.module-quiz").each(function () {
            if ($(this).attr("data-quizid") === activeQuizId) {
                $(this).addClass("active-quiz");
            }
        });
    }

    // Xử lý sự kiện click trên các liên kết bài học
    $(".module-lesson").click(function (e) {
        e.preventDefault(); // Ngăn chặn hành động mặc định của liên kết (chuyển hướng)

        var lessonId = $(this).attr("data-lessonid"); // Lấy id của bài học

        // Xóa lớp active-lesson khỏi tất cả các liên kết bài học
        $(".module-lesson").removeClass("active-lesson");

        // Thêm lớp active-lesson vào liên kết bài học được chọn
        $(this).addClass("active-lesson");

        // Lưu trạng thái của bài học được chọn vào localStorage
        setActiveLesson(lessonId);

        // Chuyển hướng đến URL của bài học được chọn
        window.location.href = $(this).attr("href");
    });

    // Xử lý sự kiện click trên các liên kết quiz
    $(".module-quiz").click(function (e) {
        e.preventDefault(); // Ngăn chặn hành động mặc định của liên kết (chuyển hướng)

        var quizId = $(this).attr("data-quizid"); // Lấy id của quiz

        // Xóa lớp active-quiz khỏi tất cả các liên kết quiz
        $(".module-quiz").removeClass("active-quiz");

        // Thêm lớp active-quiz vào liên kết quiz được chọn
        $(this).addClass("active-quiz");

        // Lưu trạng thái của quiz được chọn vào localStorage
        setActiveQuiz(quizId);

        // Chuyển hướng đến URL của quiz được chọn
        window.location.href = $(this).attr("href");
    });
});

// lưu trạng thái hoàn thành từng lesoson

document.getElementById('lessonVideo').addEventListener('ended', function() {
    var lessonId = $(this).closest('.module-lesson').data('lessonid'); // Lấy id của bài học
    markAsCompleted("completedLessons", lessonId); // Lưu vào localStorage
    alert("Bạn đã hoàn thành bài học!");
});
$(document).ready(function () {
    // Xử lý sự kiện click để đánh dấu bài học là hoàn thành
    $(".complete-lesson-btn").click(function () {
        var lessonId = $(this).data("lessonid"); // Lấy id của bài học
        markAsCompleted("completedLessons", lessonId); // Lưu vào localStorage
        alert("Bạn đã hoàn thành bài học!");
        $("a[data-lessonid='" + lessonId + "']").addClass("lesson-completed"); // Cập nhật giao diện
    });
});
