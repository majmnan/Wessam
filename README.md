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
## 🧩 Contributions (Abdulrahman)
| Contribution | Description |
|-------------|-------------|
|  |  |
|  |  |
|  |  |

---

## 🧩 Contributions (Mohannad)
| Contribution | Description |
|-------------|-------------|
|  |  |
|  |  |
|  |  |

---

## 🔗 System Diagrams
| Diagram | Link |
|--------|------|
| 🎨 Figma UI Design | [Figma Link](https://www.figma.com/design/JB0Gov46coABiU4SIsEtbm/Gym-landing-page-website--Community-?node-id=0-1&t=aV0c2TGbKTqVvj1E-1) |
| 🧩 ER Diagram (ERD) | [Mermaid Live Link](https://mermaid.live/view#pako:eNqtVttu4jAQ_...) |
| 🔄 Use Case Diagram | [Google Drive Link](https://drive.google.com/XXXXXXXX) |

---
## 📡  Endpoints 
## 🔐 Authentication & Authorization
| Method | Endpoint | Description | Name |
|------|--------|------------|------|
| POST | `/api/v1/auth/register` | Register new user | - |
| POST | `/api/v1/auth/login` | User login | - |
| POST | `/api/v1/auth/logout` | User logout | - |
| GET | `/api/v1/auth/me` | Get current authenticated user | - |
| PUT | `/api/v1/auth/change-password` | Change password | - |

---

## 👤 User Management (Admin)
| Method | Endpoint | Description | Name |
|------|--------|------------|------|
| GET | `/api/v1/users` | Get all users | - |
| GET | `/api/v1/users/{id}` | Get user by id | - |
| PUT | `/api/v1/users/role/{id}` | Update user role | - |
| PUT | `/api/v1/users/block/{id}` | Block or unblock user | - |

---

## 🏋️ Coach Registration & Management
| Method | Endpoint | Description | Name |
|------|--------|------------|------|
| POST | `/api/v1/coach/register` | Coach registration | - |
| GET | `/api/v1/coach/pending` | Get pending coaches (Admin) | - |
| PUT | `/api/v1/coach/approve/{id}` | Approve coach (Admin) | - |
| GET | `/api/v1/coach/{id}` | Get coach profile | Leena |
| GET | `/api/v1/coach/by-sport/{sportId}` | Get coaches by sport | - |
| GET | `/api/v1/coach/get` | Get all coaches | Leena |
| PUT | `/api/v1/coach/update/{id}` | Update coach profile | Leena |
| DELETE | `/api/v1/coach/delete/{id}` | Delete coach | Leena |
| GET | `/get/coach/status/{coachId}` | Coach dashboard statistics | - |
| GET | `/{coachId}/total-trainees` | Total trainees per coach | Leena |
| GET | `/{coachId}/total-courses` | Total courses per coach | Leena |
| GET | `/{coachId}/average-rating` | Coach average rating | Leena |
| GET | `/get/feedback/{coachId}` | AI coach feedback analysis | Leena |
| GET | `/dashboard/{coachId}` | Coach dashboard overview | Leena |

---

## 🏢 Branch Management
| Method | Endpoint | Description | Name |
|--------|---------|-------------|------|
| GET | `/api/v1/branches/get-all` | Get all branches (Admin) | - |
| POST | `/api/v1/branches/add` | Add new branch (Gym) | - |
| PUT | `/api/v1/branches/update/{branchId}` | Update branch (Gym) | - |
| DELETE | `/api/v1/branches/delete/{branchId}` | Delete branch (Gym) | - |
| GET | `/api/v1/branches/get-gym` | Get branches by gym | - |

---

## 🏢 Gym Management (CRUD)
| Method | Endpoint | Description | Name |
|--------|---------|-------------|------|
| GET | `/api/v1/gym/get` | Get all gyms | Leena |
| POST | `/api/v1/gym/register` | Register a new gym | - |
| PUT | `/api/v1/gym/update/{gymId}` | Update gym profile | Leena |
| PUT | `/api/v1/gym/activate/{gymId}` | Activate gym | - |
| DELETE | `/api/v1/gym/delete/{gymId}` | Delete gym | Leena |
| GET | `/get-inactive` | Get inactive gyms (Admin) | - |
| GET | `/get-active` | Get active gyms | - |
| PUT | `/deactivate/{gymId}` | Deactivate gym (Admin) | - |

---

## 🧑‍🎓 Trainee & AI Coach
| Method | Endpoint | Description | Name |
|--------|---------|-------------|------|
| POST | `/api/v1/trainee/register` | Trainee registration | - |
| GET | `/api/v1/trainee/profile/{id}` | Get trainee profile | - |
| PUT | `/api/v1/trainee/update/{id}` | Update trainee profile | - |
| DELETE | `/api/v1/trainee/delete/{id}` | Delete trainee | - |
| GET | `/coach/{traineeId}` | AI personalized coach advice | Leena |
| GET | `/api/v1/trainee/my-courses` | Get trainee courses | - |
| GET | `/get/registerd/{traineeId}` | Trainee registered courses | Leena |

---

## 📚 Courses & AI Analytics
| Method | Endpoint | Description | Name |
|--------|---------|-------------|------|
| POST | `/api/v1/course/add` | Add new course | - |
| PUT | `/api/v1/course/update/{id}` | Update course | - |
| DELETE | `/api/v1/course/delete/{id}` | Delete course | - |
| GET | `/api/v1/course/{id}` | Get course details | - |
| GET | `/api/v1/course/by-coach/{coachId}` | Get courses by coach | - |
| GET | `/api/v1/course/by-sport/{sportId}` | Get courses by sport | - |
| GET | `/get/top/{courseId}` | Get top courses | Leena |
| GET | `/get/recommended/{traineeId}/{sportId}` | AI recommended courses | Leena |
| GET | `/get/dateRange/{sportId}/{startDate}/{endDate}` | Courses by date range | Leena |
| GET | `/get/upcoming` | Upcoming courses | Leena |
| GET | `/get/totalTrainee/{id}` | Total trainees per course | Leena |
| GET | `/get/feedback/{courseId}` | AI course feedback analysis | Leena |
| GET | `/next-level-courses` | Get next level courses for trainee | - |
| PUT | `/complete/{registrationId}` | Mark course as completed | - |
| PUT | `/drop/{registrationId}` | Drop course (Coach) | - |
| GET | `/completed` | Get completed registrations | - |
| GET | `/dropped` | Get dropped registrations (Admin) | - |

---

## 💳 Payments & Registration
| Method | Endpoint | Description | Name |
|--------|---------|-------------|------|
| POST | `/registration/enroll/{courseId}` | Enroll in course | - |
| DELETE | `/registration/cancel/{id}` | Cancel registration | - |
| PUT | `/pay-pending/{registrationId}` | Pay pending registration | - |
| GET | `/complete-payment/{registrationId}` | Complete payment callback | - |
| POST | `/pay` | Test payment | - |
| GET | `/callback/{n}/{nn}` | Payment gateway callback | - |

---

## 🎓 Certificates & Reviews
| Method | Endpoint | Description | Name |
|--------|---------|-------------|------|
| POST | `/certificates/{courseId}` | Generate course certificate | - |
| POST | `/review/add` | Add review | - |
| PUT | `/review/update/{id}` | Update review | - |
| DELETE | `/review/delete/{id}` | Delete review | - |
| GET | `/review/course/{courseId}` | Get course reviews | - |
| GET | `/review/coach/{coachId}` | Get coach reviews | - |

---

## 🥗 AI Health & Training Content
| Method | Endpoint | Description | Name |
|--------|---------|-------------|------|
| GET | `/nutrition-tip/{sportId}` | Get AI nutrition tip | - |
| GET | `/home-workout/{sportId}` | Get AI home workout | - |
| GET | `/analyze/sport/{sportId}` | AI sport analysis | Leena |

---

## 🏆 Tournament & Social AI
| Method | Endpoint | Description | Name |
|--------|---------|-------------|------|
| GET | `/get/upcoming` | Upcoming tournaments | Leena |
| GET | `/get/dateRange/{sportId}/{startDate}/{endDate}` | Tournaments by date range | Leena |
| GET | `/get/totalTrainee/{id}` | Total trainees per tournament | Leena |
| GET | `/generate-post/{tournamentId}` | Generate AI social media post | - |

---

## 🎥 Online Meetings (Zoom)
| Method | Endpoint | Description | Name |
|--------|---------|-------------|------|
| POST | `/create` | Create online meeting | - |

---


---

### 📚 Courses & Registrations
| Method | Endpoint | Description | Name |
|--------|---------|-------------|------|
| GET | `/api/v1/course-registration/get-by-course/{courseId}` | Get registrations of a course | - |
| POST | `/api/v1/course-registration/register/{courseId}` | Register in course | - |
| DELETE | `/api/v1/course-registration/delete/{registrationId}` | Delete registration | - |
| GET | `/api/v1/course-registration/get/registerd/{traineeId}` | Get trainee registered courses | - |
| GET | `/api/v1/course-registration/get/totalTrainee/{id}` | Get total trainees of a course | - |

---

### 🏆 Tournament Registration
| Method | Endpoint | Description | Name |
|--------|---------|-------------|------|
| GET | `/api/v1/registeredTournament/get` | Get all registered tournaments | - |
| POST | `/api/v1/registeredTournament/add/{tournamentId}` | Add registered tournament | - |
| PUT | `/api/v1/registeredTournament/update/{oldTournamentId}/{newTournamentId}` | Update tournament registration | - |
| DELETE | `/api/v1/registeredTournament/delete/{id}` | Delete tournament registration | - |
| GET | `/api/v1/registeredTournament/get/totalTrainee/{id}` | Get total trainees per tournament | - |

---

### 🏅 Sport Management
| Method | Endpoint | Description | Name |
|--------|---------|-------------|------|
| GET | `/api/v1/sport/get` | Get all sports | Leena |
| POST | `/api/v1/sport/add` | Add new sport | Leena |
| PUT | `/api/v1/sport/update/{id}` | Update sport | Leena |
| DELETE | `/api/v1/sport/delete/{id}` | Delete sport | Leena |
| GET | `/api/v1/sport/analyze/sport/{sportId}` | Analyze sport popularity | Leena |

---

### 🏢 Organizer Management
| Method | Endpoint | Description | Name |
|--------|---------|-------------|------|
| GET | `/api/v1/organizer/get` | Get all organizers | Leena |
| POST | `/api/v1/organizer/add` | Add new organizer | Leena |
| PUT | `/api/v1/organizer/update/{id}` | Update organizer | Leena |
| DELETE | `/api/v1/organizer/delete/{id}` | Delete organizer | Leena |

---

## 🧠 Technologies Used
- Java  
- Spring Boot  
- RESTful APIs  
- JPA / Hibernate  
- MySQL  
- AI Prompt-based Analysis  
- Figma (UI/UX)
