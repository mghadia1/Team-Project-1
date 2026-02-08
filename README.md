# TP1-Team 32 — Student Discussion System

> **Course:** CSE 360 — Spring 2026  
> **Phase:** Team Project Phase 1 (TP1)  
> **Sprint:** February 5 – February 9, 2026  
> **Design Inspiration:** Instagram

---

## 📌 Project Overview

The **Student Discussion System** is a JavaFX-based desktop application that supports user authentication, role-based access control, and account management. The application is built upon foundational code provided by the course and enhanced by the team to implement all required user stories for Phase 1.

The system supports three user roles:
- **New User** — Register, set up credentials, and access the system
- **Admin** — Manage users, invite new users, reset passwords, assign roles
- **Staff** *(Phase 2+)* — Additional functionality to be implemented in later phases

The UI design is inspired by **Instagram's** clean, minimalist login and account management screens — including dynamic button states, password visibility toggles, inline validation, and proximity-based error messages.

---

## 👥 Team Members

| Name | Role |
|------|------|
| **Meet Sutariya** | Developer — First user setup, role selection, delete user, role management, final doc review |
| **Perran Walia** | Developer — Username validation, logout, list users, username test automation |
| **Mayank Ghadia** | Developer — Password validation, invite user, password test automation |
| **Shorya Azriel Moses** | Developer — Account info update, password reset OTP, UML design documents |

---

## 🛠️ Tech Stack

| Technology | Purpose |
|------------|---------|
| **Java** | Core application logic |
| **JavaFX** | User interface (GUI) |
| **JUnit** | Automated testing |
| **GitHub** | Version control & collaboration |
| **Astah** | UML diagrams (architecture & detailed design) |
| **Zoom** | Standup meeting recordings |

---

## 🎨 Design Inspiration — Instagram

The team selected **Instagram (Web)** as the UI design inspiration for its professional, clean approach to authentication and account management.

### Key Design Elements Adopted:
| Element | Description |
|---------|-------------|
| **Dynamic Login Button** | "Log In" button remains disabled until input fields meet minimum requirements |
| **Password Visibility Toggle** | Eye icon allows users to show/hide password to reduce input errors |
| **Minimalist Branding** | Simple logo with high-contrast primary action button |
| **Inline Placeholder Labels** | Text within fields provides immediate context, clears on interaction |
| **Proximity-Based Error Messages** | Errors displayed directly adjacent to the relevant input field |
| **Visual Error Indicators** | Color coding (red outlines/text) highlights fields that fail validation |
| **Real-time Validation** | Clear instructions appear above/below fields for immediate feedback |
| **Actionable Guidance** | Error messages explain exactly how to correct input (not generic codes) |

---

## 📂 Project Structure

```
TP1/
├── TP1 Source Code/           # Java/JavaFX application source code
├── TP1 Design Documents/      # UML architecture & detailed design (Astah)
├── TP1 Scrum Standup Video Recordings/
│   ├── TP1 Scrum Standup 2026-02-05.mp4
│   ├── TP1 Scrum Standup 2026-02-07.mp4
│   └── ...
├── TP1-Team32 Team Norms.pdf
├── User Experience.pdf
├── Input Validation.pdf
├── Sprint Backlog.pdf
├── TP1 Plan.pdf
├── TP1 Test Cases.pdf
├── TP1 Functional Requirements Validated.mp4
├── TP1 Flow Explained.mp4
└── README.md
```

---

## 📋 Sprint Backlog — User Stories

### New User Stories

| ID | User Story | Owner | Target | Status |
|----|-----------|-------|--------|--------|
| NU-1 | As the first user, I can create an account and become admin so the system can be initialized | Meet Sutariya | Feb 6 | ✅ Done |
| NU-2 | As a user, I can establish my username, password, and account info so I can use the system | Perran Walia | Feb 6 | ✅ Done |
| NU-3 | As a user, I can login after account creation so I can access the system | Mayank Ghadia | Feb 7 | ✅ Done |
| NU-4 | As a user with one role, I am taken directly to that role's home screen after login | Shorya Moses | Feb 7 | ✅ Done |
| NU-5 | As a user with multiple roles, I can select which role to play after login | Meet Sutariya | Feb 7 | ✅ Done |
| NU-6 | As a user, I can log out when finished using the system | Perran Walia | Feb 7 | ✅ Done |

### Admin Stories

| ID | User Story | Owner | Target | Status |
|----|-----------|-------|--------|--------|
| AD-1 | As an admin, I can invite users using a one-time code and deadline | Mayank Ghadia | Feb 8 | ✅ Done |
| AD-2 | As an admin, I can reset a user's password using a one-time password | Shorya Moses | Feb 8 | ✅ Done |
| AD-3 | As an admin, I can delete a user account with confirmation | Meet Sutariya | Feb 8 | ✅ Done |
| AD-4 | As an admin, I can list all user accounts | Perran Walia | Feb 8 | ✅ Done |
| AD-5 | As an admin, I can add or remove roles while ensuring at least one admin exists | Meet Sutariya | Feb 8 | ✅ Done |

### Testing & Documentation

| ID | Description | Owner | Target | Status |
|----|------------|-------|--------|--------|
| TEST-1 | Automated tests for username validation | Perran Walia | Feb 8 | ✅ Done |
| TEST-2 | Automated tests for password evaluator | Mayank Ghadia | Feb 8 | ✅ Done |
| DOC-1 | UML updates (architecture + detailed design) | Shorya Moses | Feb 8 | 🔄 In Progress |
| DOC-2 | Final documentation consistency review | Meet Sutariya | Feb 9 | ⬜ To Do |

---

## ✅ Input Validation Rules

### General Rules (All Fields)
- Leading and trailing whitespace is trimmed before validation
- Required fields must not be empty
- All text fields enforce a maximum length to prevent large-input attacks
- Input is validated before processing or saving
- Clear, user-friendly error messages are displayed on failure

### Field-Specific Rules

| Field | Required | Min | Max | Rules | Allowed Characters |
|-------|----------|-----|-----|-------|--------------------|
| **Username** | Yes | 6 | 32 | Must start with a letter, no spaces | `A-Z a-z 0-9 _ # $ !` |
| **Password** | Yes | 8 | 64 | Must include: 1 uppercase, 1 lowercase, 1 digit, 1 special char | `A-Z a-z 0-9 _ @ # $ !` |
| **Confirm Password** | Yes | — | — | Must exactly match new password | — |
| **Full Name** | Yes | 4 | 32 | No digits or special symbols | `A-Z a-z - '` |
| **Email** | Yes | 4 | 32 | Must follow `local@domain` format, no spaces | Standard email characters |

### Error Messages
| Field | Error Messages |
|-------|---------------|
| **Username** | "Username must start with a letter." · "Username can only contain letters, numbers, and special characters." |
| **Password** | "Password must include at least one uppercase letter." · "Password must include at least one digit." · "Password is too long." |
| **Full Name** | "Name must contain letters only." · "Name is too long." |
| **Email** | "Invalid email address format." · "Email address is too long." |
| **Confirm Password** | "Passwords do not match." |

### Security Considerations
- Maximum length limits protect against denial-of-service and overflow attacks
- Invalid characters are rejected immediately
- Input is not processed unless all validation rules pass
- Admin privileges do not bypass validation

---

## 🔒 Security Checklist

### Password Security
- **BCrypt Hashing**: All passwords are hashed using BCrypt with a cost factor of 12 before storage
- **No Plaintext Storage**: Passwords are NEVER stored in plaintext in the database
- **Secure Verification**: Password verification uses BCrypt's constant-time comparison to prevent timing attacks
- **Strong Requirements**: Minimum 8 characters with complexity requirements enforced

### Database Security
- **Location**: Database file stored at `~/FoundationDatabase` (H2 database)
- **Backup Recommendations**: 
  - Regular backups of `~/FoundationDatabase.*` files
  - Store backups in secure, encrypted location
  - Test restore procedures periodically
- **Access Control**: Database file should have restricted file permissions (600 or 400)

### Development Environment
- **Recommended JDK**: Java 17+ (OpenJDK or Oracle JDK)
- **JavaFX SDK**: JavaFX 17+ required for GUI components
- **Dependencies**: 
  - JUnit 5 for testing
  - H2 Database Engine (embedded)
  - jBCrypt library v0.4 (org.mindrot:jbcrypt) included in `lib/jbcrypt-0.4.jar`

### Running Tests
```bash
# Using Eclipse: Right-click test class → Run As → JUnit Test
# Using command line with JUnit console launcher:
java -jar junit-platform-console-standalone.jar --class-path out --scan-classpath

# Run specific test class:
java -jar junit-platform-console-standalone.jar --class-path out --select-class test.validation.PasswordValidatorTest
```

### Security Notes
- ✅ All database operations use try-with-resources to prevent resource leaks
- ✅ Prepared statements used throughout to prevent SQL injection
- ✅ Input validation enforced before any database operations
- ✅ Password hashing cost factor can be adjusted via `BCRYPT_COST_FACTOR` constant in `Database.java` if needed for future hardware

---

## 🧪 Test Cases

### Test Summary

| Category | Test Count | Description |
|----------|-----------|-------------|
| General Validation | 5 | Whitespace trimming, required fields, max length, pre-save validation, error messages |
| Username | 12 | Start-with-letter, allowed chars, spaces, min/max length, trimming |
| Password | 12 | Uppercase, lowercase, digit, special char, invalid chars, spaces, min/max length |
| Confirm Password | 4 | Match, mismatch, empty, both-invalid scenarios |
| Full Name | 10 | Letters only, hyphen, apostrophe, digits, symbols, min/max length |
| Email | 9 | Format validation, missing @, missing domain, spaces, min/max length |
| Admin Validation | 4 | Admin inputs follow same rules, no bypass |
| **Total** | **56** | |

### Sample Test Cases

#### Username Tests
| ID | Description | Input | Expected |
|----|------------|-------|----------|
| U-01 | Valid username | `Meet12` | ✅ Accepted |
| U-04 | Starts with digit | `1Meet12` | ❌ Rejected — must start with letter |
| U-05 | Contains space | `Meet 12` | ❌ Rejected |
| U-07 | Too short (5 chars) | `Meet1` | ❌ Rejected — min length 6 |
| U-10 | Too long (33 chars) | 33-char string | ❌ Rejected — too long |

#### Password Tests
| ID | Description | Input | Expected |
|----|------------|-------|----------|
| P-01 | Valid password | `Aa1!aaaa` | ✅ Accepted |
| P-02 | Missing uppercase | `aa1!aaaa` | ❌ Rejected |
| P-03 | Missing lowercase | `AA1!AAAA` | ❌ Rejected |
| P-04 | Missing digit | `Aa!aaaaa` | ❌ Rejected |
| P-11 | Too long (65 chars) | 65-char password | ❌ Rejected — too long |

> 📄 **Full test case details are in `TP1 Test Cases.pdf`**

---

## 📅 TP1 Plan Summary

### Timeline
| Phase | Date | Activities |
|-------|------|-----------|
| **Sprint Start** | Feb 5 | Plan created, task ownership assigned, sprint backlog finalized |
| **Core Features** | Feb 6–7 | New User stories (NU-1 through NU-6) |
| **Admin Features** | Feb 8 | Admin stories (AD-1 through AD-5) |
| **Testing** | Feb 8 | Automated tests for username and password validation |
| **Documentation** | Feb 8–9 | UML updates, final consistency review |
| **Sprint End** | Feb 9 | Final submission by 11:59 PM |

### Risks & Mitigation

| Risk | Mitigation |
|------|-----------|
| Integration conflicts in shared classes | Feature branches + reviewed pull requests |
| Password evaluator complexity | Early testing with `PasswordEvaluationTestbed`, isolated reusable methods |
| Time constraints near deadline | Prioritize core stories first, submit working version by noon on due date |
| Documentation falling out of sync | Update UML/docs immediately after code changes, final review before submission |

### Definition of Done
A task is complete when:
- ✅ Code is implemented and tested
- ✅ Validation rules are enforced
- ✅ Documentation is updated
- ✅ Changes are merged into `main` branch

---

## 🤝 Team Norms Summary

| Category | Norm |
|----------|------|
| **Communication** | WhatsApp — respond within 1 hour (normal) / 10 minutes (urgent) |
| **Standups** | At least 2x/week, recorded via Zoom, follow what-done/what-next/blockers format |
| **Accountability** | Every task has a clear owner; notify team if blocked >30 minutes |
| **Version Control** | Feature branches only, no direct commits to `main`, PRs require 1 reviewer |
| **Conflict Resolution** | Address privately first → standup discussion → team decision |

---

## 🚀 Getting Started

### Prerequisites
- Java JDK 17+
- JavaFX SDK
- JUnit 5
- IDE: Eclipse or IntelliJ IDEA

### Clone the Repository
```bash
git clone https://github.com/mghadia1/Team-Project-1.git
cd Team-Project-1
```

### Run the Application
```bash
# Using Eclipse: Import as JavaFX project and run main class
# Using command line:
javac --module-path /path/to/javafx/lib --add-modules javafx.controls,javafx.fxml -d out src/**/*.java
java --module-path /path/to/javafx/lib --add-modules javafx.controls,javafx.fxml -cp out application.Main
```

### Run Tests
```bash
# Using Eclipse: Right-click test class → Run As → JUnit Test
# Using command line:
java -jar junit-platform-console-standalone.jar --class-path out --scan-classpath
```

---

## 📊 Deliverables Checklist

| # | Deliverable | Weight | Status |
|---|------------|--------|--------|
| Task 1 | TP1 archive named properly | 5% | ⬜ |
| Task 2 | Team Norms PDF | 5% | ✅ |
| Task 3 | User Experience PDF | 5% | ✅ |
| Task 4 | Input Validation PDF | 5% | ✅ |
| Task 5.1 | Sprint Backlog PDF | 5% | ✅ |
| Task 5.2 | TP1 Plan PDF | 5% | ✅ |
| Task 6 | TP1 Test Cases PDF | 5% | ✅ |
| Task 7 | TP1 Design Documents folder | 5% | 🔄 |
| Task 8 | ~~Source Code folder~~ *(removed)* | 5% | N/A |
| Task 9.1 | Functional Requirements Validated screencast | 15% | ⬜ |
| Task 9.2 | Flow Explained screencast | 20% | ⬜ |
| Task 9.3 | Scrum Standup Video Recordings folder | 20% | 🔄 |

---

## 📄 License

This project is developed as part of **CSE 360 — Spring 2026** at Arizona State University.  
For academic use only.

---

> **TP1-Team 32** — Meet · Perran · Mayank · Shorya
