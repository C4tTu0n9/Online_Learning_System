use master
go
drop database [Project Online Learning]
go

create database [Project Online Learning]
go
use [Project Online Learning]
go

CREATE table Role(
	RoleId int primary key identity(1,1),
	RoleName varchar(20)
);

insert into Role
values ('ADMIN'),
		('Manager'),('Mentor'),('Mentee');

create table Account(
	AccountId int primary key identity(1,1),
	Email varchar(255) not null, 
	Password varchar(255) not null,
	Status bit not null,
	FullName varchar(255),
	Gender bit,
	Avatar text,
	Money int,
	RoleId int foreign key references Role(RoleId) not null
);

insert into Account
values
('tuong0505ht@gmail.com','10101010',1,'Pham Cat Tuong',1,null,null,1),
('tuongpcfx21626@funix.edu.vn','123123123',1,'Manager one',1,null,null,2),
('mentor001@gmail.com','123123123',1,'Mentor one',1,null,null,3),
('mentee001@gmail.com','123123123',1,'Mentee one',0,null,null,4),
('howkteam@gmail.com','12345678',1,'HowKTeam',1,null,null,3),
('mentor002@gmail.com','123123123',1,'Mentor two',0,null,null,3)


create table ManagedBy(
	ManagerId int not null,
	foreign key (ManagerId) references Account(AccountId),
	MentorId int not null,
	foreign key (MentorId) references Account(AccountId),
	primary key(ManagerId,MentorId)
);

insert into Profile
values
(1,'Pham Cat Tuong',1,null,99999,null),
(2,'Manager001',2,null,99999,null),
(3,'Mentor001',0,null,100,2),
(4,'Mentee001',0,null,100,null),
(5,'HowKTeam', 1,null,9999,2),
(6,'Mentor002',0,null,100,2),
(7,'Mentor003',0,null,100,2),
(8,'Mentor004',0,null,100,2);


create table CourseCategory(
	CourseCategoryId varchar(10) primary key,
	CategoryName nvarchar(255)
);
insert into CourseCategory
values
('IT','Information Technology'),
('BSN','Business'),
('CS','Computer Science'),
('MC','Multimedia Communications');

create table Course(
	CourseId int primary key identity(1,1),
	CourseName nvarchar(255),
	Description nvarchar(2000),
	Image nvarchar(500),
	Price float,
	Discount float,
	CourseCategoryId varchar(10) foreign key references CourseCategory(CourseCategoryId),
	CreatedBy int references Account(AccountId),
	DateCreated date,
	StudyTime varchar(50),
	Status bit,
);




insert into Course
values (N'Lập trình hướng đối tượng trong C#', 
N'
Serial dành cho những bạn chưa có bất kỳ kiến thức về lập trình hướng đối tượng. Hoặc cần củng cố lại lập trình hướng đối tượng trong C#.

Nội dung Serial này được phân tách chi tiết nhất có thể, nhằm giúp các bạn dễ hiểu và thực hành được ngay.

Thời lượng mỗi video trung bình từ 5 – 30p

Qua serial này, LEPETE hi vọng các bạn sẽ:
 
 -Nắm vững kiến thức nền tảng về OOP C# để học những khóa chuyên sâu hơn.',
 'https://lotusacademy.edu.vn/api/media/download/641/C-OOP-Interview-Questions.jpg',
 500000, 20, 'IT',2 ,'2024-06-12', '1.30 Hrs', 1
 ),
 --cach--
 (N'Khóa học lập trình C# căn bản', 
N'
Bạn mới bắt đầu học lập trình? Bạn đang muốn học thêm ngôn ngữ lập trình mới? C# là lựa chọn hoàn hảo để đáp ứng các nhu cầu trên.

Ngôn ngữ C# là một ngôn ngữ mới, cấu trúc rõ ràng, dễ hiểu và dễ học. C# thừa hưởng những ưu việt từ ngôn ngữ Java, C, C++ cũng như khắc phục được những hạn chế của các ngôn ngữ này. C# là ngôn ngữ lập trình hướng đối tượng được phát triển bởi Microsoft, được xây dựng dựa trên C++ và Java.

Khoá học lần này sẽ mang đến toàn bộ những kiến thức cơ bản về C#. Chào mừng các bạn đã đến với khoá học LẬP TRÌNH C# CƠ BẢN của LEPETE.

Khoá học tập trung vào:
Làm quen với ngôn ngữ lập trình C# và công cụ lập trình Visual Studio.
Các khái niệm nền tảng trong C#.
Một số từ khoá, cú pháp và cấu trúc cơ bản trong C#.
Nâng cao tư duy bằng cách sử dụng C# để giải một số thuật toán cơ bản.
Và nhiều kỹ thuật hay ho khác. LEPETE
hy vọng sau khóa học, bạn sẽ nắm được các kiến thức:

Cái nhìn tổng quan về ngôn ngữ lập trình C#.
Nắm vững kiến thức nền tảng về C# để học những khóa chuyên sâu hơn 
Nắm vững các coding convention, naming convention
Thành thạo các thao tác lập trình trên visual studio
Biết cách tìm ra lỗi và gỡ lỗi trong lập trình
Và còn nhiều thứ khác chờ các bạn khám phá
Là kiến thức nền tảng để tiếp cận các công nghệ hay ho của C# như Winform, WPF, ASP.NET, WCF, Xamarin,…,'
,'https://tedu.com.vn//uploaded/images/courses/082021/c-sharp-can-ban.png',
 500000, 20, 'IT',2 ,'2024-06-12', '1.30 Hrs', 1
 ),
 (N'Lập trình Http Request với C#', 
N'
Trong khoá học này các bạn sẽ được hướng dẫn về:

Cách lấy dữ liệu từ một website.
Get & post dữ liệu (ví dụ: chấp nhận bạn bè facebook; lấy dữ liệu từ website nghe nhạc)
Kỹ thuật vượt normal captcha, re-captcha.
Upload file lên website hoặc hệ thống bất kỳ
Tải hình ảnh, file, video…
Verify email, fake IP
Và rất nhiều thủ thuật cá nhân cùng kinh nghiệm thực tế từ tác giả.
Để có đủ khả năng học hiểu các nội dung được đề cập đến trong khóa học. Bạn nên có tối thiểu kiến thức về các phần:
LẬP TRÌNH C# CƠ BẢN
LẬP TRÌNH KEYLOGGER VỚI C#
LẬP TRÌNH WINFORM CƠ BẢN
Ngoài ra, cũng nên trau dồi thêm kiến thức khác qua các project thực tế như:
LẬP TRÌNH TỪ ĐIỂN NÓI VỚI C# WINFORM
LẬP TRÌNH GAME CARO VỚI C# WINFORM
LẬP TRÌNH ỨNG DỤNG LẬP LỊCH BẰNG C# WINFORM
Thời lượng mỗi video từ 3 – 30 phút nhằm chia nhỏ quá trình thực hiện, giúp bạn dễ tiếp thu và ứng dụng source code hỗ trợ từ thư viện LEPETE',
 'https://i.ytimg.com/vi/44jiI6a2fi0/mqdefault.jpg',
 800000, 30, 'IT',2 ,'2024-06-12', '3.65 Hrs', 1
 ),

 (N'Khóa học lập trình C# nâng cao', 
N'
Bạn đã học hết các khoá học về C# CƠ BẢN, LẬP TRÌNH WINFORM thậm chí là LẬP TRÌNH WPF nhưng vẫn bối rối trước những hướng dẫn về CÁC PROJECT THỰC TẾ của LEPETE?

Hoặc bạn nghe nói mãi về .NET nhưng vẫn thắc mắc nó có gì hay ho bên trong đó?

Hay đơn giản bạn viết muốn 1 đoạn code cực kỳ nguy hiểm khiến người khác khi nhìn vào phải thốt lên: “Ồ!!!......... Trẻ trâu…”

Khoá học lần này sẽ giúp bạn giải đáp những thắc mắc trên. Chào mừng các bạn đã đến với khoá học Lập trình C# nâng cao của Kteam.

Khoá học tập trung vào:

Giới thiệu một số cấu trúc dữ liệu phổ biến được hỗ trợ sẵn trong .NET Framework.
Các khái niệm mới trong C#.
Một số từ khoá và cú pháp chưa được giới thiệu trong khoá học LẬP TRÌNH C# CƠ BẢN.
Và nhiều kỹ thuật hay ho khác.
Sau khoá học bạn sẽ có được:

Cái nhìn hoàn thiện hơn về C# và .NET Framework.
Có đủ kiến thức để dễ tiếp cận các khoá học project thực tế của Kteam.
Là nền tảng để xây dựng các ứng dụng và làm project thực tế.',
 'https://blog.luyencode.net/wp-content/uploads/2021/08/c.png ',
 1000000, 10, 'IT',2 ,'2024-06-12', '5.7 Hrs', 1
 ),
 (N'Lập trình hướng đối tượng trong C#', 
N'
Trong các khóa học SỬ DỤNG SQL SERVER và PHÂN TÍCH THIẾT KẾ PHẦN MỀM, Kteam đã đề cập đến các kiến thức cần thiết để bạn có thể bắt đầu xây dựng một cơ sở dữ liệu với SQL server và truy vấn qua CSDL đó.

Ở combo Thực chiến SQL, chúng ta sẽ cùng nhau phân tích các CSDL đó từ đặc tả và ứng dụng các kiến thức đã học ở hai khóa trên vào việc giải quyết các yêu cầu thực tế của một CSDL. Trong bộ khóa học này các bạn sẽ đi qua các kiến thức từ cơ bản đến nâng cao qua các dự án khác nhau.
Ở phần này, chúng ta sẽ cùng nhau tìm hiểu Dự án Quản lý sinh viên cực kỳ cơ bản. Bạn sẽ là đối tượng phù hợp của khóa này nếu: 
Bạn mất căn bản và muốn học lại SQL từ đầu.
Bạn đã học qua lý thuyết mà chưa ứng dụng được bao nhiêu.
Bạn muốn củng cố lại kiến thức đã học từ lâu.
',
 'https://lh3.googleusercontent.com/d/1xxouEo75gTaX_nkVHpqdJrvZC50aKMTv',
 700000, 20, 'IT',2 ,'2024-06-12', '1.30 Hrs', 1
 ),(
    'Crash Course Business - Soft Skills',
	'Welcome to a new Crash Course series! Join Evelyn from the Internets as she hosts this 17 episode series on Soft Skills, which is going to be all about learning to be in a working environment, applying for jobs, interviewing, and so much more. In this episode, we start by talking about why "Trust" is so important in Business. ',
    'https://i.ytimg.com/vi/8UnfCkFVQ9E/maxresdefault.jpg',
    600000,
    10,
    'BSN',
    2,
    '2024/06/19',
    '6.1 Hrs',
    1
),(
    'Visual Communication Design for Digital Media',
	'The course will impart knowledge on the different aspects of visual communication design, emphasizing on virtual media platform. In contemporary visual design pedagogy, virtual media technology is an emerging paradigm. The course will emphasize on understanding of visual cognition, which is the key factor to achieve user-friendly design. Usage of contemporary technology like, eye tracking will also be introduced as user testing tool. The course will enable the students to learn visual design in virtual media through a methodological approach.',
    'https://i.ytimg.com/vi/oa67x6faDJg/maxresdefault.jpg',
    500000,
    15,
    'MC',
    2,
    '2024/06/19',
    '5.1 Hrs',
    1
), (
    'Crash Course Computer Science',' Welcome to Crash Course Computer Science! So today, we’re going to take a look at computing’s origins, because even though our digital computers are relatively new, the need for computation is not. Since the start of civilization itself, humans have had an increasing need for special devices to help manage laborious tasks, and as the scale of society continued to grow, these computational devices began to play a crucial role in amplifying our mental abilities. From the abacus and astrolabe to the difference engine and tabulating machine, we’ve come a long way to satisfying this increasing need, and in the process completely transformed commerce, government, and daily life. ',
    'https://www.chrisharrison.net/projects/crashcourse/CrashCourseCSLogoSplash.jpg',
    650000,
    10,
    'CS',
    2,
    '2024/06/19',
    '8.3 Hrs',
    1
)

 

		   
		 


 


create table CourseRating(
	RatingId int primary key identity(1,1),
	Star int,
	Comment text,
	DateCreated date,
	CourseId int references Course(CourseId),
	AccountId int references Account(AccountId)
);
insert into CourseRating
  values
  (4,'Cung oke','2024-06-01',1,2),
  (5,'Huu ich!','2024-05-29',1,3),
  (3,'Tam duoc thoi','2024-06-01',1,4),
  (5,'Very good','2024-06-10',1,5),
  (1,'Khoa hoc chan','2024-05-05',2,4),
  (5,'Khoa hoc hay nhi!','2024-06-07',1,1);

create table Teaching(
	MentorId int references Account(AccountId),
	CourseId int references Course(CourseId),
	primary key(MentorId,CourseId)
);


insert into Teaching 
values (5, 1),  (5,5) , (5,8),
(3, 3), (3, 4),(3,2),(3, 6), (3, 7)


create table WishList(
	AccountId int references Account(AccountId),
	CourseId int references Course(CourseId),
	primary key(AccountId,CourseId)
);


insert into WishList
values
(1,1),
(1,3),
(1,5);

create table Enrollment(
	EnrollmentId int primary key identity(1,1),
	AccountId int references Account(AccountId),
	CourseId int references Course(CourseId),
	EnrollmentDate date,
	Progress int --per 100%
);
 
 
 insert into Enrollment
  values
  (1,1,'2024-06-07',0),
  (1,2,'2024-06-09',50),
  (1,3,'2024-06-13',20),
  (1,4,'2024-05-20',100),
  (4,1,'2024-07-08',80),
  (4,2,'2024-05-07',50),
  (4,3,'2024-07-07',80),
  (4,4,'2024-04-07',100);




create table PaymentMethod(
	PaymentMethodId varchar(10) primary key,
	PaymentMethodName varchar(50)
);

insert into PaymentMethod
values
('VNPAY','VN Pay'),
('CRD','Credit'),
('PP','PayPal'),
('MM','Momo');

create table Payment(
	PaymentId int primary key identity(1,1),
	AccountId int references Account(AccountId),
	CourseId int references Course(CourseId),
	PaymentDate date,
	PaymentMethodId varchar(10) references PaymentMethod(PaymentMethodId),
	Money float
);
INSERT INTO [dbo].[Payment]
           ([AccountId]
           ,[CourseId]
           ,[PaymentDate]
           ,[PaymentMethodId]
           ,[Money])
     VALUES
           (1
           ,1
           ,'2024/06/07'
           ,'VNPAY'
           ,500000)
INSERT INTO [dbo].[Payment]
           ([AccountId]
           ,[CourseId]
           ,[PaymentDate]
           ,[PaymentMethodId]
           ,[Money])
     VALUES
           (1
           ,2
           ,'2024/06/09'
           ,'VNPAY'
           ,500000)
INSERT INTO [dbo].[Payment]
           ([AccountId]
           ,[CourseId]
           ,[PaymentDate]
           ,[PaymentMethodId]
           ,[Money])
     VALUES
           (1
           ,3
           ,'2024/06/09'
           ,'VNPAY'
           ,800000)
INSERT INTO [dbo].[Payment]
           ([AccountId]
           ,[CourseId]
           ,[PaymentDate]
           ,[PaymentMethodId]
           ,[Money])
     VALUES
           (1
           ,4
           ,'2024/05/20'
           ,'VNPAY'
           ,1000000)
insert into [Payment]
VALUES(
4,1,'2024-07-08','VNPAY',500000
)
insert into [Payment]
VALUES(
4,3,'2024-07-07','VNPAY',800000
)
insert into [Payment]
VALUES(
4,4,'2024-04-07','VNPAY',1000000
)
insert into [Payment]
VALUES(
4,2,'2024-05-07','VNPAY',500000
)

--Tuong insert ModuleNumber
create table Module(
	ModuleId int primary key identity(1,1),
	ModuleName nvarchar(255),
	CourseId int foreign key references Course(CourseId),
	ModuleNumber int 
);

insert into Module 
values(N'Kiến thức cơ bản về C#',2,1),
(N'Loop in C#', 2,2),
(N'Ref and out in C#',2,3),
(N'Struct and enum in C#', 2,4)

create table Lesson(
	LessonId int primary key identity(1,1),
	ModuleId int foreign key references Module(ModuleId),
	LessonName nvarchar(255),
	LessonContent nvarchar(1000),
	LessonVideo text,
	Duration int
);




insert into Lesson
values(1, N'C# la gi?',N'Trong bài học này, chúng ta sẽ cùng tìm hiểu các vấn đề:
Sơ lược về ngôn ngữ C#.
Những đặc trưng của ngôn ngữ C#.
Tại sao lại lựa chọn ngôn ngữ C#' ,'https://www.youtube.com/embed/9kohr6pMwag?si=U9sJEvQTUKLxvHHx', 318),


(1, N'Cấu trúc lệnh cơ bản trong C# Console Application', N'Trong bài học này, chúng ta sẽ cùng tìm hiểu các vấn đề:

Cấu trúc cơ bản của một chương trình trong C#.
Giải thích ý nghĩa một số từ khóa được sử dụng trong chương trình đầu tiên.
Cách viết comment trong C#.
Ví dụ chương trình đầu tiên bằng C#.','https://www.youtube.com/embed/FhAIc0tlyaQ?si=c5ClSGle5mNg1fNw',1386),
(1,N'Nhập xuất cơ bản trong C# Console Application
',N'Trong bài học này, chúng ta sẽ cùng tìm hiểu các vấn đề:

Cấu trúc cơ bản của các lệnh nhập xuất và ý nghĩa của chúng trong C#.
Ví dụ demo chương trình nhập xuất bằng C#.','https://www.youtube.com/embed/BAscPWPtCD8?si=-hrRHd1obZIIp2vy',1828),

(1,N'Biến trong C#',N'Để đọc hiểu bài này tốt nhất các bạn nên có kiến thức cơ bản về các phần:

Cấu trúc lệnh của C# viết trên nền Console Application.
Cấu trúc nhập xuất của C# trên nền Console Application.
 Trong bài học này, chúng ta sẽ cùng tìm hiểu các vấn đề:

Biến là gì? Tại sao phải sử dụng biến?
Khai báo và sử dụng biến.
Quy tắc đặt tên biến.','https://www.youtube.com/embed/IEz7uMSHitM?si=D_FhSF4eWfGE4Qok',1524),
(1,N'Kiểu dữ liệu trong C#',N'Để đọc hiểu bài này tốt nhất các bạn nên có kiến thức cơ bản về các phần:

Cấu trúc lệnh của C# viết trên nền Console Application.
Cấu trúc nhập xuất của C# trên nền Console Application.
Biến trong C#.
 Trong bài học này, chúng ta sẽ cùng tìm hiểu các vấn đề:

Kiểu dữ liệu là gì? Tại sao phải có kiểu dữ liệu?
Phân loại kiểu dữ liệu và ý nghĩa của từng kiểu dữ liệu.
Ví dụ chương trình sử dụng một số kiểu dữ liệu.
','https://www.youtube.com/embed/yrH7Qe8FXqE?si=lC8OMRwzNwPpJvU3',1234),
(1,N'Toán tử trong C#',N'Để đọc hiểu bài này tốt nhất các bạn nên có kiến thức cơ bản về các phần:

CẤU TRÚC LỆNH CỦA C# VIẾT TRÊN NỀN CONSOLE APPLICATION.
CẤU TRÚC NHẬP XUẤT CỦA C# TRÊN NỀN CONSOLE APPLICATION.
BIẾN & KIỂU DỮ LIỆU trong C#.
Trong bài học này, chúng ta sẽ cùng tìm hiểu các vấn đề:

Toán tử là gì? Có mấy loại toán tử?
Cú pháp và ý nghĩa của từng toán tử.
Độ ưu tiên của toán tử.
Ví dụ chương trình sử dụng một số toán tử.','https://www.youtube.com/embed/niz7Gg8uB-k?si=bnm5v5MPvW2tE1ls',1235);


create table Quiz(
	QuizId int primary key identity(1,1),
	ModuleId int foreign key references Module(ModuleId),
	QuizName varchar(255),
	QuizTime time,
	PassScore int
);

create table Question(
	QuestionId int primary key identity(1,1),
	QuestionNum int,
	QuizId int foreign key references Quiz(QuizId),
	QuestionName nvarchar(255),
	Type bit -- 0: checkBox, 1: Radio
);

create table QuestionChoices(
	QuestionId int foreign key references Question(QuestionId),
	Choices text,
	IsCorrect bit
);

create table AnswerQuestion(
	AnswerQuestion int identity(1,1) primary key,
	AccountId int references Account(AccountId),
	QuestionId int references Question(QuestionId),
	Answer text,
	IsCorrect bit
);

--Hatro edit
create table ScoreQuiz(
	ScoreQuizId int identity(1,1) primary key,
	AccountId int references Account(AccountId),
	QuizId int references Quiz(QuizId),
	Score float,
	IsPass bit
);

create table DiscussionLesson(
	DisscussionId int primary key identity(1,1),
	ParentCommentID int, -- null nếu là comment chính, có id là comment của reply
	CreatedAt DATETIME DEFAULT GETDATE(), -- Sử dụng kiểu DATETIME và thiết lập giá trị mặc định là thời gian hiện tại
	Comment nvarchar(2000),
	AccountId int foreign key references Account(AccountId),
	LessonId int foreign key references Lesson(LessonId),
	FOREIGN KEY (ParentCommentID) REFERENCES DiscussionLesson(DisscussionId)
);

create table Message(
	MessageId int primary key identity(1,1),
	SenderId int foreign key references Account(AccountId),
	ReceiverId int foreign key references Account(AccountId),
	MessageText text,
	MessageTime datetime
);








create table Certificate(
	CertificateId int primary key identity(1,1),
	CourseId int foreign key references Course(CourseId),
	AccountId int foreign key references Account(AccountId),
	Issuer int foreign key references Account(AccountId),
	IssueDate date
);



-- Thêm cột AttemptNumber vào bảng AnswerQuestion
ALTER TABLE AnswerQuestion
ADD AttemptNumber INT;