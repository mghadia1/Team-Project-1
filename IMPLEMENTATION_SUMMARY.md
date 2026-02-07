# Team Project 1 - Implementation Summary

## Overview
This implementation adds all missing source code packages, implements input validation per README requirements, implements admin stub features, and adds automated tests for the CSE 360 Foundation Application.

## What Was Implemented

### 1. Validation Package (`src/validation/`)
Four validator classes following README input validation rules:

#### UsernameValidator
- MIN_LENGTH = 6, MAX_LENGTH = 32
- Must start with a letter
- No spaces allowed
- Allowed characters: A-Z a-z 0-9 _ @ # $ !
- Returns "" if valid, error message if invalid

#### PasswordValidator
- MIN_LENGTH = 8, MAX_LENGTH = 16
- Must include: 1 uppercase, 1 lowercase, 1 digit, 1 special char (_@#$!)
- Allowed characters: A-Z a-z 0-9 _ @ # $ !
- Returns "" if valid, error message if invalid

#### NameValidator
- MIN_LENGTH = 4, MAX_LENGTH = 32
- Allowed: A-Z a-z - ' (letters, hyphens, apostrophes)
- No digits or special symbols
- Returns "" if valid, error message if invalid

#### EmailValidator
- MIN_LENGTH = 4, MAX_LENGTH = 32
- Must follow local@domain format
- No spaces allowed
- Returns "" if valid, error message if invalid

### 2. GUI Packages (MVC Pattern)
All 10 GUI packages created following the Model-View-Controller pattern:

1. **`guiUserLogin/`** - User login page with validation
   - Model.java
   - ViewUserLogin.java
   - ControllerUserLogin.java (with username/password validation)

2. **`guiFirstAdmin/`** - First admin setup page
   - ModelFirstAdmin.java
   - ViewFirstAdmin.java
   - ControllerFirstAdmin.java (with validation)

3. **`guiNewAccount/`** - New account creation
   - ModelNewAccount.java
   - ViewNewAccount.java
   - ControllerNewAccount.java (with validation)

4. **`guiAdminHome/`** - Admin home page with all admin functions
   - ModelAdminHome.java
   - ViewAdminHome.java
   - ControllerAdminHome.java (implements AD-1 through AD-4)

5. **`guiUserUpdate/`** - User account information update
   - Model.java
   - ViewUserUpdate.java
   - ControllerUserUpdate.java (with name and email validation)

6. **`guiAddRemoveRoles/`** - Role management
   - ModelAddRemoveRoles.java
   - ViewAddRemoveRoles.java
   - ControllerAddRemoveRoles.java (implements AD-5)

7. **`guiMultipleRoleDispatch/`** - Role selection for multi-role users
   - ModelMultipleRoleDispatch.java
   - ViewMultipleRoleDispatch.java
   - ControllerMultipleRoleDispatch.java

8. **`guiRole1/`** - Role 1 home page
   - ModelRole1Home.java
   - ViewRole1Home.java
   - ControllerRole1Home.java

9. **`guiRole2/`** - Role 2 home page
   - ModelRole2Home.java
   - ViewRole2Home.java
   - ControllerRole2Home.java

10. **`guiTools/`** - Utility classes
    - GUISingleRoleDispatch.java

### 3. Admin Stub Features (Database.java)

#### AD-1: Invite with Deadline
- Added `deadline` column (VARCHAR) to InvitationCodes table
- Updated `generateInvitationCode()` to accept and store deadline parameter
- Added `getInvitationDeadline(code)` method
- Implemented in `ControllerAdminHome.performInvitation()`

#### AD-2: Reset Password with OTP
- Added `updatePassword(username, newPassword)` method
- Added `setOneTimePassword(username, otp)` method
- Implemented in `ControllerAdminHome.setOnetimePassword()`
- Generates 6-digit random OTP and displays it to admin

#### AD-3: Delete User with Confirmation
- Added `deleteUser(username)` method
- Implemented in `ControllerAdminHome.deleteUser()`
- Shows confirmation dialog before deletion
- Prevents deleting the last admin
- Cannot delete own account

#### AD-4: List All Users
- Added `getAllUsers()` method returning List<User>
- Implemented in `ControllerAdminHome.listUsers()`
- Displays all users with username, roles, and email

#### AD-5: Ensure At Least One Admin Exists
- Added `countAdmins()` method
- Implemented checks in `ControllerAddRemoveRoles.performRemoveRole()`
- Prevents removing Admin role from last admin user

### 4. Validation Integration

Validation is integrated into all appropriate controllers:

- **ControllerFirstAdmin.doSetupAdmin()**: Validates username and password
- **ControllerUserLogin.doLogin()**: Validates username and password
- **ControllerNewAccount.doCreateUser()**: Validates username and password
- **ControllerUserUpdate.doSave()**: Validates names and email
- **ControllerAdminHome.invalidEmailAddress()**: Uses EmailValidator

### 5. JUnit Tests (`src/test/validation/`)

#### UsernameValidatorTest.java
12 test cases covering:
- Valid username scenarios
- Start with letter requirement
- Space rejection
- Length constraints (min 6, max 32)
- Special character validation
- Whitespace trimming

#### PasswordValidatorTest.java
12 test cases covering:
- Valid password scenarios
- Uppercase requirement
- Lowercase requirement
- Digit requirement
- Special character requirement
- Length constraints (min 8, max 16)
- Whitespace trimming

### 6. Module Configuration
Updated `module-info.java` to export the validation package:
```java
module FoundationsF25 {
    requires javafx.controls;
    requires java.sql;
    
    opens applicationMain to javafx.graphics, javafx.fxml;
    exports validation;
}
```

## Technical Details

### Validation Rules (Per README)
- All validators trim whitespace before validation
- Return empty string ("") if valid
- Return specific error message if invalid
- Follow exact error message format from README

### GUI Implementation
- All GUI packages follow singleton pattern
- Views handle UI layout and component setup
- Controllers handle business logic and validation
- Models are currently stub classes for future expansion

### Database Changes
- InvitationCodes table now has deadline column
- New methods for admin operations
- Maintains backward compatibility with existing code

## Testing

### Manual Testing Performed
Validators were tested with sample inputs:
- ✅ UsernameValidator correctly validates all test cases
- ✅ PasswordValidator correctly validates all test cases
- ✅ NameValidator correctly validates all test cases
- ✅ EmailValidator correctly validates all test cases

### Compilation Status
- ✅ All validation classes compile successfully
- ✅ All entity classes compile successfully
- ✅ Database class compiles successfully
- ⚠️ GUI classes require JavaFX in classpath (IDE-based compilation)

## Files Added/Modified

### New Files (39 total)
- 4 validation classes
- 2 test classes
- 29 GUI package files (10 packages × 2-3 files each)
- 1 .gitignore
- 1 module-info.java (modified)
- 1 Database.java (modified)

### Modified Files
- `src/database/Database.java` - Added admin methods and deadline support
- `src/module-info.java` - Added validation export

## Notes

1. **Lambda Syntax**: Fixed underscore (`_`) lambda parameters to use named parameter (`e`) for Java 9+ compatibility
2. **Build Artifacts**: Added .gitignore to exclude compiled classes and build artifacts
3. **JavaFX Dependency**: Application requires JavaFX to be configured in the IDE
4. **JUnit Dependency**: Test execution requires JUnit 5 to be configured in the IDE

## Compliance with Requirements

✅ All missing GUI packages added  
✅ Input validation per README requirements implemented  
✅ All 5 admin stub features (AD-1 through AD-5) implemented  
✅ Validation integrated into all appropriate controllers  
✅ JUnit test classes created with all required test cases  
✅ module-info.java updated to export validation  
✅ Code follows existing MVC pattern and conventions  

## Next Steps (if needed)

1. Configure JavaFX in IDE for full compilation
2. Configure JUnit 5 in IDE to run unit tests
3. Test application end-to-end in GUI environment
4. Add additional test cases if needed for edge cases
