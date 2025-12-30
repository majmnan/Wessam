🏅 Wessam (وسام)
📌 Project Description

Wessam (وسام) is a smart sports management and analytics platform designed to connect coaches, trainees, gyms, and organizers in one unified system.
The platform focuses on improving sports training and decision-making through AI-powered insights, including coach performance analysis, course recommendations, feedback sentiment analysis, and sport popularity evaluation.

Wessam enables trainees to receive personalized guidance, allows coaches to monitor their performance, and helps sports organizations make data-driven decisions using real engagement and feedback data.

👥 Team Members

Leena

Abdulrahman

Mohannad

🧩 My Contribution (Leena)

Designed and implemented AI-related features

Implemented analytics & dashboard & extra endpoints

Designed ER Diagram

Designed Figma Interfaces 

Designed Use Case Diagram

Contributed to system documentation



🔗 System Diagrams

🎨 Figma UI Design
👉(https://www.figma.com/design/JB0Gov46coABiU4SIsEtbm/Gym-landing-page-website--Community-?node-id=0-1&t=aV0c2TGbKTqVvj1E-1)
🧩 ER Diagram (ERD)
👉 (https://mermaid.live/view#pako:eNqtVttu4jAQ_ZXIUt9oBeW25I3StItQoQp0V1shRSaZEu8mdmQ7bSnl39cJBBJiCq2aJ_B45py5e4lc5gEyEfBrgucch1M6pYb6HsaWbSzXv5OPUGkQz7gf7I6eMXd9zI1YAKc4hLIkwkK8MO6VJZwFm_urDPH2z92JgLNYEApCOC5wSZ6IiyU4RIOiZ-WBcDmJJGG0LBQSy1jszj1l2xDxbKviAPWc5LRINQmC4mDcDPbcurK7w97PEz0LmPJFy8slclE0MV-EzkHU8f3InpwIuovSVn1k33aH_cdyCWSA94PKFvQbU5OP_pbMxO72h5b1WSp6BAgxCcrHc5VV4EUAH8jcl8Wzl72zbeLgGYK9qpkRLn1NqYiIcanNWm_ULZfKt_oZ-YyCQ-Nwlvf2UPEn8AvAXDjsyYHXCDgB6sKJfs44pq6fd_SUCDzYY-vThVvwnEq-cPYSkliJOClxVx4rJkXuqUDf5S7DyqODXbdm79jWbX-sqnbSHw1PdEUXeZfFXIAufpJj1WVwmMimZZwby7q-6vYGR1ikHhe9_XBYJia4JjgpXw5zIhTDdFh-GKVffev3ifH5BI2j-BOFP-zeWcPJl-psjfGC82vtS7Xk4zBKOO5nMbvA-BxT8qaEh24c7qN1CVq2de2c7G5aWSqHibtUfqnuzs4MG4I09MInkci9Jd7fz8_ZMt3ypsFeqEa22zqmQTTybBHopevhuZbtXhQb4WYLm4aPRX5DlixHHLtSzQnPmS00FzMQEYFLcKCyIxxCdRZ3UVeUqBvEqoT33gR7NiGMArYQxWWwvZPORdOQoCYQiOJmLFwqjh9z0w_ARWHCfqiSBunwSCsGbTdkEiwXyDOIo7rFGVBA3FWBLpRZU-gjoC970_jLSFZwufMjSi6jEqd6qILmnHjIlDyGCgqBq-2q_qK0oaZI-qAmBDLVTw_zf1M0pSulE2H6yFiYqXEWz31kPuFAqH9xlIyEzat7eyV9hvRYTCUya-3UBDKX6BWZjUbzol5tVGv1VqtRbbZ-KOkCmfXORbvR6bQ7zcvLVq3Wqa8q6C0FrV78aDerua9WQeARyfjd-s2fPv1X_wH525NI)

🔄 Use Case Diagram
👉 https://drive.google.com/XXXXXXXX



PI Endpoints (Implemented by Leena)
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

Java

Spring Boot

RESTful APIs

JPA / Hibernate

MySQL

AI Prompt-based Analysis

Figma (UI/UX)
