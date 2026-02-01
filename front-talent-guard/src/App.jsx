import { useState } from 'react'
import axios from 'axios'
import './App.css'

function App() {
  // Estados para armazenar dados
  const [token, setToken] = useState(localStorage.getItem('token'))
  const [feedback, setFeedback] = useState('')
  const [resultado, setResultado] = useState('')
  const [loading, setLoading] = useState(false)

  // Dados do formulário de registro
  const [form, setForm] = useState({
    firstname: '', lastname: '', email: '', password: ''
  })

  // Função para lidar com o Registro/Login
  const handleRegister = async (e) => {
    e.preventDefault()
    try {
      // Nota: Estamos usando o Register para pegar o token direto.
      // Em produção, você teria uma rota de /authenticate separada.
      const response = await axios.post('http://localhost:8080/api/auth/register', form)
      
      const newToken = response.data.token
      setToken(newToken)
      localStorage.setItem('token', newToken) // Salva no navegador
      alert("Bem-vindo ao Talent Guard!")
    } catch (error) {
      console.error(error)
      alert("Erro ao registrar. Verifique o console ou se o servidor Java está rodando.")
    }
  }

  // Função para chamar a IA
  const handleRefine = async () => {
    if (!feedback) return
    setLoading(true)
    setResultado('')

    try {
      const response = await axios.post(
        'http://localhost:8080/api/hr/refine',
        { feedback: feedback },
        {
          headers: { Authorization: `Bearer ${token}` } // Envia o Token
        }
      )
      setResultado(response.data.refinado)
    } catch (error) {
      console.error(error)
      if (error.response && error.response.status === 403) {
        alert("Sessão expirada. Faça login novamente.")
        logout()
      } else {
        alert("Erro ao contatar a IA.")
      }
    } finally {
      setLoading(false)
    }
  }

  const logout = () => {
    setToken(null)
    localStorage.removeItem('token')
    setResultado('')
    setFeedback('')
  }

  // --- TELA DE LOGIN/REGISTRO ---
  if (!token) {
    return (
      <div className="container">
        <h1>🔐 Talent Guard Access</h1>
        <form onSubmit={handleRegister} className="card">
          <input type="text" placeholder="Nome" onChange={e => setForm({...form, firstname: e.target.value})} />
          <input type="text" placeholder="Sobrenome" onChange={e => setForm({...form, lastname: e.target.value})} />
          <input type="email" placeholder="Email" onChange={e => setForm({...form, email: e.target.value})} />
          <input type="password" placeholder="Senha" onChange={e => setForm({...form, password: e.target.value})} />
          <button type="submit">Acessar Sistema</button>
        </form>
      </div>
    )
  }

  // --- TELA DA IA (SÓ APARECE SE TIVER TOKEN) ---
  return (
    <div className="container">
      <header>
        <h1>🤖 Talent Guard AI</h1>
        <button onClick={logout} className="outline">Sair</button>
      </header>
      
      <div className="main-content">
        <textarea 
          value={feedback}
          onChange={(e) => setFeedback(e.target.value)}
          placeholder="Digite o feedback 'tóxico' aqui... (Ex: Ele é muito preguiçoso)"
          rows="5"
        />
        
        <button onClick={handleRefine} disabled={loading}>
          {loading ? 'Refinando...' : '✨ Refinar com IA'}
        </button>

        {resultado && (
          <div className="result-card">
            <h3>Resultado Profissional:</h3>
            <p>{resultado}</p>
          </div>
        )}
      </div>
    </div>
  )
}

export default App