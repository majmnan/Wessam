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

## 🧩 Contributions (Leena)
| Contribution | Description |
|-------------|-------------|
| 💡 AI Features | تنفيذ الميزات المتعلقة بالذكاء الاصطناعي |
| 📊 Analytics & Dashboards | تنفيذ التحليلات، اللوحات، والنقاط الإضافية |
| 📝 ER Diagram | تصميم ER Diagram |
| 🎨 Figma Interfaces | تصميم واجهات Figma |
| 🔄 Use Case Diagram | تصميم Use Case Diagram |
| 📚 Documentation | توثيق النظام |

---

## 🔗 System Diagrams
| Diagram | Link |
|--------|------|
| 🎨 Figma UI Design | [Figma Link](https://www.figma.com/design/JB0Gov46coABiU4SIsEtbm/Gym-landing-page-website--Community-?node-id=0-1&t=aV0c2TGbKTqVvj1E-1) |
| 🧩 ER Diagram (ERD) | [Mermaid Live Link](https://mermaid.live/view#pako:eNqtVttu4jAQ_...) |
| 🔄 Use Case Diagram | [Google Drive Link](https://drive.google.com/XXXXXXXX) |

---

## 📡 PI Endpoints (Implemented by Leena)

### 🏋️ Coach Management (CRUD + Analytics)
| Method | Endpoint | Description |
|--------|---------|-------------|
| GET | `/api/v1/coach/get` | Get all coaches |
| PUT | `/api/v1/coach/update/{id}` | Update coach profile |
| DELETE | `/api/v1/coach/delete/{id}` | Delete coach |
| GET | `/get/coach/status/{coachId}` | Coach dashboard statistics |
| GET | `/{coachId}/total-trainees` | Total trainees per coach |
| GET | `/{coachId}/total-courses` | Total courses per coach |
| GET | `/{coachId}/average-rating` | Coach average rating |
| GET | `/get/feedback/{coachId}` | AI coach feedback analysis |

### 🏢 Gym Management (CRUD)
| Method | Endpoint | Description |
|--------|---------|-------------|
| GET | `/api/v1/gym/get` | Get all gyms |
| POST | `/api/v1/gym/register` | Register a new gym |
| PUT | `/api/v1/gym/update/{gymId}` | Update gym profile |
| PUT | `/api/v1/gym/activate/{gymId}` | Activate gym |
| DELETE | `/api/v1/gym/delete/{gymId}` | Delete gym |

### 🧑‍🎓 Trainee & AI Coach
| Method | Endpoint | Description |
|--------|---------|-------------|
| PUT | `/api/v1/trainee/update/{id}` | Update trainee profile |
| DELETE | `/api/v1/trainee/delete/{id}` | Delete trainee |
| GET | `/coach/{traineeId}` | AI personalized coach advice |

### 📚 Courses & AI Analytics
| Method | Endpoint | Description |
|--------|---------|-------------|
| GET | `/get/top/{courseId}` | Get top courses |
| GET | `/get/recommended/{traineeId}/{sportId}` | AI recommended courses |
| GET | `/get/dateRange/{sportId}/{startDate}/{endDate}` | Courses by date range |
| GET | `/get/upcoming` | Upcoming courses |
| GET | `/get/registerd/{traineeId}` | Trainee registered courses |
| GET | `/get/totalTrainee/{id}` | Total trainees per course |
| GET | `/get/feedback/{courseId}` | AI course feedback analysis |

### 🏆 Tournament Analytics
| Method | Endpoint | Description |
|--------|---------|-------------|
| GET | `/get/upcoming` | Upcoming tournaments |
| GET | `/get/dateRange/{sportId}/{startDate}/{endDate}` | Tournaments by date range |
| GET | `/get/totalTrainee/{id}` | Total trainees per tournament |

### 🏅 Sport Management & AI
| Method | Endpoint | Description |
|--------|---------|-------------|
| GET | `/api/v1/sport/get` | Get all sports |
| POST | `/api/v1/sport/add` | Add new sport |
| PUT | `/api/v1/sport/update/{id}` | Update sport |
| DELETE | `/api/v1/sport/delete/{id}` | Delete sport |
| GET | `/analyze/sport/{sportId}` | AI sport popularity analysis |

### 🏟️ Organizer Management (CRUD)
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
- AI Prompt-based Analysis  
- Figma (UI/UX)
