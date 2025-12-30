🏅 Wessam (وسام)
📌 Project Description

Wessam (وسام) is a smart sports management and analytics platform connecting coaches, trainees, gyms, and organizers in one unified system.
It improves sports training and decision-making with AI-powered insights:

Coach performance analysis

Course recommendations

Feedback sentiment analysis

Sport popularity evaluation

The platform allows:

Trainees to receive personalized guidance

Coaches to monitor their performance

Sports organizations to make data-driven decisions using engagement and feedback data

👥 Team Members
Name
Leena
Abdulrahman
Mohannad
🧩 My Contribution (Leena)
Contribution
Designed and implemented AI-related features
Implemented analytics, dashboards & extra endpoints
Designed ER Diagram
Designed Figma Interfaces
Designed Use Case Diagram
Contributed to system documentation
🔗 System Diagrams
Diagram	Link
🎨 Figma UI Design	Figma Link

🧩 ER Diagram (ERD)	Mermaid Live Link

🔄 Use Case Diagram	Google Drive Link
📡 PI Endpoints (Implemented by Leena)
🏋️ Coach Management (CRUD + Analytics)
Method	Endpoint	Description	Owner
GET	/api/v1/coach/get	Get all coaches	Leena
POST	/api/v1/coach/register	Register a new coach	Leena
PUT	/api/v1/coach/update/{id}	Update coach profile	Leena
DELETE	/api/v1/coach/delete/{id}	Delete coach	Leena
PUT	/api/v1/coach/activate/{gymId}/{coachId}	Activate coach by gym	Leena
GET	/get/coach/status/{coachId}	Coach dashboard statistics	Leena
GET	/{coachId}/total-trainees	Total trainees per coach	Leena
GET	/{coachId}/total-courses	Total courses per coach	Leena
GET	/{coachId}/average-rating	Coach average rating	Leena
GET	/get/feedback/{coachId}	AI coach feedback analysis	Leena
🏢 Gym Management (CRUD)
Method	Endpoint	Description	Owner
GET	/api/v1/gym/get	Get all gyms	Leena
POST	/api/v1/gym/register	Register a new gym	Leena
PUT	/api/v1/gym/update/{gymId}	Update gym profile	Leena
PUT	/api/v1/gym/activate/{gymId}	Activate gym	Leena
DELETE	/api/v1/gym/delete/{gymId}	Delete gym	Leena
🧑‍🎓 Trainee & AI Coach
Method	Endpoint	Description	Owner
POST	/api/v1/trainee/register	Register trainee	Leena
PUT	/api/v1/trainee/update/{id}	Update trainee profile	Leena
DELETE	/api/v1/trainee/delete/{id}	Delete trainee	Leena
GET	/coach/{traineeId}	AI personalized coach advice	Leena
📚 Courses & AI Analytics
Method	Endpoint	Description	Owner
GET	/get/top/{courseId}	Get top courses	Leena
GET	/get/recommended/{traineeId}/{sportId}	AI recommended courses	Leena
GET	/get/dateRange/{sportId}/{startDate}/{endDate}	Courses by date range	Leena
GET	/get/upcoming	Upcoming courses	Leena
GET	/get/registerd/{traineeId}	Trainee registered courses	Leena
GET	/get/totalTrainee/{id}	Total trainees per course	Leena
GET	/get/feedback/{courseId}	AI course feedback analysis	Leena
🏆 Tournament Analytics
Method	Endpoint	Description	Owner
GET	/get/upcoming	Upcoming tournaments	Leena
GET	/get/dateRange/{sportId}/{startDate}/{endDate}	Tournaments by date range	Leena
GET	/get/totalTrainee/{id}	Total trainees per tournament	Leena
🏅 Sport Management & AI
Method	Endpoint	Description	Owner
GET	/api/v1/sport/get	Get all sports	Leena
POST	/api/v1/sport/add	Add new sport	Leena
PUT	/api/v1/sport/update/{id}	Update sport	Leena
DELETE	/api/v1/sport/delete/{id}	Delete sport	Leena
GET	/analyze/sport/{sportId}	AI sport popularity analysis	Leena
🏟️ Organizer Management (CRUD)
Method	Endpoint	Description	Owner
GET	/api/v1/organizer/get	Get all organizers	Leena
POST	/api/v1/organizer/add	Add new organizer	Leena
PUT	/api/v1/organizer/update/{id}	Update organizer	Leena
DELETE	/api/v1/organizer/delete/{id}	Delete organizer	Leena
🧠 Technologies Used
Technology
Java
Spring Boot
RESTful APIs
JPA / Hibernate
MySQL
AI Prompt-based Analysis
Figma (UI/UX)

إذا تحبين، أقدر أسوي لك نسخة جاهزة تمامًا لـ README.md مع جدول محتويات وروابط مباشرة لكل قسم بحيث يكون كامل واحترافي جدًا للتقديم 🚀.

هل تريديني أسويها؟
