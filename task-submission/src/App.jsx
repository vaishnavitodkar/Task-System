import { useState } from 'react'
import './App.css'

function App() {
  const [payload, setPayload] = useState('')
  const [tasks, setTasks] = useState([])
  const [loading, setLoading] = useState(false)
  const [error, setError] = useState(null)

  const handleCreateTask = async (e) => {
    e.preventDefault()
    if (!payload.trim()) return

    setLoading(true)
    setError(null)

    try {
      const res = await fetch('http://localhost:8081/tasks', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ payload }),
      })

      if (!res.ok) throw new Error(`Request failed: ${res.status}`)

      const data = await res.json()

      setTasks(prev => [{
        id: Date.now(),
        payload: data.payload || payload,
        status: data.status || 'PENDING',
        workerPort: data.workerPort || null,
      }, ...prev])

      setPayload('')
    } catch (err) {
      setError(err.message)
    } finally {
      setLoading(false)
    }
  }

  return (
    <div className="app">
      <div className="container">
        <header className="header">
          <h1>Task System</h1>
          <p>Create and manage your tasks</p>
        </header>

        <form onSubmit={handleCreateTask} className="form">
          <input
            type="text"
            value={payload}
            onChange={(e) => setPayload(e.target.value)}
            placeholder="Enter task payload (e.g., resize image)"
            disabled={loading}
          />
          <button type="submit" disabled={loading || !payload.trim()}>
            {loading ? <span className="spinner" /> : 'Create Task'}
          </button>
        </form>

        {error && (
          <div className="error">
            <span>Error: {error}</span>
          </div>
        )}

        <div className="tasks-list">
          {tasks.length === 0 ? (
            <p className="empty">No tasks yet. Create one above!</p>
          ) : (
            tasks.map(task => (
              <div key={task.id} className="task-card">
                <div className="task-row">
                  <span className="label">Task:</span>
                  <span className="value">{task.payload}</span>
                </div>
                <div className="task-row">
                  <span className="label">Status:</span>
                  <span className={`status status-${task.status.toLowerCase()}`}>
                    {task.status}
                  </span>
                </div>
                <div className="task-row">
                  <span className="label">Handled by:</span>
                  <span className="value">
                    {task.workerPort ? `Worker ${task.workerPort}` : 'Pending...'}
                  </span>
                </div>
              </div>
            ))
          )}
        </div>
      </div>
    </div>
  )
}

export default App