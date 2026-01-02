# 🏅 Wessam (وسام)

## 📌 Project Description
Wessam (وسام) هو منصة ذكية لإدارة الرياضة وتحليل البيانات تربط بين المدربين، المتدربين، الصالات الرياضية والمنظمين.  
تساعد المنصة في تحسين التدريب واتخاذ القرارات باستخدام **تحليلات مدعومة بالذكاء الاصطناعي**.

### الميزات الرئيسية
- 🏋️ **تحليل أداء المدربين**  
- 📚 **توصيات الكورسات للمتدربين**  
- 💬 **تحليل انطباعات المتدربين**  
- 📊 **تقييم شعبية الرياضات**  

### الفوائد
- المتدربين يحصلون على إرشادات شخصية  
- المدربين يمكنهم متابعة أداءهم  
- المنظمات الرياضية تتخذ قرارات مبنية على البيانات  

---

## 👥 Team Members
- Leena  
- Abdulrahman  
- Mohannad  

---
## 🧩 My Contributions
| Contribution | Description |
|-------------|-------------|
|  AWS | رفع ونشر المشروع على سيرفرات امازون |
|  Moyassar | اضافة ميزة الدفع ميسر |
|  ER Diagram | تصميم ER Diagram |
|  TESTING |   Postman اختبار النظام بالكامل |
|  Documentation | توثيق النظام |
|  DTO   |  بناء المودلز  |
|  AI Features | المشاركة في انشاء ميزات متعلقة بالذكاء الاصطناعي | 
---

## 🔗 System Diagrams
| Diagram | Link |
|--------|------|
| 🎨 Figma UI Design | [Figma Link](https://www.figma.com/design/JB0Gov46coABiU4SIsEtbm/Gym-landing-page-website--Community-?node-id=0-1&t=aV0c2TGbKTqVvj1E-1) |
| 🧩 ER Diagram (ERD) | [Mermaid Live Link](https://mermaid.live/view#pako:eNqtVttu4jAQ_...) |
| 🔄 Use Case Diagram |[ [Google Drive Link](https://drive.google.com/XXXXXXXX)](https://drive.google.com/file/d/1ZX58tYyVYizoSxi4GZqc3QLax3pZ9bxb/view?usp=drivesdk) |
|  API documentation  | [postman link](https://documenter.getpostman.com/view/49815088/2sBXVbJZXe)  |

---

# My Work
## 🏋️ Coach Registration & Management
path: /api/v1/coach

| Method | Endpoint | Description |
|------|--------|------------|
| POST | `/register` | Coach registration |
| PUT | `activate/{coachId}` | Activate a coach (Gym) | 

---

---


## 🏢 Branch Management
path: /api/v1/branches
| Method | Endpoint | Description |
|--------|---------|-------------|
| GET | `/get-all` | Get all branches (Admin) |
| POST | `/add` | Add new branch (Gym) |
| PUT | `/update/{branchId}` | Update branch (Gym) |
| DELETE | `/delete/{branchId}` | Delete branch (Gym) |
| GET | `/get-gym` | Get branches by gym |

---

## 🏢 Gym Management 
path: /api/v1/gym

| Method | Endpoint | Description |
|--------|---------|-------------|
| POST | `/register` | Register a new gym | 
| PUT | `/activate/{gymId}` | Activate gym |
| GET | `/subscibe/` (month\quarter\half\year) | Subsecribe and pay |

---

## 🧑‍🎓 Trainee
path: /api/v1/trainee
| Method | Endpoint | Description |
|--------|---------|-------------|
| POST | `/register` | Trainee registration |
| GET | `/nutrition` | generate a day nutrition with AI | 
| PUT | `/update/{id}` | Update trainee profile |

---

## 📚 Courses & AI Analytics
path: /api/v1/course/

| Method | Endpoint | Description |
|--------|---------|-------------|
| POST | `/add` | Add new course |
| PUT | `/update/{id}` | Update course | 
| GET | `/top` | Get course details |

---

---

### 📚 course registration
path: /api/v1/course-registration/

| Method | Endpoint | Description |
|--------|---------|-------------|
| POST | `/register/{courseId}` | Register in course and pay | 
| DELETE | `/get-by-course/{courseId}` | get a course registration | 
| GET | `/delete/registration` | delete registration | 
| GET | `/complete/{registrationId}` | mark student complete a course |

---

### Course Review
path: /api/v1/reviews
| HTTP Method | Endpoint | Auth    | Description|
| ----------- | --------------------- | ------- | ---- |
| GET | `/get/{courseId}`| Public  | Get all reviews for a specific course |
| POST | `/add` | TRAINEE | Add a review for a completed course |
| PUT | `/update/{reviewId}` | TRAINEE | Update an existing course review |
| `DELETE`    | `/delete/{reviewId}`  | TRAINEE | Delete a course review                                       |
| `GET`       | `/summary/{courseId}` | Public  | Get AI-generated summary and insights for a completed course |


---

### 🏅 Sport Management
| Method | Endpoint | Description |
|--------|---------|-------------|
| GET | `/api/v1/sport/get` | Get all sports | 
| POST | `/api/v1/sport/add` | Add new sport | 
| PUT | `/api/v1/sport/update/{id}` | Update sport |
| DELETE | `/api/v1/sport/delete/{id}` | Delete sport |
| GET | `/api/v1/sport/analyze/sport/{sportId}` | Analyze sport popularity |

---

### 🏢 Organizer Management
| Method | Endpoint | Description |
|--------|---------|-------------|
| GET | `/api/v1/organizer/get` | Get all organizers | 
| POST | `/api/v1/organizer/add` | Add new organizer |
| PUT | `/api/v1/organizer/update/{id}` | Update organizer |
| DELETE | `/api/v1/organizer/delete/{id}` | Delete organizer |

---

## 🧠 Technologies Used
- Java  
- Spring Boot  
- RESTful APIs  
- JPA / Hibernate  
- MySQL  
- AI Prompt-based Analysis (Open Ai)
- Figma (UI/UX)
- junit
- Moyassar payment
