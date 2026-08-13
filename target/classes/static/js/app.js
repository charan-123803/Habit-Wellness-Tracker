/**
 * LifeSync - Core Application JavaScript
 * Handles API calls, state management, charts, theme toggling, and interactive UI functions.
 */

const API_BASE_URL = '/api/v1';

// --- State Management ---
const LifeSyncState = {
  user: JSON.parse(localStorage.getItem('lifesync_user')) || {
    id: 2,
    username: 'john_doe',
    fullName: 'John Doe',
    email: 'john@example.com',
    avatarUrl: 'https://images.unsplash.com/photo-1535713875002-d1d0cf377fde?auto=format&fit=crop&w=250&q=80',
    xpPoints: 480,
    userLevel: 3,
    coins: 150,
    heightCm: 175,
    weightKg: 70,
    gender: 'MALE',
    age: 26
  },
  token: localStorage.getItem('lifesync_token') || null,
  theme: localStorage.getItem('lifesync_theme') || 'light'
};

// --- Theme Engine ---
function initTheme() {
  document.documentElement.setAttribute('data-theme', LifeSyncState.theme);
  const themeToggles = document.querySelectorAll('.theme-toggle-btn');
  themeToggles.forEach(btn => {
    btn.innerHTML = LifeSyncState.theme === 'dark' ? '<i class="fas fa-sun text-warning"></i>' : '<i class="fas fa-moon text-primary"></i>';
    btn.addEventListener('click', toggleTheme);
  });
}

function toggleTheme() {
  LifeSyncState.theme = LifeSyncState.theme === 'dark' ? 'light' : 'dark';
  localStorage.setItem('lifesync_theme', LifeSyncState.theme);
  document.documentElement.setAttribute('data-theme', LifeSyncState.theme);
  
  const themeToggles = document.querySelectorAll('.theme-toggle-btn');
  themeToggles.forEach(btn => {
    btn.innerHTML = LifeSyncState.theme === 'dark' ? '<i class="fas fa-sun text-warning"></i>' : '<i class="fas fa-moon text-primary"></i>';
  });
  
  showToast('Theme updated to ' + LifeSyncState.theme + ' mode', 'info');
}

// --- Toast / Notification Helper ---
function showToast(message, icon = 'success') {
  if (window.Swal) {
    Swal.fire({
      toast: true,
      position: 'top-end',
      icon: icon,
      title: message,
      showConfirmButton: false,
      timer: 3000,
      timerProgressBar: true
    });
  } else {
    alert(message);
  }
}

// --- Auth Manager ---
function checkAuth() {
  const authProtectedPages = ['dashboard.html', 'habits.html', 'wellness.html', 'sleep.html', 'exercise.html', 'meals.html', 'mood.html', 'goals.html', 'reports.html', 'calendar.html', 'community.html', 'profile.html', 'settings.html', 'admin.html'];
  const currentPage = window.location.pathname.split('/').pop();
  
  if (authProtectedPages.includes(currentPage) && !LifeSyncState.token && !localStorage.getItem('lifesync_demo_mode')) {
    // Enable seamless demo mode for offline preview if token isn't present
    localStorage.setItem('lifesync_demo_mode', 'true');
  }
  
  // Render user details in UI
  const userAvatarEls = document.querySelectorAll('.user-avatar-img');
  userAvatarEls.forEach(img => img.src = LifeSyncState.user.avatarUrl);
  
  const userNameEls = document.querySelectorAll('.user-name-text');
  userNameEls.forEach(el => el.textContent = LifeSyncState.user.fullName);
  
  const userXpEls = document.querySelectorAll('.user-xp-badge');
  userXpEls.forEach(el => el.textContent = `Lvl ${LifeSyncState.user.userLevel} • ${LifeSyncState.user.xpPoints} XP`);
}

function logout() {
  localStorage.removeItem('lifesync_token');
  localStorage.removeItem('lifesync_user');
  localStorage.removeItem('lifesync_demo_mode');
  showToast('Logged out successfully');
  setTimeout(() => { window.location.href = 'login.html'; }, 1000);
}

// --- Mock / Cache Storage Engine for Standalone Dynamic Interactivity ---
const MockData = {
  habits: [
    { id: 1, title: 'Morning Hydration', category: 'Water', priority: 'HIGH', currentStreak: 5, targetCount: 1, completed: true },
    { id: 2, title: '30-Min Cardio Workout', category: 'Fitness', priority: 'HIGH', currentStreak: 3, targetCount: 1, completed: true },
    { id: 3, title: 'Read 20 Pages', category: 'Reading', priority: 'MEDIUM', currentStreak: 7, targetCount: 1, completed: false },
    { id: 4, title: 'Evening Meditation', category: 'Meditation', priority: 'MEDIUM', currentStreak: 2, targetCount: 1, completed: false },
    { id: 5, title: 'Code Practice', category: 'Coding', priority: 'HIGH', currentStreak: 10, targetCount: 1, completed: true }
  ],
  wellness: {
    heightCm: 175,
    weightKg: 70,
    bmi: 22.9,
    systolicBp: 120,
    diastolicBp: 80,
    heartRate: 72,
    bloodSugar: 95,
    oxygenLevel: 99,
    waterIntakeMl: 2500,
    steps: 8450,
    caloriesBurned: 480
  },
  sleep: { totalHours: 8.0, quality: 'EXCELLENT', sleepTime: '23:00', wakeTime: '07:00', deepHours: 2.5, lightHours: 5.5 },
  exercise: [
    { id: 1, type: 'Running', duration: 35, calories: 320, distance: 5.2, speed: 8.9, date: 'Today' },
    { id: 2, type: 'Yoga', duration: 25, calories: 110, distance: 0, speed: 0, date: 'Yesterday' }
  ],
  meals: [
    { id: 1, type: 'BREAKFAST', name: 'Oatmeal & Berry Protein Bowl', calories: 420, protein: 32, carbs: 55, fat: 8 },
    { id: 2, type: 'LUNCH', name: 'Grilled Chicken Salad with Avocado', calories: 550, protein: 45, carbs: 20, fat: 22 }
  ],
  moods: [
    { id: 1, type: 'HAPPY', score: 4, notes: 'Completed all workout goals!', date: 'Today' },
    { id: 2, type: 'EXCELLENT', score: 5, notes: 'Great energy and clear focus.', date: 'Yesterday' }
  ],
  goals: [
    { id: 1, title: 'Target Weight 68kg', category: 'Fitness', current: 70, target: 68, unit: 'kg', percentage: 75 },
    { id: 2, title: 'Read 5 Non-Fiction Books', category: 'Reading', current: 2, target: 5, unit: 'books', percentage: 40 }
  ]
};

// Local storage cache wrapper
function getCached(key, fallback) {
  const cached = localStorage.getItem('ls_cache_' + key);
  return cached ? JSON.parse(cached) : fallback;
}
function setCached(key, data) {
  localStorage.setItem('ls_cache_' + key, JSON.stringify(data));
}

// Initialize cached datasets
if (!localStorage.getItem('ls_cache_habits')) setCached('habits', MockData.habits);
if (!localStorage.getItem('ls_cache_wellness')) setCached('wellness', MockData.wellness);
if (!localStorage.getItem('ls_cache_exercise')) setCached('exercise', MockData.exercise);
if (!localStorage.getItem('ls_cache_meals')) setCached('meals', MockData.meals);
if (!localStorage.getItem('ls_cache_goals')) setCached('goals', MockData.goals);
if (!localStorage.getItem('ls_cache_moods')) setCached('moods', MockData.moods);

// Initialize on DOM Ready
document.addEventListener('DOMContentLoaded', () => {
  initTheme();
  checkAuth();
});
