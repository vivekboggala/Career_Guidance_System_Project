# 🎯 AI-Based Career Guidance System

## 📌 Project Overview
This is a simple **AI-Based Career Guidance System** designed using **HTML, CSS, JavaScript (Frontend), SQL (Database), and Java (Functionality, without Servlets)**.  
The project helps users choose suitable career paths based on their inputs.

## 🛠️ Tech Stack

| Component  | Technology Used                    |
|------------|------------------------------------|
| Frontend   | HTML, CSS, JavaScript              |
| Backend    | Java (without servlets)            |
| Database   | MySQL                              |
| Tools      | VS Code, Postman (for API testing) |

## 📂 Folder Structure

```
Career_Guidance_System_Project/
│── backend/  # Backend Logic (Java)
│   │── src/
│   │   │── Main.java  # Starts HTTP server
│   │   │── DBHelper.java  # Manages MySQL Connection
│   │   │── CareerLogic.java  # Processes career suggestions
│   │── lib/  # External JAR files (Gson, MySQL Connector)
│   │── config/  # Configuration files (if needed)
│   │── bin/  # Compiled Java classes
│
│── database/
│   │── career_db.sql  # Database Schema & Sample Data
│
│── frontend/  # Frontend UI
│   │── index.html  # Main Web Page
│   │── script.js  # Handles API Requests
│   │── style.css  # Styling
│
│── README.md  # Project Documentation
```


## ✨ Features
- Simple and user-friendly UI
- Database connectivity using SQL
- Career recommendations based on user inputs
- Lightweight Java-based backend (without Servlets)

## 🚀 Technologies Used
- **Frontend**: HTML, CSS, JavaScript
- **Backend**: Java (Core Java without Servlets)
- **Database**: SQL
- **Development Tool**: VS Code

## ⚙️ Setup Instructions
1. Import `career_db.sql` into your SQL database.
2. Configure the database connection in `DBHelper.java`.
3. Run `Main.java` to start the backend.
4. Open `index.html` in a browser.

## 🔮 Future Enhancements
- Improve AI-based recommendations
- Add authentication system
- Enhance UI/UX with better design

## 📜 License
This project is open-source under the **MIT License**.  
See the [LICENSE](./LICENSE) file for more details.  
