import { useState, useEffect } from "react";
import "./App.css";

interface User {
  id: number;
  name: string;
  email: string;
}

function App() {
  const [users, setUsers] = useState<User[]>([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);
  const [newUser, setNewUser] = useState({ name: "", email: "" });

  useEffect(() => {
    fetchUsers();
  }, []);

  const fetchUsers = async () => {
    try {
      setLoading(true);
      const response = await fetch("/api/users");
      if (!response.ok) {
        throw new Error(`HTTP error! Status: ${response.status}`);
      }
      const data = await response.json();
      setUsers(data);
      setError(null);
    } catch (err) {
      setError("Failed to fetch users. Using mock data instead.");
      // Mock data for demonstration
      setUsers([
        { id: 1, name: "John Doe", email: "john@example.com" },
        { id: 2, name: "Jane Smith", email: "jane@example.com" },
      ]);
    } finally {
      setLoading(false);
    }
  };

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    try {
      const response = await fetch("/api/users", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify(newUser),
      });

      if (!response.ok) {
        throw new Error(`HTTP error! Status: ${response.status}`);
      }

      const createdUser = await response.json();
      setUsers([...users, createdUser]);
      setNewUser({ name: "", email: "" });
    } catch (err) {
      setError("Failed to create user. Please try again.");
      // For demo purposes, simulate a successful creation
      const mockUser = {
        id: users.length + 1,
        ...newUser,
      };
      setUsers([...users, mockUser]);
      setNewUser({ name: "", email: "" });
    }
  };

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const { name, value } = e.target;
    setNewUser({ ...newUser, [name]: value });
  };

  return (
    <div className="app">
      <header>
        <h1>React + Kotlin Demo</h1>
        <p>A simple user management application</p>
      </header>

      <main>
        <section className="form-section">
          <h2>Add New User</h2>
          {error && <div className="error">{error}</div>}
          <form onSubmit={handleSubmit}>
            <div className="form-group">
              <label htmlFor="name">Name:</label>
              <input
                type="text"
                id="name"
                name="name"
                value={newUser.name}
                onChange={handleChange}
                required
              />
            </div>
            <div className="form-group">
              <label htmlFor="email">Email:</label>
              <input
                type="email"
                id="email"
                name="email"
                value={newUser.email}
                onChange={handleChange}
                required
              />
            </div>
            <button type="submit">Add User</button>
          </form>
        </section>

        <section className="users-section">
          <h2>Users</h2>
          {loading ? (
            <p>Loading users...</p>
          ) : (
            <table>
              <thead>
                <tr>
                  <th>ID</th>
                  <th>Name</th>
                  <th>Email</th>
                </tr>
              </thead>
              <tbody>
                {users.map((user) => (
                  <tr key={user.id}>
                    <td>{user.id}</td>
                    <td>{user.name}</td>
                    <td>{user.email}</td>
                  </tr>
                ))}
              </tbody>
            </table>
          )}
        </section>
      </main>

      <footer>
        <p>&copy; {new Date().getFullYear()} React + Kotlin Demo</p>
      </footer>
    </div>
  );
}

export default App;
