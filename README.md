# **QuizHub**

QuizHub is a web-based quiz platform that allows administrators to efficiently manage quizzes, categories, questions, and options, while users can participate in quizzes to test their knowledge. The system supports robust role-based access, ensuring a seamless experience for both admins and users.

---

## **Features**

### **Admin Functionalities**
1. **Category Management**:
   - Add new categories for organizing quizzes.
   - Edit existing categories to update their details.
   - Delete categories if they are no longer needed.
   - Disable categories to temporarily hide them from quizzes.

2. **Quiz Management**:
   - Add new quizzes and associate them with specific categories.
   - Edit quiz details, including title, description, and associated category.
   - Delete quizzes that are no longer required.
   - Disable quizzes to make them temporarily unavailable.

3. **Question & Option Management**:
   - Add questions to quizzes with multiple-choice options.
   - Edit question text or options as needed.
   - Delete questions that are no longer relevant.
   - Add, edit, or delete individual options for each question.

---

## **Technology Stack**

- **Backend**: Spring Boot, Spring Security, Spring JPA
- **Frontend**: React.js
- **Database**: MySQL (or any supported relational database)
- **Authentication**: Role-based access control (Admin/User), JWT-based authentication
- **Additional Features**: OTP verification for secure user authentication

---

## **Installation**

1. **Clone the Repository**:
   ```bash
   git clone https://github.com/your-username/quizhub.git
   cd quizhub
