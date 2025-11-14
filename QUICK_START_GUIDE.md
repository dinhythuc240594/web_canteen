# Web Canteen - Quick Start Guide

## Getting Started

This guide will help you understand how to use the web canteen system after all implementations.

---

## User Roles & Access

### 1. **Customer** (Students/Staff)
**Login:** Regular user account with role = 'customer'

**Can Access:**
- Browse stalls and food menus
- Add items to cart
- Place orders
- View order history
- Track order status

**URLs:**
- `/foods?action=list` - Browse food menu
- `/cart` - View and manage cart
- `/order-history` - View order history
- `/store` - Browse stalls

---

### 2. **Stall User** (Stall Manager)
**Login:** User account with role = 'stall'

**Can Access:**
- All customer features
- Manage stall menu (CRUD operations)
- View and update order status
- View stall statistics

**URLs:**
- `/foods?action=create` - Add new food item
- `/foods?action=update&id=X` - Edit food item
- `/stall-orders` - Manage orders
- `/stall-orders?status=new_order` - View new orders only
- `/statistics` - View stall statistics

**Order Management:**
1. Go to `/stall-orders`
2. See all orders for your stall
3. Click status buttons to update:
   - "Xác nhận" - Confirm new order
   - "Bắt đầu giao" - Start delivery
   - "Hoàn thành" - Mark as delivered

---

### 3. **Admin** (Administrator)
**Login:** User account with role = 'admin'

**Can Access:**
- All system features
- User management
- Stall management
- System-wide statistics
- All orders from all stalls

**URLs:**
- `/admin` - Admin dashboard
- `/admin#users` - User management
- `/admin#stalls` - Stall management
- `/admin#orders` - All orders
- `/admin#reports` - Reports
- `/statistics` - Comprehensive statistics

**Admin Dashboard Features:**
- View total users, stalls, revenue, and orders
- Manage users (create, update, block, unblock)
- Manage stalls (create, update, delete)
- View system statistics

---

## Common Workflows

### Customer: Placing an Order

1. **Browse Menu**
   ```
   Go to: /foods?action=list
   Or: /store → select a stall → view menu
   ```

2. **Add to Cart**
   ```
   Click "Add to Cart" on food items
   Adjust quantity as needed
   ```

3. **Checkout**
   ```
   Go to: /cart
   Review items
   Enter delivery location
   Select payment method
   Click "Place Order"
   ```

4. **Track Order**
   ```
   Go to: /order-history
   View order status:
   - Đơn mới (New)
   - Đã xác nhận (Confirmed)
   - Đang giao (In Delivery)
   - Đã giao (Delivered)
   ```

---

### Stall User: Managing Orders

1. **View New Orders**
   ```
   Go to: /stall-orders?status=new_order
   ```

2. **Confirm Order**
   ```
   Click "Xác nhận" button
   Status changes to "Đã xác nhận"
   ```

3. **Start Delivery**
   ```
   Click "Bắt đầu giao" button
   Status changes to "Đang giao"
   ```

4. **Complete Order**
   ```
   Click "Hoàn thành" button
   Status changes to "Đã giao"
   ```

---

### Stall User: Managing Menu

1. **Add New Food**
   ```
   Go to: /foods?action=create
   Fill in:
   - Name
   - Price
   - Inventory
   - Category
   - Description
   Click "Create"
   ```

2. **Update Food**
   ```
   Go to: /foods?action=list
   Find item → Click "Edit"
   Update information
   Click "Update"
   ```

3. **Delete Food**
   ```
   Go to: /foods?action=list
   Find item → Click "Delete"
   Confirm deletion
   ```

---

### Admin: Managing Users

1. **View All Users**
   ```
   Go to: /admin
   Click "Người dùng" in sidebar
   Or go to: /admin#users
   ```

2. **Block/Unblock User**
   ```
   In user list, click the lock/unlock icon
   Confirm action
   User status toggles between active/blocked
   ```

3. **View User by Role**
   ```
   Use filter dropdown to select:
   - All roles
   - Customer
   - Stall
   - Admin
   ```

---

### Admin: Managing Stalls

1. **View All Stalls**
   ```
   Go to: /admin#stalls
   ```

2. **View Stall Details**
   ```
   In stall list, click stall name
   View details:
   - Manager
   - Status (Open/Closed)
   - Description
   ```

---

### Admin: Viewing Statistics

1. **Dashboard Overview**
   ```
   Go to: /admin
   View:
   - Today's revenue
   - Today's orders
   - Total users
   - Total stalls
   ```

2. **Detailed Reports**
   ```
   Go to: /statistics
   Select date range
   View:
   - Best selling items
   - Revenue by stall
   - Daily order counts
   ```

3. **Best Selling Items**
   ```
   Go to: /statistics?action=bestSelling
   Set date range parameters:
   &startDate=2025-11-01&endDate=2025-11-14
   ```

4. **Revenue by Stall**
   ```
   Go to: /statistics?action=revenue
   Set date range parameters
   ```

---

## URL Reference

### Public URLs
- `/` - Home page
- `/login` - Login page
- `/register` - Registration page
- `/store` - Browse stalls
- `/foods?action=list` - Browse food menu

### Customer URLs (Login Required)
- `/cart` - Shopping cart
- `/order-history` - Order history
- `/order-success` - Order confirmation page

### Stall URLs (Stall Role Required)
- `/stall-orders` - Manage orders
- `/foods?action=create` - Add food
- `/foods?action=update&id=X` - Edit food
- `/foods?action=delete&id=X` - Delete food
- `/statistics` - View statistics

### Admin URLs (Admin Role Required)
- `/admin` - Admin dashboard
- `/admin?action=users` - User list (JSON)
- `/admin?action=stalls` - Stall list (JSON)
- `/admin?action=statistics` - Statistics (JSON)

---

## Filter & Search Examples

### Filter Orders by Status
```
/stall-orders?status=new_order
/stall-orders?status=confirmed
/stall-orders?status=in_delivery
/stall-orders?status=delivered
/stall-orders?status=all
```

### Filter Statistics by Date
```
/statistics?action=overview&startDate=2025-11-01&endDate=2025-11-14
/statistics?action=byStall&stallId=1&startDate=2025-11-01&endDate=2025-11-14
```

### Food Pagination & Sorting
```
/foods?action=list&page=1&pageSize=25&sortField=name&orderField=ASC
/foods?action=list&keyword=pho
```

---

## AJAX API Endpoints

### Update Order Status
```javascript
POST /order
Parameters:
- action=updateStatus
- orderId=123
- status=confirmed

Response:
{
  "success": true,
  "message": "Order status updated successfully"
}
```

### Get Users List
```javascript
GET /admin?action=users

Response:
[
  {
    "id": 1,
    "username": "user1",
    "email": "user1@example.com",
    "role": "customer",
    "status": true
  },
  ...
]
```

### Toggle User Status
```javascript
POST /admin
Parameters:
- action=toggleUserStatus
- id=1
- status=false

Response:
{
  "success": true,
  "message": "User status updated successfully"
}
```

### Get Statistics
```javascript
GET /statistics?action=bestSelling&startDate=2025-11-01&endDate=2025-11-14

Response:
[
  {
    "foodId": 1,
    "quantitySold": 156,
    "revenue": 1560000
  },
  ...
]
```

---

## Order Status Flow

```
Customer places order
        ↓
    [new_order] ← Stall sees new order
        ↓
Stall clicks "Xác nhận"
        ↓
    [confirmed] ← Order confirmed
        ↓
Stall clicks "Bắt đầu giao"
        ↓
   [in_delivery] ← Order in delivery
        ↓
Stall clicks "Hoàn thành"
        ↓
    [delivered] ← Order completed
```

---

## Default Credentials

**Note:** These are example credentials. Update based on your `insert_data.sql`

### Admin Account
```
Username: admin
Password: admin123
Role: admin
```

### Stall Account
```
Username: stall1
Password: stall123
Role: stall
```

### Customer Account
```
Username: customer1
Password: customer123
Role: customer
```

**⚠️ Important:** Change default passwords after first login!

---

## Troubleshooting

### Cannot Access Admin Panel
- Check if logged in as admin role
- Verify session is active
- URL should be `/admin` not `/admin.jsp`

### Cannot Update Order Status
- Check if logged in as stall or admin
- Verify you're managing your own stall's orders
- Check if order already at final status

### Statistics Not Showing
- Verify database has statistics data
- Check date range parameters
- Ensure statistics table is populated

### Food Not Appearing in Menu
- Check `is_available` status
- Verify `inventory > 0`
- Check if stall is open

---

## Support & Documentation

For more detailed information, see:
- `IMPLEMENTATION_SUMMARY.md` - Complete implementation details
- `SESSION_SECURITY_GUIDE.md` - Security features
- `ORDER_HISTORY_IMPLEMENTATION.md` - Order system details

---

**Last Updated:** November 14, 2025

