🛡️ Talent Guard

> **Transformando a comunicação corporativa com Inteligência Artificial.**

![Java](https://img.shields.io/badge/Java-21-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-6DB33F?style=for-the-badge&logo=spring&logoColor=white)
![React](https://img.shields.io/badge/React-20232A?style=for-the-badge&logo=react&logoColor=61DAFB)
![Ollama](https://img.shields.io/badge/AI-Local_LLM-blue?style=for-the-badge)

## 📋 Sobre o Projeto

O **Talent Guard** é uma plataforma Full Stack desenvolvida para promover a **segurança psicológica** e o profissionalismo no ambiente de trabalho.

Utilizando **Inteligência Artificial Local (LLM)**, a aplicação atua como um intermediário em avaliações de desempenho e feedbacks. Ela intercepta mensagens informais ou potencialmente tóxicas e as reescreve automaticamente para um padrão corporativo construtivo, garantindo que a comunicação seja sempre respeitosa e eficiente.

## 🚀 Tecnologias Utilizadas

### Backend
- **Java 21**
- **Spring Boot 3**
- **Spring AI** (Integração com LLMs)
- **Maven** (Gerenciamento de dependências)

### Frontend
- **React.js** (Vite)
- **JavaScript/TypeScript**
- **Axios** (Integração com API)

### Inteligência Artificial
- **Ollama** (Rodando localmente)
- **Llama 3 / Mistral** (Modelos de linguagem)

---

## ⚙️ Funcionalidades Principais

- **🛡️ Refatoração de Texto:** Transforma feedbacks brutos (ex: "Isso ficou horrível") em orientações profissionais (ex: "O projeto poderia se beneficiar de uma revisão nos pontos X e Y para alinhar com os padrões de qualidade").
- **🔒 Privacidade Total:** Como a IA roda localmente via Ollama, nenhum dado sensível da empresa sai do servidor.
- **⚡ Processamento em Tempo Real:** Integração rápida entre o front em React e o back em Spring.

---

## 📦 Como Rodar o Projeto

### Pré-requisitos
- Java 21 instalado.
- Node.js instalado.
- [Ollama](https://ollama.com/) instalado e rodando.

### 1. Configurando a IA (Ollama)
Certifique-se de que o Ollama está rodando e baixou o modelo necessário:
```bash
ollama run llama3
# ou
ollama run mistral

2. Rodando o Backend (API)
Bash

cd talent-guard
./mvnw spring-boot:run

O servidor iniciará em http://localhost:8080
3. Rodando o Frontend (Web)
Bash

cd front-talent-guard
npm install
npm run dev

Acesse a aplicação em http://localhost:5173

Autor

Desenvolvido por Diego Cortez Estudante de Tecnologia emSistemas para Internet| Desenvolvedor Full Stack
🤝 Autor

Desenvolvido por Diego Cortez Estudante de Segurança da Informação | Desenvolvedor Full Stack
