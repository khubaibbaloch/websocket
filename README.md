# 💬 Ktor Chat Backend

A real-time chat backend built with **Ktor** (HTTP + WebSockets), using **PostgreSQL**, **Exposed ORM**, and **HikariCP**. Features include user signup/login, presence tracking, messaging with delivery and seen statuses.

---

## 🚀 Features

- 🔐 User signup and login  
- 🟢 Real-time status (online/offline) updates  
- 💬 Live messaging via WebSockets  
- ✅ Delivery & seen message states  
- 🗃️ PostgreSQL storage with Exposed ORM  
- 🛡️ Connection pooling with HikariCP  
- 📦 JSON serialization via kotlinx  

---

## 🛠️ Tech Stack

- Kotlin + Ktor  
- Ktor WebSockets  
- PostgreSQL  
- Exposed ORM  
- HikariCP  
- kotlinx.serialization  

---

## 📁 Project Layout

```
├── com.example.config     # Configuration (DB, WebSockets, Serialization)
├── dao                    # Data access objects (UserDao, MessageDao)
├── models                 # Request and response models
├── routes                 # REST and WebSocket route definitions
├── tables                 # Exposed table schemas
├── Application.kt         # App entry point
├── build.gradle.kts       # Build configuration
└── README.md              # Project documentation
```

---

## ⚙️ Setup Guide

### 🧑‍💻 Clone or Download

```bash
git clone https://github.com/your-username/ktor-chat-backend.git
cd ktor-chat-backend
```

Or download:

👉 [Download as ZIP](https://github.com/your-username/ktor-chat-backend/archive/refs/heads/main.zip)

*(After hosting on GitHub, update the above link accordingly.)*

---

### 🐘 Database Setup

1. Create a PostgreSQL database named `ktor_chat`.
2. Update credentials in `configureDatabase()` if needed (default user/password are included).

---

### ▶️ Run the Server

```bash
./gradlew run
```

Server starts at:  
`http://localhost:8080`

---

## 🔌 Important Endpoints

- **HTTP**
  - `POST /signup` – Register user  
  - `POST /login` – Authenticate user  
  - `POST /user/status` – Update presence  
  - `POST /message/send` – Send a message  
  - `POST /message/seen` – Mark as seen  
  - `GET /messages/{sender}/{receiver}` – Fetch message history  
  - `GET /user/status/{userId}` – Check online status  
  - `GET /json/kotlinx-serialization` – Test JSON serialization

- **WebSocket**
  - Connect via `/ws/{userId}` for real-time messaging and pending delivery

---

## 👥 Download Links

- 👉 **Download ZIP**:  
  https://github.com/your-username/ktor-chat-backend/archive/refs/heads/main.zip  

- Or clone with:  
  ```bash
  git clone https://github.com/your-username/ktor-chat-backend.git
  ```

*(Be sure to update the above with your actual repo URL once available.)*

---

## 🧪 Testing / Validation

After running the server:

1. Use WebSocket tools (e.g., [PieSocket WebSocket Tester])  
2. Or test endpoints via Postman / curl

---

## ✅ What’s Next?

- ✅ Upload to GitHub and replace the download link  
- 🔄 (Optional) Add a Postman collection  
- 🌐 Deploy to a cloud platform like Heroku or Railway  

---

## 🙂 Author

**Khubaib**

---

## 📜 License

MIT License – free to use, modify, and share.