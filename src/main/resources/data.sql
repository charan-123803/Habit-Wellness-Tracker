-- LifeSync Seed Data SQL

-- Roles
MERGE INTO roles (id, name) KEY(id) VALUES (1, 'ROLE_ADMIN');
MERGE INTO roles (id, name) KEY(id) VALUES (2, 'ROLE_USER');

-- Seed Users (Passwords are BCrypt encoded 'password123')
MERGE INTO users (id, username, email, password, full_name, avatar_url, height_cm, weight_kg, gender, age, xp_points, user_level, coins) KEY(id) 
VALUES (1, 'admin', 'admin@lifesync.com', '$2a$10$7R.v3727tD8.4u44xQ1r2.a9tD7n8l9e0w1q2r3t4y5u6i7o8p9', 'LifeSync Admin', 'https://images.unsplash.com/photo-1534528741775-53994a69daeb?auto=format&fit=crop&w=250&q=80', 178, 75, 'MALE', 30, 1250, 5, 340);

MERGE INTO users (id, username, email, password, full_name, avatar_url, height_cm, weight_kg, gender, age, xp_points, user_level, coins) KEY(id) 
VALUES (2, 'john_doe', 'john@example.com', '$2a$10$7R.v3727tD8.4u44xQ1r2.a9tD7n8l9e0w1q2r3t4y5u6i7o8p9', 'John Doe', 'https://images.unsplash.com/photo-1535713875002-d1d0cf377fde?auto=format&fit=crop&w=250&q=80', 175, 70, 'MALE', 26, 480, 3, 150);

MERGE INTO user_roles (user_id, role_id) KEY(user_id, role_id) VALUES (1, 1);
MERGE INTO user_roles (user_id, role_id) KEY(user_id, role_id) VALUES (1, 2);
MERGE INTO user_roles (user_id, role_id) KEY(user_id, role_id) VALUES (2, 2);

-- Badges / Achievements
MERGE INTO badges (id, code, title, description, icon, xp_reward) KEY(id) VALUES
(1, 'STREAK_7', '7-Day Streak', 'Maintained a habit for 7 consecutive days', 'fa-fire', 100),
(2, 'STREAK_30', '30-Day Streak', 'Maintained a habit for 30 consecutive days', 'fa-bolt', 500),
(3, 'WATER_MASTER', 'Water Master', 'Reached daily water goal 10 times', 'fa-glass-water', 150),
(4, 'SLEEP_CHAMPION', 'Sleep Champion', 'Logged 8+ hours of sleep for 5 days', 'fa-moon', 200),
(5, 'FITNESS_HERO', 'Fitness Hero', 'Completed 20 workouts', 'fa-dumbbell', 300),
(6, 'HABIT_KING', 'Habit King', 'Completed 100 total habit logs', 'fa-crown', 1000);

-- User Badges
MERGE INTO user_badges (id, user_id, badge_id) KEY(id) VALUES (1, 2, 1);
MERGE INTO user_badges (id, user_id, badge_id) KEY(id) VALUES (2, 2, 3);

-- Sample Habits
MERGE INTO habits (id, user_id, title, description, category, priority, frequency, target_count, current_streak, best_streak) KEY(id) VALUES
(1, 2, 'Morning Hydration', 'Drink 500ml water right after waking up', 'Water', 'HIGH', 'DAILY', 1, 5, 12),
(2, 2, '30-Min Cardio Workout', 'Morning jog or cycling session', 'Fitness', 'HIGH', 'DAILY', 1, 3, 8),
(3, 2, 'Read 20 Pages', 'Read non-fiction or self-development books', 'Reading', 'MEDIUM', 'DAILY', 1, 7, 14),
(4, 2, 'Evening Meditation', '10 minutes of mindfulness before bed', 'Meditation', 'MEDIUM', 'DAILY', 1, 2, 5),
(5, 2, 'Code Practice', 'Solve 1 problem or build feature code', 'Coding', 'HIGH', 'DAILY', 1, 10, 15);

-- Sample Wellness Record
MERGE INTO wellness_records (id, user_id, record_date, height_cm, weight_kg, bmi, systolic_bp, diastolic_bp, heart_rate, blood_sugar, oxygen_level, water_intake_ml, steps, calories_burned) KEY(id) VALUES
(1, 2, CURRENT_DATE(), 175.0, 70.0, 22.86, 120, 80, 72, 95.0, 99.0, 2500, 8450, 480);

-- Sample Sleep Record
MERGE INTO sleep_records (id, user_id, record_date, sleep_time, wake_time, total_hours, quality, deep_sleep_hours, light_sleep_hours, notes) KEY(id) VALUES
(1, 2, CURRENT_DATE(), '23:00', '07:00', 8.0, 'EXCELLENT', 2.5, 5.5, 'Felt very well rested and clear headed');

-- Sample Exercise Record
MERGE INTO exercise_records (id, user_id, record_date, exercise_type, duration_minutes, calories_burned, distance_km, avg_speed_kmh, heart_rate, notes) KEY(id) VALUES
(1, 2, CURRENT_DATE(), 'Running', 35, 320, 5.2, 8.9, 142, 'Morning interval trail run');

-- Sample Meal Record
MERGE INTO meal_records (id, user_id, record_date, meal_type, food_name, calories, protein_g, carbs_g, fat_g, sugar_g, fiber_g) KEY(id) VALUES
(1, 2, CURRENT_DATE(), 'BREAKFAST', 'Oatmeal with Berries & Whey Protein', 420, 32.0, 55.0, 8.0, 12.0, 7.0),
(2, 2, CURRENT_DATE(), 'LUNCH', 'Grilled Chicken Salad with Olive Oil', 550, 45.0, 20.0, 22.0, 4.0, 8.0);

-- Sample Mood Record
MERGE INTO mood_records (id, user_id, record_date, mood_type, score, notes) KEY(id) VALUES
(1, 2, CURRENT_DATE(), 'HAPPY', 4, 'Productive morning workout and achieved daily coding goals');

-- Sample Goals
MERGE INTO goals (id, user_id, title, category, target_value, current_value, unit, deadline, status) KEY(id) VALUES
(1, 2, 'Target Weight 68kg', 'Fitness', 68.0, 70.0, 'kg', CURRENT_DATE() + 30, 'IN_PROGRESS'),
(2, 2, 'Read 5 Books', 'Reading', 5.0, 2.0, 'books', CURRENT_DATE() + 60, 'IN_PROGRESS');

-- Sample Community Posts
MERGE INTO community_posts (id, user_id, content, likes_count) KEY(id) VALUES
(1, 2, 'Just completed a 10-day streak on my morning workouts! Consistency is key 💪⚡ #LifeSync #FitnessGoal', 12);
