import { useState, useEffect } from 'react'
import './App.css'

function App() {

  const [payload, setPayload] = useState('')
  const [tasks, setTasks] = useState([])
  const [error, setError] = useState(null)

  // CREATE TASK
  const handleCreateTask = async (e) => {

    e.preventDefault()

    if (!payload.trim()) return

    setError(null)

    try {

      const res = await fetch('http://localhost:8081/tasks', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json'
        },
        body: JSON.stringify({
          payload
        })
      })

      if (!res.ok) {
        throw new Error(`Request failed: ${res.status}`)
      }

      const data = await res.json()

      console.log('Task created:', data)

      setPayload('')

    } catch (err) {

      console.error(err)

      setError(err.message)

    }
  }

  // FETCH ALL TASKS
  useEffect(() => {

    const fetchTasks = async () => {

      try {

        const res = await fetch('http://localhost:8080/tasks')

        const data = await res.json()

        // Latest task on top
        setTasks(data.reverse())

      } catch (err) {

        console.error(err)

      }
    }

    // Initial fetch
    fetchTasks()

    // Poll every 3 seconds
    const interval = setInterval(fetchTasks, 3000)

    return () => clearInterval(interval)

  }, [])

  return (
    <div className="app">

      <div className="container">

        <header className="header">
          <h1>Task System</h1>
          <p>Submit and track your tasks in real-time</p>
        </header>

        {/* FORM */}
        <form onSubmit={handleCreateTask} className="form">

          <input
            type="text"
            value={payload}
            onChange={(e) => setPayload(e.target.value)}
            placeholder="Enter task description"
          />

          <button
            type="submit"
            disabled={!payload.trim()}
          >
            Create Task
          </button>

        </form>

        {/* ERROR */}
        {error && (
          <div className="error">
            Error: {error}
          </div>
        )}

        {/* TASK LIST */}
        <div className="tasks-list">

          {tasks.length === 0 ? (

            <p className="empty">
              No tasks yet. Submit one above!
            </p>

          ) : (

            tasks.map(task => (

              <div
                key={task.id}
                className="task-card"
              >

                <div className="task-header">

                  <span className="task-icon">

                    {task.status === 'PENDING' && '⏳'}
                    {task.status === 'PROCESSING' && '⚙️'}
                    {task.status === 'COMPLETED' && '✅'}
                    {task.status === 'FAILED' && '❌'}

                  </span>

                  <span className="task-payload">
                    {task.payload}
                  </span>

                </div>

                <div className="task-details">

                  <div className="task-row">

                    <span className="label">
                      Status
                    </span>

                    <span
                      className={`status status-${task.status.toLowerCase()}`}
                    >
                      {task.status}
                    </span>

                  </div>

                  <div className="task-row">

                    <span className="label">
                      Worker
                    </span>

                    <span>
                      {task.workerPort
                        ? `Worker ${task.workerPort}`
                        : 'Waiting...'}
                    </span>

                  </div>

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