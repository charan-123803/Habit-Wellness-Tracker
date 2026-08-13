# LifeSync – Habit & Wellness Tracker

> **Build Better Habits. Live Healthier Every Day.**

LifeSync is a complete, production-ready, full-stack **Habit & Wellness Tracker** web application designed to help users build consistency, monitor physical vitals, track sleep quality, log meals and exercise, track emotional mood, achieve target goals, and engage in gamified wellness challenges.

---

## 🚀 Technology Stack

### **Backend**
- **Java 21**
- **Spring Boot 3.2.3**
- **Spring Security 6** (JWT Authentication, RBAC)
- **Spring Data JPA & Hibernate**
- **Maven**
- **Lombok**
- **ModelMapper**
- **Hibernate Validation**

### **Database**
- **MySQL 8.0** (Production / Docker)
- **H2 In-Memory Database** (Instant local dev execution out-of-the-box)

### **Frontend**
- **HTML5 & CSS3** (Custom Glassmorphism SaaS Design System)
- **JavaScript (ES6)**
- **Bootstrap 5.3**
- **Chart.js** (Interactive Analytics & Graphs)
- **Font Awesome 6**
- **SweetAlert2** (Toast alerts & interactive modals)
- **AOS Animation**

---

## 📁 Project Folder Structure

```
Habit-Wellness-Tracker/
├── pom.xml
├── Dockerfile
├── docker-compose.yml
├── README.md
├── .github/
│   └── workflows/
│       └── ci.yml
├── database/
│   ├── schema.sql
│   └── data.sql
├── frontend/
│   ├── index.html
│   ├── login.html
│   ├── register.html
│   ├── dashboard.html
│   ├── habits.html
│   ├── wellness.html
│   ├── sleep.html
│   ├── exercise.html
│   ├── meals.html
│   ├── mood.html
│   ├── goals.html
│   ├── reports.html
│   ├── calendar.html
│   ├── community.html
│   ├── profile.html
│   ├── settings.html
│   ├── admin.html
│   ├── css/
│   │   └── style.css
│   └── js/
│       └── app.js
└── src/
    └── main/
        ├── java/com/lifesync/
        │   ├── LifeSyncApplication.java
        │   ├── config/ (SecurityConfig, JwtAuthFilter, CorsConfig)
        │   ├── controller/ (Auth, User, Habit, Wellness, Sleep, Exercise, Meal, Mood, Goal, Gamification, AICoach, Community, Admin)
        │   ├── dto/ (AuthDTOs, ApiResponse)
        │   ├── entity/ (User, Role, Habit, HabitLog, WellnessRecord, SleepRecord, ExerciseRecord, MealRecord, MoodRecord, Goal, Badge, UserBadge, Post, Comment)
        │   ├── exception/ (GlobalExceptionHandler)
        │   ├── repository/ (UserRepository, HabitRepository, WellnessRepository, etc.)
        │   ├── security/ (JwtTokenProvider, CustomUserDetailsService)
        │   └── service/ (AuthService, HabitService, WellnessService, SleepService, ExerciseService, MealService, MoodService, GoalService)
        └── resources/
            ├── application.yml
            ├── schema.sql
            ├── data.sql
            └── static/ (Hosted single-run Web UI)
```

---

## ✨ Features Highlight

### 1. **Authentication & Authorization**
- Registration, Login with JWT Tokens
- Role-based authorization (`ROLE_USER`, `ROLE_ADMIN`)
- Password encryption with BCrypt
- Remember Me & Logout session handler

### 2. **Landing Page**
- Glassmorphic UI hero section, feature cards, pricing tiers, FAQ accordion, contact form, dark mode toggle.

### 3. **Dashboard**
- **11 Metric Cards**: Today's Habits, Completed Habits, Current Streak, Water Intake, Calories, Sleep Hours, Mood Score, Exercise Minutes, Steps, Weight, BMI.
- **7 Interactive Chart.js Graphs**: Weekly Habit Rate, Monthly Progress, Water Graph, Sleep Analytics, Mood Breakdown, Calories Graph, Exercise Stats.

### 4. **Habit Manager**
- Complete CRUD habits with categories (Fitness, Coding, Reading, Gym, Meditation, Water, Sleep, etc.), priority tags (High, Medium, Low), streak counters, and quick check buttons.

### 5. **Wellness & Vitals Tracker**
- Log Height, Weight, auto-calculated **BMI**, Blood Pressure (Systolic/Diastolic), Heart Rate, Blood Sugar, Oxygen Level (SpO2), Steps, and **Interactive Water Glass Counter**.

### 6. **Sleep, Exercise, Meal & Mood Trackers**
- **Sleep**: Sleep/Wake times, quality score, deep vs light sleep breakdown.
- **Exercise**: Workouts (Running, Cycling, Gym, Yoga, Swimming), duration, calories, speed, distance.
- **Meals**: Breakfast, Lunch, Dinner, Snacks with calories, protein, carbs, fats.
- **Mood Journal**: Emoji selector (😀, 😊, 😐, 😴, 😔, 😡) with notes & weekly trend.

### 7. **Gamification & Community**
- XP points, Levels, Coins, Badges (7-Day Streak, Water Master, Sleep Champion, Fitness Hero), Global XP Leaderboard, social motivation feed with likes & comments.

### 8. **AI Coach**
- Dynamic personalized daily wellness recommendations based on tracked stats.

### 9. **Admin Panel**
- Admin dashboard, user management table (suspend/activate), system status, announcements.

---

## ⚙️ How to Run the Application

### Method 1: Run with Maven & Embedded H2 (Quickest)

1. Open your terminal in the project root directory.
2. Run:
   ```bash
   mvn spring-boot:run
   ```
3. Open your browser and navigate to:
   **`http://localhost:8080`**

- **H2 Console**: `http://localhost:8080/h2-console`
  - JDBC URL: `jdbc:h2:mem:lifesync_db`
  - Username: `sa`
  - Password: *(leave blank)*

- **Demo User Login**:
  - Username: `john_doe`
  - Password: `password123`
- **Demo Admin Login**:
  - Username: `admin`
  - Password: `password123`

---

### Method 2: Run with Docker Compose (Spring Boot + MySQL)

1. Ensure Docker Desktop is running.
2. Run:
   ```bash
   docker-compose up --build
   ```
3. Access the application at `http://localhost:8080`.

---

## 📡 REST API Reference

| Endpoint | Method | Description |
| :--- | :--- | :--- |
| `/api/v1/auth/register` | `POST` | User registration |
| `/api/v1/auth/login` | `POST` | Authenticate & acquire JWT Token |
| `/api/v1/habits/user/{userId}` | `GET` | Get habits for user |
| `/api/v1/habits/user/{userId}` | `POST` | Create new habit |
| `/api/v1/habits/{id}/log` | `POST` | Mark habit complete/skipped |
| `/api/v1/wellness/user/{userId}` | `GET` | Get wellness vitals history |
| `/api/v1/wellness/user/{userId}` | `POST` | Save wellness vitals log |
| `/api/v1/sleep/user/{userId}` | `POST` | Save sleep log |
| `/api/v1/exercise/user/{userId}` | `POST` | Save workout log |
| `/api/v1/meals/user/{userId}` | `POST` | Save meal & macro log |
| `/api/v1/mood/user/{userId}` | `POST` | Save mood entry |
| `/api/v1/goals/user/{userId}` | `GET` / `POST` | Manage target goals |
| `/api/v1/gamification/leaderboard` | `GET` | Get top XP leaderboard |
| `/api/v1/aicoach/recommendations/{userId}` | `GET` | Get AI coach recommendations |
| `/api/v1/community/posts` | `GET` / `POST` | Community feed & posts |
| `/api/v1/admin/stats` | `GET` | Admin dashboard stats |

---

## 📄 License
This project is released under the **MIT License**.
