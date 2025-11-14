# Session Security & Authentication Guide

## Overview
This document describes the comprehensive session management and authentication security implemented across the Canteen Web Application.

## Session Variables

### Core Session Attributes
All authenticated users have the following session attributes set:

```java
session.setAttribute("is_login", true);           // Boolean - login status
session.setAttribute("userId", user.getId());      // Integer - user ID
session.setAttribute("username", user.getUsername()); // String - username
session.setAttribute("type_user", user.getRole()); // String - user role (admin/stall/customer)
```

### User Roles
- **admin**: Full system access, can manage all resources
- **stall**: Can manage their stall's food items and view orders
- **customer**: Can order food and view their own order history

## Authentication Points

### 1. Login Process (`LoginServerlet`)
**Location**: `src/main/java/controller/LoginServerlet.java`

**Security Measures**:
- Username and password validation
- Session creation with user details
- Remember-me token generation (optional)
- Secure cookie handling with HttpOnly flag

**Session Setup**:
```java
session.setAttribute("is_login", isLogin);
session.setAttribute("userId", user.getId());
session.setAttribute("username", user.getUsername());
session.setAttribute("type_user", user.getRole());
```

### 2. Remember-Me Authentication (`AuthFilter`)
**Location**: `src/main/java/filter/AuthFilter.java`

**Security Measures**:
- SHA-256 hashed tokens
- Token rotation on each use
- Expiration checking
- Automatic session recreation from valid tokens

**Process**:
1. Read token from cookie
2. Hash and validate against database
3. Check expiration
4. Create new session if valid
5. Generate new token and update cookie

### 3. Logout Process (`LogoutServerlet`)
**Location**: `src/main/java/controller/LogoutServerlet.java`

**Security Measures**:
- Complete session invalidation
- Cookie cleanup
- Redirect to home page

## Protected Resources

### Servlet-Level Protection

#### 1. CartServerlet (`/cart`)
**Authentication**: Required  
**Authorization**: All logged-in users

```java
HttpSession session = request.getSession(false);
if (session == null || session.getAttribute("userId") == null) {
    response.sendRedirect(request.getContextPath() + "/login");
    return;
}
```

**Protected Operations**:
- View cart (GET)
- Add items to cart (POST)
- Remove items from cart (POST)
- Checkout process (POST)

#### 2. OrderServerlet (`/order`)
**Authentication**: Required  
**Authorization**: All logged-in users

```java
HttpSession session = request.getSession(false);
if (session == null || session.getAttribute("userId") == null) {
    response.sendRedirect(request.getContextPath() + "/login");
    return;
}
```

**Protected Operations**:
- View order list
- View order details

#### 3. OrderHistoryServerlet (`/order-history`)
**Authentication**: Required  
**Authorization**: Role-based

```java
HttpSession session = request.getSession(false);
if (session == null || session.getAttribute("userId") == null) {
    response.sendRedirect(request.getContextPath() + "/login");
    return;
}
```

**Role-Based Access**:
- **Admin**: Views all orders from all users and stalls
- **Stall**: Views orders for their managed stall only
- **Customer**: Views their own order history only

#### 4. FoodServerlet (`/foods`)
**Authentication**: Required for sensitive operations  
**Authorization**: Admin and Stall only for create/update/delete

```java
// For create, update, delete actions only
if ("create".equals(action) || "update".equals(action) || "delete".equals(action)) {
    if (userId == null) {
        response.sendRedirect(request.getContextPath() + "/login");
        return;
    }
    if (!"admin".equals(userRole) && !"stall".equals(userRole)) {
        response.sendRedirect(request.getContextPath() + "/home");
        return;
    }
}
```

**Public Operations**:
- List foods
- View food details

**Protected Operations** (Admin/Stall only):
- Create food item
- Update food item
- Delete food item

### JSP-Level Protection

#### 1. cart.jsp
**Security Check**:
```jsp
<%
    Integer userId = (Integer) session.getAttribute("userId");
    if (userId == null) {
        response.sendRedirect(request.getContextPath() + "/login");
        return;
    }
%>
```

#### 2. orders.jsp
**Security Check**:
```jsp
<%
    Integer userId = (Integer) session.getAttribute("userId");
    if (userId == null) {
        response.sendRedirect(request.getContextPath() + "/login");
        return;
    }
%>
```

#### 3. order_history.jsp
**Security Check**:
- Relies on servlet-level authentication
- Displays different content based on user role

#### 4. order-success.jsp
**Security Check**:
```jsp
<%
    Integer userId = (Integer) session.getAttribute("userId");
    if (userId == null) {
        response.sendRedirect(request.getContextPath() + "/login");
        return;
    }
%>
```

#### 5. admin.jsp
**Security Check** (Strictest):
```jsp
<%
    Integer userId = (Integer) session.getAttribute("userId");
    String userRole = (String) session.getAttribute("type_user");
    
    if (userId == null) {
        response.sendRedirect(request.getContextPath() + "/login");
        return;
    }
    
    if (!"admin".equals(userRole)) {
        response.sendRedirect(request.getContextPath() + "/home");
        return;
    }
%>
```

## Security Best Practices Implemented

### 1. Session Management
✅ Use `request.getSession(false)` for authentication checks  
✅ Never create sessions automatically for unauthenticated requests  
✅ Invalidate sessions completely on logout  
✅ Set proper session timeout values  

### 2. Authorization Checks
✅ Check both authentication (logged in) and authorization (correct role)  
✅ Implement role-based access control (RBAC)  
✅ Validate on both servlet and JSP levels (defense in depth)  
✅ Redirect unauthorized users appropriately  

### 3. Cookie Security
✅ HttpOnly flag to prevent XSS attacks  
✅ Secure flag for HTTPS connections  
✅ Path-specific cookies  
✅ Token-based instead of storing credentials  

### 4. Token Management
✅ SHA-256 hashing for tokens  
✅ Token rotation after each use  
✅ Expiration timestamps  
✅ Secure random token generation (UUID)  

### 5. Error Handling
✅ No sensitive information in error messages  
✅ Appropriate HTTP status codes  
✅ User-friendly error pages  
✅ Graceful degradation  

## Public Resources (No Authentication Required)

- `/home` - Home page (food listing)
- `/login` - Login page
- `/register` - Registration page
- `/category` - Category listing
- `/store` - Store listing
- `/foods?action=list` - Food list view
- `/foods?action=detail` - Food detail view
- Static resources (CSS, JS, images)

## Testing Checklist

### Authentication Tests
- [ ] Test accessing protected pages without login → Should redirect to `/login`
- [ ] Test accessing admin pages as customer → Should redirect to `/home`
- [ ] Test accessing stall operations as customer → Should redirect to `/home`
- [ ] Test remember-me functionality → Should maintain session
- [ ] Test session expiration → Should require re-login
- [ ] Test logout → Should clear all session data

### Authorization Tests
- [ ] Admin can view all orders
- [ ] Stall can view only their stall's orders
- [ ] Customer can view only their own orders
- [ ] Admin can access admin.jsp
- [ ] Stall/Customer cannot access admin.jsp
- [ ] Admin/Stall can create/update/delete foods
- [ ] Customer cannot create/update/delete foods

### Session Tests
- [ ] Test parallel sessions from different browsers
- [ ] Test session hijacking prevention
- [ ] Test token rotation on remember-me
- [ ] Test expired token handling
- [ ] Test concurrent requests with same session

## Common Vulnerabilities Prevented

### 1. Unauthorized Access ✅
**Prevention**: Session validation on all protected resources

### 2. Session Fixation ✅
**Prevention**: New session created on login, token rotation

### 3. Session Hijacking ✅
**Prevention**: HttpOnly cookies, secure token generation

### 4. Privilege Escalation ✅
**Prevention**: Role-based authorization checks

### 5. CSRF (Cross-Site Request Forgery) ⚠️
**Status**: Consider implementing CSRF tokens for state-changing operations

### 6. XSS (Cross-Site Scripting) ⚠️
**Status**: Ensure all user input is properly escaped in JSP pages

## Recommendations for Further Security Enhancements

### High Priority
1. **Implement CSRF Protection**
   - Add CSRF tokens to all forms
   - Validate tokens on POST requests

2. **Add Rate Limiting**
   - Limit login attempts per IP
   - Prevent brute force attacks

3. **Implement Account Lockout**
   - Lock accounts after N failed login attempts
   - Add unlock mechanism

### Medium Priority
4. **Add Password Strength Requirements**
   - Minimum length, complexity rules
   - Password history

5. **Implement Audit Logging**
   - Log all authentication events
   - Log authorization failures
   - Log sensitive operations

6. **Add Session Activity Tracking**
   - Last activity timestamp
   - Idle timeout
   - Concurrent session limits

### Low Priority
7. **Two-Factor Authentication (2FA)**
   - SMS or email-based OTP
   - Authenticator app support

8. **IP Whitelisting for Admin**
   - Restrict admin access to specific IPs
   - Configurable whitelist

## Troubleshooting

### Issue: User keeps getting redirected to login
**Solution**: Check if userId is properly set in session after login

### Issue: Remember-me not working
**Solution**: Check cookie settings, ensure HTTPS for Secure flag

### Issue: Session lost after restart
**Solution**: Sessions are in-memory by default, consider persistent storage

### Issue: Role-based access not working
**Solution**: Verify type_user attribute is set correctly during login

## Code Examples

### Checking Authentication in Servlet
```java
HttpSession session = request.getSession(false);
if (session == null || session.getAttribute("userId") == null) {
    response.sendRedirect(request.getContextPath() + "/login");
    return;
}
```

### Checking Authorization in Servlet
```java
String userRole = (String) session.getAttribute("type_user");
if (!"admin".equals(userRole)) {
    response.sendRedirect(request.getContextPath() + "/home");
    return;
}
```

### Checking Authentication in JSP
```jsp
<%
    Integer userId = (Integer) session.getAttribute("userId");
    if (userId == null) {
        response.sendRedirect(request.getContextPath() + "/login");
        return;
    }
%>
```

### Conditional Rendering Based on Role
```jsp
<%
    String userRole = (String) request.getAttribute("type_user");
%>
<% if ("admin".equals(userRole)) { %>
    <!-- Admin-only content -->
<% } %>
```

## Summary

All critical user operations are now protected with proper session validation and role-based access control. The application implements defense-in-depth with checks at both servlet and JSP levels. Users must be authenticated to access protected resources, and authorization is enforced based on user roles for sensitive operations.

**Last Updated**: November 14, 2025  
**Version**: 1.0

