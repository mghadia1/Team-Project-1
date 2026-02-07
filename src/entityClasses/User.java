package entityClasses;

public class User {
	
    private String userName;
    private String password;
    private String firstName;
    private String middleName;
    private String lastName;
    private String preferredFirstName;
    private String emailAddress;
    private boolean adminRole;
    private boolean role1;
    private boolean role2;
    
    public User() {
    }

    public User(String userName, String password, String fn, String mn, String ln, String pfn, 
			String ea, boolean r1, boolean r2, boolean r3) {
        this.userName = userName;
        this.password = password;
        this.firstName = fn;
        this.middleName = mn;
        this.lastName = ln;
        this.preferredFirstName = pfn;
        this.emailAddress = ea;
        this.adminRole = r1;
        this.role1 = r2;
        this.role2 = r3;
    }

    public void setAdminRole(boolean role) { this.adminRole=role; }
    public void setRole1User(boolean role) { this.role1=role; }
    public void setRole2User(boolean role) { this.role2=role; }

    public String getUserName() { return userName; }
    public String getPassword() { return password; }
    public String getFirstName() { return firstName; }
    public String getMiddleName() { return middleName; }
    public String getLastName() { return lastName; }
    public String getPreferredFirstName() { return preferredFirstName; }
    public String getEmailAddress() { return emailAddress; }

    public void setUserName(String s) { userName = s; }
    public void setPassword(String s) { password = s; }
    public void setFirstName(String s) { firstName = s; }
    public void setMiddleName(String s) { middleName = s; }
    public void setLastName(String s) { lastName = s; }
    public void setPreferredFirstName(String s) { preferredFirstName = s; }
    public void setEmailAddress(String s) { emailAddress = s; }

    public boolean getAdminRole() { return adminRole; }
    public boolean getNewRole1() { return role1; }
    public boolean getNewRole2() { return role2; }

    public int getNumRoles() {
     int numRoles = 0;
     if (adminRole) numRoles++;
     if (role1) numRoles++;
     if (role2) numRoles++;
     return numRoles;
    }
}