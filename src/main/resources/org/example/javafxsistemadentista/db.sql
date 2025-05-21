-- Enable foreign key constraints (important for SQLite)
PRAGMA foreign_keys = ON;

-- 1. patients table (simplificada sem patient_profiles)
CREATE TABLE IF NOT EXISTS patients (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    name TEXT NOT NULL,
    cpf TEXT NOT NULL,
    phone TEXT NOT NULL,
    email TEXT,
    birth_date TEXT,
    address TEXT,
    notes TEXT,
    created_at TEXT DEFAULT (datetime('now', 'localtime')),
    UNIQUE(cpf),
    UNIQUE(phone)
);

-- 2. dentists
CREATE TABLE IF NOT EXISTS dentists (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    name TEXT NOT NULL,
    cpf TEXT NOT NULL,
    specialization TEXT,
    phone TEXT,
    email TEXT,
    UNIQUE(cpf)
);

-- 3. employees
CREATE TABLE IF NOT EXISTS employees (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    name TEXT NOT NULL,
    cpf TEXT NOT NULL,
    role TEXT NOT NULL,
    phone TEXT,
    email TEXT,
    UNIQUE(cpf)
);

-- 4. treatments
CREATE TABLE IF NOT EXISTS treatments (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    name TEXT NOT NULL,
    cost REAL NOT NULL DEFAULT 0.0,
    description TEXT
);

-- 5. exams
CREATE TABLE IF NOT EXISTS exams (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    patient_id INTEGER NOT NULL,
    type TEXT NOT NULL,
    requested_by INTEGER NOT NULL,
    request_date TEXT DEFAULT (datetime('now', 'localtime')),
    result_path TEXT,
    status TEXT NOT NULL DEFAULT 'REQUESTED' CHECK (status IN ('REQUESTED', 'COMPLETED', 'CANCELLED')),
    notes TEXT,
    FOREIGN KEY (patient_id) REFERENCES patients(id) ON DELETE CASCADE,
    FOREIGN KEY (requested_by) REFERENCES dentists(id)
);

-- 6. appointments (scheduling)
CREATE TABLE IF NOT EXISTS appointments (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    patient_id INTEGER NOT NULL,
    dentist_id INTEGER NOT NULL,
    employee_id INTEGER NOT NULL,  -- Who booked it
    date_time TEXT NOT NULL,       -- Format: YYYY-MM-DD HH:MM
    duration INTEGER NOT NULL DEFAULT 30 CHECK (duration > 0 AND duration <= 240),
    status TEXT NOT NULL DEFAULT 'SCHEDULED' CHECK (status IN ('SCHEDULED', 'COMPLETED', 'CANCELLED', 'NO_SHOW')),
    notes TEXT,
    created_at TEXT DEFAULT (datetime('now', 'localtime')),
    total_price REAL NOT NULL CHECK (total_price >= 0),
    FOREIGN KEY (patient_id) REFERENCES patients(id) ON DELETE CASCADE,
    FOREIGN KEY (dentist_id) REFERENCES dentists(id),
    FOREIGN KEY (employee_id) REFERENCES employees(id)
);

-- 7. appointment_treatments (many-to-many link)
CREATE TABLE IF NOT EXISTS appointment_treatments (
    appointment_id INTEGER NOT NULL,
    treatment_id INTEGER NOT NULL,
    quantity INTEGER DEFAULT 1 CHECK (quantity > 0),
    notes TEXT,
    PRIMARY KEY (appointment_id, treatment_id),
    FOREIGN KEY (appointment_id) REFERENCES appointments(id) ON DELETE CASCADE,
    FOREIGN KEY (treatment_id) REFERENCES treatments(id)
);

-- Create indexes
CREATE INDEX IF NOT EXISTS idx_appointments_patient ON appointments(patient_id);
CREATE INDEX IF NOT EXISTS idx_appointments_dentist ON appointments(dentist_id);
CREATE INDEX IF NOT EXISTS idx_appointments_datetime ON appointments(date_time);
CREATE INDEX IF NOT EXISTS idx_exams_patient ON exams(patient_id);